package fr.lifl.iuta.compilator.compile.fbs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.lifl.iuta.compilator.compile.fbs.grammar.Grammar;

public class ReadFile {
	
	private String source = "";
	
	public ReadFile(String pathname) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathname)));
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(line != null) {
			source += line + '\n';
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getString() {
		return source;
	}

}
