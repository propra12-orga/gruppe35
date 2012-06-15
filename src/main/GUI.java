package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Level.Levellist;

//import main.Global;

public class GUI extends JFrame implements KeyListener {
	public JPanel panel;

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

				// draw level
				Levellist.activeLevel.drawComponent(g, panel);
				// draw bombs
				for (int i = 0; i < Bomblist.list.size(); i++) {
					Bomblist.list.get(i).DrawComponent(g, panel);
				}
				// draw flames
				for (int i = 0; i < Flamelist.list.size(); i++) {
					Flamelist.list.get(i).DrawComponent(g, panel);

				}

				// draw player1
				for (int i = 0; i < Playerlist.list.size(); i++) {
					Playerlist.list.get(i).DrawComponent(g, panel);

				}

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
		this.setSize(Levellist.activeLevel.getXsize() * Global.sqsize * 2,
				Levellist.activeLevel.getYsize() * Global.sqsize * 2);
		// this.setResizable(false);
		this.setVisible(Menu.panelvisible);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37 || e.getKeyCode() == 38
				|| e.getKeyCode() == 39 || e.getKeyCode() == 40) {
			Playerlist.list.get(0).setMoving(false);
		}

	}

	public void keyPressed(KeyEvent e) {
		// Rechts Spieler 1
		if (39 == e.getKeyCode()) {
			Playerlist.list.get(0).setMoving(true);

			Playerlist.list.get(0).move(1, 0);

		}
		// Links Spieler 1
		if (37 == e.getKeyCode()) {
			Playerlist.list.get(0).setMoving(true);
			Playerlist.list.get(0).move(-1, 0);
		}
		// Oben Spieler 1
		if (40 == e.getKeyCode()) {
			Playerlist.list.get(0).setMoving(true);
			Playerlist.list.get(0).move(0, 1);
		}
		// Unten Spieler 1
		if (38 == e.getKeyCode()) {
			Playerlist.list.get(0).setMoving(true);
			Playerlist.list.get(0).move(0, -1);
		}
		// Leertaste (Bombe) Spieler 1
		if (10 == e.getKeyCode()) {
			Playerlist.list.get(0).placebomb();
		}

		// Rechts Spieler 2
		if (68 == e.getKeyCode()) {
			Playerlist.list.get(1).move(1, 0);
		}
		// Links Spieler 2
		if (65 == e.getKeyCode()) {
			Playerlist.list.get(1).move(-1, 0);
		}
		// Oben Spieler 2
		if (83 == e.getKeyCode()) {
			Playerlist.list.get(1).move(0, 1);
		}
		// Unten Spieler 2
		if (87 == e.getKeyCode()) {
			Playerlist.list.get(1).move(0, -1);
		}
		// Leertaste (Bombe) Spieler 2
		if (32 == e.getKeyCode()) {
			Playerlist.list.get(1).placebomb();
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
