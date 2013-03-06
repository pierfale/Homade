package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.util.ArrayList;
import java.util.Stack;

public class ParameterManager {
	
	private static Stack<String> parameters;
	
	private ParameterManager() {
		
	}
	
	public static void init() {
		parameters = new Stack<String>();
	}
	
	public static void add(String parameter) {
		parameters.push(parameter);
	}
	
	public static String next() {
		return parameters.pop();
	}
	
	public static boolean hasNext() {
		return !parameters.empty();
	}

}
