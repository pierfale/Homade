package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.instruction.Instruction;

/**
 * 
 * @author danglotb
 * Classe Abstraire qui représente les IPs afin de gérer la généricité.
 * 
 */

public abstract class AbstractIP implements Instruction{
	
	protected String name;
	protected int in,out,number,numberOfInstr, numberOfIP, mask;
	protected boolean shortIP;
	
	public abstract void exec(short instruction);
	public void putNumberOfIP(int nb){this.numberOfIP = nb;}
	public void setMask(int mask) {this.mask = mask;}
	public boolean itsMe() {return ((this.number & this.mask) == this.numberOfIP);}
	public void setName(String name) {this.name = name;}
	public String getName() {return this.name;}
	
}
