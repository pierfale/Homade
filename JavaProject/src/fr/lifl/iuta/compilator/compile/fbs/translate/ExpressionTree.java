package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;

public class ExpressionTree {
	
	private ArrayList<ExpressionTree> node;
	private String value;
	
	public ExpressionTree() {
		value = "";
		node = new ArrayList<ExpressionTree>();
	}
	
	public ExpressionTree(String value) {
		this.value = value;
		node = new ArrayList<ExpressionTree>();
	}
	
	public void add(ExpressionTree node) {
		this.node.add(node);
	}
	
	public int nodeSize() {
		return node.size();
	}
	
	public ExpressionTree get(int i) {
		return node.get(i);
	}
	
	public String value() {
		return value;
	}
	
	public String toString() {
		String retour = "[";
		if(!value.equals(""))
			retour += value;
		else
		for(int i=0; i<node.size(); i++)
			retour += node.get(i);
		retour += "]";
		return retour;
	}
	
	public void display(int n) {
		if(!value.equals("")) {
			System.out.print(n+".");
			for(int i=0; i<n; i++)
				System.out.print("   ");
			System.out.println(value);
		}
		for(int i=0; i<node.size(); i++)
			node.get(i).display(n+1);
	}
	
	public String display() {
		int span = 1;
		if(node.size() > 0)
			span = node.size();
		String s = "<table style=\"border: 1px solid black;text-align: center\"><tr><td colspan=\""+span+"\">"+value;

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

}
