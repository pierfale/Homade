package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;
import java.util.Stack;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Token;

public class FunctionTranslate {
	
	public static String translate(WordTree tree, Map<String, MemoryBlock> addrVariable) {
		String beforeArg = "", afterArg = "", args = "";
		ParameterManager.init();
		beforeArg += VariableManager.createFrame(MemoryBlock.nextFreeSegment(addrVariable));
		afterArg += "CALL _FUN_"+tree.getNode(0).getToken().getContents()+"\n";
		afterArg += VariableManager.deleteFrame();
		
		//parameter
		if(tree.nodeSize() == 4) {
			Stack<WordTree> stack = new Stack<WordTree>();
			stack.push(tree.getNode(2));
			while(!stack.empty()) {
				if(stack.peek().getFunction().equals("list_parameter")) {
					if(stack.peek().nodeSize() == 1) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(0));
					}
					if(stack.peek().nodeSize() == 3) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(0));
						stack.push(curr.getNode(2));
					}
				}
				else if(stack.peek().getFunction().equals("parameter")) {
					args += NumberTranslate.exec(stack.pop().getNode(0), addrVariable);
				}
			}
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
