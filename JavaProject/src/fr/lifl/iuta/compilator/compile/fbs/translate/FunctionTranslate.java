package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class FunctionTranslate {
	
	public static String translate(ExpressionTree tree, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		String beforeArg = "", afterArg = "", args = "";
		for(int i=0; i<tree.nodeSize(); i++) {
			if(tree.get(i).value().equals("_FUN_")) {
				ParameterManager.init();
			}
			else if(!tree.get(i).value().equals("")) {
				int lbl = LabelManager.getNext();
				beforeArg += VariableManager.createFrame(MemoryBlock.nextFreeSegment(addrVariable));
				afterArg += "CALL _FUN_"+tree.get(i).value()+"\n";
				afterArg += VariableManager.deleteFrame();
			}
			else {
				ParameterManager.add(NumberTranslate.translate(tree.get(i), addrVariable));
			}
			
			
		}
		while(ParameterManager.hasNext()) {
			args += ParameterManager.next();
		}
		return args+beforeArg+afterArg;
	}
	/*
	public static String translate(WordTree tree, Map<String, MemoryBlock> addrVariable) {
		
		if(tree.getFunction().equals("call_function")) {
			
		}
	}
	*/

}
