package fr.lifl.iuta.compilator.processor;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import fr.lifl.iuta.compilator.base.Util;
import fr.lifl.iuta.compilator.exception.EndOfInstructionsException;
import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnassignedIPException;
import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.graphics.MyObservable;
import fr.lifl.iuta.compilator.instruction.Decode;
import fr.lifl.iuta.compilator.instruction.Instruction;
import fr.lifl.iuta.compilator.ip.BusIP;
import fr.lifl.iuta.compilator.ip.AbstractIP;
import fr.lifl.iuta.compilator.ip.IPConfig;

/**
 * 
 * @author falezp
 * 
 * Singleton qui représente le processeur
 *
 */

public class Processor extends MyObservable{
	
	
	private static boolean on;
	private static int PC; //compteur ordinal
	private static Core core;
	private static Stack<Integer> stack;
	private static Stack<Integer> stackFunction;
	
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
		stack = new Stack<Integer>();
		stackFunction = new Stack<Integer>();
		BusIP.removeAllIP();
	}
	
	public static void loadIPS(IPConfig ips) {
		Iterator<String> it = ips.getKeys().iterator();
		while(it.hasNext()) {
			String curr = it.next();
			BusIP.addIP(ips.getIP(curr));
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
	
	public static void stepByStep() {
		if (on)
			pulse();
		else 
			stop();
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
		/*
		//etat stack
		for(int i = stack.size()-1; i>= 0; i--)
			System.out.println((stack.size()-i)+" => "+stack.get(i));
		Scanner sc = new Scanner(System.in);
		sc.nextLine();*/
		
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
	/*
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
	}*/
	
	public static void stackPush(int value) {
		stack.push(value);
		notifyObserver();
	}
	
	public static int stackPop() throws EmptyStackException {
		if(stack.isEmpty())
			throw new EmptyStackException();
		else {
			notifyObserver();
			return stack.pop();
		}
	}
	
	public static void stackFuncPush(int value) {
		stackFunction.push(value);
		notifyObserver();
	}
	
	public static int stackFuncPop() throws EmptyStackException {
		if(stackFunction.isEmpty())
			throw new EmptyStackException();
		else {
			notifyObserver();
			return stackFunction.pop();
		}
	}
	
	public static void setPC(int pc){
		PC = pc;
	}
	
	public static int getPC(){
		return PC;
	}
	
	public static Stack<Integer> getStack() {
		return stack;
	}
	
	public static Stack<Integer> getStackFunction() {
		return stackFunction;
	}

	public static void setOn(boolean b) {
		on = b;
	}

	public static boolean isOn() {
		return on;
	}
}
