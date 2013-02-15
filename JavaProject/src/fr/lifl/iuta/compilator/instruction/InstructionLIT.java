package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


public class InstructionLIT implements Instruction {

	private int value;
	
	public InstructionLIT(int value) {
		this.value = value;
	}
	
	public void exec() {
		Processor.stackPush(value);
	}
	
	public String toString() {
		return "Push de "+value+"(instruction LIT)";
	}
}
