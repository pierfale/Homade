package fr.lifl.iuta.compilator.compile.fbs.check;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class Variable {

	private String name;
	private String type;
	private int line;
	
	public Variable(String name, String type, int line) {
		this.name = name;
		this.type = type;
		this.line = line;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getLine() {
		return line;
	}
	
	public static boolean contains(ArrayList<Variable> list, String name) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getName().equals(name))
				return true;
		}
		return false;
	}
	
	public static String getType(ArrayList<Variable> list, String name) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getName().equals(name))
				return list.get(i).getType();
		}
		return null;
	}

	public static boolean isTypeFunction(WordTree tree) {
		String [] function = {"number", "varaible"};
		for(String f : function) {
			if(f.equals(tree.getFunction()))
				return true;
		}
		return false;
	}
	
}
