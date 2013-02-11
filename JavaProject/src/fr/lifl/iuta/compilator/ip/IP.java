package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.instruction.Instruction;


public abstract class IP implements Instruction{
	
	protected int in,out,number,numberOfInstr;
	protected boolean shortIP;
	
	public abstract void exec(short instruction);
	public abstract boolean itsMe();
}
