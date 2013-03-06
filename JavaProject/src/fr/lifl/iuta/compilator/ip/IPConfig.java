package fr.lifl.iuta.compilator.ip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import fr.lifl.iuta.compilator.exception.IncorrectFormatSourceException;

public class IPConfig {
	
	private Map<String, Integer> positions;
	private Map<String, AbstractIP> ips;
	private Map<String, Boolean> shorts;
	
	public IPConfig(String pathname) throws FileNotFoundException, IncorrectFormatSourceException {
		
		positions = new HashMap<String, Integer>();
		ips = new HashMap<String, AbstractIP>();
		shorts = new HashMap<String, Boolean>();
		File file = new File(pathname);
		if(!file.exists())
			throw new FileNotFoundException(pathname);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String curr = "";
		int i = 0;
		try {
			curr = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(curr != null) {
			i++;
			Scanner sc = new Scanner(curr);
			int number = 0;
			String sn = sc.next();
			try {
				number = Integer.parseInt(sn);
			} catch(NumberFormatException e) {
				throw new IncorrectFormatSourceException("Numero d'IP incorrecte", i);
			}
			int mask = Integer.decode(sc.next());
			String sf = sc.next();
			if(sf.substring(0, 5).equals("class")) {
				String sf2 = sf.substring(6, sf.length()-1).trim();
				try {
					Class c = Class.forName(sf2);
					positions.put(sf2, number);
					ips.put(sf2, (AbstractIP)c.newInstance());
					ips.get(sf2).putNumberOfIP(Integer.parseInt(sn));
					ips.get(sf2).setMask(mask);
					if(sc.hasNext()) {
						if(sc.next().equals("short"))
							shorts.put(sf2, true);
						else
							shorts.put(sf2, false);
					}
					else
						shorts.put(sf2, false);
	
				} catch (ClassNotFoundException e) {
					throw new IncorrectFormatSourceException("Classe introuvable "+sf2, i);
				} catch (InstantiationException e) {
					throw new IncorrectFormatSourceException("Erreur instantiation de la classe "+sf2, i);
				} catch (IllegalAccessException e) {
					throw new IncorrectFormatSourceException("Erreur accès "+sf2, i);
				}
			}
			
			
			try {
				curr = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	public Set<String> getKeys() {
		return ips.keySet();
	}
	
	public int getPosition(String key) {
		return positions.get(key);
	}
	
	public AbstractIP getIP(String key) {
		return ips.get(key);
	}
	
	public boolean isShort(String key) {
		return shorts.get(key);
	}

}
