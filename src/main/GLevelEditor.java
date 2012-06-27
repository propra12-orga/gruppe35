package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class GLevelEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	static int xsize;
	static int ysize;
	static JPanel EditorPanel;

	static JButton SinglePlayer = new JButton("SinglePlayer");
	static JButton MultiPlayer = new JButton("MultiPlayer");
	static JSpinner XSpinner;
	static JSpinner YSpinner;
	JLabel labelx;
	JLabel labely;
	Container cp = this.getContentPane();
	static boolean Singleplayer = true;
	
	public void intitialize() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(Startmenu.panelSizeX, Startmenu.panelSizeY);
		this.setVisible(true);
	
			
		
		GLevelEditor.EditorPanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Global.mauer, 0, 0, 40, 400, EditorPanel);

			}
		};
		
		GridBagConstraints pa = new GridBagConstraints();
		pa.gridx = 0;
		pa.gridy = 0;
		pa.gridwidth = 2;
		pa.fill = GridBagConstraints.BOTH;
		pa.weightx = 1.0;
		pa.weighty = 1.0;
		cp.setLayout(new GridBagLayout());
		cp.add(EditorPanel, pa);

		GridBagConstraints spc = new GridBagConstraints();
		spc.gridx = 0;
		spc.gridy = 2;
		// spc.gridwidth = 2;
		spc.fill = GridBagConstraints.HORIZONTAL;
		spc.weightx = 1.0;
		cp.setLayout(new GridBagLayout());
		cp.add(SinglePlayer, spc);
		SinglePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GLevelEditor.Singleplayer = true;
				MultiPlayer.setVisible(false);
				SinglePlayer.setVisible(false);
				XSpinner.setVisible(false);
				YSpinner.setVisible(false);
				labely.setVisible(false);
				labelx.setVisible(false);

				xsize = Integer.valueOf(XSpinner.getValue().toString());
				ysize = Integer.valueOf(YSpinner.getValue().toString());
				EditorPanel.setSize(xsize * Global.sqsize, xsize
						* Global.sqsize);
				EditorPanel.setVisible(true);
								

			}
		});
		GridBagConstraints mpc = new GridBagConstraints();
		mpc.gridx = 1;
		mpc.gridy = 2;
		// mpc.gridwidth = 2;
		mpc.fill = GridBagConstraints.HORIZONTAL;
		mpc.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(MultiPlayer, mpc);
		MultiPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GLevelEditor.Singleplayer = false;
				MultiPlayer.setVisible(false);
				SinglePlayer.setVisible(false);
				XSpinner.setVisible(false);
				YSpinner.setVisible(false);
				labely.setVisible(false);
				labelx.setVisible(false);
				xsize = Integer.valueOf(XSpinner.getValue().toString());
				ysize = Integer.valueOf(YSpinner.getValue().toString());
				EditorPanel.setSize(xsize * Global.sqsize, xsize
						* Global.sqsize);
				EditorPanel.setVisible(true);

			}
		}

		);

		SpinnerModel modelx = new SpinnerNumberModel(3, 3, 20, 1);
		XSpinner = new JSpinner(modelx);
		GridBagConstraints spinnerx = new GridBagConstraints();
		spinnerx.gridx = 0;
		spinnerx.gridy = 1;
		// spc.gridwidth = 2;
		spinnerx.fill = GridBagConstraints.BOTH;
		// spinner.weightx = 1.0;
		cp.add(XSpinner, spinnerx);
		XSpinner.setVisible(true);

		SpinnerModel modely = new SpinnerNumberModel(3, 3, 20, 1);
		YSpinner = new JSpinner(modely);
		GridBagConstraints spinnery = new GridBagConstraints();
		spinnery.gridx = 1;
		spinnery.gridy = 1;
		// spc.gridwidth = 2;
		spinnery.fill = GridBagConstraints.BOTH;
		// spinner.weighty = 1.0;
		cp.add(YSpinner, spinnery);
		YSpinner.setVisible(true);

		labelx = new JLabel("X-Size");
		GridBagConstraints lx = new GridBagConstraints();
		lx.gridx = 0;
		lx.gridy = 0;
		// spc.gridwidth = 2;
		lx.fill = GridBagConstraints.BOTH;
		// spinner.weightx = 1.0;
		cp.add(labelx, lx);
		labelx.setVisible(true);

		labely = new JLabel("Y-Size");
		GridBagConstraints ly = new GridBagConstraints();
		ly.gridx = 1;
		ly.gridy = 0;
		// spc.gridwidth = 2;
		ly.fill = GridBagConstraints.BOTH;
		// spinner.weightx = 1.0;
		cp.add(labely, ly);
		labely.setVisible(true);

	}
	

	public static void main(String args[]) {

		// new Sound("src/sounds/blabal.wav", true);
		
		final GLevelEditor KLaus = new GLevelEditor();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				KLaus.intitialize();
			}
		});

	}
}
