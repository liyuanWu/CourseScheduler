package liyuan.wu.classschedulor.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;


import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

public class AddPanelDialog<T> extends JFrame {

	private JPanel contentPane;
	private JButton button;
	private JComboBox comboBox;
	private DialogReturnListener dialogReturnListener;

	public static void main(String[] a){
		JFrame frame = new AddPanelDialog<String>(new String[]{"asdh","awqdsdh","asasdh","asdsdh","asadsdh"});
		frame.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public AddPanelDialog(T[] tSet) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 242);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		button = new JButton("\u663E\u793A");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("\u786E\u8BA4\u65B0\u5EFA");
		
		comboBox = new JComboBox();
		comboBox.setModel(new ListComboBoxModel<>(Arrays.asList(tSet)));
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setEditable(true);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(comboBox, 0, 331, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxNewCheckBox, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxNewCheckBox)
					.addContainerGap(135, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireAction();
				dispose();
			}
		});
	}
	
	public void fireAction(){
		if(this.dialogReturnListener!=null){
			this.dialogReturnListener.objectAction(this.comboBox.getSelectedItem());
		}
		
	}
	
	public void setDialogReturnListener(DialogReturnListener dialogReturnListener) {
		this.dialogReturnListener = dialogReturnListener;
	}
	

}
