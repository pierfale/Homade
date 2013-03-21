package fr.lifl.iuta.compilator.graphics;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import fr.lifl.iuta.compilator.exception.UnknownInstructionException;

public class Bot_hand_pan extends JPanel {
	public Bot_hand_pan() {
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon1 = null;
		GraphicsConsole console = null;

		try{	
			icon1 = new ImageIcon(ImageIO.read(
					getClass().getResource("/Console.png")).
					getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
		}catch(Exception e){e.printStackTrace();}

		try {
			console = GraphicsConsole.init(null);
		} catch (Exception e) {}
		
		//JComponent txtare =  new JTextArea("");
		tabbedPane.addTab("Console", icon1, new JScrollPane(console));
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		add(tabbedPane);

		//The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	//	protected JComponent makeTextPanel(String text) {
	//		JPanel panel = new JPanel(false);
	//		JLabel filler = new JLabel(text);
	//		filler.setHorizontalAlignment(JLabel.CENTER);
	//		panel.setLayout(new GridLayout(1, 1));
	//		panel.add(filler);
	//		return panel;
	//	}
}
