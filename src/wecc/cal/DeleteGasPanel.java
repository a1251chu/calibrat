package wecc.cal;

import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSliderUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class DeleteGasPanel extends JPanel {

	SystemSetting sys = new SystemSetting();
	JLabel infoText;
	JButton deleteBtn;
	public DeleteGasPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(sys.sysSettingMenuTitleBack);
		
		JLabel lblNewLabel = new JLabel("Delete GAS");
		lblNewLabel.setForeground(sys.sysWhite);
		lblNewLabel.setFont(sys.font);
		add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBackground(sys.sysGasBackground);
		add(centerPanel,BorderLayout.CENTER);
		
		infoText = new JLabel("Are your sure you want to delete");
		infoText.setFont(sys.textFont);
		infoText.setForeground(sys.sysWhite);
		
		infoText.setBounds(40, 64, 600, 30);
		centerPanel.add(infoText);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(null);
		btnPanel.setBackground(sys.sysWhite);
		
		
		deleteBtn = sys.sysDelBtn;
		deleteBtn.setLocation(350, 120);
		centerPanel.add(deleteBtn);		
		
		JButton cancelDBtn = sys.sysCancelDBtn;
		cancelDBtn.setLocation(150, 120);
		centerPanel.add(cancelDBtn);
		
		cancelDBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "gas");
			}
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String delGasName = deleteBtn.getActionCommand();
				MainFrame.mainFrame.cal.deleteGas(delGasName);
				MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "gas");
				MainFrame.mainFrame.gasPanel.refreshGasList();
			}
		});
	}
	
	void setText(String delGasName){
		infoText.setText("Are your sure you want to delete [" + delGasName+"] ?");
		deleteBtn.setActionCommand(delGasName);
	}
}
