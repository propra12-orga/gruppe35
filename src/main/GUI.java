package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Level.Levellist;

//import main.Global;

public class GUI extends JFrame implements KeyListener {
	public JPanel panel;
	public Runnable runnable = null;
	WindowListener windowListener = null;

	private static final long serialVersionUID = 1L;

	public GUI() {

		super("Boltzmann - Total Destruction");
	}

	public void initialize() {
		if (windowListener == null) {
			windowListener = new WindowListener() {
				public void windowClosed(WindowEvent arg0) {
					System.out.println("Window close event occur");
				}

				public void windowActivated(WindowEvent arg0) {
					// System.out.println("Window Activated");
				}

				public void windowClosing(WindowEvent arg0) {
					System.out.println("Window Closing");
					GUI.this.setVisible(false);
					// Stop running or drawing anything
					Levellist.terminate();

				}

				public void windowDeactivated(WindowEvent arg0) {
					// System.out.println("Window Deactivated");
				}

				public void windowDeiconified(WindowEvent arg0) {
					// System.out.println("Window Deiconified");
				}

				public void windowIconified(WindowEvent arg0) {
					// System.out.println("Window Iconified");
				}

				public void windowOpened(WindowEvent arg0) {
					// System.out.println("Window Opened");
				}
			};
			addWindowListener(windowListener);
		}

		Container cp = this.getContentPane();

		panel = new JPanel() {

			// @Override
			public void paintComponent(Graphics g) {

				for (int i = GlobalGraphics.drawarray.array.size() - 1; i >= 0; i--) {
					int[] drawItem = GlobalGraphics.drawarray.array.get(i);
					g.drawImage(GlobalGraphics.imageList.get(drawItem[4]),
							drawItem[0], drawItem[1], drawItem[2], drawItem[3],
							panel);
				}
				g.drawRect(GlobalGraphics.drawarray.statsRect[0],
						GlobalGraphics.drawarray.statsRect[1],
						GlobalGraphics.drawarray.statsRect[2],
						GlobalGraphics.drawarray.statsRect[3]);
				for (int i = 0; i < GlobalGraphics.drawarray.playernames.length; i++) {
					g.drawString(GlobalGraphics.drawarray.playernames[i],
							GlobalGraphics.drawarray.playernameposx[i],
							20 + i * 50);
					g.drawString("Leben: "
							+ GlobalGraphics.drawarray.playerlifes[i],
							GlobalGraphics.drawarray.playerlifeposx[i],
							40 + i * 50);
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

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pack();
		this.setSize(Levellist.activeLevel.getXsize() * GlobalGraphics.sqsize
				* 2, Levellist.activeLevel.getYsize() * GlobalGraphics.sqsize
				* 2);
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
