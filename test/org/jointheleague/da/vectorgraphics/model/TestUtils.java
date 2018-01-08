package org.jointheleague.da.vectorgraphics.model;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class TestUtils {

	static int getLastSegment(Path2D path, double[] coords) {
		PathIterator iterator = path.getPathIterator(null);
		int type = 0;
		while(!iterator.isDone()) {
			type = iterator.currentSegment(coords);
			iterator.next();
		}
		return type;
	}
	
	static void assertEqualPoints(Point2D p1, Point2D p2) {
		assertEquals(p1.getX(), p2.getX(), 1e-6);
		assertEquals(p1.getY(), p2.getY(), 1e-6);
	}
	
}
