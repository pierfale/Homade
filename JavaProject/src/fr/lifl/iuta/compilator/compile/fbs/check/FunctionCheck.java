package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;
import java.util.Stack;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

/**
 * 
 * @author falezp
 *
 * Vérifie la cohérence des fonctions (nom, type des parametres) :
 * 	-l'unicité de celle-ci
 * 	-l'existance lors des appelles de fonction
 * 
 */

public class FunctionCheck {
	
	private static ArrayList<ArrayList<String>> functions;
	
	public static boolean exec(WordTree wt) {
		functions = new ArrayList<ArrayList<String>>();
		if(!functionList(wt))
			return false;
		if(!functionCall(wt))
			return false;
		return true;
	}
	
	private static boolean functionList(WordTree wt) {
		if(wt.getFunction().equals("function") && wt.nodeSize() >= 8) {
			ArrayList<String> f = new ArrayList<String>();
			f.add(wt.getNode(2).functionName().getContents());
			if(wt.getNode(4).getFunction().equals("list_parameter_init")) {
				Stack<WordTree> stack = new Stack<WordTree>();
				stack.push(wt.getNode(4));
				while(!stack.empty()) {
					if(stack.peek().getFunction().equals("list_parameter_init")) {
						if(stack.peek().nodeSize() == 1) {
							WordTree curr = stack.pop();
							stack.push(curr.getNode(0));
						}
						if(stack.peek().nodeSize() == 3) {
							WordTree curr = stack.pop();
							stack.push(curr.getNode(0));
							stack.push(curr.getNode(2));
						}
					}
					else if(stack.peek().getFunction().equals("parameter_init")) {
						f.add(stack.pop().getNode(0).getNode(0).getToken().getContents());
					}
					else
						stack.pop();
				}
			}
			Rapport.add("nouvelle fonction : ");
			for(int i=0; i<f.size(); i++) {
				if(i != 0)
					Rapport.add(", ");
				Rapport.add(f.get(i));	
			}
			Rapport.add("<br />");
			for(int i=0; i<functions.size(); i++) {
				if(f.size() == functions.get(i).size()) {
					boolean ok = true;
					for(int j=0; j<f.size(); j++) {
						if(!f.get(j).equals(functions.get(i).get(j)))
							ok = false;
					}
					if(ok) {
						Rapport.addLineError("Fonction déclaré en double");
						return false;
					}
				}
			}
			functions.add(f);
				
		}
		for(int i=0; i<wt.nodeSize(); i++) {
			if(!functionList(wt.getNode(i)))
				return false;
		}
		
		return true;
	}
	
	private static boolean functionCall(WordTree wt) {
		if(wt.getFunction().equals("call_function") && wt.nodeSize() >= 3) {
			String funName;
			int nArgs = 0;
			funName = wt.getNode(0).functionName().getContents();
			if(wt.getNode(2).getFunction().equals("list_parameter")) {
				Stack<WordTree> stack = new Stack<WordTree>();
				stack.push(wt.getNode(2));
				while(!stack.empty()) {
					if(stack.peek().getFunction().equals("list_parameter")) {
						if(stack.peek().nodeSize() == 1) {
							WordTree curr = stack.pop();
							stack.push(curr.getNode(0));
						}
						if(stack.peek().nodeSize() == 3) {
							WordTree curr = stack.pop();
							stack.push(curr.getNode(0));
							stack.push(curr.getNode(2));
						}
					}
					else if(stack.peek().getFunction().equals("parameter")) {
						nArgs++;
						stack.pop();
					}
					else
						stack.pop();
				}
			}
			Rapport.add("Appelle fonction : "+funName+" args : "+nArgs);

			Rapport.add("<br />");
			for(int i=0; i<functions.size(); i++) {
				if(funName.equals(functions.get(i).get(0)) && nArgs == functions.get(i).size()-1) {
					return true;
				}
			}
			Rapport.addLineError("Fonction non déclaré");
			return false;
		}
		for(int i=0; i<wt.nodeSize(); i++) {
			if(!functionCall(wt.getNode(i)))
				return false;
		}	
		return true;
	}

}
