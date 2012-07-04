package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Editor.GLevelEditor;

/**
 * 
 * The Startmenu class is the main executable for this program. From here the
 * User can go to Singleplayer Mode, Multiplayer Mode or the Level Editor. The
 * Menu starts with a small intro and intriguing background music.
 * <P>
 * 
 * @author Fabian, Friedrich
 */

public class Startmenu extends JFrame {
	static boolean IntroStopped = false;
	JPanel startpanel;
	static int panelSizeX = 600;
	static int panelSizeY = 600;
	static boolean playsound;
	Container cp = this.getContentPane();
	JButton SinglePlayer = new JButton("SinglePlayer");
	JButton MultiPlayer = new JButton("MultiPlayer");
	JButton LevelEditor = new JButton("LevelEditor");
	JButton Server = new JButton("createGame");
	JButton Client = new JButton("joinGame");
	JTextField IPFeld;
	JLabel IPLabel;
	String IP;

	public void intitialize() {
		Client.setVisible(false);
		Server.setVisible(false);

		GridBagConstraints spc = new GridBagConstraints();
		spc.gridx = 0;
		spc.gridy = 1;
		// spc.gridwidth = 2;
		spc.fill = GridBagConstraints.HORIZONTAL;
		spc.weightx = 1.0;
		cp.setLayout(new GridBagLayout());
		cp.add(SinglePlayer, spc);
		SinglePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Singleplayer");
				Startmenu.IntroStopped = true;
				Startmenu.playsound = true;
				try {
					Runtime.getRuntime().exec(
							new String[] { "java", "-jar", "Server.jar" });// ,"parameter"});
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// ,parameter});
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Runtime.getRuntime().exec(
							new String[] { "java", "-jar", "Client.jar" });// ,"parameter"});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// ,parameter});

			}
		}

		);
		GridBagConstraints mpc = new GridBagConstraints();
		mpc.gridx = 1;
		mpc.gridy = 1;
		// mpc.gridwidth = 2;
		mpc.fill = GridBagConstraints.HORIZONTAL;
		mpc.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(MultiPlayer, mpc);
		MultiPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Multi");
				Startmenu.IntroStopped = true;
				Startmenu.playsound = true;

				MultiPlayer.setVisible(false);
				SinglePlayer.setVisible(false);
				LevelEditor.setVisible(false);
				Client.setVisible(true);
				Server.setVisible(true);
				IPFeld.setVisible(true);
				IPLabel.setVisible(true);
				
			}
		}

		);
		GridBagConstraints le = new GridBagConstraints();
		le.gridx = 0;
		le.gridy = 2;
		le.gridwidth = 2;
		le.fill = GridBagConstraints.HORIZONTAL;
		le.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(LevelEditor, le);
		LevelEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Levelmachen");
				Startmenu.IntroStopped = true;
				Startmenu.playsound = true;
				// startpanel.setVisible(false);

				final GLevelEditor KLaus = new GLevelEditor();
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						KLaus.intitialize();
					}
				});

			}
		}

		);
		
		
		
		
		this.startpanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				if (!Startmenu.IntroStopped)
					g.drawImage(GlobalGraphics.intro, 0, 0,
							Startmenu.panelSizeX, Startmenu.panelSizeY - 50,
							this);
				else {
					g.drawImage(GlobalGraphics.intro2, 0, 0,
							Startmenu.panelSizeX, Startmenu.panelSizeY - 50,
							this);
					if (Startmenu.playsound == true)
						new Sound("src/sounds/DelayExp.wav", 4000).start();
					Startmenu.playsound = false;
				}

			}

		};
		IPFeld= new JTextField("localhost");
		GridBagConstraints ipf = new GridBagConstraints();
		ipf.gridx = 1;
		ipf.gridy = 1;
		spc.gridwidth = 1;
		ipf.fill = GridBagConstraints.HORIZONTAL;
		// spinner.weighty = 1.0;
		cp.add(IPFeld, ipf);
		IPFeld.setVisible(false);
		GridBagConstraints pa = new GridBagConstraints();
		pa.gridx = 0;
		pa.gridy = 0;
		pa.gridwidth = 2;
		pa.fill = GridBagConstraints.BOTH;
		pa.weightx = 1.0;
		pa.weighty = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(startpanel, pa);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(Startmenu.panelSizeX, Startmenu.panelSizeY);
		this.setVisible(true);

		GridBagConstraints server = new GridBagConstraints();
		server.gridx = 0;
		server.gridy = 2;
		server.gridwidth = 1;
		server.fill = GridBagConstraints.HORIZONTAL;
		server.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(Server, server);
		Server.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
			
			}
		});
		GridBagConstraints client = new GridBagConstraints();
		client.gridx = 1;
		client.gridy = 2;
		client.gridwidth = 1;
		client.fill = GridBagConstraints.HORIZONTAL;
		client.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(Client, client);
		Client.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				IP=IPFeld.getText();
					
				try {
					Runtime.getRuntime().exec(
							new String[] { "java", "-jar", "Client.jar", IP, "4000" });// ,"parameter"});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// ,parameter});
			}
		});
		IPLabel = new JLabel("   Server IP : ");
		GridBagConstraints il = new GridBagConstraints();
		il.gridx = 0;
		il.gridy = 1;
		// spc.gridwidth = 2;
		il.fill = GridBagConstraints.HORIZONTAL;
		// spinner.weightx = 1.0;
		cp.add(IPLabel, il);
		IPLabel.setVisible(false);

	}

	public Startmenu() {
		super("Startmenu");
	}

	public static void main(String args[]) {
		new Sound("src/sounds/blabal.wav", -1).start();
		final Startmenu KLaus = new Startmenu();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				KLaus.intitialize();
			}
		});
	}

}
