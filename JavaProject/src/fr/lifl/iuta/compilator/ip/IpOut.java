package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.BufferOut;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class IpOut extends AbstractIP {
	
	public void exec() throws InstructionFailedException {
		int []in = new int[this.in];
		int []out = null;
		for (int i = 0 ; i < in.length ; i++)
			in[i] = Processor.stackPop();
		switch (this.numberOfInstr){
			case 0: out = this.add(in);break;
		}
		if(this.out <= out.length)
			for(int i=0; i<this.out; i++)
				Processor.stackPush(out[i]);			
		else
			throw new InstructionFailedException("IP ne retourne pas assez de valeur");
		
	}

	public void exec(short instruction) {
		this.in = (instruction & 0x6000)>>13;
		this.out = (instruction & 0x1800)>>11;
		this.shortIP = ((instruction & 0x0400)>>10 == 1); 
		this.number = (instruction & 0x03FF);
		this.numberOfInstr = (instruction & 0x000F);
	}

	
	/*
	 * in  :	value
	 * 			type
	 * 
	 * out :
	 */
	public int [] add(int [] in){
		int [] out = new int[0];
		if(in[1] == 0) //char
			BufferOut.add((char)in[0]);
		if(in[1] == 1) {//integer
			String tmp = ""+in[0];
			for(int i=0; i<tmp.length(); i++)
				BufferOut.add(tmp.charAt(i));
		}
		return out;
	}

	
	public String toString() {return "IP OUT : "+this.numberOfInstr;}
	
	
}