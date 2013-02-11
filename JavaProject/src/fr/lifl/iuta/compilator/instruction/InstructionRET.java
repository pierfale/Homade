package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


public class InstructionRET implements Instruction {
	
	public void exec() {Processor.setPC(Processor.stackFuncPop());}
	
	public String toString() {return "RETurn à l'adresse :"+Processor.getPC();}
	
}
