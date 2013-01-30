package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import exception.FileNotFoundException;
import exception.IncorrectFormatSourceException;


/**
 * 
 * @author falezp
 * 
 * Convertie le/les fichier(s) ou tout les fichiers contenue dans le/les répertoire(s) passé 
 * en parametre
 *
 */

public class Compile {
	
	public static long[] exec(String[] args) {
		
		long[] r = new long[0];
		
		for(String path : args) {
			File file = new File(path);
			if(file.isFile()) { //Cas d'un simple fichier
				try {
					r = Parser.AsmToBin(getContent(file));
					System.out.println(path + " compilé avec succès");
				} catch (java.io.FileNotFoundException e) {
					e.printStackTrace();
				} catch (IncorrectFormatSourceException e) {
					e.printStackTrace();
				}
			}
			else if(file.isDirectory()) { //Cas d'un répertoire
				for(File subFile : file.listFiles()) {
					try {
						r = Parser.AsmToBin(getContent(subFile));
						System.out.println(path + " compilé avec succès");
					} catch (java.io.FileNotFoundException e) {
						e.printStackTrace();
					} catch (IncorrectFormatSourceException e) {
						e.printStackTrace();
					}
				}
			}
			else {
				new FileNotFoundException(path).printStackTrace();
			}
		}
		return r;
	}
	
	
	
	public static String getContent(File f) throws java.io.FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String r = "";
		String line = "";
		try {
			line = reader.readLine();

			while(line != null) {
				
				if(line != "")
					r += line+'\n';
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}

}
