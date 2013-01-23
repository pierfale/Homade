package Processor;

/**
 * 
 * @author falezp
 * 
 * Singleton qui représente le processeur
 *
 */

public class Processor {
	
	private static boolean on;
	private static long [] instructions;
	private static int PC; //compteur ordinal
	private static int regAddr;
	private static Core core;
	
	
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
	}
	
	
	public static void start(long [] instr) {
		on = true;
		instructions = instr;
		PC = 0;
		while(on) {
			pulse();
		}
	}
	
	public static void stop() {
		on = false;
	}
	

	/**
	 * fonction appeler a chaque pulsation du processeur
	 */
	private static void pulse() {
		core.exec(fetch());
		
		
	}
	
	private static short fetch() {
		short currInstr = getCurrInstr();
		System.out.println(String.format("%x", currInstr));
		if(((currInstr >> 13) & 0x1) == 0xF || ((currInstr >> 9) & 0x3) == 0x3F) { //si call ou ba
			System.out.println("regAddr");
			PC++;
			short currInstr2 = getCurrInstr();
			regAddr = currInstr2 << 16;
			PC++;
			regAddr += getCurrInstr();
		}
		PC++;
		return currInstr;
	}
	
	private static short getCurrInstr() {
		return  (short) (instructions[PC/4] >> 16*(3 - PC%4));
	}
}
