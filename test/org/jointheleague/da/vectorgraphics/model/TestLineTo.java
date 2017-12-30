package org.jointheleague.da.vectorgraphics.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class TestLineTo {
	
	private double[] coords;
	private LineTo line;
	private Point2D p0;
	
	@Before
	void setUp() {
		coords = new double[]{1, 2, 3, 4, 5, 6};
		line = new LineTo(coords);
		p0 = new Point2D.Double(0, 0);
	}

	@Test
	void testConstructor() {
		coords = new double[]{1, 2, 3, 4, 5, 6};
		line = new LineTo(coords);
		p0 = new Point2D.Double(0, 0);
		assertNotNull(line);
		assertTrue(line instanceof Segment);
		Point2D p = new Point2D.Double(1, 2);
		assertEquals(p, line.getP1());
	}
	
	@Test
	void testPointAt() {
		coords = new double[]{1, 2, 3, 4, 5, 6};
		line = new LineTo(coords);
		p0 = new Point2D.Double(0, 0);
		Point2D p = line.pointAt(p0, 1);
		assertNotNull(p);
		assertEquals(line.getP1(), p);
	}
	
	@Test
	void testLength() {
		coords = new double[]{0, 1, 3, 4, 5, 6};
		line = new LineTo(coords);
		p0 = new Point2D.Double(0, 0);
		assertEquals(1, line.length(p0));
	}

	@Test
	void testAddTo() {
		coords = new double[]{0, 1, 3, 4, 5, 6};
		line = new LineTo(coords);
		Path2D path = new Path2D.Double();
		path.moveTo(0, 0);
		assertNotNull(line.addTo(path));
		coords = new double[6];
		assertEquals(TestUtils.getLastSegment(path, coords), PathIterator.SEG_LINETO);
		assertEquals(0, coords[0]);
		assertEquals(1, coords[1]);
	}
	
	@Test
	void testAddToWithT() {
		coords = new double[]{0, 1, 3, 4, 5, 6};
		line = new LineTo(coords);
		Path2D path = new Path2D.Double();
		path.moveTo(0, 0);
		assertNotNull(line.addTo(path, 0.5));
		coords = new double[6];
		assertEquals(TestUtils.getLastSegment(path, coords), PathIterator.SEG_LINETO);
		assertEquals(0, coords[0]);
		assertEquals(0.5, coords[1]);
	}
	
}
