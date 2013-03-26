package fr.lifl.iuta.compilator.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fr.lifl.iuta.compilator.processor.Processor;

/**
 * @author danglotb
 * Classe qui represente les stacks du processeur.
 */
public class AbstractGraphicsStack extends JPanel implements Observer{
	
	protected MyTableModel model;
	protected JTable table;

	public AbstractGraphicsStack(Stack<Integer> stack, String s) {
		Processor.addObserver(this);
		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		JLabel l = new JLabel(s);
		l.setHorizontalAlignment(JLabel.CENTER);
		this.add(l,b.NORTH);
		model = new MyTableModel(stack);
		table = new JTable(model);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer renderer2 = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		renderer2.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer2);
		renderer.setBackground(new Color(206,206,206));
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	public void update(Observable arg0, Object arg1) {
		model.fireTableDataChanged();
		this.validate();
		this.repaint();
	}

}
