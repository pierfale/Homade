package compile.fbs.grammar;

import compile.fbs.Rapport;

public class Parser {
	
	public static WordTree exec(WordList wl) {
		Rapport.addLine("<h2>Recherche de correspondance grammatical</h2>");
		return Grammar.match(wl);
	}

}
