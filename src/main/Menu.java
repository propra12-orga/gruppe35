package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Level.Levellist;

public class Menu extends JFrame {
	// Constructor
	public static boolean panelvisible = false;
	final public static GUI feld = new GUI();

	public Menu() {
		super("MainMenu");

		JMenu start = new JMenu("Start");
		start.setMnemonic('S');

		JMenu options = new JMenu("Options");
		options.setMnemonic('O');

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('X');

		JMenuItem l1Item = new JMenuItem("Level 1");
		start.add(l1Item);

		l1Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Levels laden
				Levellist.load(0);
				// Player laden
				Playerlist.load();
				
				for (int i = 0; i < Playerlist.list.size(); i++) {
					Playerlist.list.get(i).spawn();
				}

				Menu.panelvisible = true;
				// new Sound("src/sounds/background.wav",true);
				if (Menu.feld.runnable == null) {
					Menu.feld.runnable = new Runnable() {
						public void run() {
							Menu.feld.initialize();
						}
					};
				}
				javax.swing.SwingUtilities.invokeLater(Menu.feld.runnable);

				

			}
		});

		// Action listener für Exit
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(start);
		bar.add(options);
		bar.add(exitItem);

		getContentPane();
		setSize(400, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		// Player laden
		Playerlist.load();

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

		Menu menu = new Menu();
		new Sound("src/sounds/blabal.wav",0).start();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
