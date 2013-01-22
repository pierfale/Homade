package Compile;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Exception.IncorrectFormatSource;

/**
 * Classe qui permet de convertir l'assembleur vers le binaire et réciproquement
 */

public class Parser {
	
	/**
	 * Convertie un code en assembleur en tableau instruction 64 bit
	 * @param source : Code source en assembleur
	 * @return tableau d'instruction binaire
	 * @throws IncorrectFormatSource : Code assembleur incorrecte
	 */
	
	public long[] AsmToBin(String source) throws IncorrectFormatSource {
		ArrayList<Long> tmp = new ArrayList<Long>();
		BufferedReader reader = new BufferedReader(new CharArrayReader(source.toCharArray()));
		String line;
		int nbLine = 0;
		try {
			line = reader.readLine();

			while(line != null) {
				nbLine++;
				Scanner sc = new Scanner(line);
				if(sc.hasNext()) { //type instruction
					String type = sc.next();
					if(type.equals("IP")) {
						int [] inOut = new int[2];
						int sIP = 0, IP = 0;
						for(int i=0; i<2; i++) { //Pop et Push
							if(sc.hasNext()) {
								String p = sc.next();
								try {
									inOut[i] = Integer.parseInt(p);
									if(inOut[i] < 0 && inOut[i] > 3) {
										sc.close();
										throw new IncorrectFormatSource("Parametre "+(i+1)+" de IP doit etre compris entre 0 et 3", nbLine);
									}
										
								} catch(NumberFormatException e) {
									sc.close();
									throw new IncorrectFormatSource("Parametre "+(i+1)+" de IP non numérique", nbLine);
								}
							}
							else {
								sc.close();
								throw new IncorrectFormatSource("Parametre "+(i+1)+" IP manquant", nbLine);
							}
						}
						
						if(sc.hasNext()) { // Short IP
							String p3 = sc.next();
								try {
									sIP = Integer.parseInt(p3);
									if(sIP != 0 && sIP != 1) {
										sc.close();
										throw new IncorrectFormatSource("Parametre short IP doit valoir 0 ou 1", nbLine);
									}
								} catch(NumberFormatException e) {
									sc.close();
									throw new IncorrectFormatSource("Parametre short IP non numérique", nbLine);
								}			
						}
						else {
							sc.close();
							throw new IncorrectFormatSource("Parametre short IP manquant", nbLine);
						}
						
						if(sc.hasNext()) { // IP
							String p4 = sc.next();
							try {
								sIP = Integer.parseInt(p4);
								if(sIP < 0 && sIP > 1024) {
									sc.close();
									throw new IncorrectFormatSource("Parametre numero IP doit etre compris entre 0 et 1024", nbLine);
								}
							} catch(NumberFormatException e) {
								sc.close();
								throw new IncorrectFormatSource("Parametre numero IP non numérique", nbLine);
							}			
						}
						else {
							sc.close();
							throw new IncorrectFormatSource("Parametre numero IP manquant", nbLine);
						}
						
						short instr = (short) 0x8000; //signature
						instr += inOut[0] << 13; //pop
						instr += inOut[1] << 11; //push	
						instr += sIP << 10;
						instr += IP; //IP
					}
					else if(type.equals("BR")) { //Branchement relatif
						
					}
					else if(type.equals("BZ")) { //Branchement relatif conditionnel
						
					}
					else if(type.equals("BNZ")) { //Branchement relatif conditionnel inverse
						
					}
					else if(type.equals("CALL")) { //Branchement absolue
						
					}
					else if(type.equals("RET")) { // Retour
						
					}
					else if(type.equals("HALT")) { // Arret
						
					}
					else {
						sc.close();
						throw new IncorrectFormatSource("Type inconnue", nbLine);
					}
				}
				else {
					sc.close();
					throw new IncorrectFormatSource("Type manquant", nbLine);
				}
				sc.close();
				line = reader.readLine();
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		long [] instr = new long[tmp.size()];
		for(int i=0; i<tmp.size(); i++)
			instr[i] = tmp.get(i);
		return instr;
	}

}
