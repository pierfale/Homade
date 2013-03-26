package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *	
 *	Intruction du Branchement Relatif avec pour saut l'@see {@link #offSetAdr} passer en parametre. 
 */
public class InstructionBR extends InstructionBrAbstract{
	
	/**
	 * Constructor
	 * @param offSetAdr : offset du saut a effectuer.
	 */
	public InstructionBR(int offSetAdr) {super(offSetAdr);}
	
	public void exec() {Processor.setPC(Processor.getPC()+(offSetAdr));}

	public String toString() {return "Branchement relatif (BR) +"+offSetAdr;}
}
