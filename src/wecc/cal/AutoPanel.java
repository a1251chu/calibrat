package wecc.cal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AutoPanel extends JPanel {
	SystemSetting sys = new SystemSetting();
	int[] concNum = new int[6];
	int[] flowNum = new int[4];
	String[] gasTable;
	String[] unitTable;
	HashMap<String,Double> gasTablePPM;
	static int gasTableNum;
	static int showGasIndex;
	static int unitTableNum;
	static int showUnitIndex;
	
	JLabel unitLbl;
	JLabel gasNameLbl;
	JButton gasUpBtn;
	JButton gasDownBtn;
	JButton genBtn;
	Unit gasUnit = new Unit();
	public AutoPanel() {
		setBackground(sys.sysGenBackground);
		setLayout(null);
	
		genBtn = sys.sysGenBtn;
		genBtn.setLocation(225, 361);
		add(genBtn);
		
		JButton cancelBtn = sys.sysCancelBtn;
		cancelBtn.setLocation(425, 361);
		add(cancelBtn);
		
		initComponent();
		

		
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
//				mainFrame = new MainFrame();

				//mainFrame.setVisible(true);
				CloseAutoFrame();
			}
		});
		
		genBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//mainFrame = new MainFrame();
				//device.setFullScreenWindow(mainFrame);
				//mainFrame.setVisible(true);
				

				Double targetConcPPB;
				Double targetFlowCC;
				Double targetGasConcPPB;
				String targetGas = gasNameLbl.getText();
				targetGasConcPPB = gasTablePPM.get(targetGas) * 1000;
				String targetUnit = unitLbl.getText();
				double originFlow = arrayToDouble(flowNum);
				double originConc = arrayToDouble(concNum);
				targetFlowCC = originFlow * 1000;
				targetConcPPB = originConc*(gasUnit.unitMap.get(targetUnit));
				MainFrame.cal.autoGen(targetGasConcPPB, targetConcPPB, targetFlowCC);
				MainFrame.airVoltage.setText(Double.toString(MainFrame.cal.airVoltage));
				MainFrame.gasVoltage.setText(Double.toString(MainFrame.cal.gasVoltage));
				MainFrame.standbyBtn.setEnabled(true);
				MainFrame.manualBtn.setEnabled(false);
				MainFrame.statusMessage = String.valueOf(originConc) + targetUnit + " " + targetGas;
				MainFrame.txtStatusMessage.setText(MainFrame.statusMessage);
				MainFrame.txtStatusMessage.setForeground(Color.WHITE);;
				MainFrame.lblAirTargetFlow.setText(String.valueOf(MainFrame.cal.highMFC.targetFlowCC/1000)+"L");
				MainFrame.lblGasTargetFlow.setText(String.valueOf(MainFrame.cal.lowMFC.targetFlowCC)+"cc");
				
				CloseAutoFrame();
			}
		});		
	}
	private void initComponent() {
		
		
		int xPosition = 41;
		int numXPosition = 59;
		int yPosition = 12;
		JLabel lblGas = new JLabel("GAS");                                                                                                         new JLabel("GAS");
		lblGas.setForeground(Color.WHITE);
		lblGas.setBounds(xPosition, yPosition, 86, 44);
		lblGas.setFont(sys.font);
		
		JLabel lblFlow = new JLabel("Concontration");
		lblFlow.setIcon(null);
		lblFlow.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlow.setForeground(Color.WHITE);
		lblFlow.setFont(sys.font);
		lblFlow.setBounds(xPosition*5, yPosition, 350, 44);
		add(lblFlow);
		
		yPosition+=50;
		add(lblGas);
		gasUpBtn = new JButton();
		gasUpBtn.setIcon(sys.upIcon);
		gasUpBtn.setBackground(sys.sysPlus);
		gasUpBtn.setBorder(null);
		gasNameLbl = new JLabel("--");
		gasDownBtn = new JButton(sys.minusIcon);
		gasDownBtn.setBackground(sys.sysMinus);
		gasDownBtn.setBorder(null);
		gasUpBtn.setBounds(xPosition, yPosition, 80, 43);
		gasNameLbl.setBounds(xPosition,yPosition+40,100,44);
		gasDownBtn.setBounds(xPosition, yPosition+80, 80	, 43);
		gasNameLbl.setForeground(Color.ORANGE);
		gasNameLbl.setFont(sys.font);
		add(gasUpBtn);
		add(gasNameLbl);
		add(gasDownBtn);

		getGasName();
		showGasIndex = 0;
		gasNameLbl.setText(gasTable[showGasIndex]);
		setGasBtnListener(gasUpBtn, gasNameLbl, gasDownBtn);
		
		xPosition +=85;
		numXPosition +=85;
		JButton[] concUpBtn = new JButton[6];
		JButton[] concDownBtn = new JButton[6];
		JLabel[] concLbl = new JLabel[6];

		int i;
		for(i=0;i<6;i++){
			concUpBtn[i] = new JButton(sys.upIcon);
			concUpBtn[i].setBackground(sys.sysPlus);
			concUpBtn[i].setBorder(null);
			concLbl[i] = new JLabel("0");
			concDownBtn[i] = new JButton(sys.minusIcon);
			concDownBtn[i].setBackground(sys.sysMinus);
			concDownBtn[i].setBorder(null);
			concUpBtn[i].setBounds(xPosition, yPosition, 80, 43);
			concLbl[i].setBounds(numXPosition,yPosition+40,23,44);
			concDownBtn[i].setBounds(xPosition, yPosition+80, 80	, 43);
			concLbl[i].setForeground(Color.ORANGE);
			concLbl[i].setFont(sys.font);
			add(concUpBtn[i]);
			add(concDownBtn[i]);
			add(concLbl[i]);
			if(i==3){
				xPosition += 68;
				numXPosition += 68;
				JLabel label = new JLabel(".");
				label.setForeground(Color.ORANGE);
				label.setFont(sys.font);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBounds(numXPosition,yPosition+40,23,44);
				add(label);
			}
			xPosition += 85;
			numXPosition += 85;
		}
		JButton unitUpBtn = new JButton(sys.upIcon);
		unitUpBtn.setBackground(sys.sysPlus);
		unitUpBtn.setBorder(null);
		JButton unitDownBtn = new JButton(sys.minusIcon);
		unitDownBtn.setBackground(sys.sysMinus);
		unitDownBtn.setBorder(null);
		unitUpBtn.setBounds(xPosition, yPosition, 80, 43);
		unitDownBtn.setBounds(xPosition, yPosition+80, 80, 43);
		unitLbl = new JLabel("PPB");
		unitLbl.setForeground(Color.ORANGE);
		unitLbl.setFont(sys.font);
		unitLbl.setBounds(xPosition,yPosition+40,123,44);
		add(unitUpBtn);
		add(unitLbl);
		add(unitDownBtn);
		
		getUnit();
		showUnitIndex = 0;
		unitLbl.setText(unitTable[showUnitIndex]);
		setUnitBtnListener(unitUpBtn, unitDownBtn, unitLbl);
		JLabel lblTotalFlow = new JLabel("Total FLow");
		lblTotalFlow.setForeground(Color.WHITE);
		lblTotalFlow.setFont(sys.font);
		lblTotalFlow.setBounds(26, 251, 290, 44);
		add(lblTotalFlow);
		
		JButton[] flowUpBtn = new JButton[4];
		JButton[] flowDownBtn = new JButton[4];
		JLabel[] flowLbl = new JLabel[4];
		xPosition = 41 + 85*3;
		numXPosition = 59 + 85*3;
		yPosition +=130;
		for(i=0;i<4;i++){
			flowUpBtn[i] = new JButton(sys.upIcon);
			flowUpBtn[i].setBackground(sys.sysPlus);
			flowUpBtn[i].setBorder(null);
			flowLbl[i] = new JLabel("0");
			flowDownBtn[i] = new JButton(sys.minusIcon);
			flowDownBtn[i].setBackground(sys.sysMinus);
			flowDownBtn[i].setBorder(null);
			flowUpBtn[i].setBounds(xPosition, yPosition, 80, 43);
			flowLbl[i].setBounds(numXPosition,yPosition+40,23,44);
			flowDownBtn[i].setBounds(xPosition, yPosition+80, 80, 43);
			flowLbl[i].setForeground(Color.ORANGE);
			flowLbl[i].setFont(sys.font);
			add(flowUpBtn[i]);
			add(flowDownBtn[i]);
			add(flowLbl[i]);
			if(i==1){
				xPosition += 68;
				numXPosition += 68;
				JLabel label = new JLabel(".");
				label.setForeground(Color.ORANGE);
				label.setFont(sys.font);
				label.setBounds(numXPosition,yPosition+40,23,44);
				add(label);
			}
			xPosition += 85;
			numXPosition += 85;
		}
		JLabel flowUnitLbl = new JLabel("Liter");
		flowUnitLbl.setForeground(Color.ORANGE);
		flowUnitLbl.setFont(sys.font);
		flowUnitLbl.setBounds(xPosition,yPosition+40,120,84);
		
		add(flowUnitLbl);
		
		setConcBtnListener(concUpBtn, concLbl,concDownBtn);
		setFlowBtnListener(flowUpBtn,flowLbl,flowDownBtn);
	}
	private void setUnitBtnListener(JButton unitUpBtn, JButton unitDownBtn, JLabel unitLbl) {
		unitUpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showUnitIndex++;
				if(showUnitIndex>(unitTableNum-1))
					showUnitIndex = 0;
				unitLbl.setText(unitTable[showUnitIndex]);
			}
		});
		unitDownBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showUnitIndex--;
				if(showUnitIndex<0)
					showUnitIndex = unitTableNum-1;
				unitLbl.setText(unitTable[showUnitIndex]);
				
			}
		});
	}
	private void setGasBtnListener(JButton gasUpBtn, JLabel gasNameLbl, JButton gasDownBtn) {
		gasUpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showGasIndex++;
				if(showGasIndex>(gasTableNum-1))
					showGasIndex = 0;
				gasNameLbl.setText(gasTable[showGasIndex]);
			}
		});
		
		gasDownBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showGasIndex--;
				if(showGasIndex<0)
					showGasIndex = gasTableNum-1;
				gasNameLbl.setText(gasTable[showGasIndex]);
				
			}
		});
	}
	
	public void getGasName(){
		gasTablePPM = new HashMap(MainFrame.cal.gasTablePPM);
		gasTableNum = MainFrame.cal.gasTablePPM.size();
		gasTable = new String[gasTableNum];
		 int i = 0;
		 for (Object key : MainFrame.cal.gasTablePPM.keySet()) {
	            gasTable[i] = (String) key;
	            i++;
	     }
		 if(gasTable.length>0){
			 gasNameLbl.setText(gasTable[0]);
			 gasUpBtn.setVisible(true);
			 gasDownBtn.setVisible(true);
			 genBtn.setEnabled(true);
		 }
		 else{
			 gasNameLbl.setText("---");
			 gasUpBtn.setVisible(false);
			 gasDownBtn.setVisible(false);
			 genBtn.setEnabled(false);
			 
		 }
	}
	
	private void getUnit(){
		unitTableNum = gasUnit.unitMap.size();
		unitTable = new String[unitTableNum];
		 int i = 0;
		 for (Object key : gasUnit.unitMap.keySet()) {
	            unitTable[i] = (String) key;
	            i++;
	        }
	}
	private void setConcBtnListener(JButton[] concUpBtn, JLabel[] concLbl,JButton[] concDownBtn) {
		concUpBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
						concNum[0]++;
						if(concNum[0]>9)
							concNum[0] = 0;
				       concLbl[0].setText(Integer.toString(concNum[0]));
				   }
			}
		});
		
		concUpBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					concNum[1]++;
					if(concNum[1]>9)
						concNum[1] = 0;
			       concLbl[1].setText(Integer.toString(concNum[1]));
				   }
			}
		});
		
		concUpBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					concNum[2]++;
					if(concNum[2]>9)
						concNum[2] = 0;
			       concLbl[2].setText(Integer.toString(concNum[2]));
				   }
			}
		});
		
		concUpBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					concNum[3]++;
					if(concNum[3]>9)
						concNum[3] = 0;
			       concLbl[3].setText(Integer.toString(concNum[3]));
				   }
			}
		});
		
		concUpBtn[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					concNum[4]++;
					if(concNum[4]>9)
						concNum[4] = 0;
			       concLbl[4].setText(Integer.toString(concNum[4]));
				   }
			}
		});
		
		concUpBtn[5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					concNum[5]++;
					if(concNum[5]>9)
						concNum[5] = 0;
			       concLbl[5].setText(Integer.toString(concNum[5]));
				   }
			}
		});
		
		concDownBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[0]--;
				if(concNum[0]<0)
					concNum[0] = 9;
		       concLbl[0].setText(Integer.toString(concNum[0]));
			}
		});
		
		concDownBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[1]--;
				if(concNum[1]<0)
					concNum[1] = 9;
		       concLbl[1].setText(Integer.toString(concNum[1]));
			}
		});
		
		concDownBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[2]--;
				if(concNum[2]<0)
					concNum[2] = 9;
		       concLbl[2].setText(Integer.toString(concNum[2]));
			}
		});
		
		concDownBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[3]--;
				if(concNum[3]<0)
					concNum[3] = 9;
		       concLbl[3].setText(Integer.toString(concNum[3]));
			}
		});
		
		concDownBtn[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[4]--;
				if(concNum[4]<0)
					concNum[4] = 9;
		       concLbl[4].setText(Integer.toString(concNum[4]));
			}
		});
		
		concDownBtn[5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				concNum[5]--;
				if(concNum[5]<0)
					concNum[5] = 9;
		       concLbl[5].setText(Integer.toString(concNum[5]));
			}
		});

	}
	
	private void setFlowBtnListener(JButton[] flowUpBtn, JLabel[] flowLbl,JButton[] flowDownBtn) {
		flowUpBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
						flowNum[0]++;
						if(flowNum[0]>9)
							flowNum[0] = 0;
				       flowLbl[0].setText(Integer.toString(flowNum[0]));
				   }
			}
		});
		
		flowUpBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					flowNum[1]++;
					if(flowNum[1]>9)
						flowNum[1] = 0;
			       flowLbl[1].setText(Integer.toString(flowNum[1]));
				   }
			}
		});
		
		flowUpBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					flowNum[2]++;
					if(flowNum[2]>9)
						flowNum[2] = 0;
			       flowLbl[2].setText(Integer.toString(flowNum[2]));
				   }
			}
		});
		
		flowUpBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( e.getSource() instanceof JButton) {
					flowNum[3]++;
					if(flowNum[3]>9)
						flowNum[3] = 0;
			       flowLbl[3].setText(Integer.toString(flowNum[3]));
				   }
			}
		});
		
		
		flowDownBtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flowNum[0]--;
				if(flowNum[0]<0)
					flowNum[0] = 9;
		       flowLbl[0].setText(Integer.toString(flowNum[0]));
			}
		});
		
		flowDownBtn[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flowNum[1]--;
				if(flowNum[1]<0)
					flowNum[1] = 9;
		       flowLbl[1].setText(Integer.toString(flowNum[1]));
			}
		});
		
		flowDownBtn[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flowNum[2]--;
				if(flowNum[2]<0)
					flowNum[2] = 9;
		       flowLbl[2].setText(Integer.toString(flowNum[2]));
			}
		});
		
		flowDownBtn[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flowNum[3]--;
				if(flowNum[3]<0)
					flowNum[3] = 9;
		       flowLbl[3].setText(Integer.toString(flowNum[3]));
			}
		});
		

	}
	private double arrayToDouble(int[] num){
		double result = 0;
		double dec=0.01;
		for(int i=(num.length-1);i>=0;i--){
			result += num[i] * dec;
			dec = dec * 10;
		}
		return result;
		
	}
	public void CloseAutoFrame(){
		MainFrame.mainFrame.cl.show(MainFrame.mainFrame.getContentPane(), "main");
	}
	

}
