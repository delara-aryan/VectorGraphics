package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class LineTo implements Segment{
	
	private final Point2D p1;
	
	public LineTo(double[] coords) {
		p1 = new Point2D.Double(coords[0], coords[1]);
	}
	
	public Point2D getP1() {
		return p1;
	}

	@Override
	public Path2D addTo(Path2D path) {
		path.lineTo(p1.getX(), p1.getY());
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		Point2D p = path.getCurrentPoint();
		Point2D pt = pointAt(p, t);
		path.lineTo(pt.getX(), pt.getY());
		return path;
	}

	@Override
	public double length(Point2D point) {
		return point.distance(p1);
	}

	@Override
	public Point2D pointAt(Point2D p0, double t) {
		return Segment.affineCombo(new Point2D[] {p0, p1}, new double[] {1-t, t});
	}

}
