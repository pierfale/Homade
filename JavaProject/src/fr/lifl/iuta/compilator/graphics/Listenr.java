package fr.lifl.iuta.compilator.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class Listenr implements ActionListener{

	private Vue vue;
	private String[] tab;

	public Listenr(Vue vue){
		this.vue = vue;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		Runtime r = Runtime.getRuntime();
		if(e.getActionCommand().equals("Play")||e.getActionCommand().equals("Run")){
			Component c = vue.getPan1().getPanelsec().getCell1().
					getTabbedPane().getSelectedComponent();
			if (c instanceof JScrollPane){
				JViewport viewport = ((JScrollPane) c).getViewport();
				c = viewport.getView();
				if(c instanceof TxtArea){
					tab = new String[] {((TxtArea) c).gett().getChemin()+((TxtArea)c).gett().getName()};
					try {
						Process p = r.exec("java -jar compile "+tab[0]);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else{
					System.out.println("BarreOutils play   " + c.toString());
				}
			}
		}else if(e.getActionCommand().equals("Stop")){

		}else if(e.getActionCommand().equals("Save")){
			Component c = vue.getPan1().getPanelsec().getCell1().getTabbedPane().getSelectedComponent();
			if(c instanceof JScrollPane){
				JViewport viewport = ((JScrollPane) c).getViewport();
				c = viewport.getView();
				if(c instanceof TxtArea){
					String tmpChemin = ((TxtArea) c).gett().getChemin()+((TxtArea)c).gett().getName();

					String tmpChaine = ((TxtArea)c).getcontenu();
					new Texttxt(tmpChemin,tmpChaine);

				}else{
					System.out.println("BarreOutils save  " + c.toString());
				}
			}
		}else if(e.getActionCommand().equals("Debug")){
			//fr.lifl.iuta.compilator.base.Main.load(tab); 
			vue.remove(vue.getPan1());
			vue.setPanel(vue.getPan2());
			vue.getMenu().maj();
			this.actionPerformed(new ActionEvent(new Object(), 0, "Play"));
			vue.getPan2().loadGraphicsDebug();
		}else if(e.getActionCommand().equals("Fbs Doc")){
			try{
//				Desktop.getDesktop().browse(URI.create("http://www.google.fr"));
				Desktop.getDesktop().open(new File("ressources/fbs.html"));
			}catch(Exception e1){e1.printStackTrace();}
		}else if(e.getActionCommand().equals("Search")){
			search();
		}
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
		}else if (e.getActionCommand().equals("Quit")){		
			System.exit(0);
		}else if(e.getActionCommand().equals("IDE")){
			vue.remove(vue.getPan2());
			vue.setPanel(vue.getPan1());
		}
	}
	
	public void search() {
		JTextField entry = vue.getPan1().getBarreOutils().getTextField();
		Highlighter hilit = new DefaultHighlighter();
        //hilit.removeAllHighlights();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
        JTextArea are = new JTextArea("");
        String s = entry.getText();
        if (s.length() <= 0) {
            return;
        }
        Component c = vue.getPan1().getPanelsec().getCell1().getTabbedPane().getSelectedComponent();
		if(c instanceof JScrollPane){
			JViewport viewport = ((JScrollPane) c).getViewport();
			c = viewport.getView();
			if(c instanceof TxtArea){
				String tmps = ((TxtArea) c).getcontenu();
				are = new JTextArea(tmps);
			}else{
				System.out.println("BarreOutils search " + c.toString());
			}
		}
		are.setHighlighter(hilit);
		String content = are.getText();
        int index = content.indexOf(s, 0);
        System.out.println(index);
        if (index >= 0) {   // match found
            try {
                int end = index + s.length();
                System.out.println("index : " + index  + "  end : " + end);
                are.setText(content);
                are.getHighlighter().addHighlight(index, end, painter);
                are.setCaretPosition(end);
                entry.setBackground(Color.GREEN);
                //message("'" + s + "' found. Press ESC to end search");
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            entry.setBackground(Color.PINK);
            //message("'" + s + "' not found. Press ESC to start a new search");
        }
    }
}