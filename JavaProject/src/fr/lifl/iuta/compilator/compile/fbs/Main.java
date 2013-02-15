package fr.lifl.iuta.compilator.compile.fbs;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Lexer;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Match;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Parser;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Translation;
import fr.lifl.iuta.compilator.compile.fbs.grammar.VariableChecker;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordTree;

public class Main {
	
	public static void main(String [] args) {
		Date d = new Date();

		Rapport.newRapport("rapport-"+(new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")).format(d)+".html");
		long beg = System.currentTimeMillis();
		try {
			if(Grammar.load("fbsGrammar")) {
				
				if(args.length == 0) {
					System.out.println("Aucun fichier en entré");
				}
				
				for(String s : args) {
					ReadFile reader = new ReadFile(s);
					WordList wl = Lexer.exec(reader.getString());
					if(wl == null) {
						Rapport.addLineError("La reconnaissance de la lexical a échoué");
						System.out.println("La reconnaissance de la lexical a échoué");
						break;
					}		
					WordTree wt = Parser.exec(wl);
					if(wt == null || wl.size() > wt.size()) {
						Rapport.addLineError("La reconnaissance de la syntaxe a échoué");
						System.out.println("La reconnaissance de la syntaxe a échoué");
						break;
					}
					else {
						Rapport.addLine(wt.display());
						if(VariableChecker.exec(wt)) {
							Translation.exec("out.asm", wt);
							Rapport.addLineSuccess("La compilation s'est terminé avec succès!<br />Resultat : <br />");
							
							System.out.println("La compilation s'est terminé avec succès!");
						}
						else {
							Rapport.addLineError("La vérification des types a échoué");
							System.out.println("La vérification des types a échoué");
						}
					}
				}
			}
			else {
				Rapport.addLineError("Le traitement de la configuration de la grammaire a échoué");
				System.out.println("Le traitement de la configuration de la grammaire a échoué");
			}
				
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		System.out.println("compilation terminé en "+((double)(System.currentTimeMillis()-beg)/1000.0)+" seconds");
		Rapport.close();
		
		//test en direct
		
		String [] argsMain = new String[1];
		argsMain[0] = "out.asm";
		fr.lifl.iuta.compilator.base.Main.main(argsMain);
		
	}

}
