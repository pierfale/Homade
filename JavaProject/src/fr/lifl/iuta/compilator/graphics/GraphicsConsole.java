package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import fr.lifl.iuta.compilator.processor.BufferOut;

public class GraphicsConsole extends JPanel{
	
	private JTextPane text;
	
	public GraphicsConsole() {
		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		text = new JTextPane();
		text.setEditable(false);
		this.add(text);
	}
	
	public void refresh() {
		String s = "";
		while(BufferOut.ready()) {
			s+= BufferOut.next();
		}
		if(!s.equals(""))
			text.setText(text.getText()+s);
		repaint();validate();
	}
	
	public static void main(String [] args) {
		JFrame f = new JFrame();
		final GraphicsConsole console = new GraphicsConsole();
		f.setContentPane(console);
		f.setSize(400, 400);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Timer timer = new Timer();
		timer.schedule (new TimerTask() {
	    	public void run() {
	    		console.refresh();
	    	}
		}, 0, 50);
	}
	
	public static GraphicsConsole init(String [] args) {
		final GraphicsConsole console = new GraphicsConsole();
		Timer timer = new Timer();
		timer.schedule (new TimerTask() {
	    	public void run() {
	    		console.refresh();
	    	}
		}, 0, 50);
		return console;
	}
}
