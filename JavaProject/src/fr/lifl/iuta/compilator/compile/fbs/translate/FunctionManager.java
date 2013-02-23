package fr.lifl.iuta.compilator.compile.fbs.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class FunctionManager {

	private static Map<String, Integer> list;
	
	/*
	 * Singleton
	 */
	private FunctionManager() {
		
	}
	
	public static void init() {
		list = new HashMap<String, Integer>();
	}
	
	public static void add(String name, int i) {
		list.put(name, i);
	}
	
	public static int get(String name) {
		if(list.get(name) != null)
			return list.get(name);
		else
			return -1;
	}
	
	public static String replace(String in) {
		String out = "";
		BufferedReader reader = new BufferedReader(new StringReader(in));
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(line != null) {
			Scanner sc = new Scanner(line);
			while(sc.hasNext()) {
				String curr = sc.next();

				if(curr.length() > 5 && curr.substring(0, 5).equals("_FUN_")) {
					out += "_LBL"+get(curr.substring(5));
				}
				else
					out += curr;
				if(sc.hasNext())
					out += " ";
				else
					out += "\n";
			}
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return out;
	}
	
	public static void dipslay() {
		Iterator<String> it = list.keySet().iterator();
		while(it.hasNext()) {
			String curr = it.next();
			System.out.println("fun : "+curr+" => "+list.get(curr));
		}
	}
}
