package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.util.ArrayList;

import fr.lifl.iuta.compilator.compile.fbs.Rapport;

public class WordList {
	
	ArrayList<Token> tokens;
	
	public WordList() {
		tokens = new ArrayList<Token>();
	}
	
	public void add(String s, int line) {
		tokens.add(new Token(s, line));
	}
	
	public void add(Token t) {
		tokens.add(t);
	}
	
	public void add(WordList wl2) {
		for(int i=0; i<wl2.size(); i++)
			this.add(wl2.get(i));
	}
	
	public int size() {
		return tokens.size();
	}
	
	public void clear() {
		tokens.clear();
	}
	
	public Token get(int i) {
		return tokens.get(i);
	}
	
	public WordList part(int min, int max) {
		if(min >= 0 && max <= tokens.size() && min < max) {
			WordList wl = new WordList();
			for(int i=min; i<max; i++) {
				wl.add(tokens.get(i).getContents(), tokens.get(i).getLine());
			}
			return wl;
		}
		else {
			System.out.println("erreur borne ("+min+","+max+")");
			Rapport.addLineError("erreur borne ("+min+","+max+")");
			return null;
		}
	}
	
	public String toString() {
		String r = "";
		for(int i=0; i<tokens.size(); i++) {
			if(i != 0)
				r += ", ";
			r += "\""+tokens.get(i).getContents()+"\"";	
		}
		return r;
	}
}
