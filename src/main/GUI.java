package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

		super("Boltzmann - Total Destruction");
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

				// draw players
				for (int i = 0; i < Playerlist.list.size(); i++) {
					Playerlist.list.get(i).DrawComponent(g, panel);
				}
				
				// draw enemies
				for (int i = 0; i < Enemylist.list.size(); i++) {
					Enemylist.list.get(i).DrawComponent(g, panel);
				}
				
				
				g.drawRect(Global.sqsize * Levellist.activeLevel.getXsize(),
						0,100,100);
				for (int i = 0; i < Playerlist.list.size(); i++) {
					g.drawString(Playerlist.list.get(i).getName(),
							Global.sqsize * Levellist.activeLevel.getXsize()+10,
							20+i*50);
					g.drawString(
							"Leben: "
									+ String.valueOf(Playerlist.list.get(i)
											.getLifes()), Global.sqsize
									* Levellist.activeLevel.getXsize()+10, 40+i*50);
				}
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
		this.setSize(Levellist.activeLevel.getXsize() * Global.sqsize * 2,
				Levellist.activeLevel.getYsize() * Global.sqsize * 2);
		// this.setResizable(false);
		this.setVisible(Menu.panelvisible);
	}

	javax.swing.Timer t = new javax.swing.Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < Playerlist.list.size(); i++) {
				if (Playerlist.list.get(i).isMovingRight() == true)
					Playerlist.list.get(i).move(1, 0);
				if (Playerlist.list.get(i).isMovingLeft() == true)
					Playerlist.list.get(i).move(-1, 0);
				if (Playerlist.list.get(i).isMovingUp() == true)
					Playerlist.list.get(i).move(0, 1);
				if (Playerlist.list.get(i).isMovingDown() == true)
					Playerlist.list.get(i).move(0, -1);
			}
		}
	});

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < Playerlist.list.size(); i++) {

			// Rechts Spieler
			if (Playerlist.list.get(i).getControl().getRight() == e
					.getKeyCode()) {
				Playerlist.list.get(i).setMovingRight(false);

			}
			// Links Spieler
			if (Playerlist.list.get(i).getControl().getLeft() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingLeft(false);

			}
			// Oben Spieler
			if (Playerlist.list.get(i).getControl().getUp() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingUp(false);

			}
			// Unten Spieler
			if (Playerlist.list.get(i).getControl().getDown() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingDown(false);

			}
			t.start();
		}

	}

	public void keyPressed(KeyEvent e) {
		
		for (int i = 0; i < Playerlist.list.size(); i++) {

			// Rechts Spieler
			if (Playerlist.list.get(i).getControl().getRight() == e
					.getKeyCode()) {
				Playerlist.list.get(i).setMovingRight(true);

			}
			// Links Spieler
			if (Playerlist.list.get(i).getControl().getLeft() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingLeft(true);

			}
			// Oben Spieler
			if (Playerlist.list.get(i).getControl().getUp() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingUp(true);

			}
			// Unten Spieler
			if (Playerlist.list.get(i).getControl().getDown() == e.getKeyCode()) {
				Playerlist.list.get(i).setMovingDown(true);

			}
			// Enter (Bombe) Spieler 1
			if (Playerlist.list.get(i).getControl().getPlaceBomb() == e
					.getKeyCode()) {
				Playerlist.list.get(i).placebomb();
			}
			t.start();
		}

		Graphics g = getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		panel.repaint();

	}

	// public JPanel getPanel() {
	// return panel;
	// }

}
