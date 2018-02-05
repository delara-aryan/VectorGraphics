package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import com.sun.javafx.geom.PathIterator;

class TestMoveTo {

	@Test
	void testConstructor() {
		double[] coords = {0,0,1,2,3,4};
		MoveTo move = new MoveTo(coords);
		assertNotNull(move);
		Point2D p = new Point2D.Double(coords[0], coords[1]);
		MoveTo move2 = new MoveTo(p);
		assertTrue(move instanceof Segment);
	}
	
	@Test
	void testAddTo(){
		Path2D path = new Path2D.Double();
		double[] coords = {1,2,3,4,5,6};
		MoveTo move = new MoveTo(coords);
		move.addTo(path);
		coords = new double[6];
		int segmentType = TestUtils.getLastSegment(path, coords);
		assertEquals(PathIterator.SEG_MOVETO, segmentType);
		assertEquals(1, coords[0]);
		assertEquals(2, coords[1]);
		path = new Path2D.Double();
		move.addTo(path, 0.5);
		coords = new double[6];
		segmentType = TestUtils.getLastSegment(path, coords);
		assertEquals(PathIterator.SEG_MOVETO, segmentType);
		assertEquals(1, coords[0]);
		assertEquals(2, coords[1]);
	}
	
	@Test
	void testLength() {
		double[] coords = {1,2,3,4,5,6};
		MoveTo move = new MoveTo(coords);
		Point2D p = new Point2D.Double(0,0);
		assertEquals(0, move.length(p));
	}
	
	@Test
	void testPointAt() {
		double[] coords = {1,2,3,4,5,6};
		MoveTo move = new MoveTo(coords);
		Point2D p = new Point2D.Double(0,0);
		Point2D pExpected = new Point2D.Double(coords[0], coords[1]);
		TestUtils.assertEqualPoints(pExpected, move.pointAt(p, 0.5));
	}

}
