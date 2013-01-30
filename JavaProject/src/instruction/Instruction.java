package instruction;

import exception.InstructionFailedException;

public interface Instruction {
	
	public void exec() throws InstructionFailedException;
}
