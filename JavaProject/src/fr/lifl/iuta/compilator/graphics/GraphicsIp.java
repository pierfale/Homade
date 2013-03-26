package fr.lifl.iuta.compilator.graphics;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;

public class GraphicsIp extends JToolBar {
	
	public GraphicsIp(String name) {
		super(name);
		this.add(new JLabel(name));
		this.setTransferHandler(new TransferHandler() {
				public void exportDone(JComponent comp, Transferable data, int action){
					System.out.println("SWITCH!");
				}
		});
	}
}
