package compile.fbs.grammar;

import java.util.ArrayList;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private String contents;
	private String function;
	
	public WordTree() {
		node = new ArrayList<WordTree>();
		contents = "";
		function = "";
	}
	
	public WordTree(String contents) {
		node = new ArrayList<WordTree>();
		this.contents = contents;
		function = "";
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
		node.add(wt);
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
	
	

}
