package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.base.Util;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class BooleanTranslate {
	
	public static WordTree createList(WordTree node) {
		WordTree retour = new WordTree();
		if(node == null)
			return new WordTree();
		if(node.getFunction().equals("number")) {
			WordTree tmp2 = new WordTree();
			tmp2.setFunction("number");
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
					else if(isNumber(list.getNode(i)) || isVariable(list.getNode(i))|| isFunction(list.getNode(i))) {
						if(i == list.nodeSize()-1)
							retour.addNode(new WordTree(list.getNode(i).getToken(), list.getNode(i).getFunction()));
						else if(priority(list.getNode(i+1).getToken().getContents()) == currPriority) {
							retour.addNode(new WordTree(list.getNode(i).getToken(), list.getNode(i).getFunction()));
						}
						else {
							int priority = priority(list.getNode(i+1).getToken().getContents());
							WordTree tmp = new WordTree();
							int parenthese = 0;
							while(i+1<list.nodeSize()  && (parenthese > 0 || list.getNode(i).getToken().getContents().equals("(") || isNumber(list.getNode(i))|| isVariable(list.getNode(i)) || priority(list.getNode(i).getToken().getContents()) > currPriority)) {
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
	
	public static String exec(WordTree node, Map<String, MemoryBlock> addrVariable) {
		Rapport.addLine("===begin=b=");
		Rapport.add("Traduction chaine boolean "+node.display());
		WordTree tmp = createList(node);
		Rapport.add("Etape intermédiare"+tmp.display());
		WordTree tmp2 = createTree(createList(node), 1);
		Rapport.add("Etape Final"+tmp2.display()+"<br />");/*
		String retour = translate(tmp2, addrVariable);
		Rapport.addLine("Traduction : <br />"+Util.lnToHTML(retour)+"====end=b===");
		return retour;*/return "";
	}
	
	public static int priority(String op) {
		if(op.equals("||"))
			return 1;
		else if(op.equals("&&"))
			return 2;
		else if(op.equals("==") || op.equals("!=") || op.equals("<") || op.equals(">") || op.equals(">=") || op.equals("<="))
			return 3;
		return -1;
	}
	
	public static boolean isNumber(WordTree wt) {
		return wt.getFunction().equals("number");
	}

	public static boolean isVariable(WordTree wt) {
		return wt.getFunction().equals("variable");
	}
	
	public static boolean isFunction(WordTree wt) {
		return wt.getFunction().equals("call_function");
	}
}
