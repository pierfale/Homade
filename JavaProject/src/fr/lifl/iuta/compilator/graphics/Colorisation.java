package fr.lifl.iuta.compilator.graphics;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.ReadFile;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Lexer;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Parser;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class Colorisation {

	private JTextPane txt;
	private WordTree wt;
	private Map<String, Color> myColor;

	public Colorisation(String[] args, JTextPane test) throws BadLocationException{
		this.txt = test;
		myColor = new HashMap<String,Color>();
		myColor.put("none", new Color(50,50,50));
		myColor.put("type", new Color(102,102,204));
		myColor.put("string", new Color(51,102,255));
		myColor.put("integer", new Color(255,153,0));
		myColor.put("function", new Color(153,153,153));
		myColor.put("special_instruction", new Color(0,153,0));
		myColor.put("structure" , new Color(153,0,0));
		myColor.put("structure_if" , new Color(153,0,0));
		Rapport.gen = false;
		try {
			Grammar.load(new FileInputStream(new File("ressources/fbsGrammar")));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		for(String s : args) {
			ReadFile reader = null;
			try {
				reader = new ReadFile(s);
			} catch (java.io.FileNotFoundException e) {
				e.printStackTrace();
			}
			WordList wl = Lexer.exec(reader.getString());
			wt = Parser.exec(wl);
			System.out.println(reader.getString());
		}

		refresh(txt.getText());
	}

	public void refresh(String args) {
		WordList wl = Lexer.exec(args);
		wt = Parser.exec(wl);
		if (wt == null) {
			setJTextPaneFont(this.txt, new Font("Serif", Font.CENTER_BASELINE, 12), myColor.get("none"), 0, args.length());
			return;
		}
		String curr = args;
		int cursor = 0;
		Stack<WordTree> stack = new Stack<WordTree>();
		stack.push(wt);
		while(!stack.empty()) {
			if(!stack.peek().getToken().getContents().equals("")) {
				WordTree pattern = stack.pop();
				while(args.length() > cursor+pattern.getToken().getContents().length() && !curr.substring(cursor,cursor+pattern.getToken().getContents().length()).equals(pattern.getToken().getContents())){
					cursor++;
				}	
				if (myColor.get(pattern.getFunction()) != null) {
					setJTextPaneFont(this.txt, new Font("Serif", Font.CENTER_BASELINE, 12), myColor.get(pattern.getFunction()), cursor, cursor+pattern.getToken().getContents().length());
				}
				else {
					setJTextPaneFont(this.txt, new Font("Serif", Font.CENTER_BASELINE, 12), myColor.get("none"), cursor, cursor+pattern.getToken().getContents().length());
				}
			} else {
				WordTree wtTmp = stack.pop();
				for (int i = wtTmp.nodeSize()-1 ; i >= 0 ; i--){
					stack.push(wtTmp.getNode(i));
				}
			}
		}

	}

	public static void setJTextPaneFont(JTextPane jtp, Font font, Color c, int beg, int length) {
		// Start with the current input attributes for the JTextPane. This
		// should ensure that we do not wipe out any existing attributes
		// (such as alignment or other paragraph attributes) currently
		// set on the text area.
		MutableAttributeSet attrs = jtp.getInputAttributes();

		// Set the font family, size, and style, based on properties of
		// the Font object. Note that JTextPane supports a number of
		// character attributes beyond those supported by the Font class.
		// For example, underline, strike-through, super- and sub-script.
		StyleConstants.setFontFamily(attrs, font.getFamily());
		StyleConstants.setFontSize(attrs, font.getSize());
		StyleConstants.setItalic(attrs, (font.getStyle() & Font.ITALIC) != 0);
		StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);

		// Set the font color
		StyleConstants.setForeground(attrs, c);

		// Retrieve the pane's document object
		StyledDocument doc = jtp.getStyledDocument();

		// Replace the style for the entire document. We exceed the length
		// of the document by 1 so that text entered at the end of the
		// document uses the attributes.
		doc.setCharacterAttributes(beg, length, attrs, false);
	}


	public static Colorisation init(final String [] args, final JTextPane txt) throws BadLocationException {
		final  Colorisation color = new Colorisation(args, txt);
		Timer timer = new Timer();
		timer.schedule (new TimerTask() {
			public void run() {
				String s = txt.getText();
				color.refresh(s);
			}
		}, 0, 1000);
		return color;
	}
}