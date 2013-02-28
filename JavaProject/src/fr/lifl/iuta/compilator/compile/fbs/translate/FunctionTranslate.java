package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class FunctionTranslate {
	
	public static String translate(ExpressionTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		String tmp = "";
		for(int i=0; i<tree.nodeSize(); i++) {
			if(tree.get(i).value().equals("_FUN_")) {
				//nop
			}
			else if(!tree.get(i).value().equals("")) {
				int lbl = LabelManager.getNext();
				tmp += VariableManager.createFrame(MemoryBlock.nextFreeSegment(addrVariable));
				tmp += "CALL _FUN_"+tree.get(i).value()+"\n";
				tmp += VariableManager.deleteFrame();
			}
			else {
				retour += NumberTranslate.translate(tree.get(i), addrVariable);
			}
			
		}
		return retour+tmp;
	}
	/*
	public static String translate(WordTree tree, Map<String, MemoryBlock> addrVariable) {
		
		if(tree.getFunction().equals("call_function")) {
			
		}
	}
	*/

}
