package schooldomain.studentname.connecteddevices.labs.module08;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

import schooldomain.studentname.connecteddevices.labs.module08.MqttClientConnector;

/**
 * Created on Feb 26, 2019
 * TempActuatorSubscriberApp.java: Java class to receive message using MQTT protocol
 * 
 * @author GANESHRAM KANAKASABAI
 */
public class TempActuatorSubscriberApp {

	/**
	 * MqttSubClientTestApp - class to receive message using MQTT protocol
	 * 
	 * @var subApp: MQTT subscriber class self instance
	 * @var mqttClient: MQTT connector helper class instance
	 */
	private static TempActuatorSubscriberApp subApp;
	private MqttClientConnector mqttClient;
	private String _host = ConfigConst.DEFAULT_UBIDOTS_SERVER;
	private String _pemFileName = "C:\\Users\\ganes\\git\\connected-devices-java\\connected-devices-java\\src\\main\\java\\schooldomain\\studentname\\connecteddevices\\labs\\module08\\"
			+ ConfigConst.UBIDOTS + ConfigConst.CERT_FILE_EXT;
	private String _authToken = "A1E-bTYcPH9gTqVm4CfXvUPay1nzCHTnie";
	public static final String UBIDOTS_VARIABLE_LABEL = "/tempactuator";
	public static final String UBIDOTS_DEVICE_LABEL = "/deviceapi";
	public static final String UBIDOTS_TOPIC_DEFAULT = "/v1.6/devices" + UBIDOTS_DEVICE_LABEL + UBIDOTS_VARIABLE_LABEL
			+ "/lv";

	/**
	 * MqttSubClientTestApp constructor
	 */
	public TempActuatorSubscriberApp() {
		super();
	}

	/**
	 * Method to start, connect the MQTT subscriber and receive message
	 * 
	 * @param topicName: name of the MQTT session topic in string
	 */
	public void start(String topicName) {
		try {
			mqttClient = new MqttClientConnector(_host, _authToken, _pemFileName);
			mqttClient.connect();
			while (true) {
				mqttClient.subscribeToTopic(topicName);
				Thread.sleep(60000); // minimum wait time - 60 sec
			}
		} catch (InterruptedException e) {
			mqttClient.disconnect();
			e.printStackTrace();
		}
	}

	/**
	 * Main method of MQTT subscriber class
	 * 
	 * @param args: arguments list
	 */
	public static void main(String[] args) {

		subApp = new TempActuatorSubscriberApp();
		try {
			subApp.start(UBIDOTS_TOPIC_DEFAULT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}