package fr.lifl.iuta.compilator.graphics;

import java.util.Stack;

import javax.swing.table.AbstractTableModel;

/**
 * @author danglotb
 * Classe du TableModel utilise par GraphicsStack
 */
public class MyTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"index","values"};
	private Object[][] data;
	private Stack<Integer> stack;
	
	public MyTableModel(Stack<Integer> stack){
		init(stack);
	}
	
	public void init(Stack<Integer> stack) {
		this.stack = stack;data = new Object[stack.size()][2];
		int cpt= stack.size()-1;
		for (int i = 0 ; i < stack.size(); i++){
			data[i][0] = cpt; data[i][1] = stack.get(cpt--);
		}
	}
	
    public String getColumnName(int col) {return columnNames[col];}

    public boolean isCellEditable(int row, int col) {return false;}

	public int getColumnCount() {return 2;}

	public int getRowCount() {return stack.size();}

	public Object getValueAt(int arg0, int arg1) {return data[arg0][arg1];}

	public void setStack(Object arg1) {init((Stack<Integer>) arg1);}
	
}
