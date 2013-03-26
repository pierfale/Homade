package fr.lifl.iuta.compilator.instruction;
/**
 * 
 * @author danglotb
 *	Classe Abstraite du Branchement Relatif.
 *	Intruction du Branchement Relatif avec pour saut l'@see {@link #offSetAdr} passer en parametre. 
 */
public abstract class InstructionBrAbstract implements Instruction{
	
	/**
	 * Constructor
	 * @param offSetAdr : offset du saut a effectuer.
	 */
	protected int offSetAdr;
	
	public InstructionBrAbstract(int offSetAdr){this.offSetAdr = offSetAdr;}
	
}
