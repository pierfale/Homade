package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private String contents;
	private String function;
	private ArrayList<String> varaible;
	
	public WordTree() {
		node = new ArrayList<WordTree>();
		varaible = new ArrayList<String>();
		contents = "";
		function = "";
	}
	
	public WordTree(String contents) {
		node = new ArrayList<WordTree>();
		this.contents = contents;
		function = "";
	}
	
	public WordTree cpy() {
		WordTree retour = new WordTree(contents) ;
		retour.setFunction(function);
		for(int i=0; i<node.size(); i++) {
			retour.addNode(node.get(i));
		}
		return retour;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public String getContents() {
		return contents;
	}
	
	public String getFunction() {
		return function;
	}
	
	public void addNode(WordTree wt) {
		node.add(wt.cpy());
	}
	
	public boolean contains(WordTree wt) {
		if(wt == this)
			return true;
		for(int i=0; i<node.size(); i++) {
			if(node.get(i).contains(wt))
				return true;
		}
		return false;
	}
	
	public int size() {
		int size = 0;
		if(!contents.equals(""))
			size = 1;
		for(int i=0; i<node.size(); i++)
			size += node.get(i).size();
		return size;
	}
	
	public void clear() {
		function = "";
		contents = "";
		node = new ArrayList<WordTree>();
	}
	
	public WordTree getNode(int i) {
		if(i >=0 && i<node.size())
			return node.get(i);
		else
			System.out.println("erreur borne WordTree : "+i);
		return null;
	}
	
	public String toString() {
		String s = "+"+contents;
		if(!function.equals("")) 
			s += "("+function+")";
		s += " => ";
		for(int i=0; i<node.size(); i++) {
			s += node.get(i).toString();
			if(i+1 != node.size())
				s += ", ";
		}
		return s;
	}
	
	public String display() {
		int span = 1;
		if(node.size() > 0)
			span = node.size();
		String s = "<table style=\"border: 1px solid black;text-align: center\"><tr><td colspan=\""+span+"\">"+contents;
		if(!function.equals(""))
			s += " ("+function+")";
		
		s += "</td></tr>";
		if(node.size() > 0) {
			s += "<tr>";
			for(int i=0; i<node.size(); i++)
				s += "<td>"+node.get(i).display()+"</td>";
			s += "</tr>";
		}
		s += "</table>";
		return s;
	}
	
	public ArrayList<Object[]> varaibleChecker() {
		ArrayList<Object[]> retour = new ArrayList<Object[]>();
		Rapport.addLine(contents+" ("+function+") : ");
		if(function.equals("declaration_instruction")) {
			String v = variableName();
			Rapport.addLineSuccess("nouvelle déclaration variable : "+v+"<br />");
			Object[] tab = new Object[2];
			tab[0] = v;
			tab[1] = new Boolean(true);
			retour.add(tab);
			return retour;
		}
		else if(function.equals("variable_name")) {
			String v = variableName();
			Rapport.addLineSuccess("nouvelle variable : "+v+"<br />");
			Object[] tab = new Object[2];
			tab[0] = v;
			tab[1] = new Boolean(false);
			retour.add(tab);
			return retour;
		}
		else if(function.equals("block_instruction")) {
			for(int i=0; i<node.size(); i++) {
				ArrayList<Object[]> tmp = node.get(i).varaibleChecker();
				if(tmp != null) {
					for(int j=0; j<tmp.size(); j++) {
						if(retour.contains(tmp.get(j)[0]) && ((Boolean)tmp.get(j)[1]).booleanValue()) {
							Rapport.addLineError("déclaration de variable en double : "+tmp.get(j));
							return null;
						}
						if(!retour.contains(tmp.get(j)[0]) && !((Boolean)tmp.get(j)[1]).booleanValue()) {
							Rapport.addLineError("variable non déclaré : "+tmp.get(j));
							return null;
						}
						retour.add(tmp.get(j));
					}
				}
				else 
					return null;
			}
			return new ArrayList<Object[]>();
		}
		else {
			for(int i=0; i<node.size(); i++) {
				ArrayList<Object[]> tmp = node.get(i).varaibleChecker();
				if(tmp != null) {
					for(int j=0; j<tmp.size(); j++) {
						if(retour.contains(tmp.get(j))) {
							Rapport.addLineError("variable en double : "+tmp.get(j));
							return null;
						}
						retour.add(tmp.get(j));
					}
				}
				else 
					return null;
			}
			return retour;
		}
		
	}
	
	public String variableName() {
		if(function.equals("declaration_instruction")) {
			for(int i=0; i<node.size(); i++) {
				String tmp = node.get(i).variableName();
				if(tmp != null) {
					return tmp;
				}
			}
		}
		else if(function.equals("variable_name") && !contents.equals("")) {
			return contents;
		}
		else {
			for(int i=0; i<node.size(); i++) {
				String tmp = node.get(i).variableName();
				if(tmp != null) {
					return tmp;
				}
			}
		}	
		return null;
	}
}
