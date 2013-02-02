package compile.fbs.grammar;

public class Match {

	public static boolean equals(String expr, String s) {
		boolean ok = true;
		int cursor = 0;
		int i = 0;
		while(i<expr.length()) {
			char curr = expr.charAt(i);
			if(curr == '(') {
				int j = i+1;
				while(j<expr.length() && expr.charAt(j) != ')') 
					j++;
				if(j<expr.length()) {
					String expr2 = expr.substring(i+1, j);
					if(j<expr.length() && expr.charAt(j+1) == '*') {
						
						while(cursor < s.length() && match(expr2, s.charAt(cursor))) 
								cursor++;
						
					}
					else if(j+1<expr.length() && expr.charAt(j+1) == '+') {
						int tmp = cursor;
						while(cursor < s.length() && match(expr2, s.charAt(cursor))) 
								cursor++;
						if(tmp == cursor)
							return false;
					}
					else {
						if(cursor >= s.length() || !match(expr2, s.charAt(cursor)))
							return false;
						cursor++;
					}
					
					if(cursor == s.length())
						return true;
				}
				
			}
			i++;
		}
		if(expr.length() == 3 && s.length() == 1) {
			if(s.charAt(0) >= expr.charAt(0) && s.charAt(0) <= expr.charAt(2))
				return true;
		}
		return false;
	}
	
	private static boolean match(String expr, char c) {
		boolean ok = false;
		for(int i=0; i<expr.length(); i++) {
			if(i+2<expr.length() && expr.charAt(i+1) == '-') {
				
				if(expr.charAt(i) < expr.charAt(i+2)) {
					if(c >= expr.charAt(i)  && c <= expr.charAt(i+2))
						return true;
				}
				else {
					if(c <= expr.charAt(i)  && c >= expr.charAt(i+2))
						return true;
				}
				i += 2;
			}
			else {
				if(expr.charAt(i) == '*')
					return true;
				if(c == expr.charAt(i))
					return true;
			}
		}
		return false;
	}
}
