package fr.lifl.iuta.compilator.graphics;

import javax.imageio.ImageIO;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import java.net.URL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutilsBarr extends JPanel implements ActionListener {

	protected JTextArea textArea;
	protected String newline = "\n";
	static final private String PLAY = "play";
	static final private String STOP = "stop";
	static final private String SAVE = "SAVE";
	static final private String NEXT = "next";
	static final private String SOMETHING_ELSE = "other";
	static final private String TEXT_ENTERED = "text";

	public OutilsBarr() {
		super(new BorderLayout());

		//Create the toolbar.
		JToolBar toolBar = new JToolBar("Still draggable");
		addButtons(toolBar);
		toolBar.setFloatable(true);
		toolBar.setRollover(true);
		toolBar.setBounds(20,20, 100, 20);
		

		//Lay out the main panel.
		setPreferredSize(new Dimension(0,30));
		add(toolBar, BorderLayout.PAGE_START);
	}

	protected void addButtons(JToolBar toolBar) {
		JButton button = null;

		JPanel panpan = new JPanel();
		
		panpan.setLayout(new BoxLayout(panpan, BoxLayout.LINE_AXIS));
		
		//first button
		button = makeNavigationButton("/play.jpg", PLAY,
		"run program");
		panpan.add(button);

		//second button
		button = makeNavigationButton("/pause.jpg", STOP,
		"stop program");
		panpan.add(button);
		//toolBar.add(panpan, BorderLayout.WEST);
		
		button = makeNavigationButton("/save.jpg", SAVE,
		"save program");
		panpan.add(button);

		JTextField textField = new JTextField("Search");
		textField.addActionListener(this);
		textField.setOpaque(true);
		textField.setActionCommand(TEXT_ENTERED);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panpan, textField);
		splitPane.setContinuousLayout(true);

		//toolBar.add(new JPanel(), BorderLayout.CENTER);
		toolBar.add(splitPane);

		//separator
		//        toolBar.addSeparator(new Dimension(this.getSize().width-textField.getSize().width,
		//        		(this.getSize().height)));
		//
		//        //fourth button
		//        button = new JButton("Another button");
		//        button.setActionCommand(SOMETHING_ELSE);
		//        button.setToolTipText("Something else");
		//        button.addActionListener(this);
		//        toolBar.add(button);
		//
		//toolBar.add(textField, BorderLayout.EAST);
	}

	protected JButton makeNavigationButton(String imageName,
			String actionCommand,
			String toolTipText) {

		URL imageURL = OutilsBarr.class.getResource(imageName);

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
		button.addActionListener(this);

		return button;
	}


	protected void displayResult(String actionDescription) {
		textArea.append(actionDescription + newline);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("play")){

		}else if(e.getActionCommand().equals("stop")){
			
		}

	}
}

