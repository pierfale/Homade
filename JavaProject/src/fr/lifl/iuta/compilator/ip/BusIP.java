package fr.lifl.iuta.compilator.ip;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import fr.lifl.iuta.compilator.instruction.Instruction;

public class BusIP {
	
	private static ArrayList<IP> observer = new ArrayList<IP>();
	
	public static Instruction out(short instruction) {
		Iterator<IP> it = observer.iterator();
		while(it.hasNext()) {
			IP tmp = it.next();
			tmp.exec(instruction);
			if (tmp.itsMe() && tmp != null) return tmp;
		}
		return null;
	}
	
	public static void addIP(IP ip) { observer.add(ip); }
	
}
