package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author falezp
 * 
 * Représente un block dans la RAM (offset par rapport a la frame courante)
 * Permet de récupérer le prochain block libre
 *
 */
public class MemoryBlock {
	
	private int address;
	private int size;
	private String type;
	
	public MemoryBlock(int address, int size, String type) {
		this.address = address;
		this.size = size;
		this.type = type;
	}
	
	public int getAddress() {
		return address;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getType() {
		return type;
	}
	
	public static int nextFreeSegment(Map<String, MemoryBlock> memory) {
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
	/*
	public static void display(Map<String, MemoryBlock> addrVariable) {
		Iterator<String> it = addrVariable.keySet().iterator();
		while(it.hasNext()) {
			String curr = it.next();
			System.out.println("var : "+curr+" => "+addrVariable.get(curr).getAddress()+" : "+addrVariable.get(curr).getSize());
		}
	}*/

}
