package fr.lifl.iuta.compilator.instruction;

import fr.lifl.iuta.compilator.processor.Processor;


public class InstructionHALT implements Instruction {

	public void exec() {Processor.stop();}
	
	public String toString() {return "Arret du processeur : Instruction HALT";}

}
