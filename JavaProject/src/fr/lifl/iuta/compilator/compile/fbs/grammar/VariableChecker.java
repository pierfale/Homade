package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class VariableChecker {
	
	public static boolean exec(WordTree wt) {
		Rapport.addLine("<h2>Verification des variables</h2>");
		return wt.varaibleChecker(new ArrayList<Token>()) != null;
	}

}
