package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private Token token;
	private String function;
	public ArrayList<String> deleteVar;
	
	
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
	
	public WordTree(Token token, String function) {
		node = new ArrayList<WordTree>();
		deleteVar = new ArrayList<String>();
		this.token = token;
		this.function = function;
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
		System.out.println("=>"+function+":"+addrVariable.size());
		String retour = "";
		boolean through = true;
		if(function.equals("list_function")) {
			for(int i=0; i<node.size(); i++) {
				retour += node.get(i).translate(new HashMap<String, MemoryBlock>());
				
			}
			through = false;
		}
		if(function.equals("function")) {
			addrVariable.clear();
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
		else if(function.equals("parameter_init")) {
			Token t = variableName();
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			int size = 1;
			
			retour += VariableManager.create(addr, size);
			retour += "LIT 0\n";
			retour += VariableManager.set(addr);
			addrVariable.put(t.getContents(), new MemoryBlock(addr, size));
			through = false;
		}
		else if(function.equals("parameter")) {/*
			retour += "%-- BEG";
			retour += node.get(0).translate(addrVariable);
			retour += "%-- END";
			through = false;*/
		}
		else if(function.equals("declaration_instruction")) {
			Token t = variableName();
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			int size = 1;
			
			retour += VariableManager.create(addr, size);
			addrVariable.put(t.getContents(), new MemoryBlock(addr, size));
		}
		else if(function.equals("allocation_instruction")) {
			Token t = node.get(0).getNode(0).variableName();
			String value = node.get(2).translate(addrVariable);
			int address = addrVariable.get(t.getContents()).getAddress();
			if(node.get(0).nodeSize() == 4) {
				retour += "LIT 0\n";
				retour += VariableManager.get(address);
				retour += NumberTranslate.exec(node.get(0).getNode(2), addrVariable);
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += value;
				retour += "IP 2 0 1 "+Config.IP_set_variable_RAM_32+"\n";
			}
			else {
				address = addrVariable.get(t.getContents()).getAddress();
				retour += "LIT 0\n";
				retour += VariableManager.set(address, value);
			}
			through = false;
		}
		else if(function.equals("variable")) {
			if(node.size() > 0) {
				Token t = node.get(0).variableName();
				int address = addrVariable.get(t.getContents()).getAddress();
				if(node.size() == 4) {
					retour += "LIT 0\n";
					retour += VariableManager.get(address);
					retour += NumberTranslate.exec(node.get(2), addrVariable);

					retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
					retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				}
				else {
					retour += "LIT 0\n";
					retour += VariableManager.get(address);
				}
			}
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
				if(node.size() == 6) {
					retour += node.get(5).translate(addrVariable);
				}
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
				retour += "LIT 1\n";
				retour += node.get(1).translate(addrVariable);
				retour += "IP 2 0 1 "+Config.IP_buffer_out+"\n";
				through = false;
			}
			
			
		}
		else if(function.equals("array")) {
			retour += ArrayManager.createArray(this, addrVariable);
			through = false;
		}	
		else if(function.equals("string")) {
			retour += StringManager.createString(this, addrVariable);
			through = false;
		}			
		else if(function.equals("return_instruction")) {
			if(node.size() > 1 && node.get(0).getToken().getContents().equals("return")) {
				retour += node.get(1).translate(addrVariable);
				retour += "RET\n";
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
