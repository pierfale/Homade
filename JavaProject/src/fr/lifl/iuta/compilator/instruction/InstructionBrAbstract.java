package fr.lifl.iuta.compilator.instruction;

public abstract class InstructionBrAbstract implements Instruction{
	
	protected int offSetAdr;
	
	public InstructionBrAbstract(int offSetAdr){this.offSetAdr = offSetAdr;}
	
}
