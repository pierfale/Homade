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

	public boolean match(String s) {
		return target.match(s);
	}
}
