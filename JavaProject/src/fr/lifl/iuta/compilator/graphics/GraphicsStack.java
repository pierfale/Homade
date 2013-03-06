package fr.lifl.iuta.compilator.graphics;

import java.util.Observable;
import java.util.Stack;

import fr.lifl.iuta.compilator.processor.Processor;

public class GraphicsStack extends AbstractGraphicsStack{

	public GraphicsStack(Stack<Integer> stack) {
		super(stack, "StackValue");
	}
	
	public void update(Observable arg0, Object arg1) {
		model.setStack(Processor.getStack());
		super.update(arg0, arg1);
	}

	
}
