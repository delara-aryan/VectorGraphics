package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

class TestQuadTo {

	@Test
	void testConstructor() {
		double[] coords = new double[]{1, 2, 3, 4, 5, 6};
		QuadTo quad = new QuadTo(coords);
		assertNotNull(quad);
		assertTrue(quad instanceof Segment);
	}

	@Test
	void testPointAt() {
		double[] coords = new double[]{1, 2, 3, 4, 5, 6};
		QuadTo quad = new QuadTo(coords);
		Point2D p0 = new Point2D.Double(0,0);
		Point2D pt = quad.pointAt(p0, 0.5);
		assertNotNull(pt);
		Point2D pExpected = new Point2D.Double(1.25, 2);
		assertEquals(pExpected, pt);
	}
	
	@Test
	void testLength() {
		double[] coords = new double[]{1, 2, 3, 4, 5, 6};
		QuadTo quad = new QuadTo(coords);
		Point2D p0 = new Point2D.Double(0,0);
		double expected = Math.sqrt(5) + Math.sqrt(8);
		assertEquals(expected, quad.length(p0), 1e-6);
	}
	
	@Test
	void testAddTo() {
		double[] coords = new double[]{1, 2, 3, 4, 5, 6};
		QuadTo quad = new QuadTo(coords);
		Path2D path = new Path2D.Double(0, 0);
		path.moveTo(0, 0);
		assertNotNull(quad.addTo(path));
		coords = new double[6];
		assertEquals(TestUtils.getLastSegment(path, coords), PathIterator.SEG_QUADTO);
		assertArrayEquals(Arrays.copyOfRange(coords, 0, 4), new double[] {1,2,3,4});
	}
	
	@Test
	void testAddToT() {
		double[] coords = new double[]{1, 2, 3, 4, 5, 6};
		QuadTo quad = new QuadTo(coords);
		Path2D path = new Path2D.Double(0, 0);
		path.moveTo(0, 0);
		double t0 = new Random().nextDouble();
		quad.addTo(path, t0);
		TestUtils.getLastSegment(path, coords);
		Point2D pt = new Point2D.Double(coords[0], coords[1]);
		QuadTo quad2 = new QuadTo(coords);
		Point2D p0 = new Point2D.Double(0, 0);
		for (double t = 0; t <= 1.0; t+=0.01) {
			TestUtils.assertEqualPoints(quad2.pointAt(p0, t), quad.pointAt(p0, t*t0));
		}
	}
	
}
