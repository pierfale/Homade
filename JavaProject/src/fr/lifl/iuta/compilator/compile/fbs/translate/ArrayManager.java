package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.Map;
import java.util.Stack;

import fr.lifl.iuta.compilator.compile.fbs.Config;

/**
 * 
 * @author falezp
 *
 * Contient les liste d'instruction permétant d'agir sur les tableaux
 */
public class ArrayManager {
	
	public static String createArray(WordTree wt, Map<String, MemoryBlock> addrVariable) {
		String retour = "";
		if(wt.size() == 2 && wt.getNode(0).getToken().getContents().equals("array")) { //if : array n;
			//create array
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			addrVariable.put(""+addr, new MemoryBlock(addr, 1, ""));
			if(Config.use_ip_freeMemory) {
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "LIT "+Config.ram_varaibles_adress +"\n";
				retour += wt.getNode(1).translate(addrVariable);
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
			}
			//create pointer
			retour += "LIT "+Config.ram_paging_adress+"\n";
			retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
			retour += "LIT "+addr+"\n";
			retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
			retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
			retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		}
		else {
			//number
			int n = 0;
			Stack<WordTree> stack = new Stack<WordTree>();
			stack.push(wt.getNode(1));
			while(!stack.empty()) {
				if(stack.peek().getFunction().equals("list_entities")) {
					if(stack.peek().nodeSize() == 1) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(0));
					}
					if(stack.peek().nodeSize() == 3) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(0));
						stack.push(curr.getNode(2));
					}
				}
				else if(stack.peek().getFunction().equals("entities")) {
					n++;
					stack.pop();
				}
			}
			int addr = MemoryBlock.nextFreeSegment(addrVariable);
			addrVariable.put(""+addr, new MemoryBlock(addr, 1, ""));
			if(Config.use_ip_freeMemory) {
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "LIT "+Config.ram_varaibles_adress +"\n";
				retour += "LIT "+n+"\n";
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
				retour += "LIT "+n+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
				retour += "LIT "+Config.ram_paging_adress+"\n";
				retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
				retour += "LIT "+addr+"\n";
				retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
				retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
				retour += "CALL _FUN__create_variable\n";
				retour += "LIT "+n+"\n";
				retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
			}
			stack.clear();
			n = 0;
			stack.push(wt.getNode(1));
			while(!stack.empty()) {
				if(stack.peek().getFunction().equals("list_entities")) {
					if(stack.peek().nodeSize() == 1) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(0));
					}
					if(stack.peek().nodeSize() == 3) {
						WordTree curr = stack.pop();
						stack.push(curr.getNode(2));
						stack.push(curr.getNode(0));
					}
				}
				else if(stack.peek().getFunction().equals("entities")) {
					retour += stack.pop().getNode(0).translate(addrVariable);
					retour += "LIT "+n+"\n";
					retour += VariableManager.set(addr);
					n++;
				}
			}
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
