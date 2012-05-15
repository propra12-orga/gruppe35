

/*import java.awt.Container;
 import java.awt.Graphics;
 import java.awt.GridBagConstraints;
 import java.awt.GridBagLayout;
 import java.awt.Image;
 import java.awt.Toolkit;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.awt.image.ImageObserver;
 import java.awt.image.ImageProducer;

 import javax.swing.JButton;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.*;

public class BeispielFrame extends JFrame implements KeyListener {
	public int psx;
	public int psy;
	public boolean move;
	// Bild laden
	public final Image image = Toolkit.getDefaultToolkit()
			.getImage("testb.jpg");

	private static final long serialVersionUID = 1L;

	public BeispielFrame() {

		super("BeispielFrame");
		this.psx = 1;
		this.psy = 1;
		this.move = false;
	}

	public void initialize() {

		Container cp = this.getContentPane();

		JPanel panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			// @Override
			public void paintComponent(Graphics g) {

				//super.paintComponent(g);

				g.drawImage(image, psy, psx, 100, 100, this);
				//panel.update(g);
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
		this.setSize(400, 300);
		// this.setResizable(false);
		this.setVisible(true);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		laufen(e.getKeyCode());
		// displayInfo(e, String.valueOf(psx) + "  " + String.valueOf(psy));
	}

	private void laufen(int Taste) {

		if (38 == Taste) {
			psx = psx + 1;
			move = true;
		}
		if (40 == Taste) {
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
