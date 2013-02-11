package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.util.ArrayList;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private Token token;
	private String function;
	private ArrayList<String> varaible;
	
	public WordTree() {
		node = new ArrayList<WordTree>();
		varaible = new ArrayList<String>();
		token = new Token("", 0);
		function = "";
	}
	
	public WordTree(Token token) {
		node = new ArrayList<WordTree>();
		varaible = new ArrayList<String>();
		this.token = token;
		function = "";
	}
	
	public WordTree cpy() {
		WordTree retour = new WordTree(token) ;
		retour.setFunction(function);
		for(int i=0; i<node.size(); i++) {
			retour.addNode(node.get(i));
		}
		return retour;
	}

	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public Token getToken() {
		return token;
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
		if(!token.getContents().equals(""))
			size = 1;
		for(int i=0; i<node.size(); i++)
			size += node.get(i).size();
		return size;
	}
	
	public void clear() {
		function = "";
		token = new Token("", 0);
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
		String s = "+"+token.getContents();
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
		String s = "<table style=\"border: 1px solid black;text-align: center\"><tr><td colspan=\""+span+"\">"+token.getContents();
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
	
	public ArrayList<Token> varaibleChecker(ArrayList<Token> variables) {
		ArrayList<Token> retour = new ArrayList<Token>();
		Rapport.addLine(token.getContents()+" ("+function+") : ");
		if(function.equals("declaration_instruction")) {
			Token t = variableName();
			if(contains(variables, t)) {
				Rapport.addLineError("variable redéclaré : "+t.getContents()+"<br />");
				return null;	
			}
			Rapport.addLineSuccess("nouvelle déclaration variable : "+t.getContents()+"<br />");
			retour.add(t);
			return retour;
		}
		else if(function.equals("variable_name")) {
			Token t = variableName();
			if(!contains(variables, t)) {
				Rapport.addLineError("variable non déclaré : "+t.getContents());
				return null;
			}
			Rapport.addLineSuccess("variable : "+t.getContents()+"<br />");
			return new ArrayList<Token>();
		}
		else {
			ArrayList<Token> variables2 = (ArrayList<Token>)variables.clone();
			for(int i=0; i<node.size(); i++) {
				ArrayList<Token> tmp = node.get(i).varaibleChecker(variables2);
				if(tmp == null)
					return null;
				for(int j=0; j<tmp.size(); j++) {
					if(!contains(variables2, tmp.get(j))) {
						variables2.add(tmp.get(j));
					}
				}
			}
			if(function.equals("block_instruction"))  {
				for(int i=0; i<variables2.size(); i++) {
					if(contains(variables, variables2.get(i)))
						varaible.add(variables2.get(i).getContents());
				}
				return variables;
			}
			else
				return variables2;
		}
		
	}
	
	public Token variableName() {
		if(function.equals("declaration_instruction")) {
			for(int i=0; i<node.size(); i++) {
				Token tmp = node.get(i).variableName();
				if(tmp != null) {
					return tmp;
				}
			}
		}
		else if(function.equals("variable_name") && !token.getContents().equals("")) {
			return token;
		}
		else {
			for(int i=0; i<node.size(); i++) {
				Token  tmp = node.get(i).variableName();
				if(tmp != null) {
					return tmp;
				}
			}
		}	
		return null;
	}
	
	public boolean contains(ArrayList<Token> list, Token t) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getContents().equals(t.getContents()))
				return true;
		}
		return false;
	}
	
	public String translate(Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		boolean through = true;
		if(function.equals("block_instruction")) {
			//xaxaxa...
		}
		if(function.equals("declaration_instruction")) {
			Token t = variableName();
			retour += "//Declaration variable "+t.getContents()+"\n";
			retour += "LIT "+(addrVariable.size()+Config.variable_memory_zone)+"\n";
			retour += "LIT 0\n";
			retour += "IP 2 0 1 "+Config.IP_add_variable_RAM+"\n";
			addrVariable.put(t.getContents(), addrVariable.size()+Config.variable_memory_zone);
		}
		if(function.equals("allocation_instruction")) {
			Token t = variableName();
			retour += "//Allocation variable "+t.getContents()+"\n";
			retour += "LIT "+addrVariable.get(t.getContents())+"\n";
			retour += node.get(2).translate(addrVariable);
			retour += "IP 2 0 1 "+Config.IP_add_variable_RAM+"\n";
			through = false;
		}
		if(function.equals("variable_name")) {
			Token t = variableName();
			retour += "//Recupération variable "+t.getContents()+"\n";
			retour += "LIT "+addrVariable.get(t.getContents())+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM+"\n";
			through = false;
		}
		if(function.equals("integer")) {
			if(!token.getContents().equals("")) {
				retour += "//Pose d'un integer sur la pile\n";
				retour += "LIT "+token.getContents()+"\n" ;
				through = false;
			}
		}
		if(function.equals("structure_if")) {
			if(node.size() > 0 && node.get(0).getToken().getContents().equals("if")) {
				retour += "//Structure IF\n";
				retour += node.get(2).translate(addrVariable);
				retour += "BZ LABEL\n";
			}
		}
		if(function.equals("boolean_expresion")) {
			retour += "//Premier element du boolean_expresion\n";
			retour += node.get(0).translate(addrVariable);
			retour += "//Dexieme element du boolean_expresion\n";
			retour += node.get(2).translate(addrVariable);
			retour += node.get(1).translate(addrVariable);
			through = false;
		}
		if(function.equals("op_compare")) {
			retour += "//Comparaison\n";
			if(node.size() > 0 && node.get(0).getToken().getContents().equals("<")) {
				retour += "IP 2 1 1 "+Config.IP_compare_lower+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals(">")) {
				retour += "IP 2 1 1 "+Config.IP_compare_upper+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals("==")) {
				retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals("<=")) {
				retour += "IP 2 1 1 "+Config.IP_compare_lowerEquals+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals(">=")) {
				retour += "IP 2 1 1 "+Config.IP_compare_upperEquals+"\n";
			}
			through = false;
		}		
		if(function.equals("boolean")) {
			if(token.getContents().equals("true"))
				retour += "LIT 1\n";
			if(token.getContents().equals("false"))
				retour += "LIT 0\n";			
		}		
		if(through) {
			for(int i=0; i<node.size(); i++) {
				retour += node.get(i).translate(addrVariable);
				
			}
		}
		return retour;
	}
}
