package fr.lifl.iuta.compilator.compile.fbs.grammar;

import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

/**
 * 
 * @author falezp
 * 
 * Représente un mot non-terminal
 * Il pointe vers une regles (Words)
 *
 */

public class NonTerminal implements Word {

	private String targetS;
	private Words target;
	
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

	public WordTree match(WordList wl, boolean infinite) {
		return target.match(wl, infinite);
	}

	public boolean equals(String s) {
		return false;
	}
	
	public String toString() {
		return "non terminal : "+target.getName();
	}
}
