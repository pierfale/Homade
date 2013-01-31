package compile.fbs.grammar;

public class Token {
	
	private String contents;
	private String function;
	
	public Token(String contents) {
		this.contents = contents;
		function = "";
	}
	
	public String getContents() {
		return contents;
	}
	
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
		
	}

}
