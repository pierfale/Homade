package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;


public class StringManager {
	public static String createString(WordTree wt, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		if(!wt.getToken().getContents().equals("")) {
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			addrVariable.put(""+addr, new MemoryBlock(addr, 1));
			if(Config.use_ip_freeMemory) {
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "LIT "+Config.ram_varaibles_adress +"\n";
				retour += "LIT "+(wt.getToken().getContents().length()-1)+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "IP 2 0 1 "+Config.IP_freeMemory_RAM+"\n";
			}
			else {
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "LIT "+Config.ram_varaibles_adress +"\n";
				retour += "LIT "+(wt.getToken().getContents().length()-1)+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
				retour += "CALL _FUN__create_variable\n";
				retour += "LIT "+(wt.getToken().getContents().length()-1)+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			}
			for(int i=1; i<wt.getToken().getContents().length()-1; i++) {
				retour += "LIT "+((int)wt.getToken().getContents().charAt(i))+"\n";
				retour += "LIT "+(i-1)+"\n";
				retour += VariableManager.set(addr);
			}
			retour += "LIT 0\n";
			retour += "LIT "+(wt.getToken().getContents().length()-1)+"\n";
			retour += VariableManager.set(addr);		
		}
		else if(wt.nodeSize() > 0)
			retour += createString(wt.getNode(0), addrVariable);
			
		return retour;
		
	}
}
