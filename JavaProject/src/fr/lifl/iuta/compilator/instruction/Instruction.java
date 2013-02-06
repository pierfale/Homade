package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;

public interface Instruction {
	
	public void exec() throws InstructionFailedException;
}
