package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;

public class NumberTranslate {
	
	private static ArrayList<Token> createList(WordTree node) {
		ArrayList<Token> retour = new ArrayList<Token>();
		if(node == null)
			return new ArrayList<Token>();
		if(!node.getToken().getContents().equals(""))
			retour.add(node.getToken());
		for(int i=0; i<node.nodeSize(); i++) {
			ArrayList<Token> tmp = createList(node.getNode(i));
			for(int j=0; j<tmp.size(); j++)
				retour.add(tmp.get(j));
		}
		return retour;
	}

	private static ExpressionTree createTree(ArrayList<Token> list, int currPriority) {
		ExpressionTree retour = new ExpressionTree();
		int i=0; 
		while(i<list.size()) {
			if(list.get(i).getContents().equals("(")) {
				ArrayList<Token> tmp = new ArrayList<Token>();
				i++;
				int parenthese = 0;
				while(!list.get(i).getContents().equals(")") || parenthese > 0) {
					tmp.add(list.get(i));
					if(list.get(i).getContents().equals("("))
						parenthese++;
					else if(list.get(i).getContents().equals(")"))
						parenthese--;
					i++;
				}
				retour.add(createTree(tmp, 1));
			}
			else {
				if(isNumber(list.get(i).getContents()) || isVariable(list.get(i).getContents())|| isFunction(list.get(i).getContents())) {
					if(i == list.size()-1)
						retour.add(new ExpressionTree(list.get(i).getContents()));
					else if(priority(list.get(i+1).getContents()) == currPriority) {
						retour.add(new ExpressionTree(list.get(i).getContents()));
					}
					else if(isFunction(list.get(i).getContents()) && i+1 < list.size() && list.get(i+1).getContents().equals("(")) {
						ExpressionTree tmp = new ExpressionTree();
						tmp.add(new ExpressionTree("_FUN_"));
						tmp.add(new ExpressionTree(list.get(i).getContents()));
						i += 2;
						int parenthese = 0;
						ArrayList<Token> curr = new ArrayList<Token>();
						while(i+1<list.size()  && (parenthese > 0 || !list.get(i).getContents().equals(")"))) {
							if(parenthese == 0 && list.get(i).getContents().equals(",")) {
								tmp.add(createTree(curr, 1));
								curr.clear();
							}
							else
								curr.add(list.get(i));
							if(list.get(i).getContents().equals("("))
								parenthese++;
							else if(list.get(i).getContents().equals(")"))
								parenthese--;
							i++;
						}
						if(curr.size() > 0)
							tmp.add(createTree(curr, 1));
						retour.add(tmp);
					}
					else {
						int priority = priority(list.get(i+1).getContents());
						ArrayList<Token> tmp = new ArrayList<Token>();
						int parenthese = 0;
						while(i+1<list.size()  && (parenthese > 0 || list.get(i).getContents().equals("(") || isNumber(list.get(i).getContents())|| isVariable(list.get(i).getContents()) || priority(list.get(i).getContents()) > currPriority)) {
							tmp.add(list.get(i));
							if(list.get(i).getContents().equals("("))
								parenthese++;
							else if(list.get(i).getContents().equals(")"))
								parenthese--;
							i++;
						}
						if(i+1 == list.size())
							tmp.add(list.get(i));
						else
							i--;
						retour.add(createTree(tmp, priority));
						
					}
					
				} else {
					retour.add(new ExpressionTree(list.get(i).getContents()));
				}

			}
			
			i++;
		}
		return retour;
	}
	
	public static String translate(ExpressionTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		if(tree.nodeSize() > 0 && tree.get(0).value().equals("_FUN_")) {
			retour += FunctionTranslate.translate(tree, addrVariable);
		}
		else {
			if(tree.value().equals("+"))
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			else if(tree.value().equals("-"))
				retour += "IP 2 1 1 "+Config.IP_operation_subtract+"\n";
			else if(tree.value().equals("/"))
				retour += "IP 2 1 1 "+Config.IP_operation_divide+"\n";
			else if(tree.value().equals("*"))
				retour += "IP 2 1 1 "+Config.IP_operation_multiplie+"\n";
			else if(tree.value().equals("%"))
				retour += "IP 2 1 1 "+Config.IP_operation_modulo+"\n";
			else if(isNumber(tree.value()))
				retour += "LIT "+tree.value()+"\n";
			else if(addrVariable.get(tree.value()) != null){
				int offset = 0;
				int address = addrVariable.get(tree.value()).getAddress();
				retour += VariableManager.get(address, offset);
			}
			
			for(int i=0; i<tree.nodeSize(); i++) {
				if(i == 0)
					retour +=  translate(tree.get(i), addrVariable);		
				if(i%2 == 1 && i+1<tree.nodeSize())
					retour +=  translate(tree.get(i+1), addrVariable);
				else if(i%2 == 0 &&  i-1 >= 0)
					retour +=  translate(tree.get(i-1), addrVariable);
			}
		}
		return retour;
	}
	
	public static String exec(WordTree node, Map<String, MemoryBlock> addrVariable) {
		ArrayList<Token> tmp = createList(node);
		return translate(createTree(createList(node), 1), addrVariable);
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
		WordList tmp = new WordList();
		tmp.add(s, 0);
		return Grammar.match(tmp, "variable_name");
	}

	public static boolean isFunction(String s) {
		WordList tmp = new WordList();
		tmp.add(s, 0);
		return Grammar.match(tmp, "function_name");
	}
}
