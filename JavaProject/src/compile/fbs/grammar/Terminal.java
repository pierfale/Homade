package compile.fbs.grammar;

import java.util.regex.Pattern;

import compile.fbs.Rapport;

public class Terminal implements Word {
	
	private String word;
	private boolean preg_match;
	
	public Terminal(String word) {
		preg_match = false;
		if(word.charAt(0) == '"' && word.charAt(word.length()-1) == '"')
			this.word = word.substring(1, word.length()-1);
		else if(word.charAt(0) == '[' && word.charAt(word.length()-1) == ']') {
			this.word = word.substring(1, word.length()-1);
			preg_match = true;
		}
		else
			this.word = word;
		
	}

	public boolean isTerminal() {
		return true;
	}

	public boolean equals(String s) {
		if(preg_match) {
			return Match.equals(word, s);
		}
		else return s.equals(word);
	}
	


	public WordTree match(WordList wl, boolean infinite) {
		if(preg_match) {
			if(wl.size() == 1 && Match.equals(word, wl.get(0).getContents())) {
				//System.out.println("match succes : "+wl.get(0).getContents()+"="+word);
				return new WordTree(wl.get(0).getContents());
			}
			else
				return null;
		}
		else {
			if(wl.size() == 1 && wl.get(0).getContents().equals(word)) {
				//System.out.println("match succes : "+wl.get(0).getContents()+"="+word);
				return new WordTree(wl.get(0).getContents());
			}
			else
				return null;
		}
	}

	public String getWord() {
		return word;
	}

	public int getLength() {
		return 1;
	}
	
}
