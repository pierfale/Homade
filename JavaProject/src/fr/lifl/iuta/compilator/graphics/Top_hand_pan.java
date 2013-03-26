package fr.lifl.iuta.compilator.graphics;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class Top_hand_pan extends JPanel {

	private JTabbedPane tabbedPane;

	public Top_hand_pan(){
		super(new GridLayout(1, 1));
		tabbedPane = new JTabbedPane();
		

		//		ImageIcon iconcroix = null;
		//		try{
		//			iconcroix = new ImageIcon(ImageIO.read(getClass().
		//					getResource("/Croix.png")).
		//					getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
		//		}catch(Exception e){e.printStackTrace();}

		Texttxt texte = new Texttxt("fbs01");
		TxtArea panel011 = new TxtArea(texte);
		try {
			Colorisation c = Colorisation.init(new String[] {"fbs01"}, panel011);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabbedPane.addTab(texte.getName(),new JScrollPane(panel011));
		//tabbedPane.addTab(new Texttxt("kikou.txt").getNameExtended(), new JScrollPane(panel011));
		
		//				int index = tabbedPane.indexOfTab("kikou");
		//				JPanel pnlTab = new JPanel(new GridBagLayout());
		//				pnlTab.setOpaque(false);

		//JLabel lblTitle = new JLabel("kikou");
		//Bouton btnClose = new Bouton("x");
		//JButton btnClose = new JButton(iconcroix);
		//btnClose.setSize(new Dimension(iconcroix.getIconWidth(),iconcroix.getIconHeight()));

		//		GridBagConstraints gbc = new GridBagConstraints();
		//		gbc.gridx = 0;
		//		gbc.gridy = 0;
		//		gbc.weightx = 1;
		//
		//		pnlTab.add(lblTitle, gbc);
		//
		//		gbc.gridx++;
		//		gbc.weightx = 0;
		//		pnlTab.add(btnClose, gbc);

		//tabbedPane.setTabComponentAt(index, pnlTab);

		this.add(tabbedPane);

		//The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private String getTexttxt(String s) {
		Texttxt text = new Texttxt(s);		
		String contenu = new String();		
		return contenu;
	}

	public void addOnglet(String s) {
		Texttxt texte = new Texttxt(s);
		JComponent panel011 = new TxtArea(texte);
		tabbedPane.addTab(texte.getName(),new JScrollPane(panel011));
		//this.add(tabbedPane);
		tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(texte.getNameExtended()));
		this.tabbedPane.validate();

	}
	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
}
