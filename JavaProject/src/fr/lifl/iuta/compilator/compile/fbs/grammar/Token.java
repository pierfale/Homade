package fr.lifl.iuta.compilator.compile.fbs.grammar;

public class Token {
	
	private static final String [][] bracket  = {	{"{", "}"},
													{"(", ")"},
													{"[", "]"}};
	private String contents;
	private String function;
	private String lex;
	
	public Token(String contents) {
		this.contents = contents;
		function = "";
		lex = "";
	}
	
	public Token(String contents, String lex) {
		this.contents = contents;
		function = "";
		this.lex = lex;
	}
	
	
	public String getContents() {
		return contents;
	}
	
	public String getFunction() {
		return function;
	}
	
	public String getLex() {
		return lex;
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
	
	public void setFunction(String function) {
		this.function = function;
		
	}

}
