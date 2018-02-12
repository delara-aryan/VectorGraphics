package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Observable;

public class PartialPath extends Observable{

	private final PathIterator pi;
	private final Path2D path;
	private Segment currentSegment;
	private double t;
	private double speed;
	private Point2D pstart;

	public PartialPath(PathIterator pi) {
		this.pi = pi;
		path = new Path2D.Double();
		t = 0;
		currentSegment = getCurrentSegment(pi);
	}

	public Segment getCurrentSegment(PathIterator pi) {
		double[] coords = new double[6];
		int type = pi.currentSegment(coords);
		switch (type) {
		case PathIterator.SEG_MOVETO:
			pstart = new Point2D.Double(coords[0], coords[1]);
			return new MoveTo(coords);
		case PathIterator.SEG_CLOSE:
			return new Close(pstart);
		case PathIterator.SEG_LINETO:
			return new LineTo(coords);
		case PathIterator.SEG_QUADTO:
			currentSegment = new QuadTo(coords);
		case PathIterator.SEG_CUBICTO:
			return new CubicTo(coords);
		default:
			return null;
		}
	}

	void setSpeed(double speed) {
		this.speed = speed;
	}

	double getSpeed() {
		return speed;
	}

	public void incrementTime() {
		if (t < 1) {
			t += speed / currentSegment.length(path.getCurrentPoint());
		}
		if (t >= 1) {
			if(currentSegment != null) {
				currentSegment.addTo(path);
				currentSegment = null;
			}	
			if (!pi.isDone()) {
				t = 0;
				pi.next();
				currentSegment = getCurrentSegment(pi);
			}
		}
		setChanged();
		notifyObservers();
	}

	public Path2D getPath() {
		if (t == 0 || t >= 1) {
			return path;			
		} else {
			Path2D copy = new Path2D.Double(path);
			currentSegment.addTo(copy, t);
			return copy;
		}
	}
	
	public boolean isComplete() {
		return pi.isDone();
	}
}
