package fr.lifl.iuta.compilator.instruction;

public class InstructionCALL implements Instruction {
	
	private int adr;
	
	public InstructionCALL(int adr) {this.adr = adr;}

	public void exec() {}

}
