package schooldomain.studentname.connecteddevices.labs.module06;

import java.util.logging.Logger;

/*
 * This Class is used to Publish Message to MQTT Broker 
 * 
 * @author GANESHRAM KANAKASABAI
 */

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttPubClientTestApp {
	/*
	 * static method is used so that it could be invoked without 
	 * creating the instance of the class
	 */
	
	private static final Logger logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp SubApp;
	private MqttClientConnector mqttClient;
	public SensorData sensorData;
	public DataUtil dataUtil;

	public MqttPubClientTestApp() {
		super();
	}
	
	/*This function is used to create Json data from SensorData
	 * @param sensorData: variable of type SensorData
	 * @return: Json object
	 */
	public String createJSON(SensorData sensorData) {
		dataUtil = new DataUtil();
		String SJobj = dataUtil.SensorDataToJson(setSensorData(sensorData));
		return SJobj;
	}
	/*
	 * This function is used to intialize or update the SensorData
	 * @param sensorData: variable of SensorData Class
	 * @return: Update SensorData variable
	 */
	
	public SensorData setSensorData(SensorData sensorData) {
		sensorData.setName("Temperature Sensor");
		sensorData.updateTimeStamp();
		sensorData.setCurVal((double)19.28);
		sensorData.setAvgVal((double)24.24);
		sensorData.setSampleCount(5);
		sensorData.setMinVal((double)0);
		sensorData.setMaxVal((double)36);
		
		return sensorData;
		
	}
	/*
	 * Function used to initialize publish action
	 * @param topicName: Topic of the message
	 */
	
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		sensorData = new SensorData(30.0,0.0,"name","Temperature");
		logger.info("SensorData before converting into Json\n");
		System.out.println(sensorData+"\n");
		mqttClient.connect();
		mqttClient.publishMessage(topicName, 2 , createJSON(sensorData).getBytes());
	}
	/**
	 * Inside the main a MqttPubClientTestApp instance is created and
	 * exceptional handling is used to separate the error handling code 
	 * from the regular code
	 */
	
	public static void main(String[] args) {

		SubApp = new MqttPubClientTestApp();
		String topic = "Temperature Sensor";
		try {
			SubApp.start(topic);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}