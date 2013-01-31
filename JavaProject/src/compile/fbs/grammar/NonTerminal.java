package compile.fbs.grammar;

public class NonTerminal implements Word {

	String targetS;
	Words target;
	
	public NonTerminal(String target) {
		this.targetS = target;
	}
	
	public void setTarget(Words target) {
		this.target = target;
	}
	
	public String getTarget() {
		return targetS;
	}
	
	public Words target() {
		return target;
	}

	public boolean isTerminal() {
		return false;
	}

	public WordList match(WordList wl, int deep) {
		return target.match(wl, deep);
	}

	public boolean equals(String s) {
		return false;
	}
}
