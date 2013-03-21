package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import fr.lifl.iuta.compilator.compile.fbs.Config;
import fr.lifl.iuta.compilator.compile.fbs.Rapport;


public class Translation {
	
	public static boolean exec(String out, WordTree wt) {
		Rapport.addLine("<h2>Traduction</h2>");
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
				String tmp2 = "";
				//init ram segment
				tmp2 += "LIT "+Config.ram_paging_adress+"\n";
				tmp2 += "LIT "+Config.ram_frame_pointer+"\n";
				tmp2 += "LIT "+(Config.ram_paging_adress+1)+"\n";
				tmp2 += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
				
				//call main
				tmp2 += "CALL _LBL"+FunctionManager.get("main")+"\n";
				tmp2 += "HALT\n";
				
				tmp1 = tmp2+tmp1;
				if(!Config.use_ip_freeMemory)
				tmp1 += VariableManager.createFunction();
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
