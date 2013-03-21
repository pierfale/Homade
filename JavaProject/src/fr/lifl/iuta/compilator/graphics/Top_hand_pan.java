package fr.lifl.iuta.compilator.graphics;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Top_hand_pan extends JPanel {

	private JTabbedPane tabbedPane;
	private JTextArea area;

	public Top_hand_pan(){
		super(new GridLayout(1, 1));
		tabbedPane = new JTabbedPane();
		area = (JTextArea) this.getTabbedPane().getSelectedComponent();
				
		Texttxt texte = new Texttxt("fbs01");
		JComponent panel011 = new TxtArea(texte);
		tabbedPane.addTab(texte.getName(),new JScrollPane(panel011));

		this.add(tabbedPane);
		
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
		tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(texte.getName()));
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

	public JTextArea getArea() {
		return area;
	}	
	
}
