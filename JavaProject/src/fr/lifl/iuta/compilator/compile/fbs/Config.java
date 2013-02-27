package fr.lifl.iuta.compilator.compile.fbs;

public class Config {
	
	//CONSTANTE
	public static final int ram_end_of_stack = 0x0;
	public static final int ram_free_varaible = 0x1;
	public static final int ram_frame_pointer = 0x2;
	public static final int	ram_paging_adress = 0x200;
	public static final int ram_varaibles_adress = 0x400;
	
	//IPS
	public static final int IP_stack_duplication = 8;
	public static final int IP_stack_swap = 9;
	public static final int IP_stack_tuck = 10;
	public static final int IP_stack_over = 11;
	public static final int IP_stack_rot = 12;
	public static final int IP_stack_invrot = 13;
	public static final int IP_stack_nip = 14;

	public static final int IP_pop1 = 15;
	
	public static final int IP_operation_sum = 32;
	public static final int IP_operation_subtract = 33;
	public static final int IP_operation_inc = 34;
	public static final int IP_operation_dec = 35;
	public static final int IP_operation_not = 36;
	public static final int IP_operation_and = 37;
	public static final int IP_operation_or = 38;
	public static final int IP_operation_xor = 39;
	public static final int IP_operation_multiplie = 40;
	public static final int IP_operation_divide = 41;
	public static final int IP_operation_modulo = 42;
	
	public static final int IP_binary_leftShit = 43;
	public static final int IP_binary_rightShit = 44;
	
	public static final int IP_true = 48;
	public static final int IP_false = 49;
	
	public static final int IP_compare_lower = 57;
	public static final int IP_compare_upper = 58;
	public static final int IP_compare_equals = 54;
	public static final int IP_compare_lowerEquals = 61;
	public static final int IP_compare_upperEquals = 60;
	
	public static final int IP_special_display = 63;
	
	public static final int IP_set_variable_RAM_32 = 64;
	public static final int IP_set_variable_RAM_64 = 65;
	public static final int IP_get_variable_RAM_32 = 66;
	public static final int IP_get_variable_RAM_64 = 67;
	public static final int IP_copy_RAM = 68;
}
