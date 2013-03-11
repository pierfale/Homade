package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;

public class ArrayManager {
	
	
	public static String createArray(WordTree wt, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		if(wt.size() == 2 && wt.getNode(0).getToken().getContents().equals("array")) { //if : array n;
			//create array
			int addr = MemoryBlock.nextFreeSegment(addrVariable);

			retour += "LIT "+Config.ram_paging_adress+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			retour += "LIT "+Config.ram_varaibles_adress +"\n";
			retour += wt.getNode(1).translate(addrVariable);
			retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			retour += "LIT "+Config.ram_paging_adress+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
			retour += "CALL _FUN__create_variable\n";
			retour += wt.getNode(1).translate(addrVariable);
			retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			//create pointer
			retour += "LIT "+Config.ram_paging_adress+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
			retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		}
		return retour;
	}

}
