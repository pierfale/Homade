package compile.fbs.grammar;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import compile.fbs.Rapport;

public class Words {
	
	private ArrayList<Word []> words;
	private String name;
	private boolean infinite;
	
	public Words(String name) {
		this.name = name;
		words = new ArrayList<Word []>();
		infinite = false;
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
	
	public void setInfinite(boolean state) {
		this.infinite = state;
	}

	public WordList match(WordList wl, boolean infinite) {
		Rapport.add("<ul><li>"+name+"("+words.size()+","+infinite+")=>"+wl+"</li>");
		WordList retour = new WordList();
		WordList maxRetour = new WordList();
		String message = "";
		//parcourir tout les choix
		for(int i=0; i<words.size(); i++) {
			retour.clear();
			message += "<li><u>Choix "+(i+1)+"</u></li>";
			//recherche des terminaux
			boolean undefinedSize = false;
			int cursorWl = 0;
			int oldCursorWl = 0;
			boolean ok = true;
			if(wl.size() < words.get(i).length)
				ok = false;
			for(int j=0; ok && j<words.get(i).length; j++) {
				if(words.get(i)[j].isTerminal()) {
					if(undefinedSize) {
						oldCursorWl = cursorWl;
						WordList tmp2 = words.get(i)[j].match(wl.part(cursorWl, cursorWl+1), this.infinite);
						Stack<String> nBracket = new Stack<String>();
						while(cursorWl+1 < wl.size() && (tmp2 == null || !nBracket.isEmpty())) {

							if(wl.get(cursorWl).isOpenBracket()) {
								nBracket.push(wl.get(cursorWl).getContents());
							}
							else if(wl.get(cursorWl).isCloseBracket()) {
								 if(!nBracket.isEmpty() && wl.get(cursorWl).isCloseBracket(nBracket.peek())) {
									 nBracket.pop();
									 
								 }
								 else if(tmp2 == null){
									ok = false;
									message += "<li><span class=\"error\">Erreur de charactere d'ouverture/fermeture : "+wl.get(cursorWl).getContents()+"</span></li>";
								 }
							}
							cursorWl++;
							tmp2 = words.get(i)[j].match(wl.part(cursorWl, cursorWl+1), this.infinite);
						}							
			
						if(ok && oldCursorWl != cursorWl && tmp2 != null) {
							WordList tmp = words.get(i)[j-1].match(wl.part(oldCursorWl, cursorWl), this.infinite);
							if(tmp == null) {
								ok = false;
								message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [1]</span></li>";
							}
							else {
								retour.add(tmp);
								tmp2.get(0).setFunction(name);
								retour.add(tmp2);
								message += "<li><span class=\"success\"><b>"+tmp+" "+tmp2+"(1)</b></span></li>";
							}
							cursorWl++;
						}
						else {
							ok = false;
							message += "<li><span class=\"error\"><li>pas de correspondance pour "+name+"("+i+") : [2]</span></li>";
						}
					}
					else {
						WordList tmp = words.get(i)[j].match(wl.part(cursorWl, cursorWl+1), this.infinite);
						if(tmp == null) {
							ok = false;
							message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [3]</span></li>";
						}
						else {
							message += "<li><span class=\"success\"><b>"+tmp+"(2)</b></span></li>";
							tmp.get(0).setFunction(name);
							retour.add(tmp);
							cursorWl++;
						}
					}
					undefinedSize = false;
				}
				else {
					if(undefinedSize) {
					
						int k = cursorWl +1;
						WordList tmp = words.get(i)[j-1].match(wl.part(cursorWl, k), this.infinite); 
						while(k <= wl.size()-(words.get(i).length-j) &&  tmp == null) {
							k++;
							tmp = words.get(i)[j-1].match(wl.part(cursorWl, k), this.infinite); 
						}
						if(k == wl.size()-(words.get(i).length-j)+1) {
							//Rapport.addLine(wl.get(cursorWl).getContents()+"-"+wl.get(k).getContents());
							ok = false;
							message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [4]</span></li>";
						}
						else {
							message += "<li><span class=\"success\"><b>"+tmp+"(3)</b></span></li>";
							retour.add(tmp);
							cursorWl = k;
						}
					
					}
					undefinedSize = true;
					if(ok && j == words.get(i).length-1) { //si derniere de la regle
						WordList tmp = words.get(i)[j].match(wl.part(cursorWl, wl.size()), this.infinite); 
						if(tmp == null) {
							ok = false;
							message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [5]</span></li>";		
						}
						else {
							if(tmp.size() < wl.size()-cursorWl) {//si envoie non complet
								WordList tmp2 = words.get(i)[j].match(wl.part(cursorWl+tmp.size(), wl.size()), this.infinite);
								if(tmp2 == null) {
									ok = false;
									message += "<li><span class=\"error\">pas de correspondance pour l'envoie incomplet "+name+"("+i+") : [5-1]</span></li>";		
								}
								else {
									retour.add(tmp);
									retour.add(tmp2);
								}
							}
							else {
								retour.add(tmp);
								message += "<li><span class=\"success\"><b>"+tmp+"(4)</b></span></li>";
							}
						}
						cursorWl = wl.size();
					}
				}
			}
			//Rapport.add("<li>cursor : "+cursorWl+", wl : "+wl.size()+"</li>");
			
			if(ok && wl.size() > cursorWl) {
				if(infinite) {
					Rapport.add("==>"+wl.part(cursorWl, wl.size()));
					WordList tmp = this.match(wl.part(cursorWl, wl.size()), this.infinite);
					
					if(tmp == null) {
						message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [6]</span></li>";
						ok = false;
					}
					else {
						retour.add(tmp);
						message += "<li><span class=\"success\">Partie d'un mot infinie detecté ! <br/><b>"+tmp+"(5)</b>";
					}
					
				}
				else { //retourner une partie de la wl
					ok = false;
					message += "<li><span class=\"error\">pas de correspondance pour "+name+"("+i+") : [7]</span></li>";
					
					if(retour.size() > maxRetour.size()) {
						maxRetour.clear();
						maxRetour.add(retour);
					}
				}
			}
			
			if(ok) {
				Rapport.add(message);
				Rapport.addSuccess("<li>[return] correspondance trouvé pour "+name+"("+i+")<br />Valeur : "+retour+"</span></li>");
				Rapport.add("Retour : "+retour);
				Rapport.add("</ul>");
				
				return retour;
			}
		}
		Rapport.add(message);
		Rapport.addError("<li>[return] pas de correspondance pour "+name+"</li>");
		if(maxRetour.size() > 0) {
			Rapport.add("<li><span class=\"error\">Retour d'une partie de la wl pour "+name+" : "+maxRetour);
			Rapport.add("</ul>");
			return maxRetour;
		}
		else {
			Rapport.add("</ul>");
			return null;
		}
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
				else if(tab[j] < max && sum(tab)>size+1) {
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
