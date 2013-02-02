package compile.fbs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Rapport {
	
	public static boolean gen = true;
	private static FileWriter writer;
	
	public static void newRapport(String pathname) {
		if(gen) {
			System.out.println(pathname);
			File f = new File(pathname);
			try {
				if(f.createNewFile()) {
					writer = new FileWriter(f);
					add("<!DOCTYPE html>");
					add("<html lang= \"fr\">");
					add("<head>");
					add("<meta charset= \"utf-8\">");
					add("<style type= \"text/css\">");
					add(".error { color: #F00; }");
					add(".success { color: #0F0; }");
					add("ul{ list-style-type: none; }");
					add("</style>");
					add("</head>");
					add("<body>");
				}
				else {
					System.out.println("Erreur rapport!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close() {
		if(gen) {
			add("</body>");
			add("</html>");
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void add(String line) {
		if(gen) {
			try {
				writer.write(line+"\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addError(String line) {
		if(gen) {
			try {
				writer.write("<span class=\"error\">"+line+"</span>\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addSuccess(String line) {
		if(gen) {
			try {
				writer.write("<span class=\"success\">"+line+"</span>\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addLine(String line) {
		if(gen) {
			try {
				writer.write(line+"<br />\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addLineError(String line) {
		if(gen) {
			try {
				writer.write("<span class=\"error\">"+line+"</span></br />\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addLineSuccess(String line) {
		if(gen) {
			try {
				writer.write("<span class=\"success\">"+line+"</span></br />\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
