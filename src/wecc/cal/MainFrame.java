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
	SystemSetting sys = new SystemSetting();
	GasPanel gasPanel;
	AutoPanel autoPanel;
	AddGasPanel addGasPanel;
	TypePanel typePanel;
	SettingMenuPanel settingMenu;
	DeleteGasPanel deleteGas;
	CardLayout cl;
	JButton autoBtn;
	Radial airPressureRad;
	Radial gasPressureRad;
	private JLabel lblGasPressure;
	private JLabel lblAirPressure;
	private JPanel panel_4;
	private JLabel lblNewLabel;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cal = new Calibratrion(5.0, 10000.0, 5.0, 100.0);
					mainFrame = new MainFrame();
					device.setFullScreenWindow(mainFrame);
					mainFrame.setVisible(true);

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
		panel_3.setBounds(197, 140, 542, 172);
		


		
		JButton gasBtn = sys.sysGasBtn;
		gasBtn.setLocation(25, 361);
		panel_1.add(gasBtn);
	
		autoBtn = sys.sysAutoBtn;
		autoBtn.setLocation(180, 361);
		panel_1.add(autoBtn);
		
		manualBtn = sys.sysManualBtn;
		manualBtn.setLocation(333, 361);
		panel_1.add(manualBtn);
		
		JButton settingBtn = sys.sysSetBtn;
		settingBtn.setLocation(625, 361);
		panel_1.add(settingBtn);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("assets/brand.png"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		txtStatusMessage = new JTextField();
		txtStatusMessage.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatusMessage.setEditable(false);
		txtStatusMessage.setText("Stand By");
		txtStatusMessage.setFont(new Font("Arial", Font.BOLD, 20));
		txtStatusMessage.setForeground(Color.WHITE);
		txtStatusMessage.setBackground(Color.BLUE);
		txtStatusMessage.setBounds(24, 323, 715, 31);
		panel_1.add(txtStatusMessage);
		txtStatusMessage.setColumns(10);
		
		standbyBtn = sys.sysStandbyBtn;
		standbyBtn.setEnabled(false);
		standbyBtn.setBounds(482, 361, 100, 100);
		panel_1.add(standbyBtn);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(sys.sysWhite);
		panel_2.setBounds(24, 10, 163, 303);
		panel_2.setVisible(true);
		panel_2.setLayout(null);
		panel_1.add(panel_2);
		
		airPressureRad = new Radial();
		airPressureRad.setFrameVisible(false);
		airPressureRad.setTrackVisible(true);
		airPressureRad.setLedVisible(false);
		airPressureRad.setBounds(23, 25, 125, 125);
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
		panel_2.add(airPressureRad);
		
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
		gasPressureRad.setBounds(23, 168, 125, 125);
		panel_2.add(gasPressureRad);
		
		lblGasPressure = new JLabel("Gas Pressure");
		lblGasPressure.setBounds(10, 151, 138, 15);
		panel_2.add(lblGasPressure);
		
		lblAirPressure = new JLabel("Air Pressure");
		lblAirPressure.setBounds(10, 10, 119, 15);
		panel_2.add(lblAirPressure);
		
		
		panel_1.add(panel_3);
		
		panel_3.setLayout(null);
		Linear airFlowLinear = new Linear();
		panel_3.add(airFlowLinear);
		airFlowLinear.setUnitString("Liter");
		airFlowLinear.setTitle("Air Act Flow");
		airFlowLinear.setLcdDecimals(2);
		airFlowLinear.setThreshold(5.5);
		airFlowLinear.setThresholdColor(ColorDef.GREEN);
		airFlowLinear.setLedVisible(false);
		airFlowLinear.setBackgroundColor(BackgroundColor.LIGHT_GRAY);
		airFlowLinear.setValueColor(ColorDef.BLUE);
		airFlowLinear.setBounds(10,38,509,57);
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
		gasFlowLinear.setBounds(10, 105, 509, 57);
		gasFlowLinear.setTrackStart(0.0);
		gasFlowLinear.setTrackSection(80.0);
		gasFlowLinear.setTrackStop(100);
		gasFlowLinear.setTrackStartColor(Color.GREEN);
		gasFlowLinear.setTrackSectionColor(Color.YELLOW);
		gasFlowLinear.setTrackStopColor(Color.RED);
		gasFlowLinear.setTrackVisible(true);
		panel_3.add(gasFlowLinear);
		
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(37,37,37));
		panel_4.setBounds(0, 0, 542, 28);
		panel_3.add(panel_4);
		panel_4.setLayout(null);
		
		lblNewLabel = new JLabel("MFC Act  Flow");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 10, 151, 15);
		lblNewLabel.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(197, 10, 542, 120);
		panel_1.add(panel);
		panel.setBackground(sys.sysMainPanel1);
		panel.setLayout(null);
		

		JLabel lblCal = new JLabel("Air");
		lblCal.setForeground(sys.sysMainText);
		lblCal.setFont(sys.mainPanelFont);
		lblCal.setBounds(175, 10, 58, 36);
		panel.add(lblCal);
		
		JLabel lblGas = new JLabel("GAS");
		lblGas.setForeground(sys.sysMainText);
		lblGas.setFont(sys.mainPanelFont);
		lblGas.setBounds(284, 10, 72, 36);
		panel.add(lblGas);
		
		JLabel lblVoltage = new JLabel("Voltage");
		lblVoltage.setForeground(sys.sysMainText);
		lblVoltage.setFont(sys.mainPanelFont);
		lblVoltage.setBounds(10, 85, 122, 36);
		panel.add(lblVoltage);
		
		airVoltage = new JLabel("0.0");
		airVoltage.setForeground(sys.sysMainText);
		airVoltage.setFont(sys.mainPanelFont);
		airVoltage.setBounds(175, 85, 122, 36);
		panel.add(airVoltage);
		
		gasVoltage = new JLabel("0.0");
		gasVoltage.setForeground(sys.sysMainText);
		gasVoltage.setFont(sys.mainPanelFont);
		gasVoltage.setBounds(294, 85, 132, 36);
		panel.add(gasVoltage);
		
		JLabel lblTargetFlow = new JLabel("Target Flow");
		lblTargetFlow.setForeground(sys.sysMainText);
		lblTargetFlow.setFont(sys.mainPanelFont);
		lblTargetFlow.setBounds(10, 39, 182, 36);
		panel.add(lblTargetFlow);
		
		lblAirTargetFlow = new JLabel("0.0");
		lblAirTargetFlow.setForeground(sys.sysMainText);
		lblAirTargetFlow.setFont(sys.mainPanelFont);
		lblAirTargetFlow.setBounds(175, 39, 122, 36);
		panel.add(lblAirTargetFlow);
		
		lblGasTargetFlow = new JLabel("0.0");
		lblGasTargetFlow.setForeground(sys.sysMainText);
		lblGasTargetFlow.setFont(sys.mainPanelFont);
		lblGasTargetFlow.setBounds(294, 39, 132, 36);
		panel.add(lblGasTargetFlow);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setBounds(385, 10, 147, 36);
		panel.add(picLabel);
		
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
				int errorCount = 0;
				while(true) {
					double oldvalue = airFlowLinear.getValue();
					double newvalue = cal.getMFC1Flow();
					//double step = (newvalue - oldvalue)/500;
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
					/*if(airPressureRad.getValue()<20 || airPressureRad.getValue()>35){
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
					}*/
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
					double newvalue = new Random().nextInt(100);
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
					/*if(airPressureRad.getValue()<20 || airPressureRad.getValue()>35){
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
					}*/
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
					double newvalue = new Random().nextInt(40);
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
					double newvalue = new Random().nextInt(40);
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
	
	
	public void setCardLayoutComponent(JPanel panel_1) {
		ManualPanel manualPanel = new ManualPanel();
		autoPanel = new AutoPanel();
		gasPanel = new GasPanel();
		addGasPanel = new AddGasPanel();
		typePanel = new TypePanel();
		settingMenu = new SettingMenuPanel();
		deleteGas = new DeleteGasPanel();
		cl = (CardLayout)(getContentPane().getLayout());
		getContentPane().add(panel_1,"main");
		getContentPane().add(manualPanel, "manual");
		getContentPane().add(autoPanel, "auto");
		getContentPane().add(gasPanel, "gas");
		getContentPane().add(addGasPanel, "addGas");
		getContentPane().add(typePanel, "type");
		getContentPane().add(settingMenu,"settingMenu");
		getContentPane().add(deleteGas,"deleteGas");
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
