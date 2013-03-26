package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

/**
 * 
 * @author falezp
 * 
 * Etape de reconnaissance du lexique
 * Prend en entrée un String contenant le source
 * Retourne une liste de Token
 *
 */

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
					while(beg < line.length() && isSpace(line.charAt(beg))) {
						beg++;
						end++;
					}
					if(beg < line.length()) {
						String name = null;
						if(line.charAt(beg) == '"') {
							end += 2;
							while(end-2 < line.length() && line.charAt(end-2) != '"')
								end++;
							name = "";
							if(end-2 == line.length())
								return null;
						}
						else {
							while(end <= line.length() && Grammar.existWord(line.substring(beg, end)) == null) {
								end++;
								
							}
							while(end <= line.length() && Grammar.existWord(line.substring(beg, end)) != null) {
								end++;
							}
							name = Grammar.existWord(line.substring(beg, end-1));
						}
						if(name != null) {
							Rapport.addLine("mot detecté: "+line.substring(beg, end-1)+" ["+name+"]");
							wl.add(line.substring(beg, end-1), nbLine);
							beg = end-1;
						}
						else {
							Rapport.addLine("Mot inconnue : "+line+" ligne "+nbLine+" colonne "+beg);
							return null;
						}
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
