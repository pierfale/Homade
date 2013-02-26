package fr.lifl.iuta.compilator.compile.fbs.console;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.RAM;

public class Console extends JFrame {
	
	private JTextPane text;
	private int ramZone;
	
	public Console(int ramZone) {
		this.ramZone = ramZone;
		text = new JTextPane();
		text.setEditable(false);
		refresh();
		this.setContentPane(text);
		this.setVisible(true);
		this.setSize(350,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	
	public void refresh() {
		//write
		String s = "";
		for(int i=0; i<24; i++) {
			for(int j=0; j<80; j++) {
				try {
					s += (char)RAM.get(ramZone+(i*80+j)/4);
				} catch (InvalideAdressException e) {
					e.printStackTrace();
				} catch (UnloadedRAMException e) {
					e.printStackTrace();
				}
			}
			s += '\n';
		}
		text.setText(s);
		//style
	}

}
