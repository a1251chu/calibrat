package wecc.cal;


import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import eu.hansolo.steelseries.extras.Horizon;
import eu.hansolo.steelseries.extras.HorizonBeanInfo;
import eu.hansolo.steelseries.gauges.AbstractLinear;
import eu.hansolo.steelseries.gauges.DigitalRadial;
import eu.hansolo.steelseries.gauges.DisplayRectangular;
import eu.hansolo.steelseries.gauges.Lcd;
import eu.hansolo.steelseries.gauges.Linear;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.Radial1Square;
import eu.hansolo.steelseries.gauges.SparkLine;
import eu.hansolo.steelseries.tools.BackgroundColor;
import eu.hansolo.steelseries.tools.ColorDef;
import eu.hansolo.steelseries.tools.CustomColorDef;
import eu.hansolo.steelseries.tools.LcdColor;
import eu.hansolo.steelseries.tools.LedColor;

import java.awt.CardLayout;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {



	/**
	 * Launch the application.
	 */
	static GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	static MainFrame mainFrame;
	static Calibratrion cal;
	static String statusMessage;
	static String status;
	static JTextField txtStatusMessage;
	static JLabel airVoltage;
	static JLabel gasVoltage;
	static JLabel lblGasTargetFlow;
	static JLabel lblAirTargetFlow;
	static JButton standbyBtn;
	static JButton manualBtn;
	static JButton autoBtn;
	static JButton gasBtn;
	SystemSetting sys = new SystemSetting();
	GasPanel gasPanel;
	AutoPanel autoPanel;
	AddGasPanel addGasPanel;
	TypePanel typePanel;
	SettingMenuPanel settingMenu;
	DeleteGasPanel deleteGas;
	MFCCalPanel mfcCal;
	CardLayout cl;

	Radial airPressureRad;
	Radial gasPressureRad;
	private JPanel panel_4;
	private JLabel lblNewLabel;
	private JTextField txtActFlow;
	private JTextField txtStatus;
	private JTextField txtGasPressure;
	private JTextField txtAirPressure;
	private JTextField txtActGasFlow;
	private JTextField txtTargetactFlow;
	

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cal = new Calibratrion(5.0, 10000.0, 5.0, 100.0);
					mainFrame = new MainFrame();
					device.setFullScreenWindow(mainFrame);
					mainFrame.setVisible(true);
					//cal.getAnalogSignal.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	/**
	 * Create the frame.
	 */

	
	
	public MainFrame() {
		initialComponent();

	}

	private void initialComponent() {
		setFullScreen();
		setBounds(0, 0, 1050, 1500);
		getContentPane().setSize(1000,1000);
		getContentPane().setLayout(new CardLayout());
		JPanel panel_1 = new JPanel();
		setCardLayoutComponent(panel_1);
		panel_1.setBackground(sys.sysLightGrayBackground);
		panel_1.setLayout(null);
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(207, 135, 575, 237);
		


		int btnStartXposition = 0;//35;
		int gap = 160;//153;
		gasBtn = sys.sysGasBtn;
		gasBtn.setLocation(btnStartXposition, 375);
		panel_1.add(gasBtn);
	
		autoBtn = sys.sysAutoBtn;
		autoBtn.setLocation(btnStartXposition+gap, 375);
		panel_1.add(autoBtn);
		
		manualBtn = sys.sysManualBtn;
		manualBtn.setLocation(btnStartXposition+gap*2, 375);
		panel_1.add(manualBtn);
		
		standbyBtn = sys.sysStandbyBtn;
		standbyBtn.setEnabled(false);
		standbyBtn.setLocation(btnStartXposition+gap*3, 375);
		panel_1.add(standbyBtn);
		
		JButton settingBtn = sys.sysSetBtn;
		settingBtn.setLocation(btnStartXposition+gap*4, 375);
		panel_1.add(settingBtn);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("assets/brand.png"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		JPanel pressurePanel = new JPanel();
		pressurePanel.setBackground(sys.sysWhite);
		pressurePanel.setBounds(28, 10, 169, 362);
		pressurePanel.setVisible(true);
		pressurePanel.setLayout(null);
		panel_1.add(pressurePanel);
		
		initGauge(panel_1, panel_3, pressurePanel);		//設定圖表樣式
		
		panel_3.setLayout(null);
		Linear airFlowLinear = new Linear();
		panel_3.add(airFlowLinear);
		airFlowLinear.setUnitString("Liter");
		airFlowLinear.setTitle("Air");
		airFlowLinear.setLcdInfoFont(new Font("Arial Black", Font.BOLD, 20));
		airFlowLinear.setLcdValueFont(new Font("Arial Black", Font.BOLD, 20));
		airFlowLinear.setTitleAndUnitFont(new Font("Arial Black", Font.BOLD, 20));
		airFlowLinear.setFont(new Font("Arial Black", Font.BOLD, 20));
		airFlowLinear.setDigitalFont(false);
		airFlowLinear.setLcdUnitFont(new Font("Arial Black", Font.BOLD, 20));
		airFlowLinear.setLcdDecimals(2);
		airFlowLinear.setThreshold(5.5);
		airFlowLinear.setThresholdColor(ColorDef.GREEN);
		airFlowLinear.setLedVisible(false);
		airFlowLinear.setBackgroundColor(BackgroundColor.LIGHT_GRAY);
		airFlowLinear.setValueColor(ColorDef.BLUE);
		airFlowLinear.setBounds(10,33,555,85);
		airFlowLinear.setFrameVisible(false);
		airFlowLinear.setValue(3.5);
		airFlowLinear.setMaxValue(10);
		airFlowLinear.setMinValue(0.0);
		airFlowLinear.setTrackStart(0.0);
		airFlowLinear.setTrackSection(8.0);
		airFlowLinear.setTrackStop(10);
		airFlowLinear.setTrackStartColor(Color.GREEN);
		airFlowLinear.setTrackSectionColor(Color.YELLOW);
		airFlowLinear.setTrackStopColor(Color.RED);
		airFlowLinear.setTrackVisible(true);
		
		Linear gasFlowLinear = new Linear();
		gasFlowLinear.setValueColor(ColorDef.JUG_GREEN);
		gasFlowLinear.setValue(3.5);
		gasFlowLinear.setUnitString("C.C.");
		gasFlowLinear.setTitle("Gas Act Flow");
		gasFlowLinear.setValueColor(ColorDef.MAGENTA);
		gasFlowLinear.setThreshold(5.5);
		gasFlowLinear.setMinValue(0.0);
		gasFlowLinear.setMaxValue(100);
		gasFlowLinear.setLedVisible(false);
		gasFlowLinear.setLcdDecimals(2);
		gasFlowLinear.setFrameVisible(false);
		gasFlowLinear.setBackgroundColor(BackgroundColor.ANTHRACITE);
		gasFlowLinear.setBounds(10, 150, 555, 84);
		gasFlowLinear.setTrackStart(0.0);
		gasFlowLinear.setTrackSection(80.0);
		gasFlowLinear.setTrackStop(100);
		gasFlowLinear.setTrackStartColor(Color.GREEN);
		gasFlowLinear.setTrackSectionColor(Color.YELLOW);
		gasFlowLinear.setTrackStopColor(Color.RED);
		gasFlowLinear.setTrackVisible(true);
		panel_3.add(gasFlowLinear);
		
		txtActFlow = new JTextField();
		txtActFlow.setText(" ACT Air Flow");
		txtActFlow.setHorizontalAlignment(SwingConstants.LEFT);
		txtActFlow.setForeground(Color.WHITE);
		txtActFlow.setFont(new Font("Arial", Font.BOLD, 20));
		txtActFlow.setEditable(false);
		txtActFlow.setColumns(10);
		txtActFlow.setBackground(new Color(11,54,100));
		txtActFlow.setBounds(0, 0, 575, 30);
		panel_3.add(txtActFlow);
		
		txtActGasFlow = new JTextField();
		txtActGasFlow.setText(" ACT Gas Flow");
		txtActGasFlow.setHorizontalAlignment(SwingConstants.LEFT);
		txtActGasFlow.setForeground(Color.WHITE);
		txtActGasFlow.setFont(new Font("Arial", Font.BOLD, 20));
		txtActGasFlow.setEditable(false);
		txtActGasFlow.setColumns(10);
		txtActGasFlow.setBackground(new Color(11, 54, 100));
		txtActGasFlow.setBounds(0, 119, 575, 30);
		panel_3.add(txtActGasFlow);
				
		JPanel panel = new JPanel();
		panel.setBounds(207, 10, 365, 120);
		panel.setBackground(new Color(20,55,255));
		panel_1.add(panel);
		
		panel.setLayout(null);
		

		JLabel lblCal = new JLabel("Air");
		lblCal.setForeground(Color.WHITE);
		lblCal.setFont(sys.mainPanelFont);
		lblCal.setBounds(175, 25, 58, 36);
		panel.add(lblCal);
		
		JLabel lblGas = new JLabel("GAS");
		lblGas.setForeground(Color.WHITE);
		lblGas.setFont(sys.mainPanelFont);
		lblGas.setBounds(268, 25, 72, 36);
		panel.add(lblGas);
		
		JLabel lblVoltage = new JLabel("Actual Flow");
		lblVoltage.setForeground(Color.WHITE);
		lblVoltage.setFont(sys.mainPanelFont);
		lblVoltage.setBounds(10, 85, 155, 36);
		panel.add(lblVoltage);
		
		airVoltage = new JLabel("0.0");
		airVoltage.setForeground(Color.WHITE);
		airVoltage.setFont(new Font("Arial Black", Font.BOLD, 22));
		airVoltage.setBounds(175, 85, 122, 36);
		panel.add(airVoltage);
		
		gasVoltage = new JLabel("0.0");
		gasVoltage.setForeground(Color.WHITE);
		gasVoltage.setFont(new Font("Arial Black", Font.BOLD, 22));
		gasVoltage.setBounds(279, 85, 132, 36);
		panel.add(gasVoltage);
		
		JLabel lblTargetFlow = new JLabel("Target Flow");
		lblTargetFlow.setForeground(Color.WHITE);
		lblTargetFlow.setFont(sys.mainPanelFont);
		lblTargetFlow.setBounds(10, 50, 153, 36);
		panel.add(lblTargetFlow);
		
		lblAirTargetFlow = new JLabel("0.0");
		lblAirTargetFlow.setForeground(Color.WHITE);
		lblAirTargetFlow.setFont(new Font("Arial Black", Font.BOLD, 22));
		lblAirTargetFlow.setBounds(175, 50, 122, 36);
		panel.add(lblAirTargetFlow);
		
		lblGasTargetFlow = new JLabel("0.0");
		lblGasTargetFlow.setForeground(Color.WHITE);
		lblGasTargetFlow.setFont(new Font("Arial Black", Font.BOLD, 22));
		lblGasTargetFlow.setBounds(279, 50, 132, 36);
		panel.add(lblGasTargetFlow);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setBounds(385, 10, 147, 36);
		panel.add(picLabel);
		
		txtTargetactFlow = new JTextField();
		txtTargetactFlow.setText("Target/Act Flow");
		txtTargetactFlow.setHorizontalAlignment(SwingConstants.LEFT);
		txtTargetactFlow.setForeground(Color.WHITE);
		txtTargetactFlow.setFont(new Font("Arial", Font.BOLD, 20));
		txtTargetactFlow.setEditable(false);
		txtTargetactFlow.setColumns(10);
		txtTargetactFlow.setBackground(new Color(15, 115, 255));
		txtTargetactFlow.setBounds(0, 0, 375, 30);
		panel.add(txtTargetactFlow);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(575, 10, 207, 120);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtStatusMessage = new JTextField();
		txtStatusMessage.setBounds(0, 29, 207, 91);
		panel_2.add(txtStatusMessage);
		txtStatusMessage.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatusMessage.setEditable(false);
		txtStatusMessage.setText("Stand By");
		txtStatusMessage.setFont(new Font("Arial", Font.BOLD, 24));
		txtStatusMessage.setForeground(Color.WHITE);
		txtStatusMessage.setBackground(new Color(12,166,90));
		txtStatusMessage.setColumns(10);
		
		txtStatus = new JTextField();
		txtStatus.setText(" Status");
		txtStatus.setHorizontalAlignment(SwingConstants.LEFT);
		txtStatus.setForeground(Color.WHITE);
		txtStatus.setFont(new Font("Arial", Font.BOLD, 20));
		txtStatus.setEditable(false);
		txtStatus.setColumns(10);
		txtStatus.setBackground(new Color(10, 150, 81));
		txtStatus.setBounds(0, 0, 207, 30);
		panel_2.add(txtStatus);
		
		standbyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cal.standBy();
				lblAirTargetFlow.setText("0.0");
				lblGasTargetFlow.setText("0.0");
				airVoltage.setText(Double.toString(cal.airVoltage));
				gasVoltage.setText(Double.toString(cal.gasVoltage));
				standbyBtn.setEnabled(false);
				manualBtn.setEnabled(true);
				autoBtn.setEnabled(true);
				gasBtn.setEnabled(true);
				statusMessage = "Stand By";
				txtStatusMessage.setText(statusMessage);
				txtStatusMessage.setForeground(Color.WHITE);
			}
		});
		
		autoBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(getContentPane(), "auto");
				autoPanel.getGasName();
				
			}
		});
		manualBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

		        cl.show(getContentPane(), "manual");

			}
		});
		gasBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				  cl.show(getContentPane(), "gas");
				
			}
		});
		settingBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(getContentPane(), "type");
				typePanel.initParameter();
			}
		});
		
		Thread airFlowDetector = new Thread(){
			@Override
			public void run() {
				while(true) {
					double oldvalue = airFlowLinear.getValue();
					double newvalue = cal.airActFlow;
					airVoltage.setText(String.valueOf(newvalue));
//					System.out.println(cal.airActFlow);
					double step = 0.01;
					if(newvalue>oldvalue){
						while(newvalue>oldvalue){
							airFlowLinear.setValue(oldvalue);
							oldvalue += step;
							try {
								Thread.sleep(2);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					else{
						while(newvalue<oldvalue){
							airFlowLinear.setValue(oldvalue);
							oldvalue -= step;
							try {
								Thread.sleep(2);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread gasFlowDetector = new Thread(){
			@Override
			public void run() {
				int errorCount = 0;
				while(true) {
					double oldvalue = gasFlowLinear.getValue();
					double newvalue = cal.gasActFlow;
					if(newvalue>=0){
						gasVoltage.setText(String.valueOf(newvalue));
					}
					double step = 0.01;
					if(newvalue>oldvalue){
						while(newvalue>oldvalue){
							gasFlowLinear.setValue(oldvalue);
							oldvalue += step;
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else{
						while(newvalue<oldvalue){
							gasFlowLinear.setValue(oldvalue);
							oldvalue -= step;
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread airPressureDetector = new Thread() {

			@Override
			public void run() {
				int errorCount = 0;
				while(true) {
					double oldvalue = airPressureRad.getValue();
					double newvalue = cal.airPressure;
					//double step = (newvalue - oldvalue)/500;
					double step = 0.03;
					if(newvalue>oldvalue){
						while(newvalue>oldvalue){
							airPressureRad.setValue(oldvalue);
							
							oldvalue += step;
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else{
						while(newvalue<oldvalue){
							airPressureRad.setValue(oldvalue);
							oldvalue -= step;
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					if(airPressureRad.getValue()<20 || airPressureRad.getValue()>35){
						errorCount++;
					}
					else{
						errorCount=0;
					}
					if(errorCount>30){
						if(airPressureRad.getLcdColor()!=LcdColor.RED_LCD){
							airPressureRad.setLcdColor(LcdColor.RED_LCD);
						}
					}
					else{
						if(airPressureRad.getLcdColor()!=LcdColor.GREEN_LCD){
							airPressureRad.setLcdColor(LcdColor.GREEN_LCD);
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		
		Thread gasPressureDetector = new Thread() {

			@Override
			public void run() {
				int errorCount = 0;
				while(true) {
					double oldvalue = gasPressureRad.getValue();
					double newvalue = cal.gasPressure;
					//double step = (newvalue - oldvalue)/500;
					double step = 0.03;
					if(newvalue>oldvalue){
						while(newvalue>oldvalue){
							gasPressureRad.setValue(oldvalue);
							
							oldvalue += step;
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else{
						while(newvalue<oldvalue){
							gasPressureRad.setValue(oldvalue);
							oldvalue -= step;
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						}
					}
					if(gasPressureRad.getValue()<20 || gasPressureRad.getValue()>35){
						errorCount++;
					}
					else{
						errorCount=0;
					}
					if(errorCount>3){
						if(gasPressureRad.getLcdColor()!=LcdColor.RED_LCD){
							gasPressureRad.setLcdColor(LcdColor.RED_LCD);
						}
					}
					else{
						if(gasPressureRad.getLcdColor()!=LcdColor.GREEN_LCD){
							gasPressureRad.setLcdColor(LcdColor.GREEN_LCD);
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		airPressureDetector.start();
		gasPressureDetector.start();
		airFlowDetector.start();
		gasFlowDetector.start();
	}
	public void initGauge(JPanel panel_1, JPanel panel_3, JPanel pressurePanel) {
		airPressureRad = new Radial();
		airPressureRad.setFrameVisible(false);
		airPressureRad.setTrackVisible(true);
		airPressureRad.setLedVisible(false);
		airPressureRad.setBounds(20, 40, 133, 133);
		//		airPressureRad.setLcdColor(LcdColor.RED_LCD);
		airPressureRad.setLcdColor(LcdColor.GREEN_LCD);
		airPressureRad.setTrackStartColor(Color.GREEN);
		airPressureRad.setTrackSectionColor(Color.YELLOW);
		airPressureRad.setTrackStopColor(Color.RED);
		airPressureRad.setValue(0.0);
		airPressureRad.setMinValue(0.0);
		airPressureRad.setMaxValue(50.0);
				//rad.setBackgroundColor(BackgroundColor.BLUE);
		airPressureRad.setLcdDecimals(2);
		airPressureRad.setUnitString("PSI");
		airPressureRad.setTrackStart(20.0);
		airPressureRad.setTrackSection(30);
		airPressureRad.setTrackStop(50.0);
		airPressureRad.setTitle("air Pressure");
		pressurePanel.add(airPressureRad);
		
		gasPressureRad = new Radial();
		gasPressureRad.setFrameVisible(false);
		gasPressureRad.setValue(0.0);
		gasPressureRad.setUnitString("PSI");
		gasPressureRad.setTrackVisible(true);
		gasPressureRad.setTrackStopColor(Color.RED);
		gasPressureRad.setTrackStop(50.0);
		gasPressureRad.setTrackStartColor(Color.GREEN);
		gasPressureRad.setTrackStart(20.0);
		gasPressureRad.setTrackSectionColor(Color.YELLOW);
		gasPressureRad.setTrackSection(30.0);
		gasPressureRad.setTitle("gas Pressure");
		gasPressureRad.setMinValue(0.0);
		gasPressureRad.setMaxValue(50.0);
		gasPressureRad.setLedVisible(false);
		gasPressureRad.setLcdDecimals(2);
		gasPressureRad.setLcdColor(LcdColor.GREEN_LCD);
		gasPressureRad.setBounds(20, 219, 136, 133);
		pressurePanel.add(gasPressureRad);
		
		txtGasPressure = new JTextField();
		txtGasPressure.setText("Gas Pressure");
		txtGasPressure.setHorizontalAlignment(SwingConstants.LEFT);
		txtGasPressure.setForeground(Color.WHITE);
		txtGasPressure.setFont(new Font("Arial", Font.BOLD, 20));
		txtGasPressure.setEditable(false);
		txtGasPressure.setColumns(10);
		txtGasPressure.setBackground(new Color(203, 80, 48));
		txtGasPressure.setBounds(0, 0, 169, 30);
		pressurePanel.add(txtGasPressure);
		
		txtAirPressure = new JTextField();
		txtAirPressure.setText("Air Pressure");
		txtAirPressure.setHorizontalAlignment(SwingConstants.LEFT);
		txtAirPressure.setForeground(Color.WHITE);
		txtAirPressure.setFont(new Font("Arial", Font.BOLD, 20));
		txtAirPressure.setEditable(false);
		txtAirPressure.setColumns(10);
		txtAirPressure.setBackground(new Color(203, 80, 48));
		txtAirPressure.setBounds(0, 183, 169, 30);
		pressurePanel.add(txtAirPressure);
		
		
		panel_1.add(panel_3);
	}
	
	
	public void setCardLayoutComponent(JPanel panel_1) {
		ManualPanel manualPanel = new ManualPanel();
		autoPanel = new AutoPanel();
		gasPanel = new GasPanel();
		addGasPanel = new AddGasPanel();
		typePanel = new TypePanel();
		settingMenu = new SettingMenuPanel();
		deleteGas = new DeleteGasPanel();
		mfcCal = new MFCCalPanel();
		cl = (CardLayout)(getContentPane().getLayout());
		getContentPane().add(panel_1,"main");
		getContentPane().add(manualPanel, "manual");
		getContentPane().add(autoPanel, "auto");
		getContentPane().add(gasPanel, "gas");
		getContentPane().add(addGasPanel, "addGas");
		getContentPane().add(typePanel, "type");
		getContentPane().add(settingMenu,"settingMenu");
		getContentPane().add(deleteGas,"deleteGas");
		getContentPane().add(mfcCal, "mfcCal");
	}
	private void setFullScreen() {
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setAlwaysOnTop(true); 
		this.setResizable(false); 
		this.setUndecorated(true);
	}
	

	public void CloseMainFrame(){
	    super.dispose();
		super.setVisible(false);
	}
	
	public class getTargetFlowRunnable implements Runnable{
		boolean doing = true;
		@Override
		public void run() {
			while(doing){
				//System.out.println(cal.highMFC.targetFlowCC);
			}
			
		}
	}
}
