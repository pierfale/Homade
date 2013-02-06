package fr.lifl.iuta.compilator.processor;

import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;

public class RAM {
	
	private static final int blockMemoryAllocSize = 1024;
	
	private static long [] memory;
	
	/*
	 * Singleton
	 */
	private RAM() {
		
	}
	
	public static void load(long [] mem) {
		memory = new long[(mem.length/blockMemoryAllocSize+1)*blockMemoryAllocSize];
		try {
			memset(0x0, mem);
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
	}
	
	public static long get(int address) throws InvalideAdressException, UnloadedRAMException {
		if(memory == null)
			throw new UnloadedRAMException();
		if(address >= 0 && address < memory.length)
			return memory[address]; 
		else
			throw new InvalideAdressException("0x"+String.format("%x", address));
	}
	
	public static void set(int address, long value) throws InvalideAdressException, UnloadedRAMException {
		if(address < 0)
			throw new InvalideAdressException("0x"+String.format("%x", address));
		if(memory.length <= address) {
			long [] memoryTmp = new long[(address/blockMemoryAllocSize+1)*blockMemoryAllocSize];
			System.arraycopy(memory, 0, memoryTmp, 0, memory.length);
			memory = memoryTmp;
		}
		memory[address] = value;
	}
	
	public static int size() throws UnloadedRAMException {
		if(memory == null)
			throw new UnloadedRAMException();
		return memory.length;
	}
	
	public static void memset(int addr, long[] mem)  throws UnloadedRAMException {
		if(memory.length <= addr+mem.length) {
			long [] memoryTmp = new long[((addr+mem.length)/blockMemoryAllocSize+1)*blockMemoryAllocSize];
			System.arraycopy(memory, 0, memoryTmp, 0, memory.length);
			memory = memoryTmp;
		}
		for(int i=0; i<mem.length; i++)
			memory[addr+i] = mem[i];
	}

}
