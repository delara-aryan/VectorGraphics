package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.Observable;
import java.util.Observer;

import org.junit.jupiter.api.Test;

class TestPartialPath {
	
	private boolean notified = false;

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
		Path2D pathIn = new Path2D.Double();
		pathIn.moveTo(0, 0);
		pathIn.lineTo(1, 0);
		PathIterator piIn = pathIn.getPathIterator(null);
		PartialPath partialPath = new PartialPath(piIn);
		partialPath.setSpeed(0.5);
		partialPath.incrementTime();
		Path2D pathOut = partialPath.getPath();
		PathIterator piOut = pathOut.getPathIterator(null);
		double[] coords1 = new double[6];
		int type = piOut.currentSegment(coords1);
		assertEquals(0, coords1[0]);
		assertEquals(0, coords1[1]);
		assertEquals(PathIterator.SEG_MOVETO, type);
		partialPath.incrementTime();
		pathOut = partialPath.getPath();
		piOut = pathOut.getPathIterator(null);
		piOut.next();
		type = piOut.currentSegment(coords1);
		assertEquals(PathIterator.SEG_LINETO, type);
		assertEquals(0.5, coords1[0]);
		assertEquals(0, coords1[1]);
		assertFalse(partialPath.isComplete());
		partialPath.incrementTime();
		pathOut = partialPath.getPath();
		piOut = pathOut.getPathIterator(null);
		piOut.next();
		type = piOut.currentSegment(coords1);
		assertEquals(PathIterator.SEG_LINETO, type);
		assertEquals(1, coords1[0]);
		assertEquals(0, coords1[1]);
		assertTrue(partialPath.isComplete());
	}
	
	@Test
	void testObserver() {
		Path2D pathIn = new Path2D.Double();
		pathIn.moveTo(0, 0);
		pathIn.lineTo(1, 0);
		PathIterator piIn = pathIn.getPathIterator(null);
		PartialPath partialPath = new PartialPath(piIn);
		partialPath.setSpeed(0.5);
		Observer observer = new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				notified = true;
			}
			
		};
		partialPath.addObserver(observer);
		partialPath.incrementTime();
		assertTrue(notified)	;
		
	}

}
