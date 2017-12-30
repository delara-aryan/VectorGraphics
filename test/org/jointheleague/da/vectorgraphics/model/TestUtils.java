package org.jointheleague.da.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

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
	
}
