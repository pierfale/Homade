package base;

import java.io.FileNotFoundException;

import iP.IPConfig;
import processor.Processor;
import processor.RAM;
import compile.Compile;
import compile.Parser;
import exception.IncorrectFormatSourceException;


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
			IPConfig ips = new IPConfig("ips");
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
