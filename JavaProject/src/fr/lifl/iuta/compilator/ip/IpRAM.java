package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class IpRAM extends AbstractIP {
	
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
			case 4: out = this.copy(in);break;
			case 5: out = this.freeMemory(in);break;
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
	 * in  :	1.value
	 * 			2.segment address
	 * 			
	 * out :
	 */
	public int [] set32(int [] in){
		int [] out = new int[0];
		try {
			System.out.println("Inserstion de "+String.format("%x", in[0])+" à l'adresse "+in[1]);
			RAM.set(in[1], in[0]);
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
			System.out.println("Inserstion de "+String.format("%x", value)+" à l'adresse "+in[2]);
			RAM.set(in[2], value);
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
	 * in  :	1.size
	 * 			2.destination
	 * 			3.origin
	 * 
	 * out :
	 */
	public int [] copy(int [] in) {
		int [] out = new int[0];
		try {
			for(int i=0; i<in[0]; i++) {
				RAM.set(in[1]+i, RAM.get(in[2]+i));
			}
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/*
	 * in  :	1.adress
	 * 			2.adressInit
	 * 
	 * out :	
	 */
	public int [] freeMemory(int [] in) {
		
		
		int [] out = new int[0];
		try {
			int currAddr = (int)(RAM.get(in[0]) >> 32);
			int size = (int)(RAM.get(in[0]));
			int pagination = in[1];
			System.out.println("currAddr : "+currAddr+" size : "+size);
			boolean ok;
			do {
				ok = true;
				System.out.println("> currAddr : "+currAddr);
				
				int page1 = 1;
				int page2 = 0;
				while(page1 != 0 && ok) {
					page1 = (int)(RAM.get(pagination) >> 32);
					page2 = (int)(RAM.get(pagination));
					System.out.println("pagination : "+pagination+"("+page1+","+page2+")");
					if(pagination != in[0]) {
						if(currAddr >= page1 && currAddr < page1+page2 || currAddr+size < page1 && currAddr+size >= page1+page2) {
							currAddr = page1+page2;
							pagination = in[1];
							ok = false;
						}
					}
					pagination++;
				}
				if(!ok)
					pagination++;
			} while(!ok);
			long value = ((long)currAddr << 32);
			value += size;
			RAM.set(in[0], value);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return out;
	}

	
	public String toString() {return "IP RAM : "+this.numberOfInstr;}
	
	
}
