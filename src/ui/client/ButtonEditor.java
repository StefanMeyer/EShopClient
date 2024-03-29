package ui.client;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;



public class ButtonEditor extends DefaultCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7314503360622970832L;
	protected JButton button;
	private String label;
	private boolean isPushed;
//	private Clientverwaltung shop;
	public ButtonEditor(JCheckBox checkBox,ActionListener listen) {

		super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    //Actionlistener
	    button.addActionListener(listen);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		}else {
			button.setForeground(table.getForeground());
	    	button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    isPushed = true;
	    return button;
	}	

	public Object getCellEditorValue() {
		if (isPushed) { 
			//JOptionPane.showMessageDialog(button, label + ": Ouch!");
			System.out.println(label + ": Ouch!");   
		}
		isPushed = false;
    	return new String(label);
    }	

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
