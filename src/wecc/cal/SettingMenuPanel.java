package wecc.cal;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class SettingMenuPanel extends JPanel {

	SystemSetting sys = new SystemSetting();
	/**
	 * Create the panel.
	 */
	public SettingMenuPanel() {
		int yPosition = 66;
		int xPosition = 50;
		int gap = 150;
		int btnWeight = 104;
		int btnHeight = 104;
		setBackground(sys.sysSettingMenuTitleBack);
		setLayout(new BorderLayout(0, 0));
		
		JLabel menuTitle = new JLabel("Setting Menu");
		menuTitle.setFont(new Font("Verdana",Font.BOLD,40));
		menuTitle.setForeground(sys.sysWhite);
		menuTitle.setBackground(sys.sysSettingMenuTitleBack);
		add(menuTitle, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(sys.sysSettingMenuBackground);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton MFCCalBtn = sys.sysCalBtn;
		MFCCalBtn.setBounds(xPosition, yPosition, btnWeight, btnHeight);
		panel.add(MFCCalBtn);
		
		xPosition +=gap;
		JButton MFCSettingBtn = sys.sysMFCSetBtn;
		MFCSettingBtn.setBounds(xPosition, yPosition,btnWeight, btnHeight);
		panel.add(MFCSettingBtn);
		
		xPosition +=gap;
		JButton AddGasBtn = sys.sysGasListBtn;
		AddGasBtn.setBounds(xPosition, yPosition,btnWeight, btnHeight);
		panel.add(AddGasBtn);
		
		xPosition +=gap;
		JButton BackBtn = sys.sysBackBtn;
		BackBtn.setBounds(xPosition, yPosition, btnWeight, btnHeight);
		panel.add(BackBtn);
		
		BackBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "main");
			}
		});
		
		MFCCalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "mfcCal");
			}
		});
		
	}
}
