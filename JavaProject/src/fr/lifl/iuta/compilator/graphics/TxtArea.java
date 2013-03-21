package fr.lifl.iuta.compilator.graphics;

import javax.swing.JTextArea;

public class TxtArea extends JTextArea {

	private Texttxt t;
	
	public TxtArea(Texttxt t){
		super(t.getChaine());
		this.t = t;
	}
	
	public Texttxt gett(){
		return this.t;
	}
	
	public String getcontenu(){
		return this.getText();
	}
}
