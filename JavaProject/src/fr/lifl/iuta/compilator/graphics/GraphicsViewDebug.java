package fr.lifl.iuta.compilator.graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class GraphicsViewDebug extends JPanel{

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
			console = GraphicsConsole.init(null);
			debug = new GraphicsDebug(console);
			ram = new GraphicsRAM(RAM.getMemory());
			stackFunction = new GraphicsStackFunction(Processor.getStackFunction());
			stackValue = new GraphicsStack(Processor.getStack());
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
		JTextField biblio = new JTextField();
		tabbed.addTab("Bibliothèque", biblio);
		panConsole.add(tabbed);
		grid.setConstraints(panConsole, c);
		add(panConsole);
		
		c.gridx++;
		c.weightx = 0.3;c.gridheight = 2;
		GraphicsIpLoad biblio2 = new GraphicsIpLoad();
		biblio2.setBorder(new TitledBorder("Loads ip"));
		grid.setConstraints(biblio2, c);
		add(biblio2);
		validate();
	}
}
