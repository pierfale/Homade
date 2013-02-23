package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class Translation {
	
	public static boolean exec(String out, WordTree wt) {
		boolean ok = true;
		File f = new File(out);
		FileWriter writer = null;
		LabelManager.init();
		FunctionManager.init();
		try {
			f.createNewFile();
			writer = new FileWriter(f);
		} catch (IOException e) {
			ok = false;
			e.printStackTrace();
		}
		if(ok) {
			try {
				String tmp1 = wt.translate(new HashMap<String, MemoryBlock>());
				if(FunctionManager.get("main") == -1) {
					System.out.println("Fonction main introuvable");
					return false;
				}
					
				tmp1 = "CALL _LBL"+FunctionManager.get("main")+"\nHALT\n"+tmp1+VariableManager.createFunction();
				tmp1 = FunctionManager.replace(tmp1);
				tmp1 = LabelManager.replace(tmp1);
				writer.write(tmp1);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		if(ok) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
