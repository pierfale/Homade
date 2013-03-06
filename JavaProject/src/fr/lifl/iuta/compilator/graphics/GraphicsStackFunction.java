package fr.lifl.iuta.compilator.graphics;

import java.util.Observable;
import java.util.Stack;

import fr.lifl.iuta.compilator.processor.Processor;

public class GraphicsStackFunction extends AbstractGraphicsStack{

	public GraphicsStackFunction(Stack<Integer> stack) {
		super(stack, "StackFunction");
	}
	
	public void update(Observable arg0, Object arg1) {
		model.setStack(Processor.getStackFunction());
		super.update(arg0, arg1);
	}

	
}
