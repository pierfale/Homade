package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.awt.geom.IllegalPathStateException;

import fr.lifl.iuta.compilator.compile.fbs.Config;

public class VariableManager {

	public static String get(int adress, int offset) {
		String retour = "LIT "+adress+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		return retour;
	}
	
	public static String set(int adress, int offset, String value) {
		String retour = "LIT "+adress+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += value;
		retour += "IP 2 0 1 "+Config.IP_set_variable_RAM_32+"\n";
		return retour;
	}
	
	public static String create(int address, int size) {
		/*
		int lbl1 = LabelManager.getNext();
		int lbl2 = LabelManager.getNext();
		String retour = "LIT "+Config.variable_memory_zone_segment+"\n";
		retour += "_LBL"+lbl2+"\n";
		retour += "IP 0 0 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 0 0 1 "+Config.IP_stack_nip+"\n";
		//0 ou 1
		retour += "IP 0 0 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 0 0 1 "+Config.IP_stack_duplication+"\n";
		retour = "LIT 0\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour = "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_or+"\n";
		retour += "BNZ _LBL"+lbl1+"\n";
		retour += "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "BA _LBL"+lbl2+"\n";
		retour += "_LBL"+lbl1+"\n";
		*/
		String retour = "LIT "+address+"\n";
		retour += "LIT "+Config.variable_memory_zone+"\n";
		retour += "LIT "+size+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		retour += "LIT "+address+"\n";
		retour += "LIT "+address+"\n";
		retour += "CALL _FUN__create_variable\n";
		retour += "LIT "+size+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		return retour;
	}
	
	public static String createFunction() {
		int lbl0 = LabelManager.getNext();
		int lbl1 = LabelManager.getNext();
		int lbl2 = LabelManager.getNext();
		int lbl3 = LabelManager.getNext();
		int lbl5 = LabelManager.getNext();
		int lbl6 = LabelManager.getNext();
		int lbl7 = LabelManager.getNext();
		
		String retour = "_LBL"+lbl0+"\n";
		FunctionManager.add("_create_variable", lbl0);
		//segment
		
		retour += "LIT "+Config.variable_memory_zone_segment+"\n";
		
		// currSegment - segment
		retour += "_LBL"+lbl3+"\n";
		
		// --while currSegment.addr != 0
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "LIT 0\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "BNZ _LBL"+lbl1+"\n";
		
		// currSegment - segment
		
		// --if currSegment != segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "BNZ _LBL"+lbl7+"\n";
		
		// currSegment - segment
		
		// -- if segment.value < currSegment.value + currSegment.size && segment.value >= currSegment.value
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// currSegment.value - currSegment.size - segment - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// currSegment.value - segment - currSegment - segment - currSegment - segment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// segment.value - segment.size - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// segment.value - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_upperEquals+"\n";
		// segment.value >= currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 3 3 1 "+Config.IP_stack_invrot+"\n";
		// currSegment - segment - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// currSegment.value - currSegment.size - segment - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// currSegment.value + currSegment.size - segment - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment - currSegment.value + currSegment.size - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// segment.value - segment.size - currSegment.value + currSegment.size - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// segment.value - currSegment.value + currSegment.size - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_lower+"\n";
		// segment.value < currSegment.value + currSegment.size - segment.value >= currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_and+"\n";
		// segment.value < currSegment.value + currSegment.size && segment.value >= currSegment.value - currSegment - segment
		retour += "BZ _LBL"+lbl2+"\n";
		retour += "_LBL"+lbl5+"\n";
		// --inc segment
		
		// currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_tuck+"\n";
		// currSegment - segment - currSegment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment - currSegment - currSegment
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		// segment - segment - currSegment - currSegment
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// currSegment -  segment - segment - currSegment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment - segment - currSegment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// segment.value - segment.size -  currSegment - segment - segment - currSegment
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		// segment.size -  currSegment - segment - segment - currSegment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// currSegment - segment.size - segment - segment - currSegment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// currSegment.value - currSegment.size - segment.size - segment - segment - currSegment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// currSegment.value + currSegment.size - segment.size - segment - segment - currSegment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment.size - currSegment.value + currSegment.size - segment - segment - currSegment
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		// segment - currSegment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "LIT "+Config.variable_memory_zone_segment+"\n";
		
		retour += "BA _LBL"+lbl3+"\n";
		retour += "_LBL"+lbl2+"\n";
		
		// -- if segment.value+segment.size > currSegment.value + currSegment.size && segment.value+segment.size <= currSegment.value
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";		
		// currSegment.value - currSegment.size - segment - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// currSegment.value - segment - currSegment - segment - currSegment - segment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// segment.value - segment.size - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// segment.value + segment.size - currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_upper+"\n";
		// segment.value + segment.size > currSegment.value - currSegment - segment - currSegment - segment
		retour += "IP 3 3 1 "+Config.IP_stack_invrot+"\n";
		// currSegment - segment - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// currSegment.value - currSegment.size - segment - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// currSegment.value + currSegment.size - segment - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// segment - currSegment.value + currSegment.size - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// segment.value - segment.size - currSegment.value + currSegment.size - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// segment.value + segment.size - currSegment.value + currSegment.size - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_lowerEquals+"\n";
		// segment.value + segment.size <= currSegment.value + currSegment.size - segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_and+"\n";
		// segment.value + segment.size <= currSegment.value + currSegment.size && segment.value + segment.size > currSegment.value - currSegment - segment
		retour += "BZ _LBL"+lbl6+"\n";
		retour += "BA _LBL"+lbl5+"\n";
		retour += "_LBL"+lbl6+"\n";
		
		// currSegment - segment
		retour += "IP 1 1 1 "+Config.IP_operation_inc+"\n";
		
		// --end while
		retour += "BA _LBL"+lbl3+"\n";
		
		retour += "_LBL"+lbl7+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "_LBL"+lbl1+"\n";
		
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// segment
		retour += "RET\n";
		
		/*
		//old

		
		String retour = "_LBL"+lbl+"\n";
		FunctionManager.add("_create_variable", lbl);
		// size
		retour += "LIT "+Config.variable_memory_zone+"\n";
		// currAddr - size
		retour += "_LBL"+lbl5+"\n";	
		retour += "LIT "+Config.variable_memory_zone_segment+"\n";
		// currSegment - currAddr - size

		//while
		retour += "_LBL"+lbl2+"\n";		
		//while condition (addr == 0 || addr == 1)
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_nip+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "LIT 0\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		retour += "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_or+"\n";
		// boolean - currSegment - currAddr - size
		retour += "BNZ _LBL"+lbl3+"\n";
		//while contents
		retour += "IP 2 2 1 "+Config.IP_stack_tuck+"\n";
		// currAddr - currSegment - currAddr - size
		retour += "IP 2 2 1 "+Config.IP_stack_over+"\n";
		// currSegment - currAddr - currSegment - currAddr - size
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// addr_real - size - curr_addr - ...
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		// addr_real - addr_real - size - curr_addr
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// addr_real - size - addr_real - curr_addr
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// addr_real+size - addr_real - curr_addr
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// curr_addr - addr_real+size - addr_real
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// addr_real+size - curr_addr - addr_real
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// curr_addr - addr_real+size - curr_addr - addr_real
		retour += "IP 2 1 1 "+Config.IP_compare_lowerEquals+"\n";
		// curr_addr <= addr_real+size - curr_addr - addr_real
		retour += "IP 3 3 1 "+Config.IP_stack_invrot+"\n";
		// curr_addr - addr_real - curr_addr <= addr_real+size
		retour += "IP 2 1 1 "+Config.IP_compare_upper+"\n";
		// curr_addr > addr_real - curr_addr <= addr_real+size
		retour += "IP 2 1 1 "+Config.IP_operation_and+"\n";
		// curr_addr > addr_real && curr_addr <= addr_real+size
		retour += "BNZ _LBL"+lbl1+"\n";
		retour += "IP 0 1 1 "+Config.IP_pop1+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "BA _LBL"+lbl5+"\n";
		retour += "_LBL"+lbl1+"\n";	
		// currSegment - currAddr - size
		retour += "IP 2 3 1 "+Config.IP_stack_tuck+"\n";
		// segment -  curr_addr - segment - size
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// curr_addr - segment -  curr_addr - segment - size
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		// curr_addr - segment - size
		retour += "IP 3 3 1 "+Config.IP_stack_invrot+"\n";
		// size - curr_addr - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// curr_addr - size - curr_addr - segment
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// curr_addr+size - curr_addr - segment
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// segment - curr_addr+size - curr_addr
		retour += "IP 2 3 1 "+Config.IP_stack_tuck+"\n";
		// segment - curr_addr+size - segment - curr_addr
		retour += "IP 2 3 1 "+Config.IP_stack_tuck+"\n";
		// curr_addr+size - segment - curr_addr+size - segment - curr_addr
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		//  segment - curr_addr+size - curr_addr+size - segment - curr_addr
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// real_address - size - curr_addr+size - curr_addr+size - segment - curr_addr
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// real_address - curr_addr+size - curr_addr+size - segment - curr_addr
		retour += "IP 2 1 1 "+Config.IP_compare_lower+"\n";
		// real_address < curr_addr+size - curr_addr+size - segment - curr_addr
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// curr_addr+size - segment - real_address < curr_addr+size - curr_addr
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - curr_addr+size - segment - real_address < curr_addr+size - curr_addr
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// real_address - size - curr_addr+size - segment - real_address < curr_addr+size - curr_addr
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		// real_address+size - curr_addr+size - segment - real_address < curr_addr+size - curr_addr
		retour += "IP 2 1 1 "+Config.IP_compare_upperEquals+"\n";
		// real_address+size >= curr_addr+size - segment - real_address < curr_addr+size - curr_addr
		retour += "IP 3 3 1 "+Config.IP_stack_rot+"\n";
		// real_address < curr_addr+size - real_address+size >= curr_addr+size - segment - curr_addr
		retour += "IP 2 1 1 "+Config.IP_operation_and+"\n";
		// real_address < curr_addr+size && real_address+size >= curr_addr+size - segment - curr_addr
		retour += "BNZ _LBL"+lbl6+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "BA _LBL"+lbl5+"\n";
		retour += "_LBL"+lbl6+"\n";		
		// segment -  curr_addr - size
		retour += "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		//inc
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "BA _LBL"+lbl2+"\n";
		//end while
		retour += "_LBL"+lbl3+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "BNZ _LBL"+lbl4+"\n";
		retour += "BA _LBL"+lbl5+"\n";
		retour += "_LBL"+lbl4+"\n";
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "RET\n";*/
		return retour;
	}
}
