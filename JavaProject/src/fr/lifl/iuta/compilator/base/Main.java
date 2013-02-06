package fr.lifl.iuta.compilator.base;

import java.io.FileNotFoundException;

import fr.lifl.iuta.compilator.compile.Compile;
import fr.lifl.iuta.compilator.compile.Parser;
import fr.lifl.iuta.compilator.exception.IncorrectFormatSourceException;
import fr.lifl.iuta.compilator.ip.IPConfig;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;


public class Main {

	public static void main(String [] args) {
		
		//Genere binaire
		long[] src = Compile.exec(args);
		System.out.println("====Code Source====");
		for(long l : src)
			System.out.println(String.format("%x", l));
		System.out.println("===================");
		RAM.load(src);
		Processor.init();
		//chargement des IP
		try {
			IPConfig ips = new IPConfig("fbsIP");
			Processor.loadIPS(ips);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IncorrectFormatSourceException e) {
			e.printStackTrace();
		}
		//lancement du processeur
		Processor.start();
	}
}
