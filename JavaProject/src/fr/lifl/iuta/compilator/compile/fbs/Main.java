package fr.lifl.iuta.compilator.compile.fbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author falezp
 * 
 * Execute la ligne de commande.
 * interprete les différentes options
 *
 */

public class Main {
	
	public static void main(String [] args) {
		Rapport.gen = false;
		Config.use_ip_freeMemory = false;
		String input = "", output = "";
		InputStream grammar;
		grammar = Main.class.getResourceAsStream("/fbsGrammar");
		for(int i=0; i<args.length; i++) {
			if(args[i].charAt(0) == '-') {
				if(args[i].length() > 1) {
					if(args[i].equals("-r") || args[i].equals("--rapport")) { //rapport
						Rapport.gen = true;
						if(i+1<args.length && args[i+1].charAt(0) != '-' && i+2<args.length)  {//name
							Rapport.newRapport(args[i+1]);
							i++;
						}
						else {
							Date d = new Date();
							Rapport.newRapport("rapport-"+(new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")).format(d)+".html");
						}
					}
					else if(args[i].equals("-u") || args[i].equals("--useipmemory")) { //use_ip_freeMemory
						Config.use_ip_freeMemory = true;
					}
					else if(args[i].equals("-o") || args[i].equals("--output")) {
						if(i+1<args.length && args[i+1].charAt(0) != '-' && i+2<args.length) {
							output = args[i+1];
							i++;
						}
						else
							System.out.println("Aucun fichier spécifié en sortie");
					}
					else if(args[i].equals("-g") || args[i].equals("--grammar")) {
						if(i+1<args.length && args[i+1].charAt(0) != '-' && i+2<args.length) {
							try {
								grammar = new FileInputStream(new File(args[i+1]));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							i++;
						}
						else
							System.out.println("Aucun fichier spécifié pour la grammaire");
					}
				}
			}
			else {
				if(input.equals(""))
					input = args[i];
				else
					System.out.println("Argument(s) inccorecte(s) (plusieurs fichiers en entrée ?)");
			}
		}
		
		if(!input.equals("")) {
			if(output.equals("")) {
				output = input+".asm";
			}
			Exec.exec(input, output, grammar);
		}
		else
			System.out.println("Aucun fichier en entrée");
		
		Rapport.close();
	}

}
