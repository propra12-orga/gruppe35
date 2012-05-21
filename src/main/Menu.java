package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JFrame {
	// Constructor
	public Menu() {
		super("MainMenu");

		JMenu start = new JMenu("Start");
		start.setMnemonic('S');
		JMenuItem l1Item =  new JMenuItem("Level 1");
		start.add(l1Item);

		JMenu options = new JMenu("Options");
		options.setMnemonic('O');
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('X');
		
		// Action listener für menu items
		l1Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Level 1 is pressed");
			}
		});
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit is pressed");
				System.exit(0);
			}
		});
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(start);
		bar.add(options);
		bar.add(exitItem);

		getContentPane();
		setSize(200, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		Menu app = new Menu();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
