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
	public JPanel panel;

	// Bilder laden
	public final Image image = Toolkit.getDefaultToolkit().getImage(
			"src/img/boltzmann2.gif");
	public final Image boden = Toolkit.getDefaultToolkit().getImage(
			"src/img/boden3.gif");
	public final Image mauer = Toolkit.getDefaultToolkit().getImage(
			"src/img/mauer2.gif");
	public final Image bomb = Toolkit.getDefaultToolkit().getImage(
			"src/img/bomb.gif");
	public final Image flame = Toolkit.getDefaultToolkit().getImage(
			"src/img/fire_central.gif");

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

				for (int x = 0; x < Levellist.activeLevel.getXsize(); x++) {
					for (int y = 0; y < Levellist.activeLevel.getYsize(); y++) {
						if (Levellist.activeLevel.getField(y, x).isSolid() == true) {
							g.drawImage(mauer, y * sqsize, x * sqsize, sqsize,
									sqsize, this);
						} else {

							if (Levellist.activeLevel.getField(y, x).getBomb() != null) {
								g.drawImage(boden, y * sqsize, x * sqsize,
										sqsize, sqsize, this);
								g.drawImage(
										bomb,
										(int) (y * sqsize + (sqsize - Bomblist.list
												.get(0).getPixsizey()) * 0.5),
										(int) (x * sqsize + (sqsize - Bomblist.list
												.get(0).getPixsizex()) * 0.5),
										Bomblist.list.get(0).getPixsizex(),
										Bomblist.list.get(0).getPixsizey(),
										this);
							} else {
								if (Levellist.activeLevel.getField(y, x)
										.getFlame() == 0) {
									g.drawImage(boden, y * sqsize, x * sqsize,
											sqsize, sqsize, this);
								} else {
									g.drawImage(boden, y * sqsize, x * sqsize,
											sqsize, sqsize, this);
									g.drawImage(
											flame,
											y * sqsize,
											x * sqsize,
											Flamelist.list.get(0).getPixsizex(),
											Flamelist.list.get(0).getPixsizey(),
											this);

								}

							}
						}
					}
				}

				g.drawImage(image, Playerlist.list.get(0).getDrawx(),
						Playerlist.list.get(0).getDrawy(),
						Playerlist.list.get(0).getPixsizex(), Playerlist.list
								.get(0).getPixsizey(), this);
				// evt.Bombe malen
				// for (int i = 0; i < Bomblist.list.size(); i++) {
				// g.drawImage(bomb, Bomblist.list.get(0).getDrawx(),
				// Bomblist.list.get(i).getDrawy(),
				// Bomblist.list.get(i).getPixsizex(), Bomblist.list
				// .get(0).getPixsizey(), this);

				// }
				// evt.Flammen malen
				// for(int i=0;i<Flamelist.list.size();i++){
				// g.drawImage(flame, Flamelist.list.get(0).getDrawx(),
				// Flamelist.list.get(i).getDrawy(),
				// Flamelist.list.get(i).getPixsizex(), Flamelist.list
				// .get(0).getPixsizey(), this);

			}

			// panel.update(g);
			// }

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
		this.setSize(Levellist.activeLevel.getXsize() * 50,
				Levellist.activeLevel.getYsize() * 50);
		// this.setResizable(false);
		this.setVisible(Menu.panelvisible);
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
		// Leertaste (Bombe)
		if (32 == e.getKeyCode()) {
			Playerlist.list.get(0).placebomb();
		}

		Graphics g = getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		panel.repaint();
		// displayInfo(e, String.valueOf(psx) + "  " + String.valueOf(psy));
	}

	public JPanel getPanel() {
		return panel;
	}
}
