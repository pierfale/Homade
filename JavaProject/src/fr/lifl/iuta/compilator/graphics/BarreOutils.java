package fr.lifl.iuta.compilator.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class BarreOutils extends JToolBar {

	private static final long serialVersionUID = 1L;
	private Vue vue;
	private Listenr listener;
	private JTextField textField;
	
	public BarreOutils(){}

	public BarreOutils(Vue vue,String name, int orientation) {
		super(name, orientation);
		this.vue = vue;
		listener = new Listenr(vue);
		this.setFloatable(true);
		this.setPreferredSize(new Dimension(500,40));
		setthings();
	}

	public void setthings(){
		JButton button = null;

		//first button
		button = makeNavigationButton("/play.jpg", "Play",
				"run program");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setForeground(Color.black);
		this.add(button,0);

		//second button
		button = makeNavigationButton("/pause.jpg", "Stop",
				"stop program");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		this.add(button,1);

		button = makeNavigationButton("/save.png", "Save",
				"save program");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setMnemonic(KeyEvent.VK_CONTROL);
		this.add(button,2);

		button = makeNavigationButton("/debug.png", "Debug",
				"run debug");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		this.add(button,3);

		button = makeNavigationButton("/book_icon.png", "Fbs Doc",
				"Read Some Doc");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		this.add(button,4);

		textField = new JTextField("Search", (int)this.vue.getSize().getWidth()/10);
		textField.setOpaque(true);
		textField.setActionCommand("Search");
		textField.addActionListener(this.listener);
		this.add(textField);
	}	

	public JButton makeNavigationButton(String imageName,
			String actionCommand,
			String toolTipText) {

		ImageIcon icontmp = null;	

		try{
			icontmp = new ImageIcon(ImageIO.read(getClass().
					getResource(imageName)).
					getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
		}catch(Exception e){e.printStackTrace();}

		//Create and initialize the button.
		JButton button = new JButton(icontmp);
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this.listener);

		return button;
	}

	public JTextField getTextField() {
		return textField;
	}

}
