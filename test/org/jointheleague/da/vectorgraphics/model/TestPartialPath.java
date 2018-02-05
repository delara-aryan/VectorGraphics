package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import org.junit.jupiter.api.Test;

class TestPartialPath {

	@Test
	void testConstructor() {
		Path2D path = new Path2D.Double();
		PartialPath partialPath = new PartialPath(path.getPathIterator(null));
		assertNotNull(partialPath.getPath());
	}
	
	@Test
	void testSetSpeed() {
		Path2D path = new Path2D.Double();
		PartialPath partialPath = new PartialPath(path.getPathIterator(null));
		partialPath.setSpeed(2.0);
		assertEquals(2.0, partialPath.getSpeed());
	}
	
	@Test
	void testGetCurrentSegment() {
		Path2D path = new Path2D.Double();
		path.moveTo(0, 0);
		path.lineTo(1, 1);
		path.quadTo(2, 3, 4, 5);
		path.curveTo(1, 2, 3, 4, 5, 6);
		path.closePath();
		PathIterator pi = path.getPathIterator(null);
		PartialPath partialPath = new PartialPath(pi);
		assertTrue(partialPath.getCurrentSegment(pi) instanceof MoveTo);
	}
	
	@Test
	void testIncrementTime() {
		Path2D path1 = new Path2D.Double();
		path1.moveTo(0, 0);
		path1.lineTo(1, 0);
		PathIterator pi = path1.getPathIterator(null);
		PartialPath partialPath = new PartialPath(pi);
		partialPath.setSpeed(0.5);
		partialPath.incrementTime();
		partialPath.incrementTime();
		Path2D path3 = partialPath.getPath();
		PathIterator pi2 = path3.getPathIterator(null);
		double[] coords1 = new double[6];
		int type = pi2.currentSegment(coords1);
		assertEquals(0, coords1[0]);
		assertEquals(0, coords1[1]);
		assertEquals(PathIterator.SEG_MOVETO, type);
		partialPath.incrementTime();
		pi2.next();
		type = pi2.currentSegment(coords1);
		assertEquals(PathIterator.SEG_LINETO, type);
		assertEquals(0.5, coords1[0]);
		assertEquals(0, coords1[1]);
	}

}
