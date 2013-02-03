package compile.fbs.grammar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import compile.fbs.Config;

public class Translation {
	
	public static boolean exec(String out, WordList wl) {
		boolean ok = true;
		File f = new File(out);
		FileWriter writer = null;
		Map<String, Integer> variableAddress = new HashMap<String, Integer>();
		try {
			f.createNewFile();
			writer = new FileWriter(f);
		} catch (IOException e) {
			ok = false;
			e.printStackTrace();
		}
		if(ok) {
			System.out.println(wl);
			//etape 1 rechecher le main
			int i = 0;
			while(i<wl.size() && (!wl.get(i).getContents().equals("main") || !wl.get(i).getFunction().equals("function_name"))) {
				i++;
			}
			
			if(i == wl.size()) {
				System.out.println("Entrée main introuvable.");
				return false;
			}
			
			//verifier qu'il n'y a pas de parametre dans le main
			if(!wl.get(i+2).getContents().equals(")")) {
				System.out.println("main ne prend pas de parametre.");
				return false;				
			}
			
			
			i += 4;
			while(i<wl.size()) {
				System.out.println(wl.get(i).getContents()+"=>"+wl.get(i).getFunction());
				if(wl.get(i).getFunction().equals("type") && wl.get(i+1).getFunction().equals("variable_name")) {
					String currVariable = wl.get(i+1).getContents();
					variableAddress.put(currVariable, Config.variable_memory_zone+variableAddress.size());
					try {
						writer.write("LIT "+variableAddress.get(wl.get(i+1).getContents())+"\n");
						writer.write("LIT 0\n");
						writer.write("IP 2 0 1 "+Config.IP_add_variable_RAM+"\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
					i += 2;
					if(wl.get(i).getFunction().equals("allocation_instruction")) {
						
					}
				}
				else
					i++;
			}
			
			
		}
		if(ok) {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ok;
	}

}
