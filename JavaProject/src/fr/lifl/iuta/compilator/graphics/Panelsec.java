package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Panelsec extends JPanel {

	private Top_hand_pan cell1;
	private Bot_hand_pan cell2 ;

	public Panelsec(){

		super(new BorderLayout());

		JPanel princpan = new JPanel(new BorderLayout());

		cell1 = new Top_hand_pan();

		cell2 = new Bot_hand_pan();


		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				cell1,cell2);

		//cell1.setMinimumSize(new Dimension(200,200));
		splitPane.setResizeWeight(0.75);
		splitPane.setContinuousLayout(true);

		princpan.add(splitPane);

		add(splitPane);

	}

	public Top_hand_pan getCell1() {
		return cell1;
	}

	public Bot_hand_pan getCell2() {
		return cell2;
	}

	public void setCell1(Top_hand_pan cell1) {
		this.cell1 = cell1;
	}

	public void setCell2(Bot_hand_pan cell2) {
		this.cell2 = cell2;
	}
}
