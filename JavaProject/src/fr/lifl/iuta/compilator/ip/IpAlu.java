package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InstructionFailedException;
import fr.lifl.iuta.compilator.processor.Processor;

public class IpAlu extends AbstractIP {
	
	public void exec() throws InstructionFailedException {
		int []in = new int[this.in];
		int []out = null;
		for (int i = 0 ; i < in.length ; i++)
			in[i] = Processor.stackPop();
		switch (this.numberOfInstr){
			case 0: out = this.add(in);break;
			case 1: out = this.minus(in);break;
			case 2: out = this.inc(in);break;
			case 3: out = this.dec(in);break;
			case 4: out = this.not(in);break;
			case 5: out = this.and(in);break;
			case 6: out = this.or(in);break;
			case 7: out = this.xor(in);break;
			case 8: out = this.multi(in);break;
			case 9: out = this.divide(in);break;
			case 10: out = this.binaryLeftShift(in);break;
			case 11: out = this.binaryRightShift(in);break;
			case 16:out = this.vrai(in);break;
			case 17:out = this.faux(in);break;
			case 18:out = this.ez(in);break;
			case 20:out = this.gtu(in);break;
			case 21:out = this.ltu(in);break;
			case 22:out = this.eq(in);break;
			case 23:out = this.geu(in);break;
			case 24:out = this.leu(in);break;
			case 25:out = this.ne(in);break;
			case 26:out = this.gt(in);break;
			case 27:out = this.lt(in);break;
			case 28:out = this.ge(in);break;
			case 29:out = this.le(in);break;
			default:out = this.display(in);break;
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
		this.numberOfInstr = (instruction & 0x001F);
	}
	
	public int [] add(int [] in){
		int [] out = new int[1];
		out[0] = in[0] + in[1];
		return out;
	}
	
	public int[] display(int [] in){
		return in;
	}
	
	public int [] minus(int [] in){
		int [] out = new int[1];
		out[0] = in[1] - in[0];
		return out;
	}
	
	public int[] inc(int [] in){
		int [] out = new int[1]; out[0] = ++in[0];
		return out;
 	}
	
	public int[] dec(int [] in){
		int [] out = new int[1]; out[0] = --in[0];
		return out;
 	}
	
	public int[] not(int [] in){
		int [] out = new int[1];
		if (in[0] == 0) out[0] = 1;
		else out[0] = 0;
		return out;
	}
	
	public int[] and(int [] in){
		int [] out = new int[1];
		if (in[0] == 0 || in[1] == 0) out[0] = 0;
		else out[0] = 1;
		return out;
	}
	
	public int[] or(int [] in){
		int [] out = new int[1];
		if (in[0] == 1 || in[1] == 1) out[0] = 1;
		else out[0] = 0;
		return out;
	}
	
	public int[] xor(int [] in){
		int [] out = new int[1];
		if ((in[0] == 1 && in[1] != 1)||(in[0] != 1 && in[1] == 1)) out[0] = 1;
		else out[0] = 0;
		return out;
	}
	
	public int[] vrai(int [] in){int [] out = new int[1]; out[0] = 1;return out;}
	
	public int[] faux(int [] in){int [] out = new int[1]; out[0] = 0;return out;}
	
	public int[] ez(int [] in){
		int [] out = new int [1];
		if (in[0] == 0) out[0] = 1;
		else out[0] = 0;
		return out;
	}
	
	public int[] gtu(int [] in){
		int [] out = new int [1];
		long [] inLong = new long[in.length];
		inLong = getUnsignedInt(in);
		if (inLong[1] > inLong[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] ltu(int [] in){
		int [] out = new int [1];
		long [] inLong = new long[in.length];
		inLong = getUnsignedInt(in);
		if (inLong[1] > inLong[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] eq(int [] in){
		int [] out = new int [1];
		if (in[1] == in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] geu(int [] in){
		int [] out = new int [1];
		long [] inLong = new long[in.length];
		inLong = getUnsignedInt(in);
		if (inLong[1] > inLong[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] leu(int [] in){
		int [] out = new int [1];
		long [] inLong = new long[in.length];
		inLong = getUnsignedInt(in);
		if (inLong[1] > inLong[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] ne(int [] in){
		int [] out = new int [1];
		if (in[1] != in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] gt(int [] in){
		int [] out = new int [1];
		if (in[1] > in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] lt(int [] in){
		int [] out = new int [1];
		if (in[1] < in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] ge(int [] in){
		int [] out = new int [1];
		if (in[1] >= in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
	public int[] le(int [] in){
		int [] out = new int [1];
		if (in[1] <= in[0]) out[0] = 1; 
		else out[0] = 0;
		return out;
	}
	
//tmp
	
	public int[] multi(int [] in){
		int [] out = new int [1];
		out[0] = in[0]*in[1];
		return out;
	}
	
	public int[] divide(int [] in){
		int [] out = new int [1];
		out[0] = in[1]/in[0];
		return out;
	}
	
	public int[] modulo(int [] in){
		int [] out = new int [1];
		out[0] = in[1]%in[0];
		return out;
	}
	
	public int[] binaryLeftShift(int [] in){
		int [] out = new int [1];
		out[0] = in[1] << in [0];
		return out;
	}
	
	public int[] binaryRightShift(int [] in){
		int [] out = new int [1];
		out[0] = in[1] >> in [0];
		return out;
	}	
	
	public String toString() {return "IP ALU : "+this.numberOfInstr;}
	
	public static long getUnsignedInt(int x) {
	    if(x > 0) return x;
	    long res = (long)(Math.pow(2, 32)) + x;
	    return res;
	}
	
	public static long[] getUnsignedInt(int [] x) {
		long [] inLong = new long[x.length];
	    for (int i = 0 ; i < x.length ; i++){
			if(x[i] > 0) inLong[i] = x[i];
		    inLong[i] = (long)(Math.pow(2, 32)) + x[i];
	    }
	    return inLong;
	}
}
