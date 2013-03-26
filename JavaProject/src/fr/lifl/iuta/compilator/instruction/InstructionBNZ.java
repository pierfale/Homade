package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


/**
 * 
 * @author danglotb
 *	
 *	Intruction du Branchement Relatif si NON ZERO avec pour saut l'@see {@link #offSetAdr} passer en parametre. 
 */
public class InstructionBNZ extends InstructionBrAbstract {
	
	/**
	 * Constructor
	 * @param offSetAdr : offset du saut a effectuer.
	 */
	public InstructionBNZ(int offSetAdr){super(offSetAdr);}

	public void exec() {
		if (Processor.stackPop() != 0) Processor.setPC(Processor.getPC()+offSetAdr);
	}
	
	public String toString() {return "Branchement relatif(si non 0 BNZ) + "+offSetAdr;}

}