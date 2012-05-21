import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Tastaturtest extends JFrame implements KeyListener {
	public int psx;
	public int psy;

	JTextArea displayArea;
	JTextField typingArea;
	static final String newline = System.getProperty("line.separator");

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
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		Tastaturtest frame = new Tastaturtest("KeyEventDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		frame.addComponentsToPane();

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addComponentsToPane() {

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.addKeyListener(this);
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setPreferredSize(new Dimension(125, 125));

		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	// Konstruktorator
	public Tastaturtest(String name) {
		super(name);
		this.psx = 1;
		this.psy = 1;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		laufen(e.getKeyCode());
		displayInfo(e, String.valueOf(psx) + "  " + String.valueOf(psy));
	}

	private void laufen(int Taste) {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
		}
		if (38 == Taste)
			psx = psx + 1;
		if (40 == Taste)
			psx = psx - 1;
		if (39 == Taste)
			psy = psy + 1;
		if (37 == Taste)
			psy = psy - 1;

	}

	private void displayInfo(KeyEvent e, String keyStatus) {
		displayArea.append(keyStatus + newline);
	}
}
