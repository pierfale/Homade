package fr.lifl.iuta.compilator.compile.fbs;

public class Config {
	public static final int variable_memory_zone = 0x400;
	
	//IPS
	
	public static final int IP_stack_duplication = 8;
	public static final int IP_stack_swap = 9;
	
	public static final int IP_operation_sum = 32;
	public static final int IP_operation_subtract = 33;
	public static final int IP_operation_multiplie = 40;
	public static final int IP_operation_divide = 41;
	public static final int IP_operation_modulo = 42;
	
	public static final int IP_compare_lower = 57;
	public static final int IP_compare_upper = 58;
	public static final int IP_compare_equals = 54;
	public static final int IP_compare_lowerEquals = 61;
	public static final int IP_compare_upperEquals = 60;
	
	public static final int IP_special_display = 63;
	
	public static final int IP_add_variable_RAM = 64;
	public static final int IP_get_variable_RAM = 65;
}
