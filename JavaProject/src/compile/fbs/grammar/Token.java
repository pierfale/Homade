package compile.fbs.grammar;

public class Token {
	
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

	public void setFunction(String function) {
		this.function = function;
		
	}

}
