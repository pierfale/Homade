package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;

/**
 * 
 * @author danglotb
 *
 *	Interface d'instruction.
 *	Toutes l'instructions doivent implémenter  cette interface.
 *
 */
public interface Instruction {
	
	public void exec() throws InstructionFailedException;
	
	public String toString();
	
}
