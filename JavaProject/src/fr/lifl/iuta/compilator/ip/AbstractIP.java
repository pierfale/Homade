package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.instruction.Instruction;


public abstract class AbstractIP implements Instruction{
	
	protected int in,out,number,numberOfInstr, numberOfIP, mask;
	protected boolean shortIP;
	
	public abstract void exec(short instruction);
	public void putNumberOfIP(int nb){this.numberOfIP = nb;}
	public void setMask(int mask) {this.mask = mask;}
	public boolean itsMe() {return ((this.number & this.mask) == this.numberOfIP);}
	
}
