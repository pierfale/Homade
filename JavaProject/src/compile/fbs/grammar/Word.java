package compile.fbs.grammar;

public interface Word {
	
	public boolean isTerminal();
	public WordList match(WordList wl, int deep);
	public boolean equals(String s);
	public int getLength();

}
