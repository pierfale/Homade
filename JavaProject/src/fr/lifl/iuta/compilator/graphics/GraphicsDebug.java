package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import fr.lifl.iuta.compilator.base.Main;
import fr.lifl.iuta.compilator.compile.ParserR;
import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnknownInstructionException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.Processor;
import fr.lifl.iuta.compilator.processor.RAM;

public class GraphicsDebug extends JPanel{
	
	private JTextPane code;
	
	public GraphicsDebug(GraphicsConsole console) throws UnknownInstructionException{
		super(new BorderLayout());code = new JTextPane();
		this.code.setBorder(new TitledBorder("Debug"));
		
		JPanel boutons = new JPanel();
		JButton stepByStep = new JButton("sBs");
		boutons.add(stepByStep);
		JButton read = new JButton("read");	
		JSpinner saisis = new JSpinner(new SpinnerNumberModel(0,0,getMaxPc(),1));
		ButtonListener listener2 = new ButtonListener(this.code,saisis,console);
		read.addActionListener(listener2);
		boutons.add(read);
		ButtonListener listener = new ButtonListener(this.code,saisis, console);
		stepByStep.addActionListener(listener);
		JLabel breakpoint = new JLabel("BreakPoint :");
		boutons.add(breakpoint);boutons.add(saisis);
		this.add(boutons,BorderLayout.NORTH);

		this.code.setEditable(false);
		this.code.setText(ParserR.BinToAsm(RAM.getMemory()));
		JScrollPane scrollPane = new JScrollPane(code);
		this.add(scrollPane,BorderLayout.CENTER);
	}
	
	public int getMaxPc() {
		int cpt = 0;
		try {
			while (RAM.get(cpt) != 0x0) cpt+=4;
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return cpt;
	}
}
