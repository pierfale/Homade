package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class IpRAM extends IP {
	private final int numberOfIp = 64;
	
	public void exec() throws InstructionFailedException {
		int []in = new int[this.in];
		int []out = null;
		for (int i = 0 ; i < in.length ; i++)
			in[i] = Processor.stackPop();
		switch (this.numberOfInstr){
			case 0: out = this.set32(in);break;
			case 1: out = this.set64(in);break;
			case 2: out = this.get32(in);break;
			case 3: out = this.get64(in);break;
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

	public boolean itsMe() {return ((this.number & 0x03F0) == this.numberOfIp);}
	
	/*
	 * in  :	1.value
	 * 			2.segment address
	 * 			
	 * out :
	 */
	public int [] set32(int [] in){
		int [] out = new int[0];
		try {
			RAM.set(in[1], in[0]);
			System.out.println("Inserstion de "+String.format("%x", in[0])+" à l'adresse "+in[1]);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}
	/*
	 * in  :	1.value (last)
	 * 			2.value	(first)
	 * 			3.segment address
	 * 
	 * out :
	 */	
	public int [] set64(int [] in){
		int [] out = new int[0];
		try {

			long value = ((long)in[1] << 32);
			value += in[0];
			RAM.set(in[2], value);
			System.out.println("Inserstion de "+String.format("%x", value)+" à l'adresse "+in[2]);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/*
	 * in  :	1.segment adresse
	 * 
	 * out :	1.value
	 */
	public int [] get32(int [] in) {
		int [] out = new int[1];
		try {
			out[0] = (int) RAM.get(in[0]);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/*
	 * in  :	1.segment adresse
	 * 
	 * out :	1.value (last)
	 * 			1.value	(first)
	 */
	public int [] get64(int [] in) {
		int [] out = new int[2];
		try {
			long value = RAM.get(in[0]);
			out[0] = (int) value;
			out[1] = (int) (value >> 32);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}

	
	/*
	 * in  :	1.segment address
	 * 			2.memory address
	 * 			3.size
	 * 
	 * out :
	 */
	public int[] create(int [] in) {
		int [] out = new int[0];
		try {
			boolean ok = false;
			int address = in[1];
			while(!ok) {
				boolean ok2 = true;
				int i = in[0];
				long currSegment = RAM.get(i);
				System.out.println("i : "+i+", ram : "+currSegment);
				while(currSegment != 0 && ok2) {
					int segAddr = (int) (currSegment >> 32);
					int segSize = (int) currSegment;
					System.out.println("segAddr : "+segAddr+", segSize : "+segSize);
					if(address >= segAddr && address < segAddr+segSize) {
						address = segAddr+segSize;
						ok2 = false;
					}
					
					i++;
					currSegment = RAM.get(i);
				}
				if(ok2)
					ok = true;
			}
			long segment = ((long)address << 32);
			System.out.println("CREATE : "+address+" => "+segment);
			segment += in[2];
			RAM.set(in[0], segment);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/*
	 * in  :	1.segment address
	 * 
	 * out :	
	 */
	public int[] delete(int [] in) {
		int [] out = new int[0];
		try {
			RAM.set(in[0], 0);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;		
	}
	
	public String toString() {return "IP RAM : "+this.numberOfInstr;}
	
	
}
