package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.ip.BusIP;
import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *	
 *	Classe qui décode le code binaire pour en extraire la bonne instruction ou l'ip a appellée.
 *
 */
public class Decode {
	
	public static Instruction decode(long instructions, int offset) throws UnknownInstructionException {
		short instruction = (short) (instructions >> offset*16);
		System.out.println("["+Processor.getPC()+"] curr instr : "+String.format("%x", instruction)+" offset : "+offset);
		if ((instruction & 0xFFFF) == 0xFFFF) {
			return null;
		}
		else if ((instruction & 0xFFFF) == 0x0) {
			Processor.stop();
			return null;
		}
		else if((instruction & 0x8000) == 0x8000) { //IP
			return BusIP.out(instruction);
		} else if ((instruction & 0xFC00) == 0){
			return new InstructionBR((instruction & 0x03FF));
		} else if ((instruction & 0xFC00) == 0x0400){
			return new InstructionBZ((instruction & 0x03FF));
		} else if ((instruction & 0xFC00) == 0x0800){
			return new InstructionBNZ((instruction & 0x03FF));			
		} else if ((instruction & 0xFFFF) == 0x1000){
			return new InstructionCALL((int)(instructions >> 16));
		} else if ((instruction & 0xFFFF) == 0x0C00) {
			return new InstructionBA((int)(instructions >> 16));
		} else if ((instruction & 0xFFFF) == 0x1400) {
			return new InstructionRET();
		} else if ((instruction & 0xFFFF) == 0x1C00) {
			return new InstructionHALT();
		} else if ((instruction & 0xF000) == 0x2000) {
			return new InstructionLIT(instruction & 0x0FFF);
		} else if ((instruction & 0xF000) == 0x3000){
			return null;
			/*
			 * L'instruction WIM n'a pas été implementée.
			 */
			//return new InstructionWim(instruction & 0x0FFF, instructions);
		} else {
			throw new UnknownInstructionException(String.format("%x", instruction));
		}
		
	}
}
