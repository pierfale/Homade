package processor;

import exception.InvalideAdressException;
import exception.UnloadedRAMException;

public class RAM {
	
	private static long [] memory;
	
	/*
	 * Singleton
	 */
	private RAM() {
		
	}
	
	public static void load(long [] mem) {
		memory = mem;
	}
	
	public static long get(int address) throws InvalideAdressException, UnloadedRAMException {
		if(memory == null)
			throw new UnloadedRAMException();
		if(address >= 0 && address < memory.length)
			return memory[address]; 
		else
			throw new InvalideAdressException("0x"+String.format("%x", address));
	}
	
	public static int size() throws UnloadedRAMException {
		if(memory == null)
			throw new UnloadedRAMException();
		return memory.length;
	}

}
