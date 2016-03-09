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
import java.util.HashMap;

public class Calibratrion {
	MassFlowController highMFC;
	MassFlowController lowMFC;
	HashMap<String,Double> gasTablePPM;
	double airVoltage;
	double gasVoltage;
	static final String CYLFILE = "cyl.txt"; 
	static final String HIGHMFCFILE = "MFC1.txt";
	static final String LOWMFCFILE = "MFC2.txt";
	private CommController commController = new CommController();
	public Calibratrion(double highRangeVoltage,double highRangeFlowCC,double lowRangeVoltage, double lowRangeFlowCC) throws IOException{
		this.highMFC = new MassFlowController(highRangeVoltage, highRangeFlowCC,1);
		this.lowMFC = new MassFlowController(lowRangeVoltage, lowRangeFlowCC,2);
		saveMFCTable();
		getGasTable();
	}
	
	public void manualGen(double highMFCflowCC, double lowMFCflowCC){
		airVoltage = highMFC.getVoltage(highMFCflowCC);
		gasVoltage = lowMFC.getVoltage(lowMFCflowCC);
		generate();
	}
	
	private void getGasTable(){
		File file = new File(CYLFILE);
		String readLine;
		gasTablePPM = new HashMap<String,Double>();
		if(file.exists()){
			try {
				FileReader cylFile = new FileReader(CYLFILE);
				BufferedReader in = new BufferedReader(cylFile);
				readLine= in.readLine();
				while(readLine!=null){
					String[] inSplit = readLine.split(",");
					gasTablePPM.put(inSplit[0], Double.valueOf(inSplit[1]));
					readLine= in.readLine();
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
		}
		else{
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
				bw.write(key.toString()+","+gasTablePPM.get(key));
				bw.newLine();
		}
		bw.close();
		fos.close();
	}
	
	public void addGas(String gasName,Double gasConcPPM){
		gasTablePPM.put(gasName, gasConcPPM);
	}
	
	public void deleteGas(String gasName){
		gasTablePPM.remove(gasName);
	}


	public void standBy(){
		airVoltage = 0;
		gasVoltage = 0;
		generate();
	}
	
	public void autoGen(double targetGasConcPPB,double targetConcPPB,double targetFlowCC){
		double gasflow;
		double airflow;
		gasflow = (targetConcPPB *  targetFlowCC) /targetGasConcPPB;
		airflow = targetFlowCC - gasflow;
		airVoltage = highMFC.getVoltage(airflow);
		gasVoltage = lowMFC.getVoltage(gasflow);
		generate();
	}
	
	private void generate(){
		DecimalFormat df = new DecimalFormat("00.000");
		String airV = df.format(airVoltage);
		String gasV = df.format(gasVoltage);
		String hiControlText = "#01C0+" + airV + (char)13;
		String lowControlText = "#01C1+" + gasV + (char)13;
		try {
			commController.MFCControl(hiControlText, lowControlText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveMFCTable() throws IOException{
		highMFC.saveTable(HIGHMFCFILE);
		lowMFC.saveTable(LOWMFCFILE);
	}
	
	public double getMFC1Flow(){
		return commController.GetAdam4017Data(2, 0);
	}
	
}
