package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class Grammar {
	
	public static ArrayList<Words> words;
	
	private Grammar() {
		
	}
	
	public static boolean load(String pathname) throws FileNotFoundException {
		Rapport.addLine("<h2>Lecture du fichier de configuration de la grammaire :</h2>");
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathname)));
		words = new ArrayList<Words>();
		String line = "";
		int nbLine = 1;
		int nbLaw = 0;
		Words currWords = null;
		boolean ok = true;
		try {
			line = reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(ok && line != null) {
			Scanner sc = new Scanner(line);
			if(sc.hasNext()) {
				Rapport.addLine("ligne : "+line);
				String s1 = sc.next();
				if(isName(s1)) {
					currWords = new Words(s1);
					words.add(currWords);
					if(sc.hasNext())
						s1 = sc.next();
				}
				if(s1.equals(":=") || s1.equals("|")) {
					
					ArrayList<Word> tmp = new ArrayList<Word>();
					while(sc.hasNext()) {
						String s2 = sc.next();
						if(s2.equals("*"))
							currWords.setInfinite(true);
						else if(s2.charAt(0) == '"' && s2.charAt(s2.length()-1) == '"'
						|| s2.charAt(0) == '[' && s2.charAt(s2.length()-1) == ']')
							tmp.add(new Terminal(s2));
						else
							tmp.add(new NonTerminal(s2));
					}
					Word[] w2 = new Word[tmp.size()];
					for(int i=0; i<w2.length; i++)
						w2[i] = tmp.get(i);
						
					currWords.add(w2);
					nbLaw++;
				}
				else {
					Rapport.addLineError("expression "+s1+" inconnue a la ligne "+nbLine);
					ok = false;
				}
				
			}
			
			nbLine++;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(ok) {
			Rapport.addLineSuccess("terminé : "+nbLaw+" ajouté");
			Rapport.addLine("<h2>Etablissement des liens entre mots non-terminaux :</h2>");
			//etablisement des liens entre non-terminaux
			for(int i=0; i<words.size(); i++) {
				for(int j=0; j<words.get(i).size(); j++) {
					for(Word w : words.get(i).get(j)) {
						if(!w.isTerminal()) {
							for(int l=0; l<words.size(); l++) {
								if(words.get(l).getName().equals(((NonTerminal)w).getTarget())) {
									((NonTerminal)w).setTarget(words.get(l));
								}
							}
							if(((NonTerminal)w).target() == null) {
								Rapport.addLineError("Mot inconnue : "+((NonTerminal)w).getTarget()+" dans la clause "+words.get(i).getName());
								ok = false;
							}
						}
					}
				}
			}
		}
		if(ok) {
			Rapport.addLineSuccess("done");
			Rapport.addLine("<h2>Calcule de la longeur minimal des mots :</h2>");
			for(int i=0; i<words.size(); i++) {
				for(int j=0; j<words.get(i).size(); j++) {
					for(Word w : words.get(i).get(j)) {
						if(!w.isTerminal())
							((NonTerminal)w).calculatesLength();
					}
				}
			}
		}
		return ok;
	}
	
	private static boolean isName(String s) {
		for(int i=0; i<s.length(); i++) {
		if(!Character.isLetter(s.charAt(i)) && s.charAt(i) != '_')
				return false;
		}
		
		return true;
	}
	
	public static WordTree match(WordList wl) {
		return words.get(0).match(wl, false);

	}
	
	public static String existWord(String word) {
		for(int i=0; i<words.size(); i++) {
			for(int j=0; j<words.get(i).size(); j++) {
				for(int k=0; k<words.get(i).get(j).length; k++) {
					if(words.get(i).get(j)[k].isTerminal()) {
						if(words.get(i).get(j)[k].equals(word)) {
							return words.get(i).getName();
						}
					}
				}
			}
		}
		return null;
	}

}