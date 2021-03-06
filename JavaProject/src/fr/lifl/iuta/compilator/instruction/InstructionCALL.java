package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *
 *	
 */
public class InstructionCALL implements Instruction {
	
	private int adr;
	
	public InstructionCALL(int adr) {this.adr = adr;}

	public void exec() {
		Processor.stackFuncPush(Processor.getPC()+2);
		Processor.setPC(this.adr);
	}
	
	public String toString() {return "CALL de la fonction a l'addresse :"+this.adr;} 

}
