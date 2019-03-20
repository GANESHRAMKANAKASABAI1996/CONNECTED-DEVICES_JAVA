package schooldomain.studentname.connecteddevices.labs.module07;

import java.io.IOException;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

/**
 * This is an application that connects to a CoAP server and 
 * runs several tests
 * 
 * @author GANESHRAM KANAKASABAI
 *
 */
public class CoapClientTestApp {
	public static void main(String[] args) throws IOException
	{		
		runTests();
	}
	
	/**
	 * This method runs tests on the CoAP server with GET, POST etc. actions
	 * 
	 * @throws IOException
	 */
	public static void runTests() throws IOException
	{
		System.out.println("Starting client...");
		CoapClientConnector coapClient = new CoapClientConnector("coap://127.0.0.1/temperature");
				
		SensorData data = new SensorData(30.0,0.0,"time","Temperature");
		data.updateValue(4);
		DataUtil dat = new DataUtil();
		System.out.println(data);
		
		//Ping the server
		coapClient.ping();
		
		//Post to the server to initialize data, then get the data back as JSON
		coapClient.get();
		coapClient.post(dat.SensorDataToJson(data));
		coapClient.get();
		
		//Update resource on server
		data.updateValue(5);
		System.out.println(data);
		coapClient.put(dat.SensorDataToJson(data));
		coapClient.get();
		
		//Delete data
		coapClient.delete();
		coapClient.get();
	}
}
