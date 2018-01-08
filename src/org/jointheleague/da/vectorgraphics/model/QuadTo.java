package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class QuadTo implements Segment{

	private final Point2D p1;
	private final Point2D p2;
	
	public QuadTo(double[] coords) {
		p1 = new Point2D.Double(coords[0], coords[1]);
		p2 = new Point2D.Double(coords[2], coords[3]);
	}
	
	@Override
	public Path2D addTo(Path2D path) {
		path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		Point2D p0 = path.getCurrentPoint();
		LineTo line = new LineTo(p1);
		Point2D m0 = line.pointAt(p0, t);
		Point2D q0 = this.pointAt(p0, t);
		path.quadTo(m0.getX(), m0.getY(), q0.getX(), q0.getY());
		return path;
	}

	@Override
	public double length(Point2D point) {
		return point.distance(p1) + p1.distance(p2);
	}

	@Override
	public Point2D pointAt(Point2D p0, double t) {
		double a = 1 - t;
		double b = a * t * 2;
		return Segment.affineCombo(new Point2D[] {p0, p1, p2}, new double[] {a * a, b, t * t});
	}

}
