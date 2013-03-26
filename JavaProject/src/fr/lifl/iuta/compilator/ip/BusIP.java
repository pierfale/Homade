package fr.lifl.iuta.compilator.ip;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import fr.lifl.iuta.compilator.graphics.MyObservable;
import fr.lifl.iuta.compilator.instruction.Instruction;
/**
 * 
 * @author danglotb
 * Le BusIP est chargé de gérer la communication entre les IPs chargés et le Processeurs.
 * 
 */
public class BusIP extends MyObservable{
	
	private static ArrayList<AbstractIP> iPs = new ArrayList<AbstractIP>();
	
	public static Instruction out(short instruction) {
		Iterator<AbstractIP> it = iPs.iterator();
		while(it.hasNext()) {
			AbstractIP tmp = it.next();
			tmp.exec(instruction);
			if (tmp.itsMe() && tmp != null) return tmp;
		}
		return null;
	}
	
	public static void addIP(AbstractIP ip) { iPs.add(ip); }
	
	public static ArrayList<AbstractIP> getIP(){ return iPs; }
	
	public static void removeIP(AbstractIP ip) { iPs.remove(ip); }

	public static void removeAllIP() { iPs.clear(); }
	
}
