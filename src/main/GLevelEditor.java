package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Fields.Earth;
import Fields.Exit;
import Fields.Field;
import Fields.Floor;
import Fields.Stone;
import Level.Level;

public class GLevelEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	static int xsize;
	static int ysize;
	static JPanel EditorPanel;

	static JButton SinglePlayer = new JButton("SinglePlayer");
	static JButton MultiPlayer = new JButton("MultiPlayer");
	static JButton editLevel = new JButton("EditLevel");
	static JButton setExit = new JButton("setExit");
	static JSpinner XSpinner;
	static JSpinner YSpinner;
	JLabel labelx;
	JLabel labely;
	JLabel EditOrSetExit;
	boolean editLevelbool = true;
	boolean exitExistent = false;
	Container cp = this.getContentPane();
	static boolean Singleplayer = true;
	Level level;
	int frameSizeX = Startmenu.panelSizeX;
	int frameSizeY = Startmenu.panelSizeY;

	public void intitialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(new Dimension(frameSizeX, frameSizeY));
		this.setVisible(true);

		GLevelEditor.EditorPanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(Global.mauersolid, 0, 0, (xsize + 1)
						* Global.sqsize, (ysize + 1) * Global.sqsize,
						EditorPanel);

				level.drawComponent(g, EditorPanel);

			}
		};
		EditorPanel.setVisible(false);
		setExit.setVisible(false);
		editLevel.setVisible(false);

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
				setExit.setVisible(true);
				editLevel.setVisible(true);
				EditOrSetExit.setVisible(true);
				createEmptyLevel();

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
				EditorPanel.repaint();
				EditorPanel.setVisible(true);

				createEmptyLevel();

			}
		});

		GridBagConstraints se = new GridBagConstraints();
		se.gridx = 0;
		se.gridy = 4;
		// mpc.gridwidth = 2;
		se.fill = GridBagConstraints.HORIZONTAL;
		se.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(setExit, se);
		setExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditOrSetExit.setText("SetExit");
				editLevelbool = false;

			}
		}

		);
		GridBagConstraints el = new GridBagConstraints();
		el.gridx = 0;
		el.gridy = 3;
		// mpc.gridwidth = 2;
		el.fill = GridBagConstraints.HORIZONTAL;
		el.weightx = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(editLevel, el);
		editLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditOrSetExit.setText("EditLevel");
				editLevelbool = true;

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

		EditOrSetExit = new JLabel("EditLevel");
		GridBagConstraints EoE = new GridBagConstraints();
		EoE.gridx = 0;
		EoE.gridy = 0;
		// spc.gridwidth = 2;
		EoE.fill = GridBagConstraints.BOTH;
		// spinner.weightx = 1.0;
		cp.add(EditOrSetExit, EoE);
		EditOrSetExit.setVisible(false);

		GridBagConstraints pa = new GridBagConstraints();
		pa.gridx = 0;
		pa.gridy = 1;
		// pa.gridwidth = 2;
		pa.fill = GridBagConstraints.BOTH;
		pa.weightx = 1.0;
		pa.weighty = 1.0;
		// cp.setLayout(new GridBagLayout());
		cp.add(EditorPanel, pa);

		GLevelEditor.EditorPanel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int mousefield[] = new int[2];
				mousefield[0] = (int) (e.getX() / Global.sqsize);
				mousefield[1] = (int) (e.getY() / Global.sqsize);
				if (0 <= mousefield[0] && mousefield[0] <= xsize - 1
						&& 0 <= mousefield[1] && mousefield[1] <= ysize - 1)
					changeField(mousefield[0], mousefield[1]);

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void createEmptyLevel() {
		int spawnpoints[][] = new int[1][2];
		spawnpoints[0][0] = 0;
		spawnpoints[0][1] = 0;

		level = new Level(xsize, ysize, spawnpoints);
		Field floor = new Floor(); // Boden
		// Erstmal alles auf Floor setzen
		for (int x = 0; x < xsize; x++) {
			for (int y = 0; y < ysize; y++) {
				level.setField(x, y, floor);
			}
		}

	}

	private void changeField(int x, int y) {
		Field floor = new Floor(); // Boden
		Field stone = new Stone(); // Unzerstörbarer Block
		Field earth = new Earth(); // Zerstörbarer Block
		Field exit = new Exit();

		if (editLevelbool) {
			if (level.getField(x, y).isTransformable() == null) {
				if (!level.getField(x, y).isExit()) {
					if (!level.getField(x, y).isSolid()) {
						level.setField(x, y, earth);
						EditorPanel.repaint();
					} else {
						level.setField(x, y, floor);
						EditorPanel.repaint();
					}
				}
			} else {
				if (!level.getField(x, y).isTransformable().isExit()) {
					level.setField(x, y, stone);
					EditorPanel.repaint();
				}
			}

		} else {

			if (!exitExistent) {

				if (level.getField(x, y).isSolid()) {
					if (level.getField(x, y).isTransformable() != null) {
						level.getField(x, y).setTransformto(exit);
						EditorPanel.repaint();
					}

				} else {
					level.setField(x, y, exit);
					level.getField(x, y).markAsExit(true);
					EditorPanel.repaint();

				}
				exitExistent = true;

			} else {
				if (level.getField(x, y).isExit()||level.getField(x, y).isTransformable().isExit()) {
					level.setField(x, y, floor);
					level.getField(x, y).markAsExit(false);
					exitExistent = false;
					EditorPanel.repaint();
				}
			}
		}
		
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
