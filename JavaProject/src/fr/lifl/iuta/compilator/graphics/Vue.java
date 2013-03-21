package fr.lifl.iuta.compilator.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;


public class Vue extends JFrame{

	private static final long serialVersionUID = 1L;
	protected PanelIDE pan1;
	protected GraphicsViewDebug pan2; 
	protected MenuBarr menu;

	public Vue() {	

		this.setTitle("IDE Fbs");

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("ressources/logo.jpg"));
		} catch (IOException e) {}

		this.setIconImage(img);
		this.setMinimumSize(new Dimension(300,400));

		setContenu();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLocationRelativeTo(null);
		this.setSize(new Dimension(500,500));
		this.setExtendedState(MAXIMIZED_BOTH);		

		this.setVisible(true);
	}

	public void setContenu(){

		menu = new MenuBarr(this);
		this.setJMenuBar(menu);	


		//OutilsBarr outils = new OutilsBarr();
		//this.add(outils);

		//		BarreOutils outils = new  BarreOutils(" Tools Bar ", 0); 
		//		
		//		pan = new PanelIDE();
		//		//this.setContentPane(pan);
		//		
		//		JPanel jpan = new JPanel( new BorderLayout()); 
		//		jpan.add(outils,BorderLayout.NORTH);
		//		jpan.add(pan, BorderLayout.CENTER);
		
		
		pan1 = new PanelIDE(this);
		pan2 = new GraphicsViewDebug(); pan2.loadGraphicsDebug();
		
		this.setContentPane(pan1);
	}

	public PanelIDE getPan1() {
		return pan1;
	}
	
	public GraphicsViewDebug getPan2() {
		return pan2;
	}

	public void setPanel(JPanel pan){
		this.setContentPane(pan);
	}

	public MenuBarr getMenu() {
		return menu;
	}

	public static void main(String[] args){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		RAM.load(new long []{});
		Processor.init();
		//fr.lifl.iuta.compilator.compile.fbs.Main.main(new String [] {"fbs01"});
		new Vue();
	}
}
