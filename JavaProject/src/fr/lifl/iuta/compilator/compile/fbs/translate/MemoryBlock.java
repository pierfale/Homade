package fr.lifl.iuta.compilator.compile.fbs.translate;

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
	
	public static int nextFreeSegment(Map<String, MemoryBlock> memory) {
		System.out.println("mem:");
		MemoryBlock.display(memory);
		int currAddr = 0;
		boolean ok = true;
		while(true) {
			ok = true;
			Iterator<String> it = memory.keySet().iterator();
			while(ok && it.hasNext()) {
				String curr = it.next();
				if(memory.get(curr).getAddress() == currAddr) {
					currAddr = memory.get(curr).getAddress()+memory.get(curr).getSize();
					ok = false;
				}
			}
			if(ok) {
				return currAddr;
			}
		}
		
	}
	
	public static void display(Map<String, MemoryBlock> addrVariable) {
		Iterator<String> it = addrVariable.keySet().iterator();
		while(it.hasNext()) {
			String curr = it.next();
			System.out.println("var : "+curr+" => "+addrVariable.get(curr).getAddress()+" : "+addrVariable.get(curr).getSize());
		}
	}

}
