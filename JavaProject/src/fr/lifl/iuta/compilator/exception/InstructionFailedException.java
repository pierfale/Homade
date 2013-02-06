package fr.lifl.iuta.compilator.exception;

import fr.lifl.iuta.compilator.processor.Processor;

@SuppressWarnings("serial")
public class InstructionFailedException extends Exception {
	
	private String message;
	
	public InstructionFailedException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("l'instruction à echoué  à l'adresse 0x"+String.format("%x", Processor.getAddrInstr())+" : "+message);
	}

}
