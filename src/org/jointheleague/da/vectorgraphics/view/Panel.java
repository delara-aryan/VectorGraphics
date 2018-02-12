package org.jointheleague.da.vectorgraphics.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.jointheleague.da.vectorgraphics.model.PartialPath;

public class Panel extends JPanel {

	private PathIterator pathIterator;
	private Path2D path = new Path2D.Double();
	private Timer ticker;
	private PartialPath partialPath;

	public static void main(String[] args) {
		Panel panel = new Panel();
		SwingUtilities.invokeLater(() -> panel.buildGUI());
	}

	private void buildGUI() {
		JFrame frame = new JFrame("Path Demo");
		setPreferredSize(new Dimension(600, 400));
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		pathIterator = getPathIterator(getTextLayout("World!", new Font(Font.SERIF, Font.PLAIN, 144)));
		partialPath = new PartialPath(pathIterator);
		ticker = new Timer(20, (e) -> update());
		ticker.start();
	}

	private void update() {
		if (!partialPath.isComplete()) {
			partialPath.incrementTime();
			path = partialPath.getPath();
			repaint();
		} else {
			ticker.stop();
		}
	}

	private Path2D nextSegment(PathIterator pi, Path2D path) {
		double[] coords = new double[6];
		int type = pathIterator.currentSegment(coords);
		switch (type) {
		case PathIterator.SEG_CLOSE:
			path.closePath();
			System.out.println("Close");
			break;
		case PathIterator.SEG_CUBICTO:
			path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
			System.out.println("Cubic: " + Arrays.toString(coords));
			break;
		case PathIterator.SEG_LINETO:
			path.lineTo(coords[0], coords[1]);
			System.out.println("Line: " + Arrays.toString(coords));
			break;
		case PathIterator.SEG_MOVETO:
			path.moveTo(coords[0], coords[1]);
			System.out.println("Move: " + Arrays.toString(coords));
			break;
		case PathIterator.SEG_QUADTO:
			path.quadTo(coords[0], coords[1], coords[2], coords[3]);
			System.out.println("Quad: " + Arrays.toString(coords));
			break;
		default:
			throw new RuntimeException();
		}
		pathIterator.next();
		return path;
	}

	private Path2D fromPathIterator(PathIterator pi) {
		Path2D path = new Path2D.Double();
		double[] coords = new double[6];
		while (!pathIterator.isDone()) {
			int type = pathIterator.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_CLOSE:
				path.closePath();
				System.out.println("Close");
				break;
			case PathIterator.SEG_CUBICTO:
				path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
				System.out.println("Cubic: " + Arrays.toString(coords));
				break;
			case PathIterator.SEG_LINETO:
				path.lineTo(coords[0], coords[1]);
				System.out.println("Line: " + Arrays.toString(coords));
				break;
			case PathIterator.SEG_MOVETO:
				path.moveTo(coords[0], coords[1]);
				System.out.println("Move: " + Arrays.toString(coords));
				break;
			case PathIterator.SEG_QUADTO:
				path.quadTo(coords[0], coords[1], coords[2], coords[3]);
				System.out.println("Quad: " + Arrays.toString(coords));
				break;
			default:
				throw new RuntimeException();
			}
			pathIterator.next();
		}
		return path;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.BLACK);
		g2.draw(path);
	}

	private TextLayout getTextLayout(String str, Font font) {
		Graphics2D g2 = (Graphics2D) getGraphics();
		FontRenderContext frc = g2.getFontRenderContext();
		return new TextLayout(str, font, frc);
	}

	private PathIterator getPathIterator(TextLayout tl) {
		Rectangle2D bounds = tl.getBounds();
		double x = (getWidth() - bounds.getWidth()) / 2;
		double y = (getHeight() - bounds.getHeight()) / 2;
		Shape outline = tl.getOutline(AffineTransform.getTranslateInstance(x - bounds.getX(), y - bounds.getY()));
		return outline.getPathIterator(null);
	}

}
