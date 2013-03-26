package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *	
 *	Intruction du Branchement Absolu avec pour saut l'@see {@link #adr} passer en parametre. 
 */
public class InstructionBA implements Instruction {

	private int adr;
	
	public InstructionBA(int adr) {this.adr = adr;}
	
	public void exec() {
		Processor.setPC(adr);
	}
	
	public String toString() {return "Branchement absolu (BA) a "+adr;}
}
