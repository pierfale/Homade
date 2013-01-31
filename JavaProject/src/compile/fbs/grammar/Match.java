package compile.fbs.grammar;

public class Match {

	public static boolean equals(String expr, String s) {
		if(expr.length() == 3 && s.length() == 1) {
			if(s.charAt(0) >= expr.charAt(0) && s.charAt(0) <= expr.charAt(2))
				return true;
		}
		return false;
	}
}
