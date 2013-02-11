package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


public class InstructionBR extends InstructionBrAbstract{
	
	public InstructionBR(int offSetAdr) {super(offSetAdr);}
	
	public void exec() {Processor.setPC(Processor.getPC()+(offSetAdr));}

	public String toString() {return "Branchement relatif (BR) +"+offSetAdr;}
}
