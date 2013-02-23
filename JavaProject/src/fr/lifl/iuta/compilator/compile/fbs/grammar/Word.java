package fr.lifl.iuta.compilator.compile.fbs.grammar;

import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public interface Word {
	
	public boolean isTerminal();
	public WordTree match(WordList wl, boolean infinite);
	public boolean equals(String s);

}
