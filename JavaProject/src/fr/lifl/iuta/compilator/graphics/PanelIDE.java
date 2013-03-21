package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PanelIDE extends JPanel {

	private Vue vue;
	private Panelsec pan;
	private BarreOutils outils;
	
	public PanelIDE(Vue vue){
		super(new BorderLayout());
		this.vue = vue;
		this.setName("IDE");
		
		outils = new  BarreOutils(this.vue," Tools Bar ", 0); 
		pan = new Panelsec();
		
		this.add(outils,BorderLayout.NORTH);
		this.add(pan, BorderLayout.CENTER);
	}
	
	public Panelsec getPanelsec(){
		return pan;
	}
	
	public BarreOutils getBarreOutils(){
		return this.outils;
	}
}
