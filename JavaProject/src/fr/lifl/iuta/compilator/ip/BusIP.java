package fr.lifl.iuta.compilator.ip;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import fr.lifl.iuta.compilator.instruction.Instruction;

public class BusIP {
	
	private static ArrayList<AbstractIP> observer = new ArrayList<AbstractIP>();
	
	public static Instruction out(short instruction) {
		Iterator<AbstractIP> it = observer.iterator();
		while(it.hasNext()) {
			AbstractIP tmp = it.next();
			tmp.exec(instruction);
			if (tmp.itsMe() && tmp != null) return tmp;
		}
		return null;
	}
	
	public static void addIP(AbstractIP ip) { observer.add(ip); }
	
}
