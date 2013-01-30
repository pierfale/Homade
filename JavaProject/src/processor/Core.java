package processor;

import exception.InstructionFailedException;
import instruction.Instruction;

public class Core {
	
	public Core() {
		
	}
	
	public void exec(Instruction instruction) {
		try {
			if(instruction != null) {
				instruction.exec();
				System.out.println("executé : "+instruction);
			}
			else
				System.out.println("ignoré");
			
		} catch (InstructionFailedException e) {
			Processor.stop();
			e.printStackTrace();
		}
		
	}

}
