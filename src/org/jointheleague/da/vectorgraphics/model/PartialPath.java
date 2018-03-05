package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Observable;

public class PartialPath extends Observable{

	private final PathIterator pi;
	private final Path2D path;
	private Segment currentSegment;
	private double timer;
	private double speed;
	private Point2D pstart;

	public PartialPath(PathIterator pi) {
		this.pi = pi;
		path = new Path2D.Double();
		timer = 0;
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
			return new QuadTo(coords);
		case PathIterator.SEG_CUBICTO:
			return new CubicTo(coords);
		default:
			return null;
		}
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	double getSpeed() {
		return speed;
	}

	public void incrementTime() {
		if (timer < 1) {
			double length = currentSegment.length(path.getCurrentPoint());
			if (length != 0) {
				timer += speed / length;				
			} else {
				timer = 1;
			}
		}
		if (timer >= 1) {
			if(currentSegment != null) {
				currentSegment.addTo(path);
				currentSegment = null;
			}	
			if (!pi.isDone()) {
				timer = 0;
				pi.next();
				currentSegment = getCurrentSegment(pi);
			}
		}
		setChanged();
		notifyObservers();
	}

	public Path2D getPath() {
		if (timer == 0 || timer >= 1) {
			return path;			
		} else {
			Path2D copy = new Path2D.Double(path);
			currentSegment.addTo(copy, timer);
			return copy;
		}
	}
	
	public boolean isComplete() {
		return pi.isDone();
	}
}
