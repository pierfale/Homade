package processor;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import iP.IP;
import iP.IPConfig;
import instruction.Decode;
import instruction.Instruction;
import exception.EndOfInstructionsException;
import exception.InvalideAdressException;
import exception.UnassignedIPException;
import exception.UnknownInstructionException;
import exception.UnloadedRAMException;

/**
 * 
 * @author falezp
 * 
 * Singleton qui représente le processeur
 *
 */

public class Processor {
	
	private static boolean on;
	private static int PC; //compteur ordinal
	private static Core core;
	private static IP [] ips_short;
	private static IP [] ips_long;
	private static Stack<Integer> stack;
	
	/**
	 * Singleton pattern
	 */
	private Processor() {
		
	}
	
	/**
	 * Initialisation du processeur :
	 * creation du coeur
	 * Cette fonction doit etre appeler avant tout appele de la fonction start
	 */
	public static void init() {
		core = new Core();
		ips_short = new IP[1024];
		ips_long = new IP[1024];
		stack = new Stack<Integer>();
	}
	
	public static void loadIPS(IPConfig ips) {
		Iterator<String> it = ips.getKeys().iterator();
		while(it.hasNext()) {
			String curr = it.next();
			if(ips.isShort(curr))
				ips_short[ips.getPosition(curr)] = ips.getIP(curr);
			else
				ips_long[ips.getPosition(curr)] = ips.getIP(curr);
			
		}
	}
	
	
	public static void start() {
		on = true;
		PC = 0;
		while(on) {
			pulse();
		}
	}
	
	public static void stop() {
		on = false;
		System.out.println("Fin de l'execution (PC="+PC+")");
	}
	

	/**
	 * fonction appeler a chaque pulsation du processeur
	 */
	private static void pulse() {
		try {
			core.exec(fetch());
		} catch (EndOfInstructionsException e) {
			stop();
		}
		
		
	}
	
	private static Instruction fetch() throws EndOfInstructionsException {
		/*
		if(((currInstr >> 13) & 0x1) == 0xF || ((currInstr >> 9) & 0x3) == 0x3F) { //si call ou ba
			System.out.println("regAddr");
			PC++;
			short currInstr2 = getCurrInstr();
			int regAddr;
			regAddr = currInstr2 << 16;
			PC++;
			regAddr += getCurrInstr();
		}*/
		try {
			if(PC/4 >= RAM.size())
				throw new EndOfInstructionsException("PC="+PC);
			else {
				Instruction instr = Decode.decode(RAM.get(PC/4), (3 - PC%4));
				PC++;
				return instr;
			}
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
			stop();
			throw new EndOfInstructionsException("PC="+PC);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
			stop();
			throw new EndOfInstructionsException("PC="+PC);
		} catch (UnknownInstructionException e) {
			e.printStackTrace();
			stop();
			throw new EndOfInstructionsException("PC="+PC);
		}
	}
	
	
	public static int getAddrInstr() {
		return PC/4;
	}
	
	public static IP getIp(int i, boolean isShort) throws UnassignedIPException {
		if(isShort) {
			if(ips_short[i] != null)
				return ips_short[i];
			else
				throw new UnassignedIPException("short ip "+i);
		}
		else {
			if(ips_long[i] != null)
				return ips_long[i];
			else
				throw new UnassignedIPException("long ip "+i);			
		}
	}
	
	public static void stackPush(int value) {
		stack.push(value);
	}
	
	public static int stackPop() throws EmptyStackException {
		if(stack.isEmpty())
			throw new EmptyStackException();
		else 
			return stack.pop();
	}
}
