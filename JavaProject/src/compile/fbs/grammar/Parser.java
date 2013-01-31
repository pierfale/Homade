package compile.fbs.grammar;

public class Parser {
	
	public static boolean exec(WordList wl) {
		System.out.println("==================");
		System.out.println("Word list Parser :");
		System.out.println("==================");

		return Grammar.match(wl);
	}

}
