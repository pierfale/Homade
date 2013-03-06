package fr.lifl.iuta.compilator.graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;



public class SearchListener implements ActionListener{

	private JTextField field;
	private JTextPane pane;
	private JScrollPane scroll;

	public SearchListener(JTextField field, JTextPane pane, JScrollPane scroll){
		this.field = field;this.pane = pane;this.scroll = scroll;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			int c = Integer.parseInt(this.field.getText());
			this.pane.getHighlighter().removeAllHighlights();
			DefaultHighlighter.DefaultHighlightPainter hl = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
			this.pane.getHighlighter().addHighlight(c*25, c*25+25, hl);
			this.pane.setCaretPosition(c*25);
		} catch (InputMismatchException x) {
			this.field.setText("Saisir un nombre");
		} catch (BadLocationException x) {
			x.printStackTrace();
		} catch (IllegalArgumentException x){
			this.pane.setCaretPosition(this.pane.getText().length());
		}
	}

}
