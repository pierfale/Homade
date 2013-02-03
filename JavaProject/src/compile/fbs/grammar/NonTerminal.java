package compile.fbs.grammar;

public class NonTerminal implements Word {

	private String targetS;
	private Words target;
	private int length;
	
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

	public WordList match(WordList wl, boolean infinite) {
		return target.match(wl, infinite);
	}

	public boolean equals(String s) {
		return false;
	}
	
	public void calculatesLength() {
		int min  = 99;
		
		for(int i=0; i<target.size(); i++) {
			if(target.get(i).length < min &&target.get(i).length !=  0)
				min = target.get(i).length;
		}
		length = min;
	}
	
	

	public int getLength() {
		return length;
	}
}
