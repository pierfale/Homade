package fr.lifl.iuta.compilator.graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import fr.lifl.iuta.compilator.base.Util;
import fr.lifl.iuta.compilator.processor.Processor;

public class ButtonListener implements ActionListener, Runnable{
	
	private JTextPane code;
	private JSpinner breakPoint;
	private GraphicsConsole console;
	
	public ButtonListener(JTextPane code, JSpinner breakPoint, GraphicsConsole console){
		this.code=code;this.breakPoint = breakPoint;this.console = console;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="sBs"){
			stepByStep();
		} else if (e.getActionCommand() == "read"){
			new Thread(this).start();
		}
	}
	
	public void stepByStep(){
		int PC = Util.getNbLine(Processor.getPC()), beg = 0, end = 0, i = 0;
		String codeTmp = code.getText();
		Scanner sc = new Scanner(codeTmp);
		sc.useDelimiter("\n");
		String tmp = sc.next();
		end += tmp.length();
		while (sc.hasNext() && i != PC){
			beg += tmp.length()+1;
			tmp = sc.next();
			i++; 
			end = beg+tmp.length();
		}
		DefaultHighlighter.DefaultHighlightPainter hl = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		try {
			if(Processor.isOn()) {
				Processor.stepByStep();
				this.code.getHighlighter().removeAllHighlights();
				this.code.getHighlighter().addHighlight(beg, end, hl);
				this.code.setCaretPosition(beg);
				this.code.getParent().repaint();
			}
		} catch (BadLocationException e) {e.printStackTrace();}
	}
	
	public void run () {
		this.code.getHighlighter().removeAllHighlights();
		 while(Util.getNbLine(Processor.getPC()) != (Integer)this.breakPoint.getValue()-1 && Processor.isOn()) {
			stepByStep();
			try {Thread.sleep(50);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		 console.refresh();
	}
}
