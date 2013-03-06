package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.awt.geom.IllegalPathStateException;

import fr.lifl.iuta.compilator.compile.fbs.Config;

public class VariableManager {

	public static String get(int adress, int offset) {
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+adress+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "LIT "+offset+"\n";
		/*gérer offset : 1o*/
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		return retour;
	}
	//first value of stack
	public static String set(int adress, int offset) {
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+adress+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		/*gérer offset : 1o*/
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		retour += "IP 2 0 1 "+Config.IP_set_variable_RAM_32+"\n";
		return retour;
	}
	
	public static String set(int adress, int offset, String value) {
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+adress+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		/*gérer offset : 1o*/
		retour += value;
		retour += "IP 2 0 1 "+Config.IP_set_variable_RAM_32+"\n";
		return retour;
	}
	
	public static String create(int address, int size) {
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+address+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "LIT "+Config.ram_varaibles_adress +"\n";
		retour += "LIT "+size+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		retour += "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+address+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "CALL _FUN__create_variable\n";
		retour += "LIT "+size+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		return retour;
	}
	
	/*
	 * il faut préalablement avoir crée la destination
	 */
	public static void copy(int origin, int destination) {
		int lbl1 = LabelManager.getNext();
		int lbl2 = LabelManager.getNext();
		String retour = "LIT "+origin+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "LIT "+destination+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";		
		retour += "IP 3 0 1 "+Config.IP_copy_RAM+"\n";

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
		
		retour += "LIT "+Config.ram_paging_adress+"\n";
		
		// currSegment - segment
		retour += "_LBL"+lbl3+"\n";
		
		// --while currSegment.addr != 0
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "LIT 0\n";
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		retour += "BNZ _LBL"+lbl7+"\n";
		
		// currSegment - segment
		
		// --if currSegment == segment || currSegment.value == 2
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment - segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		// currSegment == segment - currSegment - segment
		retour += "IP 2 3 1 "+Config.IP_stack_over+"\n";
		// currSegment  - currSegment == segment - currSegment - segment
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		// currSegment.value - currSegment.size - currSegment == segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		// currSegment.value - currSegment == segment - currSegment - segment
		retour += "LIT "+Config.ram_frame_pointer+"\n";
		// frame - currSegment.value - currSegment == segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_compare_equals+"\n";
		// frame == currSegment.value - currSegment == segment - currSegment - segment
		retour += "IP 2 1 1 "+Config.IP_operation_or+"\n";
		// frame == currSegment.value || currSegment == segment - currSegment - segment
		retour += "BNZ _LBL"+lbl6+"\n";
		
		
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
		retour += "IP 2 1 1 "+Config.IP_compare_lowerEquals+"\n";
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
		retour += "IP 2 1 1 "+Config.IP_compare_upper+"\n";
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
		retour += "LIT "+Config.ram_paging_adress+"\n";
		
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
		retour += "IP 2 1 1 "+Config.IP_compare_lower+"\n";
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
		retour += "IP 2 1 1 "+Config.IP_compare_upperEquals+"\n";
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

		return retour;
	}
	
	public static String createFrame(int address) {
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT "+address+"\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "LIT "+Config.ram_frame_pointer+"\n";
		retour += "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		retour += "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_operation_sum+"\n";
		retour += "LIT "+Config.ram_frame_pointer+"\n";
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		return retour;
	}
	
	public static String deleteFrame() {
		int lbl1 = LabelManager.getNext();
		int lbl2 = LabelManager.getNext();
		
		String retour = "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		
		retour += "_LBL"+lbl2+"\n";
		// --while addr != 0
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 1 1 "+Config.IP_stack_nip+"\n";
		retour += "BZ _LBL"+lbl1+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "LIT 0\n";
		retour += "LIT 0\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		retour += "IP 1 1 1 "+Config.IP_operation_inc+"\n";
		
		retour += "BA _LBL"+lbl2+"\n";
		retour += "_LBL"+lbl1+"\n";
		
		// --end while
		
		retour += "IP 1 0 1 "+Config.IP_pop1+"\n";
		
		// --switch frame address
		retour += "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 1 1 1 "+Config.IP_get_variable_RAM_32+"\n";
		retour += "LIT 1\n";
		retour += "IP 2 1 1 "+Config.IP_operation_subtract+"\n";
		retour += "IP 1 2 1 "+Config.IP_stack_duplication+"\n";
		retour += "IP 1 2 1 "+Config.IP_get_variable_RAM_64+"\n";
		retour += "IP 2 2 1 "+Config.IP_stack_swap+"\n";
		retour += "LIT "+Config.ram_paging_adress+"\n";
		retour += "IP 3 3 1 "+Config.IP_stack_invrot+"\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		retour += "LIT 0\n";
		retour += "LIT 0\n";
		retour += "IP 3 0 1 "+Config.IP_set_variable_RAM_64+"\n";
		
		
		return retour;
	}
	
}
