import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LineDemo extends JPanel implements Runnable, ActionListener {

	private Point2D p0 = new Point2D.Double(50, 50);
	private Point2D p1 = new Point2D.Double(450, 450);
	private Point2D pt = new Point2D.Double(50, 50);
	private double t = 0;
	private Timer ticker;

	public static void main(String[] args) {
		LineDemo v = new LineDemo();
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
		g2.setColor(Color.BLACK);
		g2.fillRect((int) (p0.getX() - 3), (int) (p0.getY() - 3), 6, 6);
		g2.fillRect((int) (p1.getX() - 3), (int) (p1.getY() - 3), 6, 6);
		g2.drawLine((int) p0.getX(), (int) p0.getY(), (int) pt.getX(), (int) pt.getY());
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
			t += 0.005;
			pt.setLocation((1 - t) * p0.getX() + t * p1.getX(), (1 - t) * p0.getY() + t * p1.getY());
			repaint();
		}
	}

}
