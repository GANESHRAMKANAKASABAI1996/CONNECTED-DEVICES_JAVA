package schooldomain.studentname.connecteddevices.labs.module08;

import java.util.Random;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

import schooldomain.studentname.connecteddevices.labs.module08.MqttClientConnector;

/**
 * Created on Feb 26, 2019 
 * TempSensorPublisherApp.java: Java class to publish message using MQTT protocol
 * 
 * @author GANESHRAM KANAKASABAI
 */
public class TempSensorPublisherApp {

	/**
	 * MqttPubClientTestApp - class to publish message using MQTT protocol
	 * 
	 * @variable pubApp: MQTT publisher class self instance
	 * @variable mqttClient: MQTT connector helper class instance
	 */
	private static TempSensorPublisherApp pubApp;
	private String _host = ConfigConst.DEFAULT_UBIDOTS_SERVER;
	private String _pemFileName = "C:\\Users\\ganes\\git\\connected-devices-java\\connected-devices-java\\src\\main\\java\\schooldomain\\studentname\\connecteddevices\\labs\\module08\\"
			+ ConfigConst.UBIDOTS + ConfigConst.CERT_FILE_EXT;
	private String _authToken = "A1E-bTYcPH9gTqVm4CfXvUPay1nzCHTnie";
	private MqttClientConnector mqttClient;
	public static final String UBIDOTS_VARIABLE_LABEL = "/TempSensor";
	public static final String UBIDOTS_DEVICE_LABEL = "/deviceapi";
	public static final String UBIDOTS_TOPIC_DEFAULT = "/v1.6/devices" + UBIDOTS_DEVICE_LABEL + UBIDOTS_VARIABLE_LABEL;

	/**
	 * MqttPubClientTestApp Constructor
	 */
	public TempSensorPublisherApp() {
		super();
	}

	public String generateRandomvalue(float min, float max) {
		Random r = new Random();
		float random = min + r.nextFloat() * (max - min);
		return Float.toString(random);
	}

	/**
	 * Function to start, connect the MQTT publisher and publish message
	 * 
	 * @param topicName: name of the MQTT session topic in string
	 */
	public void start(String topicName) {
		float min = 0f;
		float max = 40f;
		try {
			mqttClient = new MqttClientConnector(_host, _authToken, _pemFileName);
			mqttClient.connect();
			while (true) {
				mqttClient.publishMessage(topicName, ConfigConst.DEFAULT_QOS_LEVEL,
						generateRandomvalue(min, max).getBytes());
				Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			mqttClient.disconnect();
			e.printStackTrace();
		}
	}

	/**
	 * Main function of MQTT publisher class
	 * 
	 * @param args: arguments list
	 */
	public static void main(String[] args) {
		pubApp = new TempSensorPublisherApp();
		try {
			pubApp.start(UBIDOTS_TOPIC_DEFAULT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}