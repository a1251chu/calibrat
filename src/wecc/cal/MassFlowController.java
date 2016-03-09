package wecc.cal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class MassFlowController {
// all flow transfer to CC to acount
	double rangeVoltage;
	double rangeFlowCC;
	double targetFlowCC;
	double targetVoltage;
	HashMap<Double,Double> flowTable;
	int controlChannel;
	//double[][] flowTable = new double[2][20];
	public MassFlowController(double rangeVoltage, double rangeFlowCC,int controlChannel, HashMap<Double, Double> flowTable) {
		this.rangeVoltage = rangeVoltage;
		this.rangeFlowCC = rangeFlowCC;
		this.controlChannel = controlChannel;
		//this.flowTable = flowTable;
	}
	
	public MassFlowController(double rangeVoltage,double rangeFlowCC,int controlChannel){
		this.rangeVoltage = rangeVoltage;
		this.rangeFlowCC = rangeFlowCC;
		this.controlChannel = controlChannel;
		flowTable = new HashMap<Double,Double>();
		
		getNewFlowTable();
	}
	
	private void getNewFlowTable(){

	 	int i;
	    double v;
	    double f;
	    double vstep;
	    double fstep;
	    
	    vstep = rangeVoltage / 20.0;
	    fstep = rangeFlowCC / 20.0;
	    v = vstep;
	    f = fstep;
	    flowTable.put(0.0, 0.0);
	    for(i = 0;i<20;i++){
	  //       flowTable[0][i] = v;
	  //       flowTable[1][i] = f;
	    	flowTable.put(v, f);
	         v = v + vstep;
	         f = f + fstep;
	    }

	}
	
	public double getVoltage(double targetFlow){
		this.targetFlowCC = targetFlow;
		double vstep = rangeVoltage/20.0;
		
		double upperFlow = 0;
		double upperVoltage = 0;
		double downFlow = 0;
		double downVoltage = 0;
		double slope;
		double offset;
		if(targetFlow==0)
			return 0;
		for(double i=0.0 ;i<rangeVoltage;i+=vstep){
			if (flowTable.get(i)>=targetFlow){
				upperFlow = flowTable.get(i);
				upperVoltage = i;
				downVoltage = i - vstep;
				downFlow = flowTable.get(downVoltage);
				break;
			}
		}
		slope = (upperFlow-downFlow)/(upperVoltage - downVoltage);
		offset = upperFlow - (slope*upperVoltage);
		
		return  ((targetFlow - offset)/slope);
	}
	
	public void saveTable(String filename) throws IOException{
		File file = new File(filename);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (Object key : flowTable.keySet()) {
				bw.write(key.toString()+","+ (flowTable.get(key).toString()));
				bw.newLine();
		}
		bw.close();
		fos.close();
	}

	
}
