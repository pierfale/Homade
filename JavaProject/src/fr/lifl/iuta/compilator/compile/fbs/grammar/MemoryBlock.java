package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;

public class MemoryBlock {
	
	private int address;
	private int size;
	
	public MemoryBlock(int address, int size) {
		this.address = address;
		this.size = size;
	}
	
	public int getAddress() {
		return address;
	}
	
	public int getSize() {
		return size;
	}
	
	public static int nextFreeBlock(Map<String, MemoryBlock> memory, int size) {
		int currAddr = Config.variable_memory_zone;
		boolean ok = true;
		while(true) {
			ok = true;
			Iterator<String> it = memory.keySet().iterator();
			while(ok && it.hasNext()) {
				String curr = it.next();
				if(memory.get(curr).getAddress() <= currAddr && memory.get(curr).getAddress()+memory.get(curr).getSize() > currAddr) {
					currAddr = memory.get(curr).getAddress()+memory.get(curr).getSize();
					ok = false;
				}
				if(memory.get(curr).getAddress() > currAddr+size) {
					currAddr = memory.get(curr).getAddress()+memory.get(curr).getSize();
					ok = false;
				}
			}
			if(ok) {
				return currAddr;
			}
		}
		
	}

}
