package fr.lifl.iuta.compilator.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MenuListener implements ActionListener {

	public Vue vue;

	public MenuListener(Vue vue) {
		super();
		this.vue = vue;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("MenuListener : " + e.getActionCommand());

//		switch(e.getActionCommand()){
//		case "New File" :
//			String s = JOptionPane.showInputDialog("Entrez un chemin");
//			if(s == null){System.out.println("merde");}
//			else{
//				System.out.println( vue.getPan().getCell1());
//				vue.getPan().getCell1().addOnglet(s);
//			}
//			break;
//		case "Quit" : 
//			System.exit(0);
//			break;
//		case "IDE" : 
//			vue.remove(vue.getPan());
//			vue.setPanel(new Panel());
//			break;
//		case "Debug" : 
//			vue.remove(vue.getPan());
//			vue.setPanel(new GraphicsViewDebug());
//			break;
//		}
		if(e.getActionCommand().equals("New File")){
			//new NewFileWindow(vue).getText();
			String s = JOptionPane.showInputDialog("Entrez un chemin");
			if(s == null){System.out.println("merde");}
			else{
				System.out.println( vue.getPan1().getPanelsec().getCell1());
				vue.getPan1().getPanelsec().getCell1().addOnglet(s);
			}
		}else if(e.getActionCommand().equals("Open File")){
			JFileChooser chooser = new JFileChooser();
           int tmp = chooser.showOpenDialog(null);
           if(tmp == 0)
            vue.getPan1().getPanelsec().getCell1().addOnglet(chooser.getSelectedFile().getAbsolutePath());
            
		}else if(e.getActionCommand().equals("Save")){
			new Listenr(vue).actionPerformed(new ActionEvent(new Object(), 0, "Save"));
		}else if (e.getActionCommand().equals("Quit")){		
			System.exit(0);
		}else if(e.getActionCommand().equals("Run")){
			new Listenr(vue).actionPerformed(new ActionEvent(new Object(), 0, "Play"));
		}else if(e.getActionCommand().equals("Stop")){
			new Listenr(vue).actionPerformed(new ActionEvent(new Object(), 0, "Stop"));
		}else if(e.getActionCommand().equals("IDE")){
			vue.remove(vue.getPan2());
			vue.setPanel(vue.getPan1());
		}else if(e.getActionCommand().equals("Debug")){
			vue.remove(vue.getPan1());
			vue.setPanel(vue.getPan2());
			vue.getMenu().maj();
		}
	}

}
