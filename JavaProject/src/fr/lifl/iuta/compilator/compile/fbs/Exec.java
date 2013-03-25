package fr.lifl.iuta.compilator.compile.fbs;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.lifl.iuta.compilator.compile.fbs.check.Check;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Lexer;
import fr.lifl.iuta.compilator.compile.fbs.grammar.Parser;
import fr.lifl.iuta.compilator.compile.fbs.grammar.WordList;
import fr.lifl.iuta.compilator.compile.fbs.translate.Translation;
import fr.lifl.iuta.compilator.compile.fbs.translate.WordTree;

public class Exec {
	
	public static int exec(String input, String output, String grammar) {
		
		long beg = System.currentTimeMillis();
		try {
			if(Grammar.load(grammar)) {
				
				ReadFile reader = new ReadFile(input);
				WordList wl = Lexer.exec(reader.getString());
				if(wl == null) {
					Rapport.addLineError("La reconnaissance du lexique a échoué");
					System.out.println("La reconnaissance du lexique a échoué");
					return -1;
				}		
				WordTree wt = Parser.exec(wl);
				if(wt == null || wl.size() > wt.size()) {
					Rapport.addLineError("La reconnaissance de la syntaxe a échoué");
					System.out.println("La reconnaissance de la syntaxe a échoué");
					return -1;
				}
				else {
					Rapport.addLine(wt.display());
					if(Check.exec(wt)) {
						Translation.exec(output, wt);
						Rapport.addLineSuccess("La compilation s'est terminé avec succès!<br />Resultat : <br />");
					}
					else {
						Rapport.addLineError("La vérification des types a échoué");
						System.out.println("La vérification des types a échoué");
						return -1;
					}
				}
			}
			else {
				Rapport.addLineError("Le traitement de la configuration de la grammaire a échoué");
				System.out.println("Le traitement de la configuration de la grammaire a échoué");
				return -1;
			}
				
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		Rapport.addLine("compilation terminé en "+((double)(System.currentTimeMillis()-beg)/1000.0)+" seconds");
		return 0;
	}
}
