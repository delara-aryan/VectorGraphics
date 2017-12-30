package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public interface Segment {
	
	static final double EPSILON = 1e-6;
	
	Path2D addTo(Path2D path);
	
	Path2D addTo(Path2D path, double t);
	
	double length(Point2D point);
	
	Point2D pointAt(Point2D p0, double t);
	
	static Point2D affineCombo(Point2D[] points, double[] coeff) {
		if (coeff == null || points == null || points.length != coeff.length || points.length == 0 || coeff.length == 0) {
			throw new IllegalArgumentException();
		}
		double sum = 0;
		for (double c : coeff) {
			sum += c;
		}
		if (Math.abs(sum - 1) > EPSILON) {
			throw new IllegalArgumentException();
		}
		double x = 0;
		double y = 0;
		for (int i = 0; i < coeff.length; i++) {
			x += points[i].getX()*coeff[i];
			y += points[i].getY()*coeff[i];
		}
		return new Point2D.Double(x, y);
	}

}
