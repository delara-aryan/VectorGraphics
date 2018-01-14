package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.jupiter.api.Test;

class TestCubicTo {

	@Test
	void testConstructor() {
		double[] coords = new double[] { 1, 2, 3, 4, 5, 6 };
		CubicTo cubic = new CubicTo(coords);
		assertNotNull(cubic);
		assertTrue(cubic instanceof Segment);
	}

	@Test
	void testPointAt() {
		double[] coords = new double[] { 1, 2, 3, 4, 5, 6 };
		CubicTo cubic = new CubicTo(coords);
		Point2D p0 = new Point2D.Double(0, 0);
		Point2D p1 = new Point2D.Double(coords[0], coords[1]);
		Point2D p2 = new Point2D.Double(coords[2], coords[3]);
		Point2D p3 = new Point2D.Double(coords[4], coords[5]);
		double t = 0.5;
		Point2D pt = cubic.pointAt(p0, t);
		assertNotNull(pt);
		double[] coeffs = { 0.125, 3 * 0.125, 3 * 0.125, 0.125 };
		Point2D[] points = { p0, p1, p2, p3 };
		Point2D pExpected = Segment.affineCombo(points, coeffs);
		assertEquals(pExpected, pt);
	}

	@Test
	void testLength() {
		double[] coords = new double[] { 1, 2, 3, 4, 5, 6 };
		CubicTo cubic = new CubicTo(coords);
		Point2D p0 = new Point2D.Double(0, 0);
		double expected = Math.sqrt(5) + Math.sqrt(8) + Math.sqrt(8);
		assertEquals(expected, cubic.length(p0), 1e-6);
	}

	@Test
	void testAddTo() {
		double[] coords = new double[] { 1, 2, 3, 4, 5, 6 };
		CubicTo cubic = new CubicTo(coords);
		Path2D path = new Path2D.Double(0, 0);
		path.moveTo(0, 0);
		assertNotNull(cubic.addTo(path));
		coords = new double[6];
		assertEquals(TestUtils.getLastSegment(path, coords), PathIterator.SEG_CUBICTO);
		assertArrayEquals(coords, new double[] { 1, 2, 3, 4, 5, 6 });
	}

	@Test
	void testAddToT() {
		double[] coords = new double[] { 1, 2, 3, 4, 5, 6 };
		QuadTo quad = new QuadTo(coords);
		Path2D path = new Path2D.Double(0, 0);
		path.moveTo(0, 0);
		double t0 = new Random().nextDouble();
		quad.addTo(path, t0);
		TestUtils.getLastSegment(path, coords);
		Point2D pt = new Point2D.Double(coords[0], coords[1]);
		QuadTo quad2 = new QuadTo(coords);
		Point2D p0 = new Point2D.Double(0, 0);
		for (double t = 0; t <= 1.0; t += 0.01) {
			TestUtils.assertEqualPoints(quad2.pointAt(p0, t), quad.pointAt(p0, t * t0));
		}
	}

}
