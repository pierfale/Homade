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

	public WordList match(WordList wl, int deep) {
		for(int i=0; i<deep; i++)
			System.out.print("   ");
		System.out.println(name+"("+words.size()+")=>"+wl);
		for(int i=0; i<words.size(); i++) {
			boolean ok = true, ok2 = false;
			int [] sublength = new int[words.get(i).length-1];
			for(int j=0; j<sublength.length; j++)
				sublength[j] = 1;
			System.out.println("tmp : "+sublength.length+", "+wl.size());
			if(sum(sublength) >= wl.size())
				ok = false;
			for(; !ok2 && sublength!= null && sublength.length != 0 && sum(sublength) < wl.size(); sublength = inc(sublength, wl.size()-1)) {
				for(int k : sublength)
					System.out.print(k+",");
				System.out.println("");
				WordList tmp = new WordList();
				for(int j=0; ok && j<sublength.length; j++) {
					int min = cum(sublength, j), max = cum(sublength, j+1);
					//System.out.print(">>"+min+"-"+max+"->"+wl.part(min, max)+"->");

					WordList tmp2 = words.get(i)[j].match(wl.part(min, max), deep+1);
					if(tmp2 == null)
						ok = false;
					else
						tmp.add(tmp2);
				}
				//last
				//System.out.println("0>>"+sum(sublength)+"-"+ wl.size()+"->"+wl.part(sum(sublength), wl.size()));
				WordList tmp2 = words.get(i)[words.get(i).length-1].match(wl.part(sum(sublength), wl.size()), deep+1);
				if(tmp2 == null)
					ok = false;	
				else
					tmp.add(tmp2);
				if(ok) {
					wl = tmp;
					ok2 = true;
				}
				System.out.println("->"+tmp+"="+ok);
			}
			if(sublength!= null && sublength.length == 0) {
				WordList tmp = words.get(i)[words.get(i).length-1].match(wl, deep+1);
				if(tmp == null)
					ok = false;	
				else
					wl = tmp;
			}
			
			if(ok) {
				for(int j=0; j<deep; j++)
					System.out.print("   ");
				System.out.println(wl);
				for(int j=0; j<wl.size(); j++)  {
					if(wl.get(j).getFunction().equals(""))
						wl.get(j).setFunction(name);
				}
					
				return wl;
			}
			for(int j=0; j<deep; j++)
				System.out.print("   ");
			System.out.println("null");
			
		}
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
