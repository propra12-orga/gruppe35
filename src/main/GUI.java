package main;

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

import Level.Levellist;

public class GUI extends JFrame implements KeyListener {
	public int sqsize = 50;
	private JPanel panel;

	// Bilder laden
	public final Image image = Toolkit.getDefaultToolkit()
			.getImage("src/img/boltzmann2.gif");
	public final Image boden = Toolkit.getDefaultToolkit()
			.getImage("src/img/boden3.gif");
	public final Image mauer = Toolkit.getDefaultToolkit()
			.getImage("src/img/mauer2.gif");

	private static final long serialVersionUID = 1L;

	public GUI() {

		super("BeispielFrame");
	}

	public void initialize() {

		Container cp = this.getContentPane();

		panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			// @Override
			public void paintComponent(Graphics g) {
				// super.paintComponent(g);
				// g.

				for (int x = 0; x < Levellist.currentlevel.getXsize(); x++) {
					for (int y = 0; y < Levellist.currentlevel.getYsize(); y++) {
						if (Levellist.currentlevel.getField(x, y).isSolid() == true) {
							g.drawImage(mauer, y * sqsize, x * sqsize, sqsize,
									sqsize, this);
						} else {
							g.drawImage(boden, y * sqsize, x * sqsize, sqsize,
									sqsize, this);
						}
					}
				}

				g.drawImage(image, Playerlist.list.get(0).getDrawx(),
						Playerlist.list.get(0).getDrawy(),
						Playerlist.list.get(0).getPixsizex(), Playerlist.list
								.get(0).getPixsizey(), this);

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
		this.setSize(Levellist.currentlevel.getXsize() * 50,
				Levellist.currentlevel.getYsize() * 50);
		// this.setResizable(false);
		this.setVisible(true);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		// Rechts
		if (39 == e.getKeyCode()) {
			Playerlist.list.get(0).move(1, 0);
		}
		// Links
		if (37 == e.getKeyCode()) {
			Playerlist.list.get(0).move(-1, 0);
		}
		// Oben
		if (40 == e.getKeyCode()) {
			Playerlist.list.get(0).move(0, 1);
		}
		// Unten
		if (38 == e.getKeyCode()) {
			Playerlist.list.get(0).move(0, -1);
		}
		laufen(e.getKeyCode());
		Graphics g = getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		panel.repaint();
		// displayInfo(e, String.valueOf(psx) + "  " + String.valueOf(psy));
	}

	private void laufen(int Taste) {
		// Rechts

	}

}