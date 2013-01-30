package compile;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exception.IncorrectFormatSourceException;


/**
 * @author danglotb
 * Classe qui permet de convertir l'assembleur vers le binaire et réciproquement
 */

public class Parser {
	
	/**
	 * Convertie un code en assembleur en tableau instruction 64 bit
	 * @param source : Code source en assembleur
	 * @return tableau d'instruction binaire
	 * @throws IncorrectFormatSource : Code assembleur incorrecte
	 */
	
	private static long currLong;
	private static ArrayList<Long> tmp;
	private static int offsetLong, cpt;
	private static short instr;
	
	public static long[] AsmToBin(String source) throws IncorrectFormatSourceException {
		tmp = new ArrayList<Long>();
		BufferedReader reader = new BufferedReader(new CharArrayReader(source.toCharArray()));
		String line;
		int nbLine = 0;
		currLong = 0;
		offsetLong = 0;
		cpt = 0;
		boolean call = false;
		try {
			line = reader.readLine();
			while(line != null) {
				instr = 0;
				nbLine++;
				String p;
				Scanner sc = new Scanner(line);
				if(sc.hasNext()) { //type instruction
					String type = sc.next();
					if(type.equals("IP")) {
						int [] inOut = new int[2];
						int sIP = 0, IP = 0;
						for(int i=0; i<2; i++) { //Pop et Push
							if(sc.hasNext()) {
								p = sc.next();
								try {
									inOut[i] = Integer.parseInt(p);
									if(inOut[i] < 0 || inOut[i] > 3) {
										sc.close();
										throw new IncorrectFormatSourceException("Parametre "+(i+1)+" de IP doit etre compris entre 0 et 3", nbLine);
									}
										
								} catch(NumberFormatException e) {
									sc.close();
									throw new IncorrectFormatSourceException("Parametre "+(i+1)+" de IP non numérique", nbLine);
								}
							}
							else {
								sc.close();
								throw new IncorrectFormatSourceException("Parametre "+(i+1)+" IP manquant", nbLine);
							}
						}
						
						if(sc.hasNext()) { // Short IP
							p = sc.next();
								try {
									sIP = Integer.parseInt(p);
									if(sIP != 0 && sIP != 1) {
										sc.close();
										throw new IncorrectFormatSourceException("Parametre short IP doit valoir 0 ou 1", nbLine);
									}
								} catch(NumberFormatException e) {
									sc.close();
									throw new IncorrectFormatSourceException("Parametre short IP non numérique", nbLine);
								}			
						}
						else {
							sc.close();
							throw new IncorrectFormatSourceException("Parametre short IP manquant", nbLine);
						}
						
						if(sc.hasNext()) { // IP
							p = sc.next();
							try {
								IP = Integer.parseInt(p);
								if(IP < 0 || IP > 1024) {
									sc.close();
									throw new IncorrectFormatSourceException("Parametre numero IP doit etre compris entre 0 et 1024", nbLine);
								}
							} catch(NumberFormatException e) {
								sc.close();
								throw new IncorrectFormatSourceException("Parametre numero IP non numérique", nbLine);
							}			
						}
						else {
							sc.close();
							throw new IncorrectFormatSourceException("Parametre numero IP manquant", nbLine);
						}
						
						if(sIP == 1 && IP == 0x03FF) {
							sc.close();
							throw new IncorrectFormatSourceException("Identifiant IP réservé", nbLine);							
						}
						
						instr = (short) 0x8000; //signature
						instr += inOut[0] << 13; //pop
						instr += inOut[1] << 11; //push	
						instr += sIP << 10;
						instr += IP; //IP
					}
					else if(type.equals("BR")||type.equals("BZ")||type.equals("BNZ")) { //Branchement relatif
						int saut = 0;
						if (sc.hasNext()) {
							saut = Integer.parseInt(sc.next());
							if (saut == 0 || saut > 512 || saut < -512 ){
								sc.close();
								throw new IncorrectFormatSourceException("Valeur de déplacement interdite :", nbLine);
							}
						}
						if (type.equals("BR"))
							instr = (short) 0x0000; //signature
						else if (type.equals("BZ"))
							instr = (short) 0x0400; //signature
						else 
							instr = (short) 0x0800; //signature
						instr += (short) saut;
					}
					else if(type.equals("CALL") || type.equals("BA")) { //Branchement absolue
						if(offsetLong > 0) {
							for (int i = 0 ; i < offsetLong ; i++){
								instr = (short) 0xFFFF;	
								addLong();
							}
						}
							if (type.equals("CALL"))
								instr = (short) 0x1000;
							else 
								instr = (short) 0x0C00;
							call = true;
							addLong();
							int instrInt = Integer.parseInt(sc.next());
							instr = (short)(instrInt >> 16 & 0xFFFF);
							addLong();
							instr = (short)instrInt;
							addLong();
					}
					else if(type.equals("RET")) { // Retour
						instr = (short)0x1400;
					}
					else if(type.equals("HALT")) { // Arret
						instr = (short)0x1C00;
					}
					else if(type.equals("LIT")){ // Ajouter une valeur au sommet de la pile.
						instr = (short)0x2000;
						short value = Short.parseShort(sc.next());
						instr += value;
					}
					else if (type.equals("WIM")){
						if(offsetLong > 0) {
							for (int i = 0 ; i < offsetLong ; i++){
								instr = (short)0xFFFF;	
								addLong();
							}
						}
						instr = (short)0x3000;
						short adr = Short.parseShort(sc.next());
						instr += (short)adr;
						addLong();
						long [] code = AsmToBin(reader.readLine());
						for (int i = 0 ; i < 3 ; i++){
							long mask = (long)0xFFFF << (16*(3-i));
							instr = (short)(code[0] & mask);
							addLong();
						}
						
					}
					else {
						sc.close();
						throw new IncorrectFormatSourceException("Type inconnue", nbLine);
					}
				}
				else {
					sc.close();
					throw new IncorrectFormatSourceException("Type manquant", nbLine);
				}
				sc.close();
				line = reader.readLine();
				if (!call) 
					addLong();
				call = false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(cpt%4!=0){
 			while(cpt%4!=0){
				instr = (short)0xFFFF;
				addLong();
				cpt-=2;
			}
		}
		long [] instrt = new long[tmp.size()];
		for(int i=0; i<tmp.size(); i++)
			instrt[i] = tmp.get(i);
		return instrt;
	}
	
	private static void addLong(){
		cpt++;
		currLong += ((long)instr << (16*(3-offsetLong)))&(long)0xFFFF << (16*(3-offsetLong));
		offsetLong++;
		if(offsetLong == 4) {
			offsetLong = 0;
			tmp.add(new Long(currLong));
			currLong = 0;
		}
	}
}