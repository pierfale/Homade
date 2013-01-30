package compile.fbs;

import java.io.FileNotFoundException;

import compile.fbs.grammar.Grammar;

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
				reader.match();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
