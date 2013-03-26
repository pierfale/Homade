package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;
import java.util.Stack;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

/**
 * 
 * @author falezp
 * 
 * Etape de vérification des variables et fonctions
 * Prend en entrée un arbre de Tokens.
 * Retourne une version modifié de cette arbre
 *
 */

public class Check {
	
	public static boolean exec(WordTree wt) {
		Rapport.addLine("<h2>Verification des variables</h2>");
		if(VariableCheck.exec(wt, new ArrayList<Variable>(), new Stack<WordTree>()) == null)
			return false;
		Rapport.addLine("Final : <br />"+wt.display());
		
		Rapport.addLine("<h2>Verification des fonctions</h2>");
		if(!FunctionCheck.exec(wt))
			return false;
		return true;
	}

}
