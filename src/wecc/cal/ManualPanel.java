package wecc.cal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManualPanel extends JPanel {
	int[] highMFCNum = new int[4];
	int[] lowMFCNum = new int[4];
	SystemSetting sys = new SystemSetting();
	public ManualPanel() {
		initComponent();
	}
	
	public void initComponent() {     //初始化畫面元件
		setBounds(0, 0, 1050, 1500);
		setBackground(sys.sysGenBackground);
		setLayout(null);
		JButton genBtn = sys.sysGenBtn;
		genBtn.setLocation(225, 361);
		super.add(genBtn);
		
		JButton cancelBtn = sys.sysCancelBtn;
		cancelBtn.setLocation(425, 361);
		super.add(cancelBtn);

		cancelBtn.addActionListener(new ActionListener() {  //設定Cancel按鈕動作
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeManualFrame();
			}
		});
		
		genBtn.addActionListener(new ActionListener() {		//設定GEN 按鈕動作
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//mainFrame = new MainFrame(arrayToDouble(highMFCNum),arrayToDouble(lowMFCNum));
				//device.setFullScreenWindow(mainFrame);
				//mainFrame.setVisible(true);
				double airMFCFlow = (arrayToDouble(highMFCNum)* 1000.0);
				double gasMFCFlow= arrayToDouble(lowMFCNum);
				MainFrame.cal.manualGen(airMFCFlow, gasMFCFlow);
				MainFrame.statusMessage = MainFrame.cal.status;
				MainFrame.txtStatusMessage.setText(MainFrame.statusMessage);
				if(MainFrame.cal.error == 1){
					MainFrame.txtStatusMessage.setForeground(Color.RED);
				}else{
					MainFrame.standbyBtn.setEnabled(true);
					MainFrame.manualBtn.setEnabled(false);
					MainFrame.autoBtn.setEnabled(false);
					MainFrame.gasBtn.setEnabled(false);
					MainFrame.txtStatusMessage.setForeground(Color.WHITE);
					MainFrame.lblAirTargetFlow.setText(String.valueOf(new DecimalFormat("#.##").format(MainFrame.cal.highMFC.targetFlowCC / 1000)) + "L");
					MainFrame.lblGasTargetFlow.setText(String.valueOf(new DecimalFormat("##.##").format(MainFrame.cal.lowMFC.targetFlowCC)) + "cc");
				}
				closeManualFrame();

			}
		});
		
		int xPosition = 264;
		int numXPosition = 282;
		int yPosition = 50;
		JLabel lblFlow = new JLabel("High MFC");
		lblFlow.setForeground(sys.sysWhite);
		lblFlow.setFont(sys.font);
		lblFlow.setBounds(26, yPosition+40, 250, 44);
		add(lblFlow);
		
		JButton[] highMFCUpBtn = new JButton[4];
		JButton[] highMFCDownBtn = new JButton[4];
		JLabel[] highMFCLbl = new JLabel[4];

		
		int i;
		for(i=0;i<4;i++){
			highMFCUpBtn[i] = new JButton(sys.upIcon);
			highMFCLbl[i] = new JLabel("0");
			highMFCDownBtn[i] = new JButton(sys.minusIcon);
			highMFCUpBtn[i].setBackground(sys.sysPlus);
			highMFCDownBtn[i].setBackground(sys.sysMinus);
			highMFCUpBtn[i].setBorder(null);
			highMFCDownBtn[i].setBorder(null);
			highMFCUpBtn[i].setBounds(xPosition, yPosition, 80, 43);
			highMFCLbl[i].setBounds(numXPosition,yPosition+40,23,44);
			highMFCDownBtn[i].setBounds(xPosition, yPosition+80, 80	, 43);
			highMFCLbl[i].setForeground(Color.ORANGE);
			highMFCLbl[i].setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
			add(highMFCUpBtn[i]);
			add(highMFCDownBtn[i]);
			add(highMFCLbl[i]);
			if(i==1){
				xPosition += 88;
				numXPosition += 88;
				JLabel label = new JLabel(".");
				label.setForeground(Color.ORANGE);
				label.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
				label.setBounds(numXPosition,yPosition+40,23,44);
				add(label);
			}
			xPosition += 88;
			numXPosition += 88;
		}
		JLabel unitLbl = new JLabel("Liter");
		unitLbl.setForeground(Color.ORANGE);
		unitLbl.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
		unitLbl.setBounds(numXPosition-10,125,123,84);
		
		add(unitLbl);
		
		JLabel lblTotalFlow = new JLabel("Low MFC");
		lblTotalFlow.setForeground(sys.sysWhite);
		lblTotalFlow.setFont(sys.font);
		lblTotalFlow.setBounds(26, 251, 250, 44);
		add(lblTotalFlow);
		
		JButton[] lowMFCUpBtn = new JButton[4];
		JButton[] lowMFCDownBtn = new JButton[4];
		JLabel[] lowMFCLbl = new JLabel[4];
		xPosition = 264;
		numXPosition = 282;
		yPosition +=160;
		for(i=0;i<4;i++){
			lowMFCUpBtn[i] = new JButton(sys.upIcon);
			lowMFCLbl[i] = new JLabel("0");
			lowMFCDownBtn[i] = new JButton(sys.minusIcon);
			lowMFCUpBtn[i].setBackground(sys.sysPlus);
			lowMFCDownBtn[i].setBackground(sys.sysMinus);
			lowMFCUpBtn[i].setBorder(null);
			lowMFCDownBtn[i].setBorder(null);
			lowMFCUpBtn[i].setBounds(xPosition, yPosition, 80, 43);
			lowMFCLbl[i].setBounds(numXPosition,yPosition+40,23,44);
			lowMFCDownBtn[i].setBounds(xPosition, yPosition+80, 80	, 43);
			lowMFCLbl[i].setForeground(Color.ORANGE);
			lowMFCLbl[i].setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
			add(lowMFCUpBtn[i]);
			add(lowMFCDownBtn[i]);
			add(lowMFCLbl[i]);
			if(i==1){
				xPosition += 88;
				numXPosition += 88;
				JLabel label = new JLabel(".");
				label.setForeground(Color.ORANGE);
				label.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
				label.setBounds(numXPosition,256,23,44);
				add(label);
			}
			xPosition += 88;
			numXPosition += 88;
		}
		JLabel flowUnitLbl = new JLabel("CC");
		flowUnitLbl.setForeground(Color.ORANGE);
		flowUnitLbl.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 31));
		flowUnitLbl.setBounds(numXPosition-10,240,120,84);
		
		add(flowUnitLbl);
		
		setHighMFCBtnListener(highMFCUpBtn,highMFCLbl,highMFCDownBtn);
		setLowMFCBtnListener(lowMFCUpBtn,lowMFCLbl,lowMFCDownBtn);
	}
	
	private void setHighMFCBtnListener(JButton[] highMFCUpBtn, JLabel[] highMFCLbl,JButton[] highMFCDownBtn) {//設定high MFC流量按鈕
		highMFCUpBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
						highMFCNum[0]++;
						if(highMFCNum[0]>9)
							highMFCNum[0] = 0;
				       highMFCLbl[0].setText(Integer.toString(highMFCNum[0]));
				   }
			}
		});
		
		highMFCUpBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					highMFCNum[1]++;
					if(highMFCNum[1]>9)
						highMFCNum[1] = 0;
			       highMFCLbl[1].setText(Integer.toString(highMFCNum[1]));
				   }
			}
		});
		
		highMFCUpBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					highMFCNum[2]++;
					if(highMFCNum[2]>9)
						highMFCNum[2] = 0;
			       highMFCLbl[2].setText(Integer.toString(highMFCNum[2]));
				   }
			}
		});
		
		highMFCUpBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					highMFCNum[3]++;
					if(highMFCNum[3]>9)
						highMFCNum[3] = 0;
			       highMFCLbl[3].setText(Integer.toString(highMFCNum[3]));
				   }
			}
		});
		
		
		highMFCDownBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				highMFCNum[0]--;
				if(highMFCNum[0]<0)
					highMFCNum[0] = 9;
		       highMFCLbl[0].setText(Integer.toString(highMFCNum[0]));
			}
		});
		
		highMFCDownBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				highMFCNum[1]--;
				if(highMFCNum[1]<0)
					highMFCNum[1] = 9;
		       highMFCLbl[1].setText(Integer.toString(highMFCNum[1]));
			}
		});
		
		highMFCDownBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				highMFCNum[2]--;
				if(highMFCNum[2]<0)
					highMFCNum[2] = 9;
		       highMFCLbl[2].setText(Integer.toString(highMFCNum[2]));
			}
		});
		
		highMFCDownBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				highMFCNum[3]--;
				if(highMFCNum[3]<0)
					highMFCNum[3] = 9;
		       highMFCLbl[3].setText(Integer.toString(highMFCNum[3]));
			}
		});
	}
	
	private void setLowMFCBtnListener(JButton[] lowMFCUpBtn, JLabel[] lowMFCLbl,JButton[] lowMFCDownBtn) {     //設定low MFC流量按鈕
		lowMFCUpBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
						lowMFCNum[0]++;
						if(lowMFCNum[0]>9)
							lowMFCNum[0] = 0;
				       lowMFCLbl[0].setText(Integer.toString(lowMFCNum[0]));
				   }
			}
		});
		
		lowMFCUpBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					lowMFCNum[1]++;
					if(lowMFCNum[1]>9)
						lowMFCNum[1] = 0;
			       lowMFCLbl[1].setText(Integer.toString(lowMFCNum[1]));
				   }
			}
		});
		
		lowMFCUpBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					lowMFCNum[2]++;
					if(lowMFCNum[2]>9)
						lowMFCNum[2] = 0;
			       lowMFCLbl[2].setText(Integer.toString(lowMFCNum[2]));
				   }
			}
		});
		
		lowMFCUpBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					lowMFCNum[3]++;
					if(lowMFCNum[3]>9)
						lowMFCNum[3] = 0;
			       lowMFCLbl[3].setText(Integer.toString(lowMFCNum[3]));
				   }
			}
		});
		
		
		lowMFCDownBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lowMFCNum[0]--;
				if(lowMFCNum[0]<0)
					lowMFCNum[0] = 9;
		       lowMFCLbl[0].setText(Integer.toString(lowMFCNum[0]));
			}
		});
		
		lowMFCDownBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lowMFCNum[1]--;
				if(lowMFCNum[1]<0)
					lowMFCNum[1] = 9;
		       lowMFCLbl[1].setText(Integer.toString(lowMFCNum[1]));
			}
		});
		
		lowMFCDownBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lowMFCNum[2]--;
				if(lowMFCNum[2]<0)
					lowMFCNum[2] = 9;
		       lowMFCLbl[2].setText(Integer.toString(lowMFCNum[2]));
			}
		});
		
		lowMFCDownBtn[3].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lowMFCNum[3]--;
				if(lowMFCNum[3]<0)
					lowMFCNum[3] = 9;
		       lowMFCLbl[3].setText(Integer.toString(lowMFCNum[3]));
			}
		});
	}
	
	private double arrayToDouble(int[] num){   //將陣列轉型成double
		double result = 0;
		double dec=0.01;
		for(int i=(num.length-1);i>=0;i--){
			result += num[i] * dec;
			dec = dec * 10;
		}
		return result;
		
	}
	
	private void closeManualFrame(){    //關閉畫面
		MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "main");
	}

	}


