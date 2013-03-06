package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import fr.lifl.iuta.compilator.base.Util;
import fr.lifl.iuta.compilator.processor.RAM;

public class GraphicsRAM extends JPanel implements Observer{

	private long [] memory;
	private JTextPane text;
	private int memorysize;

	public GraphicsRAM(long [] memory){

		this.memory = memory;
		this.memorysize = memory.length;
		RAM.addObserver(this);
		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		text = new JTextPane();text.setEditable(false);
		String tmp="";
		for (int i = 0 ; i < memory.length ; i++){
			//tmp += (Util.fill(String.format("%x",memory[i]))+"\n").toUpperCase();
			tmp += (Util.fill(String.format("%x",memory[i]),i+"")+"\n").toUpperCase();

		}
		text.setText(tmp);
		JScrollPane scrollPane = new JScrollPane(text);
		this.add(scrollPane);

		JPanel panelInt = new JPanel();
		BorderLayout bInt = new BorderLayout();
		panelInt.setLayout(bInt);
		JLabel searchLabel = new JLabel("Rechercher :");
		JTextField input = new JTextField();
		SearchListener listener = new SearchListener(input,text,scrollPane);
		input.addActionListener(listener);
		panelInt.add(searchLabel,b.WEST);panelInt.add(input,b.CENTER);
		this.add(panelInt,b.SOUTH);
		this.setPreferredSize(new Dimension(175,100));
	}

//	public void update(Observable o, Object arg) {
//		if (arg != null) { 
//			this.memory = RAM.getMemory();
//			String tmp=text.getText().substring(0,(Integer)arg*20);
//			tmp += Util.fill(String.format("%x",memory[(Integer)arg]))+"\n";
//			tmp += text.getText().substring((Integer)(arg)*20+20);
//			System.out.println("TAILLE : "+tmp.length());
//			this.text.getHighlighter().removeAllHighlights();
//			DefaultHighlighter.DefaultHighlightPainter hl = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
//			try {
//				this.text.getHighlighter().addHighlight((Integer)arg*20, (Integer)arg*20+20, hl);
//			} catch (BadLocationException e) {this.text.setCaretPosition(this.text.getText().length());}
//			this.text.setCaretPosition((Integer)arg*20);
//			this.validate();this.repaint();
//		}
//	}
	
	public void update(Observable o, Object arg) {
		if (arg != null) { 
			String tmp = "";
			this.memory = RAM.getMemory();
			System.out.println("MEMOIRE" + memory.length);
			System.out.println("MEMOIRE SIZE : "+ memorysize);
			if (this.memorysize != this.memory.length ) {
				for (int i = 0 ; i < memory.length ; i++) {
					tmp += Util.fill(String.format("%x",memory[i]),i+"")+"\n";
				}
				this.memorysize = this.memory.length;
			} else {
				tmp=text.getText().substring(0,(Integer)arg*25);
				tmp += Util.fill(String.format("%x",memory[(Integer)arg]),((Integer)arg).toString())+"\n";
				tmp += text.getText().substring((Integer)(arg)*25+25);
				tmp.toUpperCase();
			}
			text.setText(tmp);
			this.text.getHighlighter().removeAllHighlights();
			DefaultHighlighter.DefaultHighlightPainter hl = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
			try {
				this.text.getHighlighter().addHighlight((Integer)arg*25, (Integer)arg*25+25, hl);
			} catch (BadLocationException e) {this.text.setCaretPosition(this.text.getText().length());}
			this.text.setCaretPosition((Integer)arg*25);
			this.validate();this.repaint();
		}
	}

	//	public static void main(String[] args) {
	//		JFrame f = new JFrame("test");
	//		long [] memory = {255,1024,7881,125,448725,555555,23,12,82,99,255,1024,7881,125,448725,555555,23,12,82,99};
	//		f.add(new GraphicsRAM(memory));
	//		f.setVisible(true);
	//		f.pack();
	//		f.setLocation(500,500);
	//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	}

}

