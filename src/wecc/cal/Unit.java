package wecc.cal;

import java.util.HashMap;

public class Unit {
	HashMap<String,Double> unitMap = new HashMap<String,Double>();
	
	public  Unit(){
		unitMap.put("PPB", 1.0);
		unitMap.put("PPM", 1000.0);
		unitMap.put("PCT", 10000000.0);	
	}
}
