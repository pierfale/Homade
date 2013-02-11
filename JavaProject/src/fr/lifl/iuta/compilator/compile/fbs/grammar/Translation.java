package fr.lifl.iuta.compilator.compile.fbs.grammar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;

public class Translation {
	
	public static boolean exec(String out, WordTree wt) {
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
			try {
				writer.write(wt.translate(new HashMap<String, Integer>()));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
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
		return false;
	}

}
