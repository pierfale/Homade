package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class FunctionTranslate {
	
	public static String translate(ExpressionTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		String beforeArg = "", afterArg = "", Args = "";
		for(int i=0; i<tree.nodeSize(); i++) {
			if(tree.get(i).value().equals("_FUN_")) {
				//nop
			}
			else if(!tree.get(i).value().equals("")) {
				int lbl = LabelManager.getNext();
				beforeArg += VariableManager.createFrame(MemoryBlock.nextFreeSegment(addrVariable));
				afterArg += "CALL _FUN_"+tree.get(i).value()+"\n";
				afterArg += VariableManager.deleteFrame();
			}
			else {
				Args += NumberTranslate.translate(tree.get(i), addrVariable);
			}
			
		}
		return Args+beforeArg+afterArg;
	}
	/*
	public static String translate(WordTree tree, Map<String, MemoryBlock> addrVariable) {
		
		if(tree.getFunction().equals("call_function")) {
			
		}
	}
	*/

}
