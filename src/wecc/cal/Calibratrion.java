package wecc.cal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class Calibratrion {
	MassFlowController highMFC;
	MassFlowController lowMFC;
	Map<String, Double> gasTablePPM;
	double[] signal = new double[8];
	double airVoltage;
	double gasVoltage;
	double airActFlow = 0;
	double gasActFlow = 0;
	double airPressure;
	double gasPressure;
	int airPressureChannel;
	int gasPressureChannel;
	int error = 0;
	String status; // 目前校正器狀態
	static final String CYLFILE = "cyl.txt";
	static final String HIGHMFCFILE = "MFC1.txt";
	static final String LOWMFCFILE = "MFC2.txt";
	private CommController commController = new CommController();
	GPIOController gpioControl = new GPIOController();
	public Calibratrion(double highRangeVoltage, double highRangeFlowCC, double lowRangeVoltage, double lowRangeFlowCC)
			throws IOException {
		this.highMFC = new MassFlowController(highRangeVoltage, highRangeFlowCC, 1, 5);
		this.lowMFC = new MassFlowController(lowRangeVoltage, lowRangeFlowCC, 2, 6);
		airPressureChannel = 0;
		gasPressureChannel = 1;
		saveMFCTable();
		getGasTable();
		getAnalogSignal.start();
	}

	public void manualGen(double highMFCflowCC, double lowMFCflowCC) {
		if((highMFCflowCC<1000 && highMFCflowCC != 0) || highMFCflowCC>9500 || lowMFCflowCC>95.0 || (lowMFCflowCC < 10 && lowMFCflowCC != 0)){
			status = "Flow Error";
			error = 1; 
			gpioControl.errorLedBlink();
		}else{
			airVoltage = highMFC.getVoltage(highMFCflowCC);
			gasVoltage = lowMFC.getVoltage(lowMFCflowCC);
			status = "Manual Gen";
			error = 0;
			gpioControl.errorLedOff();
			generate();
		}
	}

	private void getGasTable() {
		File file = new File(CYLFILE);
		String readLine;
		gasTablePPM = new TreeMap<String, Double>();
		if (file.exists()) {
			try {
				FileReader cylFile = new FileReader(CYLFILE);
				BufferedReader in = new BufferedReader(cylFile);
				readLine = in.readLine();
				while (readLine != null) {
					String[] inSplit = readLine.split(",");
					gasTablePPM.put(inSplit[0], Double.valueOf(inSplit[1]));
					readLine = in.readLine();
				}
				cylFile.close();
				in.close();
			} catch (IOException e) {
				try {
					createNewCyl();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			try {
				createNewCyl();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		file = null;
	}

	private void createNewCyl() throws IOException {
		gasTablePPM.put("NO", 50.0);
		gasTablePPM.put("SO2", 50.0);
		gasTablePPM.put("CO2", 100000.0);
		saveCylFile();
	}

	public void saveCylFile() throws IOException, FileNotFoundException {
		File file = new File(CYLFILE);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (Object key : gasTablePPM.keySet()) {
			bw.write(key.toString() + "," + gasTablePPM.get(key));
			bw.newLine();
		}
		bw.close();
		fos.close();
	}

	public void addGas(String gasName, Double gasConcPPM) {
		gasTablePPM.put(gasName, gasConcPPM);
	}

	public void deleteGas(String gasName) {
		gasTablePPM.remove(gasName);
	}

	public void standBy() {
		airVoltage = 0;
		gasVoltage = 0;
		generate();
	}

	public void autoGen(double targetGasConcPPB, double targetConcPPB, double targetFlowCC) {
		double gasflow;
		double airflow;
		gasflow = (targetConcPPB * targetFlowCC) / targetGasConcPPB;
		airflow = targetFlowCC - gasflow;
		if((airflow<1000 && airflow != 0) || airflow>9500 || gasflow>95.0 || (gasflow < 10 && gasflow != 0)){
			airVoltage = highMFC.getVoltage(airflow);
			gasVoltage = lowMFC.getVoltage(gasflow);
			if (airVoltage == -1 || gasVoltage == -1) {
				status = "Flow Error!!!";
				highMFC.targetFlowCC = 0.0;
				lowMFC.targetFlowCC = 0.0;
				error = 1;
				gpioControl.errorLedBlink();
			} else {
				error = 0;
				gpioControl.errorLedOff();
				generate();
			}
		}
		else{
			status = "Flow Error!!!";
			highMFC.targetFlowCC = 0.0;
			lowMFC.targetFlowCC = 0.0;
			error = 1;
			gpioControl.errorLedBlink();
		}
		
	}

	private void generate() {
		DecimalFormat df = new DecimalFormat("00.000");
		String airV = df.format(airVoltage);
		String gasV = df.format(gasVoltage);
		String hiControlText = "#01C0+" + airV + (char) 13;
		String lowControlText = "#01C1+" + gasV + (char) 13;
		gpioControl.runLedBlink();
		try {
			commController.MFCControl(hiControlText, lowControlText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveMFCTable() throws IOException {
		highMFC.saveTable(HIGHMFCFILE);
		lowMFC.saveTable(LOWMFCFILE);
	}

	Thread getAnalogSignal = new Thread() { // The Thread to get the sensor
											// signal
		@Override
		public void run() {
			String getData = null;
			while (true) {
				String buffer;
				int unit = 1;
				for (int channel = 0; channel < 7; channel++) {
					String str = "#02" + String.valueOf(channel) + (char) 13;
					commController.sendData(str);
					try {
						sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (commController.getString != null && commController.getString != "") {
						getData = commController.getString;
					}
					System.out.println("channel" + channel + "get:" + getData);
					if (getData.charAt(0) == '>') {
							buffer = getData.substring(2);
							if (buffer.charAt(1) == '-') {
								unit = -1;
							} else {
								unit = 1;
							}
							buffer.substring(2);
							double voltage = Double.parseDouble(buffer);
							voltage = voltage * unit;
							signal[channel] = voltage;
							System.out.println("voltage:"+signal[channel]);
					}
				}
				convertSignalToPar(); // 將電壓訊號轉換為數值
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	private void convertSignalToPar() {
		airActFlow = signal[highMFC.returnSigChannel] * ((highMFC.rangeFlowCC / highMFC.rangeVoltage) / 1000);  //caculate hich MFC flow
		gasActFlow = signal[lowMFC.returnSigChannel] * (lowMFC.rangeFlowCC / lowMFC.rangeVoltage);    //caculate low MFC flow
		airPressure = (signal[airPressureChannel]*50) - 5;    //caculate air pressure
		gasPressure = (signal[gasPressureChannel]*50) - 5;    //caculate gas pressure
	}

}
