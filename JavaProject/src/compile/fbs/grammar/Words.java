package compile.fbs.grammar;

import java.util.ArrayList;
import java.util.Scanner;

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

	public boolean match(String s) {
		System.out.println(s+"=>"+name+"("+words.size()+")");
		for(int i=0; i<words.size(); i++) {
			boolean ok = true;
			int [] sublength = new int[words.get(i).length];
			for(int j=0; j<sublength.length; j++)
				sublength[j] = 1;
			for(; sum(sublength) < s.length(); sublength = inc(sublength, s.length())) {
				for(int k : sublength)
					System.out.print(k+",");
				System.out.println("");
				
				for(int j=0; j<sublength.length; j++) {
					int min = cum(sublength, j), max = cum(sublength, j+1);
					if(!words.get(i)[j].match(s.substring(min, max)))
						ok = false;
				}
			}
			if(ok)
				return true;
			
		}
		return false;
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
	
	public int[] inc(int[] tab, int max) {
		for(int i=0; i<tab.length; i++) {
			if(sum(tab) < max) {
				tab[i]++;
				return tab;
			}
			else {
				tab[i] = 1;
			}
		}
		return null;
	}
}
