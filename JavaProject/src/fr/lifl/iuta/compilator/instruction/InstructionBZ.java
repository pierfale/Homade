package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *	
 *	Intruction du Branchement Relatif avec pour saut l'@see {@link #offSetAdr} passer en parametre. 
 */
public class InstructionBZ extends InstructionBrAbstract {
	
	/**
	 * Constructor
	 * @param offSetAdr : offset du saut a effectuer.
	 */
	public InstructionBZ(int offSetAdr){super(offSetAdr);}
	
	public void exec() {
		if (Processor.stackPop() == 0) Processor.setPC(Processor.getPC()+(offSetAdr));
	}
	
	public String toString() {return "Branchement relatif (si 0 BZ) + "+offSetAdr;}

}
