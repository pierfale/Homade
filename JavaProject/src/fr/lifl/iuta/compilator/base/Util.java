package fr.lifl.iuta.compilator.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import fr.lifl.iuta.compilator.compile.ParserR;
import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class Util {

	public static long getUnsignedInt(int x) {
	    if(x > 0) return x;
	    long res = (long)(Math.pow(2, 32)) + x;
	    return res;
	}
	
	public static long[] getUnsignedInt(int [] x) {
		long [] inLong = new long[x.length];
	    for (int i = 0 ; i < x.length ; i++){
			if(x[i] > 0) inLong[i] = x[i];
		    inLong[i] = (long)(Math.pow(2, 32)) + x[i];
	    }
	    return inLong;
	}

	public static String fill(String tmp) {
		String retour="";
		for (int i = 0 ; i < 16-tmp.length() ; i++){
			retour += "0";
		}
		retour+=tmp;tmp = retour;retour = "";
		for (int i = 0 ; i < tmp.length() ; i++){
			if (i%4==0 && i != 0) 
				retour += " ";
			retour += tmp.charAt(i);
		}
		System.out.println("CALL FILL");
		return retour;
	}
	
	public static String fill(String tmp, String index) {
		String retour="";
		switch (index.length()){
			case 1: retour = index+"   ";break;
			case 2: retour = index+"  ";break;
			case 3: retour = index+" ";break;
			case 4: retour = index;break;
		}
		for (int i = 0 ; i < 16-tmp.length() ; i++){
			retour += "0";
		}
		retour+=tmp;tmp = retour;retour = "";
		for (int i = 0 ; i < tmp.length() ; i++){
			if (i%4==0 && i != 0) 
				retour += " ";
			retour += tmp.charAt(i);
		} 
		return retour;
	}
	
	public static int getNbLine(int PC) {
		String code = "";
		try {
			code = ParserR.BinToAsm(RAM.getMemory());
		} catch (UnknownInstructionException e) {e.printStackTrace();}
		BufferedReader reader = new BufferedReader(new StringReader(code));
		int nbLine=0;
		int i = 0;
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (line != null) {
			
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA")) {
				if(nbLine % 4 != 0)
					nbLine += 4 - nbLine % 4;
			}
			if(nbLine >= PC) {
				System.out.println(" i : "+i+" nbLibne : "+nbLine);
				return i;
			}
			if(line.length() > 4 && line.substring(0, 4).equals("CALL") || line.length() > 2 && line.substring(0, 2).equals("BA"))
				nbLine += 3;
			else
				nbLine++;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		return nbLine;
	}
	
	public static String lnToHTML(String s) {
		String retour = "";
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) == '\n')
				retour += "<br />";
			else
				retour += s.charAt(i);
		}
		return retour;
	}
	
	
}
