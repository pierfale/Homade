package compile.fbs.grammar;

public class Terminal implements Word {
	
	private String word;
	
	public Terminal(String word) {
		this.word = word;
	}

	public boolean isTerminal() {
		return true;
	}

	public boolean match(String s) {
		if(word.equals(s)) {
			System.out.println("matched! : "+word+"="+s);
			return true;
		}
		else
			return false;
	}

}
