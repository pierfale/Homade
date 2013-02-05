package compile.fbs.grammar;

import java.util.ArrayList;

public class WordTree {
	
	private ArrayList<WordTree> node;
	private String contents;
	private String function;
	
	public WordTree() {
		node = new ArrayList<WordTree>();
		contents = "";
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public void setFunction(String contents) {
		this.function = function;
	}
	
	public void addNode(WordTree node) {
		this.node.add(node);
	}
	
	public int size() {
		int size = 0;
		if(!contents.equals(""))
			size = 1;
		for(int i=0; i<node.size(); i++)
			size += node.get(i).size();
		return size;
	}
	
	

}
