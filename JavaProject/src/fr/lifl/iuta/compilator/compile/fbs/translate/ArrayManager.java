package fr.lifl.iuta.compilator.compile.fbs.translate;

public class ArrayManager {
	
	public static int countArrayAssignment(WordTree wt) {
		if(wt == null)
			return 0;
		if(wt.getFunction().equals("array")) {
			return countArrayAssignment(wt.getNode(1));
		}
		else if(wt.getFunction().equals("list_entities")) {
			return countArrayAssignment(wt.getNode(0))+countArrayAssignment(wt.getNode(2));
		}
		else if(wt.getFunction().equals("entities")) {
			return 1;
		}		
		return 0;
	}
	
	public static String createArray(WordTree wt) {
		return "";
	}

}
