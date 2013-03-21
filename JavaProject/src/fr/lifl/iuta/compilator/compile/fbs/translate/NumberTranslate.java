package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;
import java.util.Map;

import fr.lifl.iuta.compilator.base.Util;
import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;

public class NumberTranslate {
	
	private static WordTree createList(WordTree node) {
		WordTree retour = new WordTree();
		if(node == null)
			return new WordTree();
		if(node.getFunction().equals("variable") && node.nodeSize() == 4) {
			WordTree tmp2 = new WordTree();
			tmp2.setFunction("variable");
			for(int i=0; i<node.nodeSize(); i++) {
				if(i == 0) {
					tmp2.addNode(node.getNode(0).getNode(0));
				}
				else if(i == 2) {
					tmp2.addNode(node.getNode(2));
				}
				else {
					WordTree tmp = createList(node.getNode(i));
					
					for(int j=0; j<tmp.nodeSize(); j++)
						tmp2.addNode(tmp.getNode(j));
				}
			}
			retour.addNode(tmp2);
		}
		else if(node.getFunction().equals("call_function") && node.nodeSize() >= 3) {
			WordTree tmp2 = new WordTree();
			tmp2.setFunction("call_function");
			for(int i=0; i<node.nodeSize(); i++) {
				if(node.nodeSize() == 4 && i == 2) {
					tmp2.addNode(node.getNode(2));
				}
				else {
					WordTree tmp = createList(node.getNode(i));
					for(int j=0; j<tmp.nodeSize(); j++)
						tmp2.addNode(tmp.getNode(j));
				}
			}
			retour.addNode(tmp2);			
		}
		else if(node.getFunction().equals("variable") && node.nodeSize() == 1) {
			WordTree tmp2 = new WordTree();
			tmp2.setFunction("variable");
			tmp2.addNode(node.getNode(0).getNode(0));
			retour.addNode(tmp2);
		}
		else {
			if(!node.getToken().getContents().equals(""))
				retour.addNode(new WordTree(node.getToken(), node.getFunction()));
			for(int i=0; i<node.nodeSize(); i++) {
				WordTree tmp = createList(node.getNode(i));
				for(int j=0; j<tmp.nodeSize(); j++)
					retour.addNode(tmp.getNode(j));
			}
		}
		return retour;
	}

	private static WordTree createTree(WordTree list, int currPriority) {
		WordTree retour = new WordTree();
		if(list.nodeSize() >= 3 && list.getNode(1).getToken().getContents().equals("(")) { //function
			retour.setFunction("call_function");
			for(int i = 0; i<list.nodeSize();i++)
				retour.addNode(list.getNode(i));
		}
		else if(list.nodeSize() >= 4 &&list.getNode(1).getToken().getContents().equals("[")) { // array
			retour.setFunction("variable");
			for(int i = 0; i<list.nodeSize();i++)
				retour.addNode(list.getNode(i));
		}
		else {
			int i=0; 
			while(i<list.nodeSize()) {
				if(list.getNode(i).nodeSize() > 1) {
					retour.addNode(createTree(list.getNode(i), 1));
				}
				else if(list.getNode(i).getToken().getContents().equals("(")) {
					WordTree tmp = new WordTree();
					i++;
					int parenthese = 0;
					while(!list.getNode(i).getToken().getContents().equals(")") || parenthese > 0) {
						tmp.addNode(list.getNode(i));
						if(list.getNode(i).getToken().getContents().equals("("))
							parenthese++;
						else if(list.getNode(i).getToken().getContents().equals(")"))
							parenthese--;
						i++;
					}
					retour.addNode(createTree(tmp, 1));
				}
				else {
					if(list.getNode(i).getFunction().equals("variable")) {
						retour.addNode(list.getNode(i));
					}
					else if(isNumber(list.getNode(i).getToken().getContents()) || isVariable(list.getNode(i).getToken().getContents())|| isFunction(list.getNode(i).getToken().getContents())) {
						if(i == list.nodeSize()-1)
							retour.addNode(new WordTree(list.getNode(i).getToken(), list.getNode(i).getFunction()));
						else if(priority(list.getNode(i+1).getToken().getContents()) == currPriority) {
							retour.addNode(new WordTree(list.getNode(i).getToken(), list.getNode(i).getFunction()));
						}
						else {
							int priority = priority(list.getNode(i+1).getToken().getContents());
							WordTree tmp = new WordTree();
							int parenthese = 0;
							while(i+1<list.nodeSize()  && (parenthese > 0 || list.getNode(i).getToken().getContents().equals("(") || isNumber(list.getNode(i).getToken().getContents())|| isVariable(list.getNode(i).getToken().getContents()) || priority(list.getNode(i).getToken().getContents()) > currPriority)) {
								tmp.addNode(list.getNode(i));
								if(list.getNode(i).getToken().getContents().equals("("))
									parenthese++;
								else if(list.getNode(i).getToken().getContents().equals(")"))
									parenthese--;
								i++;
							}
							if(i+1 == list.nodeSize())
								tmp.addNode(list.getNode(i));
							else
								i--;
							retour.addNode(createTree(tmp, priority));
							
						}
						
					} else {
						retour.addNode(new WordTree(list.getNode(i).getToken(), list.getNode(i).getFunction()));
					}
	
				}
				
				i++;
			}
		}
		return retour;
	}
	
	public static String translate(WordTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		boolean through = true;
		if(tree.nodeSize() >= 3 && isFunction(tree.getNode(0).getToken().getContents()) && tree.getNode(1).getToken().getContents().equals("(")) { //function
			retour += FunctionTranslate.translate(tree, addrVariable);
		}
		else {
			if(tree.getToken().getContents().equals("+"))
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			else if(tree.getToken().getContents().equals("-"))
				retour += "IP 2 1 1 "+Config.IP_operation_subtract+"\n";
			else if(tree.getToken().getContents().equals("/"))
				retour += "IP 2 1 1 "+Config.IP_operation_divide+"\n";
			else if(tree.getToken().getContents().equals("*"))
				retour += "IP 2 1 1 "+Config.IP_operation_multiplie+"\n";
			else if(tree.getToken().getContents().equals("%"))
				retour += "IP 2 1 1 "+Config.IP_operation_modulo+"\n";
			else if(isNumber(tree.getToken().getContents()))
				retour += "LIT "+tree.getToken().getContents()+"\n";
			else if(tree.nodeSize() > 0 && tree.getNode(0).getFunction().equals("variable_name")){
				retour += tree.translate(addrVariable);
				System.out.println("-----\n"+retour+"\n------");
				through = false;
			}
		
			
			for(int i=0; through && i<tree.nodeSize(); i++) {
				if(i == 0)
					retour +=  translate(tree.getNode(i), addrVariable);		
				if(i%2 == 1 && i+1<tree.nodeSize())
					retour +=  translate(tree.getNode(i+1), addrVariable);
				else if(i%2 == 0 &&  i-1 >= 0)
					retour +=  translate(tree.getNode(i-1), addrVariable);
			}
		}
		return retour;
	}
	
	public static String exec(WordTree node, Map<String, MemoryBlock> addrVariable) {
		Rapport.addLine("===begin===");
		Rapport.add("Traduction chaine numérique "+node.display());
		WordTree tmp = createList(node);
		Rapport.add("Etape intermédiare"+tmp.display());
		WordTree tmp2 = createTree(createList(node), 1);
		Rapport.add("Etape Final"+tmp2.display()+"<br />");
		String retour = translate(tmp2, addrVariable);
		Rapport.addLine("Traduction : <br />"+Util.lnToHTML(retour)+"====end=====");
		return retour;
	}
	
	public static int priority(String op) {
		if(op.equals("+") || op.equals("-"))
			return 1;
		else if(op.equals("*") || op.equals("/") || op.equals("%"))
			return 2;
		return -1;
	}
	
	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			
		}
		try {
			Double.parseDouble(s);
			return true;
		}
		catch(NumberFormatException e) {
			
		}
		return false;
	}
	
	public static boolean isVariable(String s) {
		boolean save = Rapport.gen;
		Rapport.gen = false;
		WordList tmp = new WordList();
		tmp.add(s, 0);
		boolean r = Grammar.match(tmp, "variable_name");
		Rapport.gen = save;
		return r;
		
	}

	public static boolean isFunction(String s) {
		boolean save = Rapport.gen;
		Rapport.gen = false;
		WordList tmp = new WordList();
		tmp.add(s, 0);
		boolean r = Grammar.match(tmp, "function_name");
		Rapport.gen = save;
		return r;
	}
}
