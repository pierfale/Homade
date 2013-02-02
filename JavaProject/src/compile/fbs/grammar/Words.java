package compile.fbs.grammar;

import java.util.ArrayList;
import java.util.Scanner;

import compile.fbs.Rapport;

public class Words {
	
	private ArrayList<Word []> words;
	private String name;
	
	public Words(String name) {
		this.name = name;
		words = new ArrayList<Word []>();
	}
	
	public void add(Word [] word) {
		words.add(word);
	}
	
	public int size() {
		return words.size();
	}
	
	public Word[] get(int i) {
		return words.get(i);
	}
	
	public String getName() {
		return name;
	}

	public WordList match(WordList wl, int deep) {

		Rapport.add("<ul><li>"+name+"("+words.size()+")=>"+wl+"</li>");
		for(int i=0; i<words.size(); i++) {
			boolean ok = true, ok2 = false;
			int [] sublength = new int[words.get(i).length];
			//optimisation de la recherche par longeur
			ArrayList<Integer> unknowLength = new ArrayList<Integer>();
			int lastUnknown = 0;
			int knownCount = 0;
			for(int j=0; j< words.get(i).length; j++) {
				if(!words.get(i)[j].isTerminal()) {
					//unknowLength.add(j);
					lastUnknown = j;
				}
				else
					knownCount++;
			}
			
			for(int j=0; j<sublength.length; j++) {
				if(words.get(i)[j].isTerminal())
					sublength[j] = 1;
				else {
					if(j != lastUnknown)
						sublength[j] = 1;
					else
						sublength[j] = wl.size()-sublength.length+1;
				}
			}
			if(sum(sublength) >= wl.size())
				ok = false;
			for(; !ok2 && sublength!= null && sublength.length != 0 && sum(sublength) == wl.size(); sublength = inc(sublength, wl.size()-sublength.length-1, i, wl.size())) {
				ok = true;
				WordList tmp = new WordList();
				for(int j=0; ok && j<sublength.length; j++) {
					int min = cum(sublength, j), max = cum(sublength, j+1);
					WordList tmp2 = words.get(i)[j].match(wl.part(min, max), deep+1);
					if(tmp2 == null)
						ok = false;
					else
						tmp.add(tmp2);
				}
				if(ok) {
					wl = tmp;
					ok2 = true;
				}
			}
			if(sublength!= null && sublength.length == 0) {
				WordList tmp = words.get(i)[words.get(i).length-1].match(wl, deep+1);
				if(tmp == null)
					ok = false;	
				else
					wl = tmp;
			}
			
			if(ok) {
				Rapport.addSuccess("<li>correspondance trouvé : "+wl.toString()+"</li></ul>");
				for(int j=0; j<wl.size(); j++)  {
					if(wl.get(j).getFunction().equals(""))
						wl.get(j).setFunction(name);
				}
					
				return wl;
			}
			
		}
		Rapport.addError("<li>pas de correspondance</li></ul>");
		return null;
	}
	
	public int sum(int [] i) {
		return cum(i, i.length);
	}
	
	public int cum(int [] tab, int i) {
		int r = 0;

		for(int j=0; j<i && j<tab.length; j++)
			r += tab[j];
		
		return r;
	}
	
	public int[] inc(int[] tab, int max, int i, int size) {
		int first = -1;
		for(int j=tab.length-1; j>=0; j--) {
			if(!words.get(i)[j].isTerminal()) {
				if(first == -1) {
					first = j;
					tab[j] = 0;
				}
				else if(tab[j] < max) {
					tab[j]++;
					tab[first] = size-sum(tab);
					return tab;
				}
				else {
					tab[j] = 1;
				}
			}
		}
		return null;
	}
}
