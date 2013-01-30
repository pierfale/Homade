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
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathname)));
		words = new ArrayList<Words>();
		String line = "";
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
						if(s2.charAt(0) == '"' && s2.charAt(s2.length()-1) == '"')
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
					System.out.println("expression "+s1+" inconnue");
			}
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
							System.out.println("[erreur] Mot inconnue : "+((NonTerminal)w).getTarget());
						}
					}
				}
			}
		}
		
	}
	
	private static boolean isName(String s) {
		for(int i=0; i<s.length(); i++) {
			if(!Character.isLetter(s.charAt(i)))
				return false;
		}
		
		return true;
	}
	
	public static void match(String source) {
		words.get(0).match(source);
	}

}
