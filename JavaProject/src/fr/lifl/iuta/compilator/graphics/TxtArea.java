package fr.lifl.iuta.compilator.graphics;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;

public class TxtArea extends JTextPane {

	private Texttxt t;
	
	public TxtArea(Texttxt t){
		super();
		this.t = t;
		this.setText(this.t.getChaine());
	}
	
	public Texttxt gett(){
		return this.t;
	}
	
	public String getcontenu(){
		return this.getText();
	}
}
