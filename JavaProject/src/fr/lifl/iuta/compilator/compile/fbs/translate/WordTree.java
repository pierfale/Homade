package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private Token token;
	private String function;
	private ArrayList<String> deleteVar;
	
	
	public WordTree() {
		node = new ArrayList<WordTree>();
		deleteVar = new ArrayList<String>();
		token = new Token("", 0);
		function = "";
	}
	
	public WordTree(Token token) {
		node = new ArrayList<WordTree>();
		deleteVar = new ArrayList<String>();
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
	
	public int nodeSize() {
		return node.size();
	}
	
	public String toString() {
		String s = "";
		if(!token.getContents().equals("")) {
			s += token.getContents();
			if(!function.equals("")) 
				s += "("+function+")";
			s += " => ";
		}
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
	
	public void displayText(int n) {
		for(int i=0; i<n; i++)
			System.out.print("| ");
		if(!token.getContents().equals(""))
			System.out.println(token.getContents());
		for(int i=0; i<node.size(); i++)
			node.get(i).displayText(n+1);
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
						deleteVar.add(variables2.get(i).getContents());
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
	
	public Token functionName() {
		 if(function.equals("function_name") && !token.getContents().equals("")) {
			return token;
		}
		else {
			for(int i=0; i<node.size(); i++) {
				Token  tmp = node.get(i).functionName();
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
		if(function.equals("function")) {
			if(node.size() > 4) {
				int lbl = LabelManager.getNext();
				retour += "_LBL"+lbl+"\n";
				FunctionManager.add(node.get(2).functionName().getContents(), lbl);
				if(node.get(4).getToken().getContents().equals(")")) {
					retour += node.get(6).translate(addrVariable);
					retour += "RET\n";
					if(node.size() == 9)
						retour += node.get(8).translate(addrVariable);
				}
				else {
					retour += node.get(4).translate(addrVariable);
					retour += node.get(7).translate(addrVariable);
					retour += "RET\n";
					if(node.size() == 10)
						retour += node.get(9).translate(addrVariable);
				}
			}
			through = false;
		}
		else if(function.equals("parameter_init")) {/*
			Token t = variableName();
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			retour += "IP 2 0 1 "+Config.IP_add_variable_RAM+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 0 1 "+Config.IP_add_variable_RAM+"\n";
			addrVariable.put(t.getContents(), new MemoryBlock(addr, 1));*/
		}
		else if(function.equals("parameter")) {
			int destination = MemoryBlock.nextFreeSegment(addrVariable);
			Token t = variableName();
			int origin = addrVariable.get(t.getContents()).getAddress();
			
			//create
			retour += "LIT "+destination+"\n";
			retour += "LIT "+Config.ram_varaibles_adress+"\n";
			retour += "LIT "+destination+"\n";
			retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
			retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
			retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			retour += "LIT "+destination+"\n";
			retour += "CALL _FUN__create_variable\n";
			
			VariableManager.copy(origin, destination);
			retour += "LIT "+destination+"\n";
		}	
		if(function.equals("call_function")) {
			int lbl = LabelManager.getNext();
			Token t =  node.get(2).functionName();
			retour += VariableManager.createFrame(MemoryBlock.nextFreeSegment(addrVariable));
			retour += "CALL _FUN_"+t.getContents()+"\n";
			//retour += VariableManager.deleteFrame();
		}
		else if(function.equals("declaration_instruction")) {
			Token t = variableName();
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			int size = 1;
			
			retour += VariableManager.create(addr, size);
			addrVariable.put(t.getContents(), new MemoryBlock(addr, size));
		}
		else if(function.equals("allocation_instruction")) {
			Token t = variableName();
			String value = node.get(2).translate(addrVariable);
			int offset = 0;
			int address = addrVariable.get(t.getContents()).getAddress();
			retour += VariableManager.set(address, offset, value);
			through = false;
		}
		else if(function.equals("variable_name")) {
			Token t = variableName();
			int offset = 0;
			int address = addrVariable.get(t.getContents()).getAddress();
			retour += VariableManager.get(address, offset);
			through = false;
		}
		else if(function.equals("structure")) {
			if(node.size() > 0 && node.get(0).getToken().getContents().equals("while")) {
				int lbl1 = LabelManager.getNext();
				retour += "_LBL"+lbl1+"\n";
				retour += node.get(2).translate(addrVariable);
				int lbl2 = LabelManager.getNext();
				retour += "BZ _LBL"+lbl2+"\n";
				retour += node.get(4).translate(addrVariable);
				retour += "BA _LBL"+lbl1+"\n";
				retour += "_LBL"+lbl2+"\n";
				through = false;
			}
		}
		else if(function.equals("structure_if")) {
			if(node.size() > 0 && node.get(0).getToken().getContents().equals("if")) {
				//retour += "//Structure IF\n";
				retour += node.get(2).translate(addrVariable);
				if(node.size() > 4) { // else
					//retour += "//duplication de la valeur du bz\n";
					retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
				}
				int lbl1 = LabelManager.getNext();
				retour += "BZ _LBL"+lbl1+"\n";
				//retour += "//Corps du if\n";
				retour += node.get(4).translate(addrVariable);
				retour += "_LBL"+lbl1+"\n";
				if(node.size() > 6) { // else
					int lbl2 = LabelManager.getNext();
					retour += "BNZ _LBL"+lbl2+"\n";
					//retour += "//Corps du else\n";
					retour += node.get(6).translate(addrVariable);
					retour += "_LBL"+lbl2+"\n";
				}
			}
			through = false;
		}
		else if(function.equals("number")) {
			retour += NumberTranslate.exec(this, addrVariable);
			through = false;
		}
		else if(function.equals("boolean_expresion")) {
			if(node.size() == 1) {
				retour += node.get(0).translate(addrVariable);
			}
			else {
				//retour += "//Premier element du boolean_expresion\n";
				retour += node.get(0).translate(addrVariable);
				//retour += "//Dexieme element du boolean_expresion\n";
				retour += node.get(2).translate(addrVariable);
				retour += node.get(1).translate(addrVariable);
			}
			through = false;
		}
		else if(function.equals("op_compare")) {
			//retour += "//Comparaison\n";
			if(node.size() > 0 && node.get(0).getToken().getContents().equals("<")) {
				retour += "IP 2 1 1 "+Config.IP_compare_lower+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals(">")) {
				retour += "IP 2 1 1 "+Config.IP_compare_upper+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals("==")) {
				retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals("!=")) {
				retour += "IP 2 1 1 "+Config.IP_compare_nonEquals+"\n";
			}			
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals("<=")) {
				retour += "IP 2 1 1 "+Config.IP_compare_lowerEquals+"\n";
			}
			else if(node.size() > 0 && node.get(0).getToken().getContents().equals(">=")) {
				retour += "IP 2 1 1 "+Config.IP_compare_upperEquals+"\n";
			}
			through = false;
		}	
		else if(function.equals("boolean")) {
			if(token.getContents().equals("true"))
				retour += "LIT 1\n";
			if(token.getContents().equals("false"))
				retour += "LIT 0\n";			
		}
		else if(function.equals("special_instruction")) {
			if(node.size() > 1 && node.get(0).getToken().getContents().equals("display")) {
				retour += node.get(1).translate(addrVariable);
				//retour += "//display\n";
				retour += "IP 1 0 1 "+Config.IP_special_display+"\n";
			}
			through = false;
		}
		if(through) {
			for(int i=0; i<node.size(); i++) {
				retour += node.get(i).translate(addrVariable);

				
			}
		}
		if(function.equals("block_instruction")) {
			for(int i=0; i<deleteVar.size(); i++) {
				addrVariable.get(deleteVar.get(i));
			}
		}
		return retour;
	}
	
}
