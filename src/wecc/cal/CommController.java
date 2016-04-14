package wecc.cal;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;


public class CommController implements SerialPortEventListener{
	 SerialPort serialPort = null;

	    private static final String PORT_NAMES[] = { 
	 //       "/dev/tty.usbmodem", // Mac OS X
//	        "/dev/usbdev", // Linux
	        "/dev/ttyUSB0", // Linux
//	        "/dev/serial", // Linux
//	        "COM4", // Windows
	    };
	    
	    private String appName;
	    private BufferedReader input;
	    private OutputStream output;
	    String getString;
	    InputStream inputStream;
	    private static final int TIME_OUT = 1000; // Port open timeout
	    private static final int DATA_RATE = 9600; // Arduino serial port

	    public boolean initialize() {
	        try {
	        	System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyUSB0");
	            CommPortIdentifier portId = null;
	            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	            while (portId == null && portEnum.hasMoreElements()) {
	                // Iterate through your host computer's serial port IDs
	                //
	                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	                for (String portName : PORT_NAMES) {
	                    if ( currPortId.getName().equals(portName) 
	                      || currPortId.getName().startsWith(portName)) {

	                        // Try to connect

	                        // Open serial port
	                        serialPort = (SerialPort)currPortId.open(appName, TIME_OUT);
	                        portId = currPortId;
	                        //System.out.println( "Connected on port" + currPortId.getName() );
	                        break;
	                    }
	                }
	            }
	        
	            if (portId == null || serialPort == null) {
	                return false;
	            }
	        
	            // set port parameters
	            serialPort.setSerialPortParams(DATA_RATE,
	                            SerialPort.DATABITS_8,
	                            SerialPort.STOPBITS_1,
	                            SerialPort.PARITY_NONE);

	            // add event listeners
	            serialPort.addEventListener(this);
	            serialPort.notifyOnDataAvailable(true);

	            // Give the Arduino some time
	            //try { Thread.sleep(50); } catch (InterruptedException ie) {}
	            
	            return true;
	        }
	        catch ( Exception e ) { 
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public void sendData(String data) {
	        try {
//	            System.out.println("Sending data: '" + data +"'");
	            
	            output = serialPort.getOutputStream();
	            output.write( data.getBytes() );
	        } 
	        catch (Exception e) {
	            System.err.println(e.toString());
	            System.exit(0);
	        }
	    }

	    //
	    // This should be called when you stop using the port
	    //
	    public synchronized void close() {
	        if ( serialPort != null ) {
	            serialPort.removeEventListener();
	            serialPort.close();
	        }
	    }

	    //
	    // Handle serial port event
	    //
	    @Override
	    public synchronized void serialEvent(SerialPortEvent oEvent) {
	    	switch (oEvent.getEventType()) {
	        case SerialPortEvent.BI:
	        case SerialPortEvent.OE:
	        case SerialPortEvent.FE:
	        case SerialPortEvent.PE:
	        case SerialPortEvent.CD:
	        case SerialPortEvent.CTS:
	        case SerialPortEvent.DSR:
	        case SerialPortEvent.RI:
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	        	//System.out.println(" no connect...");
	           break;
	        case SerialPortEvent.DATA_AVAILABLE:
	           // we get here if data has been received
	           byte[] readBuffer = new byte[20];
	           try {
	        	   if ( input == null ) {
	                    input = new BufferedReader(
	                        new InputStreamReader(
	                                serialPort.getInputStream()));
	                }
	                String inputLine = input.readLine();
	                getString = inputLine;
//	                System.out.println("getData:" + getString);
	                break;
	           } catch (IOException e) {}
	           
	           break;
	        }
	    	
	    }

	    public CommController() {
	        appName = getClass().getName();
	        if(initialize()){
	        	
	        }
	        	
	    }
	    
	    public void MFCControl(String hiControlText,String lowControlText) throws Exception {
	    	if(serialPort != null)
	    	{
	    		sendData(hiControlText);
	            try { Thread.sleep(50); } catch (InterruptedException ie) {}
	            sendData(lowControlText);
	            try { Thread.sleep(50); } catch (InterruptedException ie) {}
	          
	            //System.out.println(hiControlText);
	            //System.out.println(lowControlText);
	    	}
	        // Wait 5 seconds then shutdown
	        try { Thread.sleep(100); } catch (InterruptedException ie) {}
	    }
}
