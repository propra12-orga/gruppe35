import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Fields.Field;
import Fields.Floor;
import Fields.Stone;
import Level.Level;

public class BeispielFrame extends JFrame implements KeyListener {
	public int psx;
	public int psy;
	public boolean move;
	public int sqsize = 100;
	private JPanel panel;
	private Level leveltest;
	
	// Bilder laden
	public final Image image = Toolkit.getDefaultToolkit()
			.getImage("img/testb.jpg");
	public final Image image2 = Toolkit.getDefaultToolkit().getImage(
			"img/boltzmann2.gif");
	public final Image boden = Toolkit.getDefaultToolkit()
			.getImage("img/boden3.gif");
	public final Image mauer = Toolkit.getDefaultToolkit()
			.getImage("img/mauer2.gif");

	private static final long serialVersionUID = 1L;

	public BeispielFrame() {

		super("BeispielFrame");
		this.psx = 1;
		this.psy = 1;
		this.move = false;
	}

	public void initialize() {

		Container cp = this.getContentPane();

		int xpx = 10;
		int ypx = 10;

		leveltest = new Level(xpx, ypx);
		Field floor = new Floor(); // Boden
		Field stone = new Stone(); // Unzerstörbarer Block

		// Erstelle Level 1
		for (int x = 0; x < leveltest.getXsize(); x++) {
			for (int y = 0; y < leveltest.getYsize(); y++) {
				if (((x % 2 > 0) && (y % 2 > 0))) {
					leveltest.setField(x, y, stone);
				} else {
					leveltest.setField(x, y, floor);
				}

			}
		}

		panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			// @Override
			public void paintComponent(Graphics g) {
				int xpx = 10;
				int ypx = 10;
				// super.paintComponent(g);
				// g.

				for (int i = 0; i < xpx; i++) {
					for (int j = 0; j < ypx; j++) {
						if (leveltest.getField(i, j).isSolid() == true) {
							g.drawImage(mauer, (ypx - j - 1) * sqsize,
									(xpx - i - 1) * sqsize, sqsize, sqsize,
									this);
						} else {
							g.drawImage(boden, (ypx - j - 1) * sqsize,
									(xpx - i - 1) * sqsize, sqsize, sqsize,
									this);
						}
					}
				}

				g.drawImage(image2, psy, psx, sqsize, sqsize, this);
				g.drawImage(image2, psy + 100, psx + 100, sqsize, sqsize, this);

				// panel.update(g);
			}

		};

		// panel.repaint();

		// panel.validate();
		panel.setFocusable(true);
		panel.addKeyListener(this);

		GridBagConstraints panelc = new GridBagConstraints();
		panelc.gridx = 0;
		panelc.gridy = 0;
		panelc.gridwidth = 2;
		panelc.fill = GridBagConstraints.BOTH;
		panelc.weightx = 1.0;
		panelc.weighty = 1.0;

		cp.setLayout(new GridBagLayout());

		cp.add(panel, panelc);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
		this.setSize(xpx * 50, ypx * 50);
		// this.setResizable(false);
		this.setVisible(true);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		laufen(e.getKeyCode());
		Graphics g = getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		panel.repaint();
		// displayInfo(e, String.valueOf(psx) + "  " + String.valueOf(psy));
	}

	private void laufen(int Taste) {

		if (40 == Taste) {
			psx = psx + 1;
			move = true;
		}
		if (38 == Taste) {
			psx = psx - 1;
			move = true;
		}
		if (39 == Taste) {
			psy = psy + 1;
			move = true;
		}
		if (37 == Taste) {
			psy = psy - 1;
			move = true;

		}

	}

	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		final BeispielFrame f = new BeispielFrame();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				f.initialize();
			}

		});

	}

}
