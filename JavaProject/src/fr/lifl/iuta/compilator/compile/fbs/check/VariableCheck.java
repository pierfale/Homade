package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

/**
 * 
 * @author falezp
 * 
 * Vérifie la cohérence des variables :
 * 	-unicité (seulement le nom)
 * 	-existance
 * 	-portée
 *
 */

public class VariableCheck {
	
	

	@SuppressWarnings("unchecked")
	public static ArrayList<Variable> exec(WordTree wt, ArrayList<Variable> variables, Stack<WordTree> stack) {
		ArrayList<Variable> retour = new ArrayList<Variable>();
		Rapport.addLine(wt.getToken().getContents()+" ("+wt.getFunction()+") [nb var="+variables.size()+"] : ");
		if(wt.getFunction().equals("declaration_instruction")) {
			Token t = wt.variableName();
			Variable v = new Variable(t.getContents(), wt.variableType().getContents(), t.getLine());
			if(Variable.contains(variables, v.getName())) {
				Rapport.addLineError("variable redéclaré : "+v.getName()+" ["+v.getType()+"]<br />");
				return null;	
			}
			Rapport.addLineSuccess("nouvelle déclaration variable : "+v.getName()+" ["+v.getType()+"]<br />");
			retour.add(v);
			return retour;
		}
		if(wt.getFunction().equals("parameter_init")) {
			Token t = wt.variableName();
			Variable v = new Variable(t.getContents(), wt.variableType().getContents(), t.getLine());
			if(Variable.contains(variables, v.getName())) {
				Rapport.addLineError("variable redéclaré : "+v.getName()+" ["+v.getType()+"]<br />");
				return null;	
			}
			Rapport.addLineSuccess("nouvelle déclaration variable : "+v.getName()+" ["+v.getType()+"]<br />");
			retour.add(v);
			return retour;
		}
		else if(wt.getFunction().equals("variable_name")) {
			Token t = wt.variableName();
			if(!Variable.contains(variables,t.getContents())) {
				Rapport.addLineError("variable non déclaré : "+t.getContents());
				return null;
			}
			
			String type = Variable.getType(variables,t.getContents());
			Rapport.addLineSuccess("variable : "+t.getContents()+"<br />");
			
			WordTree lastNode = stack.pop();
			while(Variable.isTypeFunction(stack.peek())) {
				lastNode = stack.pop();
			}
			
			boolean save = Rapport.gen;
			Rapport.gen = false;
			
			Map<String, String> types = new HashMap<String, String>();
			types.put("string", "string");
			types.put("int", "number");
			WordTree ret = Grammar.search(types.get(type)).match(lastNode.toList(), false);
			if(ret != null) {
				lastNode.set(ret);
			}
			else {
				Rapport.addLineError("Erreur du type de la variable dans le contexte");
				return null;
			}
			Rapport.gen = save;

			return new ArrayList<Variable>();
		}
		else {
			ArrayList<Variable> variables2 = (ArrayList<Variable>)variables.clone();
			for(int i=0; i<wt.nodeSize(); i++) {
				
				if(wt.getNode(i).getToken().getContents().equals("") && (wt.getNode(i).getFunction().equals("block_instruction") || wt.getNode(i).getFunction().equals("function"))) {
					variables2 = (ArrayList<Variable>)variables.clone();
				}
				stack.push(wt);
				ArrayList<Variable> tmp = VariableCheck.exec(wt.getNode(i), variables2, stack);
				if(tmp == null)
					return null;
				for(int j=0; j<tmp.size(); j++) {
					if(!Variable.contains(variables2, tmp.get(j).getName())) {
						variables2.add(tmp.get(j));
					}
				}

			}
			if(wt.getFunction().equals("block_instruction"))  {
				for(int i=0; i<variables2.size(); i++) {
					if(Variable.contains(variables, variables2.get(i).getName()))
						wt.deleteVar.add(variables2.get(i).getName());
				}
				return variables;
			}
			else
				return variables2;
		}
		
	}

}
