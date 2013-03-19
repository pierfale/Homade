package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class Check {
	
	public static boolean exec(WordTree wt) {
		Rapport.addLine("<h2>Verification des variables</h2>");
		if(VariableCheck.exec(wt, new ArrayList<Token>()) == null)
			return false;
		Rapport.addLine("<h2>Verification des fonctions</h2>");
		if(!FunctionCheck.exec(wt))
			return false;
		return true;
	}

}
