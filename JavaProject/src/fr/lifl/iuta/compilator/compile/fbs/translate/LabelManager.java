package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author falezp
 *
 * Singleton qui gere les labels :
 * lorsque l'ensemble des instructions ont été généré, les label
 * detecté sont transformé en numéro d'instruction
 */

public class LabelManager {
	
	private static int number;
	
	//singleton
	private LabelManager() {
		
	}
	
	public static void init() {
		number = 0;
	}
	
	public static int getNext() {
		return number++;
	}
	
	public static String replace(String in) {
		String out = "";
		String display = "";
		Map<String, Integer> lbl = new HashMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new StringReader(in));
		String line = "";
		int nbLine = 0;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(line != null) {
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA")) {
				if(nbLine % 4 != 0)
					nbLine += 4 - nbLine % 4;
			}
			
			if(line.length() > 4 && line.substring(0, 4).equals("_LBL")) {
				lbl.put(line.substring(4), nbLine);
				nbLine--;
				display += " {"+line+"="+(nbLine)+"}";
			}
			else {
				out += line+"\n";
				display += "\n["+nbLine+"] "+line;
			}
			
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA"))
				nbLine += 3;
			else
				nbLine++;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String out2 = out;
		reader = new BufferedReader(new StringReader(out2));
		out = "";
		line = "";
		nbLine = 0;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(line != null) {
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA")) {
				if(nbLine % 4 != 0)
					nbLine += 4 - nbLine % 4;
			}
			boolean absolute = false;
			Scanner sc = new Scanner(line);
			while(sc.hasNext()) {
				String curr = sc.next();
				if((curr.length() > 1 && curr.substring(0, 2).equals("BA")) || (curr.length() > 3 && curr.substring(0, 4).equals("CALL")))
					absolute = true;
				if(curr.length() > 4 && curr.substring(0, 4).equals("_LBL")) {
					if(absolute)
						out += ""+(lbl.get(""+(curr.substring(4))));
					else
						out += ""+(lbl.get(""+(curr.substring(4)))-nbLine-1);
					
				}
				else
					out += curr;
				if(sc.hasNext())
					out += " ";
				else
					out += "\n";
			}
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA"))
				nbLine += 3;
			else
				nbLine++;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return out;
	}
	
	

}
