package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class MoveTo implements Segment {

	private final Point2D p0;
	
	public MoveTo(double[] coords) {
		this(new Point2D.Double(coords[0], coords[1]));
	}
	
	public MoveTo(Point2D p) {
		p0 = p;
	}

	@Override
	public Path2D addTo(Path2D path) {
		path.moveTo(p0.getX(), p0.getY());
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		return addTo(path);
	}

	@Override
	public double length(Point2D point) {
		return 0;
	}

	@Override
	public Point2D pointAt(Point2D p0, double t) {
		return this.p0;
	}
	
}
