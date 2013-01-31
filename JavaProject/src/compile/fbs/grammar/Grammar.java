package compile.fbs.grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Grammar {
	
	public static ArrayList<Words> words;
	
	private Grammar() {
		
	}
	
	public static void load(String pathname) throws FileNotFoundException {
		System.out.println("=====================");
		System.out.println("Read Grammar Config :");
		System.out.println("=====================");
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathname)));
		words = new ArrayList<Words>();
		String line = "";
		int nbLine = 1;
		Words currWords = null;
		try {
			line = reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(line != null) {
			Scanner sc = new Scanner(line);
			System.out.println("line : "+line);
			if(sc.hasNext()) {
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
						if(s2.charAt(0) == '"' && s2.charAt(s2.length()-1) == '"'
						|| s2.charAt(0) == '[' && s2.charAt(s2.length()-1) == ']')
							tmp.add(new Terminal(s2));
						else
							tmp.add(new NonTerminal(s2));
					}
					Word[] w2 = new Word[tmp.size()];
					for(int i=0; i<w2.length; i++)
						w2[i] = tmp.get(i);
						
					currWords.add(w2);
				}
				else
					System.out.println("expression "+s1+" inconnue a la ligne "+nbLine);
				
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
		System.out.println("=======================");
		System.out.println("link nonterminal word :");
		System.out.println("=======================");
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
							System.out.println("[erreur] Mot inconnue : "+((NonTerminal)w).getTarget()+" dans la clause "+words.get(i).getName());
						}
					}
				}
			}
		}
		System.out.println("ended");
		
	}
	
	private static boolean isName(String s) {
		for(int i=0; i<s.length(); i++) {
		if(!Character.isLetter(s.charAt(i)) && s.charAt(i) != '_')
				return false;
		}
		
		return true;
	}
	
	public static boolean match(WordList wl) {
		return words.get(0).match(wl, 0) != null;
	}
	
	public static boolean existWord(String word) {
		for(int i=0; i<words.size(); i++) {
			for(int j=0; j<words.get(i).size(); j++) {
				for(int k=0; k<words.get(i).get(j).length; k++) {
					if(words.get(i).get(j)[k].isTerminal()) {
						if(words.get(i).get(j)[k].equals(word)) {

							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
