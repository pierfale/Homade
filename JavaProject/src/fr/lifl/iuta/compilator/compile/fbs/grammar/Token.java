package fr.lifl.iuta.compilator.compile.fbs.grammar;

/**
 * 
 * @author falezp
 * 
 * Représente un mot du langage avec une fonction non-déterminé
 *
 */
public class Token {
	
	private static final String [][] bracket  = {	{"{", "}"},
													{"(", ")"},
													{"[", "]"}};
	private String contents;
	private int line;
	
	public Token(String contents, int line) {
		this.contents = contents;
		this.line = line;
	}
	
	public String getContents() {
		return contents;
	}
	
	public int getLine() {
		return line;
	}

	public boolean isOpenBracket() {
		for(int i=0; i<bracket.length; i++) {
			if(bracket[i][0].equals(contents))
				return true;
		}
		return false;
	}
	
	public boolean isCloseBracket(String o) {
		for(int i=0; i<bracket.length; i++) {
			if(bracket[i][0].equals(o) && bracket[i][1].equals(contents))
				return true;
		}
		return false;
	}
	
	public boolean isCloseBracket() {
		for(int i=0; i<bracket.length; i++) {
			if(bracket[i][1].equals(contents))
				return true;
		}
		return false;
	}


}
