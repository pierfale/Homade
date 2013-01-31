package compile.fbs;

import java.io.FileNotFoundException;

import compile.fbs.grammar.Grammar;
import compile.fbs.grammar.Lexer;
import compile.fbs.grammar.Parser;
import compile.fbs.grammar.WordList;

public class Main {
	
	public static void main(String [] args) {
		try {
			Grammar.load("fbsGrammar");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		for(String s : args) {
			try {
				ReadFile reader = new ReadFile(s);
				
				WordList wl = Lexer.exec(reader.getString());
				if(!Parser.exec(wl)) {
					System.out.println("fin...");
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
