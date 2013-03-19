package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class VariableCheck {

	public static ArrayList<Token> exec(WordTree wt, ArrayList<Token> variables) {
		ArrayList<Token> retour = new ArrayList<Token>();
		Rapport.addLine(wt.getToken().getContents()+" ("+wt.getFunction()+") [nb var="+variables.size()+"] : ");
		if(wt.getFunction().equals("declaration_instruction")) {
			Token t = wt.variableName();
			if(wt.contains(variables, t)) {
				Rapport.addLineError("variable redéclaré : "+t.getContents()+"<br />");
				return null;	
			}
			Rapport.addLineSuccess("nouvelle déclaration variable : "+t.getContents()+"<br />");
			retour.add(t);
			return retour;
		}
		if(wt.getFunction().equals("parameter_init")) {
			Token t = wt.variableName();
			if(wt.contains(variables, t)) {
				Rapport.addLineError("variable redéclaré : "+t.getContents()+"<br />");
				return null;	
			}
			Rapport.addLineSuccess("nouvelle déclaration variable : "+t.getContents()+"<br />");
			retour.add(t);
			return retour;
		}
		else if(wt.getFunction().equals("variable_name")) {
			Token t = wt.variableName();
			if(!wt.contains(variables, t)) {
				Rapport.addLineError("variable non déclaré : "+t.getContents());
				return null;
			}
			Rapport.addLineSuccess("variable : "+t.getContents()+"<br />");
			return new ArrayList<Token>();
		}
		else {
			ArrayList<Token> variables2 = (ArrayList<Token>)variables.clone();
			for(int i=0; i<wt.nodeSize(); i++) {
				
				if(wt.getNode(i).getToken().getContents().equals("") && (wt.getNode(i).getFunction().equals("block_instruction") || wt.getNode(i).getFunction().equals("function"))) {
					variables2 = (ArrayList<Token>)variables.clone();
				}
				
				ArrayList<Token> tmp = VariableCheck.exec(wt.getNode(i), variables2);
				if(tmp == null)
					return null;
				for(int j=0; j<tmp.size(); j++) {
					if(!wt.contains(variables2, tmp.get(j))) {
						variables2.add(tmp.get(j));
					}
				}

			}
			if(wt.getFunction().equals("block_instruction"))  {
				for(int i=0; i<variables2.size(); i++) {
					if(wt.contains(variables, variables2.get(i)))
						wt.deleteVar.add(variables2.get(i).getContents());
				}
				return variables;
			}
			else
				return variables2;
		}
		
	}

}
