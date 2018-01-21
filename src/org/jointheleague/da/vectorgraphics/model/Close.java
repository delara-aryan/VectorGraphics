package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Close implements Segment {

	private final Point2D pstart;
	
	public Close(Point2D p) {
		pstart = p;
	}
	
	@Override
	public Path2D addTo(Path2D path) {
		path.closePath();
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		if(t < 1) {
			LineTo line = new LineTo(pstart);
			line.addTo(path, t);
		} else {
			path.closePath();
		}
		return path;
	}

	@Override
	public double length(Point2D point) {
		return point.distance(pstart);
	}

	@Override
	public Point2D pointAt(Point2D p0, double t) {
		if(t < 1) {
			return Segment.affineCombo(new Point2D[] {p0, pstart}, new double[] {1-t, t});
		} else { 
			return pstart;
		}
	}

}
