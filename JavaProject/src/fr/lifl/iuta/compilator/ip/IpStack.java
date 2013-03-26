package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.processor.Processor;

/**
 * 
 * @author danglotb
 *
 * Cette IP permet de gérer l'état des Stacks.
 */
public class IpStack extends AbstractIP {

	public void exec(short instruction) {
		this.in = (instruction & 0x6000)>>13;
		this.out = (instruction & 0x1800)>>11;
		this.shortIP = ((instruction & 0x0400)>>10 == 1); 
		this.number = (instruction & 0x03FF);
		this.numberOfInstr = (instruction & 0x0007);
	}

	public void exec() throws InstructionFailedException {
		int []in = new int[this.in];
		int []out = null;
		for (int i = 0 ; i < in.length ; i++)
			in[i] = Processor.stackPop();
		switch (this.numberOfInstr){
			case 0 : out = dup(in);break;
			case 1 : out = swap(in);break;
			case 2 : out = tuck(in);break;
			case 3 : out = over(in);break;
			case 4 : out = rot(in);break;
			case 5 : out = invrot(in);break;
			case 6 : out = nip(in);break;
			case 7 : out = pop1(in);break;
		}
		if(this.out <= out.length)
			for(int i=0; i<this.out; i++)
				Processor.stackPush(out[i]);			
		else
			throw new InstructionFailedException("IP ne retourne pas assez de valeur");
	}
	
	public static int getNumberOfIP() {return 0;}
	
	public int[] dup(int [] in){
		int[] out = {in[0],in[0]};
		return out;
	}
	
	public int[] swap(int [] in){
		int[] out = {in[0],in[1]};
		return out;
	}
	
	public int[] tuck(int [] in){
		int[] out = {in[0],in[1],in[0]};
		return out;
	}
	
	public int[] over(int [] in){
		int[] out = {in[1],in[0],in[1]};
		return out;
	}
	
	public int[] rot(int [] in){
		int[] out = {in[1],in[0],in[2]};
		return out;
	}
	
	public int[] invrot(int [] in){
		int[] out = {in[0],in[2],in[1]};
		return out;
	}
	
	public int[] nip(int [] in){
		int[] out = {in[0]};
		return out;
	}
	
	public int[] pop1(int [] in){
		int[] out = new int[0];
		return out;
	}
	
	public int[] display(int [] in){
		System.out.println("DISPLAY : "+in[0]);
		return in;
	}
	
	public String toString() {return "IP STACK : "+this.numberOfInstr;}

}
