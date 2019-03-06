package schooldomain.studentname.connecteddevices.labs.module06;
/*
 * This Class is used to connect to MQTT Broker to publish or subscribe messages
 * 
 * @author GANESHRAM KANAKASABAI
 */

import java.util.Arrays;//importing java.util.Arrays to perform array operations
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import schooldomain.studentname.connecteddevices.common.SensorData;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
public class MqttClientConnector implements MqttCallback{
	/*
	 * static method is used so that it could be invoked without 
	 * creating the instance of the class
	 */
	
	private static final Logger logger = Logger.getLogger(MqttClientConnector.class.getName());
	private String protocol			   = ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String host                = ConfigConst.DEFAULT_MQTT_SERVER;
	private int port                   = ConfigConst.DEFAULT_MQTT_PORT;
	
	private String clientID;
	private String brokerAddr;
	private MqttClient mqttClient;
	private SensorData sensorData;
	private static String messag;
	/*
	 * Constructor to create an object
	 */
	public MqttClientConnector()
	{
		if(host!=null && host.trim().length()>0)
		{
			this.sensorData = new SensorData(30.0, 0.0, "time", "name");
			//this.host = host;
			this.clientID = mqttClient.generateClientId();
			logger.info("Using client id for broker connection: " + clientID);
			this.brokerAddr = protocol + "://" + host + ":" + port;
			logger.info("Using URL for broker connection: " + brokerAddr);
		}
	}
	/*
	 * This method is used to connect to the MQTT broker
	 */
	
	public void connect()
	{
		if (mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
		try
		{
			mqttClient = new MqttClient(brokerAddr, clientID, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			logger.info("Connecting to broker: " + brokerAddr);
			mqttClient.setCallback(this);
			mqttClient.connect(connOpts);
			logger.info("connected to broker: " + brokerAddr);
		}catch(MqttException ex)
		{
			logger.log(Level.SEVERE, "Failed to connect to broker" + brokerAddr, ex);
		}
		
		}
	}
	/*
	 * This method is used to disconnect from MQTT broker
	 */
	
	public void disconnect()
	{
		try {
			mqttClient.disconnect();
			logger.info("Disconnect from broker: " + brokerAddr);
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to disconnect from broker: " + brokerAddr , ex);
		}
	}
	/*Function used to publish message to MQTT broker
	 * @param topic: Topic of the message
	 * @param qosLevel: Quality of Service
	 * @param payload: Message to be sent
	 * @return: Message successfully sent(True) or not(False)
	 */
	
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean msgSent = false;
		try
		{
			logger.info("Publishing message to topic: " + topic + "payload : " + Arrays.toString(payload));
			MqttMessage msg = new MqttMessage(payload);
			msg.setQos(qosLevel);
			mqttClient.publish(topic, msg);
			logger.info("Message Published " + msg.getId());
			msgSent = true;
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to publish Mqtt message " + ex.getMessage());
		}
		return msgSent;
	}
	/*
	 * Function is used to subscribe to a topic 
	 * @param topic: Topic to be subscribed
	 * @return:Subscription is success(True) or not(False)
	 */
	
	public boolean subscribeToTopic(String topic)
	{
		boolean success = false;
		try {
			mqttClient.subscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public void connectionLost(Throwable cause) {
		
		logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", cause);
	}
	/*
	 * Function used to Log the messsage received from MQTT broker
	 * (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		MqttClientConnector.setMessag(message);
		logger.info("Message arrived: " + topic + "," + message.getId()+"\n");
		System.out.print("Message arrived: " + topic + "," + message.getId() + message+"\n");
	
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		
		logger.info("Deleviry Complete: " + token.getMessageId() + "-" + token.getResponse());
		
	}
	/*
	 * static method is used so that it could be invoked without 
	 * creating the instance of the class
	 */
	
	public static String getMessag() {
		return messag;
	}
	/*
	 * This static method to retrieve the message from MQTT Broker 
	 */
	
	public static void setMessag(MqttMessage message) {
		MqttClientConnector.messag = message.toString();
	}
	
	
}
