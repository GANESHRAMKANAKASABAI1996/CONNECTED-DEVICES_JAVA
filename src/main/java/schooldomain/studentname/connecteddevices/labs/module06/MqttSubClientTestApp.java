package schooldomain.studentname.connecteddevices.labs.module06;

/*
 * This Class is used to Subscribe to topic from MQTT Broker 
 * 
 * @author GANESHRAM KANAKASABAI
 */

import java.util.logging.Level;
import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttSubClientTestApp {
	/*
	 * static method is used so that it could be invoked without 
	 * creating the instance of the class
	 *  
	 */
	private static final Logger logger = Logger.getLogger(MqttSubClientTestApp.class.getName());
	private static MqttSubClientTestApp app;
	private MqttClientConnector mqttClient;
	/**
	 * Default constructor 
	 * 
	 */
	
	public MqttSubClientTestApp()
	 {
	 super();
	 }
	/*
	 * Function used to initialize subscribe action
	 * @param topicName: Topic to be subscribed
	 */
	
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		mqttClient.connect();
		mqttClient.subscribeToTopic(topicName);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mqttClient.disconnect();
	}
	/**
	 * Inside the main a MqttSubClientTestApp instance is created and
	 * exceptional handling is used to separate the error handling code 
	 * from the regular code
	 */

	public static void main(String[] args) {
		
		app = new MqttSubClientTestApp();
		String topic = "Temperature Sensor";							// Topic name is set

		try
		{
			app.start(topic);
			String message = MqttClientConnector.getMessag();			//Retrieving Json Data
			logger.info("Received Json Data\n");
			System.out.println("Received Json Message: "+message+"\n");
			DataUtil data = new DataUtil();								
			SensorData sensor = data.JsonToSensorData(message,null);    //Converting Json to SensorData
			logger.info("Printing SensorData:\n");
			System.out.println("After Json to SensorData Conversion:\n"+sensor);
			String json = data.SensorDataToJson(sensor);				//Converting SensorData to Json
			logger.info("Printing Json Data:\n");
			System.out.println("After SensorData to Json Conversion:\n"+json);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
