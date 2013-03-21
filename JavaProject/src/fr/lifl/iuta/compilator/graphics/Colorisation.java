package fr.lifl.iuta.compilator.graphics;

import java.awt.Component;

import fr.lifl.iuta.compilator.compile.fbs.ReadFile;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Lexer;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Parser;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class Colorisation {

	public Colorisation(String[] args){
		for(String s : args) {
			ReadFile reader = null;
			try {
				reader = new ReadFile(s);
			} catch (java.io.FileNotFoundException e) {
				e.printStackTrace();
			}
			WordList wl = Lexer.exec(reader.getString());
			if(wl == null) {
				System.out.println("La reconnaissance du lexique a échoué");
				break;
			}		
			WordTree wt = Parser.exec(wl);
			if(wt == null || wl.size() > wt.size()) {
				System.out.println("La reconnaissance de la syntaxe a échoué");
				break;
			}
			for(int i = 0; i<wt.size(); i++){
				wt.getNode(i).getFunction().equals("variable");
				System.out.println("variable");
			}
		}
	}

	public String getColorisedText() {
		// TODO Auto-generated method stub
		return null;
	}
}
