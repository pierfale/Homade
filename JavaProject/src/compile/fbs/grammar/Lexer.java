package compile.fbs.grammar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Lexer {
	
	private static final int [] spaceChar = {'\n', '\t'};
	
	
	public static WordList exec(String source) {
		WordList wl = new WordList();
		System.out.println("==============");
		System.out.println("Source Lexer :");
		System.out.println("==============");
		BufferedReader reader = new BufferedReader(new StringReader(source));
		int nbLine = 1;
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(line != null) {
			System.out.println("line "+nbLine+" : "+line);
			if(!isEmpty(line)) {
				int beg = 0;
				int end = 1;
				while(beg < line.length()) {
					while(isSpace(line.charAt(beg))) {
						beg++;
						end++;
					}
					while(end <= line.length() && !Grammar.existWord(line.substring(beg, end))) {
						end++;
						
					}	
					while(end <= line.length() && Grammar.existWord(line.substring(beg, end))) {
						end++;
					}

					if(Grammar.existWord(line.substring(beg, end-1))) {
						System.out.println("word detected : "+line.substring(beg, end-1));
						wl.add(line.substring(beg, end-1));
						beg = end-1;
					}
					else {
						System.out.println("Mot inconnue : "+line+" ligne "+nbLine+" colonne "+beg);
						return null;
					}
					
				}
			}
			else 
				System.out.println("empty line : ignored!");
			
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
