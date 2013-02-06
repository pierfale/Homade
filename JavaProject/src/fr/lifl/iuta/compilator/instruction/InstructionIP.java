package fr.lifl.iuta.compilator.instruction;

import java.util.EmptyStackException;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.exception.UnassignedIPException;
import fr.lifl.iuta.compilator.processor.Processor;

public class InstructionIP implements Instruction {
	
	private int in;
	private int out;
	private boolean shortIP;
	private int number;

	
	public InstructionIP(int in, int out, boolean shortIP, int number) {
		this.in = in;
		this.out = out;
		this.shortIP = shortIP;
		this.number = number;
	}
	
	public void exec() throws InstructionFailedException {
		try {
			int [] in = new int[this.in];
			try {
				
				for(int i=0; i<in.length; i++)
					in[i] = Processor.stackPop();
			} catch(EmptyStackException e) {
				throw new InstructionFailedException("Stack unit vide");
			}
			
			
			int [] out = Processor.getIp(number, shortIP).exec(in);
			if(this.out <= out.length)
				for(int i=0; i<this.out; i++)
					Processor.stackPush(out[i]);			
			else
				throw new InstructionFailedException("IP ne retourne pas assez de valeur");
		} catch (UnassignedIPException e) {
			e.printStackTrace();
			throw new InstructionFailedException("L'IP n'existe pas");
		}
	}
	
	public String toString() {
		try {
			return new String("IP ("+Processor.getIp(number, shortIP)+")");
		} catch (UnassignedIPException e) {
			e.printStackTrace();
			return new String("IP (null)");
		}
	}

}
