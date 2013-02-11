package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;



public class InstructionBNZ extends InstructionBrAbstract {
	
	public InstructionBNZ(int offSetAdr){super(offSetAdr);}

	public void exec() {
		if (Processor.stackPop() != 0) Processor.setPC(Processor.getPC()+offSetAdr);
	}
	
	public String toString() {return "Branchement relatif(si non 0 BNZ) + "+offSetAdr;}

}