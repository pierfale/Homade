package fr.lifl.iuta.compilator.processor;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.instruction.Instruction;

/**
 * 
 * @author falezp
 *
 * Class qui reprensente un coeur. 
 */

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
