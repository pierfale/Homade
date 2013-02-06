package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class Lexer {
	
	private static final int [] spaceChar = {' ', '\t'};
	
	
	public static WordList exec(String source) {
		WordList wl = new WordList();
		Rapport.addLine("<h2>Recherche des mots</h2>");
		BufferedReader reader = new BufferedReader(new StringReader(source));
		int nbLine = 1;
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(line != null) {
			if(!isEmpty(line)) {
				Rapport.addLine("ligne "+nbLine+" : "+line);
				int beg = 0;
				int end = 1;
				while(beg < line.length()) {
					while(isSpace(line.charAt(beg))) {
						beg++;
						end++;
					}
					while(end <= line.length() && Grammar.existWord(line.substring(beg, end)) == null) {
						end++;
						
					}	
					while(end <= line.length() && Grammar.existWord(line.substring(beg, end)) != null) {
						end++;
					}
					String name = Grammar.existWord(line.substring(beg, end-1));
					if(name != null) {
						Rapport.addLine("mot detecté: "+line.substring(beg, end-1)+" ["+name+"]");
						wl.add(line.substring(beg, end-1), name);
						beg = end-1;
					}
					else {
						Rapport.addLine("Mot inconnue : "+line+" ligne "+nbLine+" colonne "+beg);
						return null;
					}
					
				}
			}
			
			nbLine++;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wl;
	}
	
	private static boolean isEmpty(String expr) {
		for(int i=0; i<expr.length(); i++) {
			if(!isSpace(expr.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean isSpace(char c) {
		for(int i=0; i<spaceChar.length; i++) {
			if(spaceChar[i] == c) {
				return true;
			}
		}
		return false;
	}

}
