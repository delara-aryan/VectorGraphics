package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

class TestClose {

	@Test
	void testConstructor() {
		Point2D p = new Point2D.Double(1, 2);
		Close close = new Close(p);
		assertNotNull(close);
		assertTrue(close instanceof Segment);
	}
	
	@Test
	void testAddTo() {
		Point2D p = new Point2D.Double(1, 2);
		Close close = new Close(p);
		Path2D path = new Path2D.Double();
		path.moveTo(1, 2);
		path.quadTo(3, 4, 5, 6);
		close.addTo(path);
		double[] coords = new double[6];
		int segmentType = TestUtils.getLastSegment(path, coords);
		assertEquals(PathIterator.SEG_CLOSE, segmentType);
	}
	
	@Test
	void testAddToWithT() {
		Point2D p = new Point2D.Double(1, 2);
		Close close = new Close(p);
		Path2D path = new Path2D.Double();
		path.moveTo(1, 2);
		path.quadTo(3, 4, 5, 6);
		close.addTo(path, 0.5);
		double[] coords = new double[6];
		int segmentType = TestUtils.getLastSegment(path, coords);
		assertEquals(PathIterator.SEG_LINETO, segmentType);
		path = new Path2D.Double();
		path.moveTo(1, 2);
		path.quadTo(3, 4, 5, 6);
		close.addTo(path, 1);
		coords = new double[6];
		segmentType = TestUtils.getLastSegment(path, coords);
		assertEquals(PathIterator.SEG_CLOSE, segmentType);
	}
	
	@Test
	void testLength() {
		Point2D p = new Point2D.Double(3,0);
		Close close = new Close(p);
		Point2D p0 = new Point2D.Double(0, 0);
		assertEquals(3, close.length(p0));
	}
	
	@Test
	void testPointAt() {
		Point2D p = new Point2D.Double(1, 2);
		Close close = new Close(p);
		Path2D path = new Path2D.Double();
		path.moveTo(1, 2);
		path.quadTo(3, 4, 5, 6);
		Point2D p2 = new Point2D.Double(3,4);
		assertEquals(p2, close.pointAt(path.getCurrentPoint(), 0.5));
		path = new Path2D.Double();
		path.moveTo(1, 2);
		path.quadTo(3, 4, 5, 6);
		assertEquals(p, close.pointAt(path.getCurrentPoint(), 1));
	}

}
