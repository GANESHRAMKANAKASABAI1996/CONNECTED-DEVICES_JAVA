package schooldomain.studentname.connecteddevices.labs.module08;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import schooldomain.studentname.connecteddevices.common.SensorData;
import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import schooldomain.studentname.connecteddevices.common.DataUtil;

/**
 * Created on Feb 26, 2019
 * MqttClientConnector.java: Class for implementing MQTT protocol connector
 * 
 * @author GANESHRAM KANAKASABAI
 */
public class MqttClientConnector implements MqttCallback {

	/**
	 * MqttClientConnector.java: Class for implementing MQTT protocol connector
	 * 
	 * @variable logger: logger class
	 * @variable protocol: type of protocol in String
	 * @variable host: MQTT host server name in String
	 * @variable port: MQTT port number in integer
	 * @variable clientID: ID of the MQTT client in String
	 * @variable brokerAddr: complete address of the host server in String
	 * @variable mqttClient: MQTT client class object instance
	 * @variable sensorData: Sensor data class object instance
	 * @variable dataUtil: Data utility class object instance
	 */
	private static final Logger logger = Logger.getLogger(MqttClientConnector.class.getName());
	private String _protocol = ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String _host = ConfigConst.DEFAULT_MQTT_SERVER;
	private int _port = ConfigConst.DEFAULT_MQTT_PORT;

	private String _authToken;
	private String _clientID;
	private String _brokerAddr;
	private String _pemFileName;
	private boolean _isSecureConn = false;
	private MqttClient mqttClient;
	private SensorData sensorData;
	private DataUtil dataUtil;

	/**
	 * MqttClientConnector constructor
	 * 
	 * @param host: Name of the broker to connect.
	 * @param authToken: Authorizing access to the broker.
	 * @param pemFileName: The name of the certificate file to use. If null /
	 *        invalid, ignored.
	 */
	public MqttClientConnector(String host, String authToken, String pemFileName) {

		super();
		if (host != null && host.trim().length() > 0) {
			_host = host;
		}

		if (authToken != null && authToken.trim().length() > 0) {
			_authToken = authToken;
		}
		if (pemFileName != null) {
			File file = new File(pemFileName);
			if (file.exists()) {
				_protocol = ConfigConst.SECURE_MQTT_PROTOCOL;
				_port = ConfigConst.SECURE_MQTT_PORT;
				_pemFileName = pemFileName;
				_isSecureConn = true;
				logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			} else {
				logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			}
		}
		_clientID = MqttClient.generateClientId();
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		logger.info("Using URL for broker conn: " + _brokerAddr);

	}

	/**
	 * MqttClientConnector default constructor
	 */
	public MqttClientConnector() {
		if (_host != null && _host.trim().length() > 0) {
			this.sensorData = new SensorData(30.0,0.0,"time","temperature readings");
			this.dataUtil = new DataUtil();
			this._clientID = mqttClient.generateClientId();
			logger.info("Using client id for broker connection: " + _clientID);
			this._brokerAddr = _protocol + "://" + _host + ":" + _port;
			logger.info("Using URL for broker connection: " + _brokerAddr);
		}
	}

	/**
	 * Method to create the create JSON string of sensor data and return it
	 * 
	 * @return SJobj: sensor data object in JSON string format
	 */
	public String createJSON() {
		setSensorData(sensorData);
		String SJobj = dataUtil.SensorDataToJson(getSensorData());
		return SJobj;
	}

	/**
	 * Method to set the values of sensor data object
	 * 
	 * @param sensorData: SensorData class object instance
	 */
	public void setSensorData(SensorData sensorData) {
		sensorData.setName("Temperature Sensor");
		sensorData.setTime("time");
		sensorData.setCurVal((Double) 10.618763801287058);
		sensorData.setAvgVal((Double) 16.074480368291518);
		sensorData.setSampleCount(7);
		sensorData.setMinVal((Double) 2.618763801287058);
		sensorData.setMaxVal((Double) 23.756228445857346);
		sensorData.setTotVal((Double) 64.29792147316607);
	}

	/**
	 * Method returns the sensor data object
	 * 
	 * @return sensorData: SensorData class object instance
	 */
	public SensorData getSensorData() {
		Random r = new Random();
		float min = 20;
		float max = 25;
		float random = min + r.nextFloat() * (max - min);
		sensorData.updateValue(random);
		return sensorData;
	}

	/**
	 * Method to connect to the MQTT host server
	 */
	public void connect() {
		if (mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
			try {
				mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				connOpts.setCleanSession(true);
				if (_authToken != null)
					connOpts.setUserName(_authToken);

				// if we are using a secure connection, there's a bunch of stuff we need to
				// do...
				if (_isSecureConn)
					initSecureConnection(connOpts);

				logger.info("Connecting to broker: " + _brokerAddr);
				mqttClient.setCallback(this);
				mqttClient.connect(connOpts);
				logger.info("connected to broker: " + _brokerAddr);
			} catch (MqttException ex) {
				logger.log(Level.SEVERE, "Failed to connect to broker" + _brokerAddr, ex);
			}

		}
	}

	/**
	 * Method to disconnect from the MQTT host server
	 */
	public void disconnect() {
		try {
			mqttClient.disconnect();
			logger.info("Disconnect from broker: " + _brokerAddr);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr, ex);
		}
	}

	/**
	 * Method to publish/send MQTT message
	 * 
	 * @param topic: name of the MQTT session topic in string
	 * @param qosLevel: quality of service in integer 0,1,2
	 * @param payload: message that needs to be sent in byte array
	 * @return msgSent: true - on success, false - on failure
	 */
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean msgSent = false;
		try {
			logger.info("Publishing message to topic: " + topic);
			MqttMessage msg = new MqttMessage(payload);
			msg.setQos(qosLevel);
			mqttClient.publish(topic, msg);
			logger.info("Message Published " + msg.getId());
			msgSent = true;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Failed to publish Mqtt message " + ex.getMessage());
		}
		return msgSent;
	}

	/**
	 * Method to subscribe and receive MQTT message
	 * 
	 * @param topic: name of the MQTT session topic in string
	 * @return success: true - on success, false - on failure
	 */
	public boolean subscribeToTopic(String topic) {
		boolean success = false;
		try {
			logger.info("Subscribing to topic: " + topic);
			mqttClient.subscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}

	private void initSecureConnection(MqttConnectOptions connOpts) {
		try {
			logger.info("Configuring TLS...");
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyStore keyStore = readCertificate();
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			connOpts.setSocketFactory(sslContext.getSocketFactory());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
		}
	}

	private KeyStore readCertificate()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(_pemFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ks.load(null);
		while (bis.available() > 0) {
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("adk_store" + bis.available(), cert);
		}
		return ks;
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", cause);
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		logger.info("\n\nMessage arrived: " + topic + "," + message.getId() + ",\n" + message);
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		logger.info("Deleviry Complete: " + token.getMessageId() + "-" + token.getResponse());
		
	}
}