package fr.lifl.iuta.compilator.graphics;

import java.io.*;

public class Texttxt {

	private final String EXTENSION = ".txt";
	private String chaine;
	private String chemin;
	private String name;
	private boolean isafullname;

	public Texttxt(String s1){

		test(construct(s1));

	}



	public Texttxt(String chemin, String chaine) {
		construct(chemin);
		this.chaine = chaine;
		System.out.println("Texttxt ->  : " + this.chemin + this.name);
		this.writetxt();
	}

	public String construct(String s){
		this.chemin = s;
		this.isafullname = false;

		for(int i=s.length()-1; i>=0; i--){

			if(s.charAt(i) == '/' || s.charAt(i) == '\\' ){

				this.name = s.substring(i+1);
				chemin = s.substring(0, i+1);

				if(i==0){isafullname = false;}				
				else {isafullname = true;}

				break;
			}
			if(i==0){
				this.name = s;
				isafullname = false;
				this.chemin = "";
				break;

			}
		}
		return s;
	}

	public void test(String s){
		if(!this.readtxt()){
			if(!this.createFile(s)){
				System.out.println("Texttxt -> damit !");
			}
		}
	}


	public boolean readtxt(){
		//lecture du fichier texte
		this.chaine = "";
		try{
			InputStream ips=new FileInputStream(this.chemin+this.name); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				this.chaine+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			return false;
		}
		return true;
	}

	public boolean createFile(String s) {

		File file;

		final File parentDir = new File(chemin);
		parentDir.mkdir();
		final String hash = this.name;
		final String fileName = hash + ".txt";
		if(isafullname){			
			file = new File(fileName);
		}else{
			file = new File(parentDir, fileName);
		}
		try{
			file.createNewFile();
		}catch(IOException e){
			return false;
		}
		return true;

	}
	public void writetxt(){
		String tmp = this.chemin + this.name;
		//		if(tmp.substring(tmp.length()-4, tmp.length()-1) != ".txt"){
		//			tmp += ".txt";
		//		}

		//crÃ©ation ou ajout dans le fichier texte
		try {
			FileWriter fw = new FileWriter (tmp);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (this.chaine); 
			fichierSortie.close();
			System.out.println("Le fichier " + tmp + " a été créer!"); 
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getChaine() {
		return chaine;
	}

	public String getChemin() {
		return chemin;
	}

	public String getName(){
		return this.name;
	}

	public String getNameExtended(){
		if((this.EXTENSION).equals(this.getName().
				substring(this.getName().length()-this.EXTENSION.length()))){
			return this.name;
		}else{
			return this.name+this.EXTENSION;
		}

	}

}
