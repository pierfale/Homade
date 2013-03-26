package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;

import fr.lifl.iuta.compilator.compile.fbs.Config;

/**
 * 
 * @author falezp
 *
 * Contient les liste d'instruction nécéssaire a l'intération avec les chaines
 *
 */
public class StringManager {
	public static String createString(WordTree wt, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		if(wt.nodeSize() > 0 && !wt.getNode(0).getToken().getContents().equals("")) {
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			addrVariable.put(""+addr, new MemoryBlock(addr, 1, ""));
			if(Config.use_ip_freeMemory) {
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "LIT "+Config.ram_varaibles_adress +"\n";
				retour += "LIT "+(wt.getNode(0).getToken().getContents().length()-1)+"\n";
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
				retour += "LIT "+(wt.getNode(0).getToken().getContents().length()-1)+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
				retour += "CALL _FUN__create_variable\n";
				retour += "LIT "+(wt.getNode(0).getToken().getContents().length()-1)+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			}
			for(int i=1; i<wt.getNode(0).getToken().getContents().length()-1; i++) {
				char curr = wt.getNode(0).getToken().getContents().charAt(i);
				if(curr == '\\') {
					i++;
					curr = wt.getNode(0).getToken().getContents().charAt(i);
					switch(curr) {
						case 'n':
							curr = 13;
							break;
						case '0':
							curr = 0;
							break;
						case 't':
							curr = 9;
							break;
					}
				}
				retour += "LIT "+((int)curr)+"\n";
				retour += "LIT "+(i-1)+"\n";
				retour += VariableManager.set(addr);
			}
			retour += "LIT 0\n";
			retour += "LIT "+(wt.getNode(0).getToken().getContents().length()-1)+"\n";
			retour += VariableManager.set(addr);		
			
			//create pointer
			retour += "LIT "+Config.ram_paging_adress+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
			retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		}
		else if(wt.nodeSize() > 0 && (!wt.getNode(0).getFunction().equals("variable") || !wt.getNode(0).getFunction().equals("variable_name"))) {
			retour += wt.getNode(0).translate(addrVariable);
			
		}
		else
			retour += createString(wt.getNode(0), addrVariable);

		return retour;
		
	}
}
