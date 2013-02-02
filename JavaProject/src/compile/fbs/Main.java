package compile.fbs;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import compile.fbs.grammar.Grammar;
import compile.fbs.grammar.Scan;
import compile.fbs.grammar.Match;
import compile.fbs.grammar.Parser;
import compile.fbs.grammar.Translation;
import compile.fbs.grammar.WordList;

public class Main {
	
	public static void main(String [] args) {
		Date d = new Date();
		
		Rapport.newRapport("rapport-"+(new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")).format(d)+".html");
		long beg = System.currentTimeMillis();
		try {
			if(Grammar.load("fbsGrammar")) {
				for(String s : args) {
					
					ReadFile reader = new ReadFile(s);
					
					WordList wl = Scan.exec(reader.getString());
					wl = Parser.exec(wl);
					if(wl == null) {
						Rapport.addLineError("La compilation a échoué");
						System.out.println("La compilation a échoué");
						break;
					}
					else {
						Translation.exec("out.asm", wl);
						Rapport.addLineSuccess("La compilation s'est terminé avec succès!");
						System.out.println("La compilation s'est terminé avec succès!");
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
		
	}

}
