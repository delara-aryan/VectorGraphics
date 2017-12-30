package org.jointheleague.da.vectorgraphics.model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

class TestSegment {

	@Test
	void testAffineCombo() {
		Point2D[] points = {new Point2D.Double(2,4), new Point2D.Double(6,4)};
		double[] coeffs = {0.5, 0.5, 0.5};
		double[] coeffs2 = {0.9, 0.8};
		try {
			Point2D p = Segment.affineCombo(points, coeffs);
			fail("Arguments are invalid");
		} catch (IllegalArgumentException e) {
			
		}
		try {
			Point2D p = Segment.affineCombo(new Point2D[] {}, new double[] {});
			fail("Arrays cannot be empty");
		} catch(IllegalArgumentException e) {
			
		}
		try {
			Point2D p = Segment.affineCombo(points, coeffs2);
			fail("Coefficents must add up to 1");
		} catch (IllegalArgumentException e) {
			
		}
		Point2D[] points2 = {new Point2D.Double(1, 1)};
		double[] coeffs3 = {1};
		assertEquals(1, Segment.affineCombo(points2, coeffs3).getX(), 1e-6);
		assertEquals(1, Segment.affineCombo(points2, coeffs3).getY(), 1e-6);
		
		double[] coeffs4 = {0.5, 0.5};
		assertEquals(4, Segment.affineCombo(points, coeffs4).getX(), 1e-6);
		assertEquals(4, Segment.affineCombo(points, coeffs4).getY(), 1e-6);
		
		double[] coeffs5 = {20, -19};
		assertEquals(-74, Segment.affineCombo(points, coeffs5).getX(), 1e-6);
		assertEquals(4, Segment.affineCombo(points, coeffs5).getY(), 1e-6);
		
		double[] coeffs6 = {-99, 100};
		assertEquals(402, Segment.affineCombo(points, coeffs6).getX(), 1e-6);
		assertEquals(4, Segment.affineCombo(points, coeffs5).getY(), 1e-6);
	}
	
}

