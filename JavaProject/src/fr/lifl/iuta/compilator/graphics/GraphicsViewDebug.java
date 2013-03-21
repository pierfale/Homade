package fr.lifl.iuta.compilator.graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class GraphicsViewDebug extends JPanel{

	public GraphicsViewDebug(){
		this.setName("Debug");
	}
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}	
		fr.lifl.iuta.compilator.compile.fbs.Main.main(new String [] {"fbs01"});
		JFrame f = new JFrame("Debugueur");
		GraphicsViewDebug g = new GraphicsViewDebug();g.loadGraphicsDebug();
		f.add(g);
		
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public void loadGraphicsDebug() {
		this.removeAll();
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(grid);
		c.fill = GridBagConstraints.BOTH;
		
		AbstractGraphicsStack stackFunction=null, stackValue=null;
		GraphicsRAM ram=null;
		GraphicsDebug debug=null;
		GraphicsConsole console = null;
		
		try {
			debug = new GraphicsDebug();
			ram = new GraphicsRAM(RAM.getMemory());
			stackFunction = new GraphicsStackFunction(Processor.getStackFunction());
			stackValue = new GraphicsStack(Processor.getStack());
			console = GraphicsConsole.init(null);
		} catch (UnknownInstructionException e) {}
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
		left.add(stackFunction);
		left.add(stackValue);
		
		c.gridy = 0;
		
		c.gridx = 0;
		c.gridheight = 3;
		c.gridwidth = 1;
		c.weightx = 0.2;
		c.weighty = 1;
		grid.setConstraints(left, c);
		add(left);
		
		c.gridx++;
		c.gridheight = 1;
		c.weightx = 0.5;
		grid.setConstraints(debug,c);
		add(debug);
		
		c.gridx++;
		c.weightx = 0.3;
		c.gridwidth = 1;
		grid.setConstraints(ram,c);
		add(ram);
		
		c.gridx = 1;
		c.gridy++;
		
		c.weightx = 0.6;
		c.gridheight = 1;
		
		JPanel panConsole = new JPanel();
		panConsole.setLayout(new BoxLayout(panConsole,BoxLayout.Y_AXIS));
		JTabbedPane tabbed = new JTabbedPane();
		tabbed.addTab("Console",new JScrollPane(console));
		JTextArea biblio = new JTextArea("XAXA");
		tabbed.addTab("Bibliothèque", biblio);
		panConsole.add(tabbed);
		grid.setConstraints(panConsole, c);
		add(panConsole);
		
		c.gridx++;
		c.weightx = 0.3;c.gridheight = 2;
		JTextArea biblio2 = new JTextArea("XAXA");
		grid.setConstraints(biblio2, c);
		add(biblio2);
		
	}
}
