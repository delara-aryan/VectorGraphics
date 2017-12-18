import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CubicCurveDemo extends JPanel implements Runnable, ActionListener {

	private Point2D p0 = new Point2D.Double(50, 50);
	private Point2D p1 = new Point2D.Double(100, 450);
	private Point2D p2 = new Point2D.Double(300, 100);
	private Point2D p3 = new Point2D.Double(400, 350);
	private Point2D pt = new Point2D.Double(50, 50);
	private Point2D m = new Point2D.Double(50, 50);
	private Point2D q = new Point2D.Double(50, 50);
	private Point2D c = new Point2D.Double(50, 50);
	private double t = 0;
	private Timer ticker;

	public static void main(String[] args) {
		CubicCurveDemo v = new CubicCurveDemo();
		SwingUtilities.invokeLater(v);
	}

	public void setUpGUI() {
		JFrame frame = new JFrame("Vector Graphics");
		this.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		ticker = new Timer(10, this);
		ticker.start();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		g2.fillRect((int) (p0.getX() - 3), (int) (p0.getY() - 3), 6, 6);
		g2.fillRect((int) (p1.getX() - 3), (int) (p1.getY() - 3), 6, 6);
		g2.fillRect((int) (p2.getX() - 3), (int) (p2.getY() - 3), 6, 6);
		g2.fillRect((int) (p3.getX() - 3), (int) (p3.getY() - 3), 6, 6);
		g2.setColor(Color.LIGHT_GRAY);
		Path2D cubicInitial = new Path2D.Double();
		cubicInitial.moveTo(p0.getX(), p0.getY());
		cubicInitial.curveTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
		g2.draw(cubicInitial);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		Path2D cubic = new Path2D.Double();
		cubic.moveTo(p0.getX(), p0.getY());
		cubic.curveTo(m.getX(), m.getY(), q.getX(), q.getY(), c.getX(), c.getY());
		g2.draw(cubic);
	}

	@Override
	public void run() {
		setUpGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (t >= 1.0) {
			ticker.stop();
		} else {
			t += 0.001;
			double u = 1 - t;
			m.setLocation(u * p0.getX() + t * p1.getX(), u * p0.getY() + t * p1.getY());
			q.setLocation(u*u * p0.getX() + 2*u*t*p1.getX() + t*t*p2.getX(), u*u * p0.getY() + 2*u*t*p1.getY() + t*t*p2.getY());
			c.setLocation(u*u*u * p0.getX() + 3*u*u*t*p1.getX() + 3*u*t*t*p2.getX() + t*t*t*p3.getX(),
						 u*u*u * p0.getY() + 3*u*u*t*p1.getY() + 3*u*t*t*p2.getY() + t*t*t*p3.getY());
			repaint();
		}
	}

}
