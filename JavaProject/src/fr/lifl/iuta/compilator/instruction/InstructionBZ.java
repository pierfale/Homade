package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


public class InstructionBZ extends InstructionBrAbstract {
	
	public InstructionBZ(int offSetAdr){super(offSetAdr);}
	
	public void exec() {
		if (Processor.stackPop() == 0) Processor.setPC(Processor.getPC()+(offSetAdr));
	}
	
	public String toString() {return "Branchement relatif (si 0 BZ) + "+offSetAdr;}

}
