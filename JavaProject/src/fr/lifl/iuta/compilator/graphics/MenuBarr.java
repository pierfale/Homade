package fr.lifl.iuta.compilator.graphics;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MenuBarr extends JMenuBar{

	private JMenu mFile = new JMenu("File");

	private JMenu mNFile = new JMenu("New");
	private JMenuItem iClass = new JMenuItem("New File");
	private JMenuItem iOther = new JMenuItem("Other");

	private JMenuItem iOpen = new JMenuItem("Open File");	
	private JMenuItem iSave = new JMenuItem("Save");
	private JMenuItem iQuit = new JMenuItem("Quit");


	private JMenu mHelp = new JMenu("Help");
	private JMenuItem iDoc  = new JMenuItem("Fbs Doc");

	private JMenu mRun = new JMenu("Run");
	private JMenuItem iCompil = new JMenuItem("Compiler");
	private JMenuItem iRun  = new JMenuItem("Run");
	private JMenuItem iStop = new JMenuItem("Stop");
	
	private JMenu mInterface = new JMenu("Interface");
	private JRadioButtonMenuItem radBut1 = new JRadioButtonMenuItem("IDE");
	private JRadioButtonMenuItem radBut2 = new JRadioButtonMenuItem("Debug");
	
	private Listenr listener; 

	public MenuBarr(Vue vue){
		
		listener = new Listenr(vue);
		
		this.mFile.add(this.mNFile);
		this.mFile.add(this.iOpen);
		iOpen.addActionListener(listener);
		this.mFile.addSeparator();
		this.mFile.add(this.iSave);
		this.iSave.setMnemonic(KeyEvent.VK_Q);//+InputEvent.CTRL_MASK);
		iSave.addActionListener(listener);
		this.mFile.addSeparator();
		this.mFile.add(this.iQuit);	
		iQuit.addActionListener(listener);

		this.mNFile.add(this.iClass);
		iClass.addActionListener(listener);
		this.mNFile.add(this.iOther);
		iOther.addActionListener(listener);

		this.mRun.add(this.iCompil);
		iRun.addActionListener(listener);
		this.mRun.add(this.iRun);
		iRun.addActionListener(listener);
		this.mRun.add(this.iStop);
		iStop.addActionListener(listener);
		
		this.mInterface.add(this.radBut1);
		radBut1.addActionListener(listener);
		this.mInterface.add(this.radBut2);
		radBut2.addActionListener(listener);
		ButtonGroup group = new ButtonGroup();
		radBut1.setSelected(true);
		radBut1.setMnemonic(KeyEvent.VK_R);
		group.add(radBut1);
		mInterface.add(radBut1);

		radBut2.setMnemonic(KeyEvent.VK_O);
		group.add(radBut2);
		mInterface.add(radBut2);

		this.mHelp.add(this.iDoc);
		iDoc.addActionListener(listener);

		this.add(this.mFile);
		this.add(this.mRun);
		this.add(this.mInterface);
		this.add(this.mHelp);
		
	}
	
	public void maj (){
		this.remove(mInterface);
		this.mInterface = new JMenu("Interface");
		this.radBut1 = new JRadioButtonMenuItem("IDE");
		this.radBut2 = new JRadioButtonMenuItem("Debug");
		radBut1.addActionListener(listener);
		radBut2.addActionListener(listener);
		this.mInterface.add(this.radBut1);
		this.mInterface.add(this.radBut2);
		ButtonGroup group = new ButtonGroup();
		radBut1.setMnemonic(KeyEvent.VK_R);
		group.add(radBut1);
		mInterface.add(radBut1);
		radBut2.setSelected(true);
		radBut2.setMnemonic(KeyEvent.VK_O);
		group.add(radBut2);
		mInterface.add(radBut2);
		this.add(mInterface);
		
	}
}
