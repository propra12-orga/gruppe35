package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Startmenu extends JFrame {
	static boolean IntroStopped=false;
	JPanel startpanel;
	static int panelSizeX = 400;
	static int panelSizeY = 400;
	static boolean playsound;
	Container cp = this.getContentPane();
	JButton SinglePlayer = new JButton("SinglePlayer");
	JButton MultiPlayer = new JButton("MultiPlayer");
	JButton LevelEditor = new JButton("LevelEditor");

	public void intitialize() {

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
				System.out.println("Hallo");
				Startmenu.IntroStopped=true;
				Startmenu.playsound=true;
					
							
			
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
				Startmenu.IntroStopped=true;
				Startmenu.playsound=true;
				
				
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
				Startmenu.IntroStopped=true;
				Startmenu.playsound=true;
			
			}
		}

		);
		this.startpanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				if (!Startmenu.IntroStopped)
				g.drawImage(Global.intro, 0, 0, Startmenu.panelSizeX, Startmenu.panelSizeY-50, this);
				else{
				g.drawImage(Global.intro2, 0, 0, Startmenu.panelSizeX, Startmenu.panelSizeY-50, this);
				if (Startmenu.playsound==true)
				new Sound("src/sounds/DelayExp.wav",false);
				Startmenu.playsound=false;
				}	
				
			}

		};
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
		this.setSize(Startmenu.panelSizeX,Startmenu.panelSizeY);
		this.setVisible(true);

	}

	public Startmenu() {
		super("Startmenu");
	}

	public static void main(String args[]) {
		new Sound("src/sounds/blabal.wav",true);
		final Startmenu KLaus = new Startmenu();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				KLaus.intitialize();
			}
		});
	}

}