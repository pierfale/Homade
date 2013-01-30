package compile.fbs.grammar;

public interface Word {
	
	public boolean isTerminal();
	public boolean match(String s);

}
