package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

public class FunctionTranslate {
	
	public static String translate(ExpressionTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		String tmp = "";
		for(int i=0; i<tree.nodeSize(); i++) {
			if(tree.get(i).value().equals("_FUN_")) {
				//nop
			}
			else if(!tree.get(i).value().equals("")) {
				tmp = "CALL _FUN_"+tree.get(i).value()+"\n";
			}
			else {
				retour += NumberTranslate.translate(tree.get(i), addrVariable);
			}
			
		}
		return retour+tmp;
	}
	

}
