package schooldomain.studentname.connecteddevices.labs.module05;

/**
 * @author GANESHRAM KANAKASABAI
 */
import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;


/**
 * Creating a class TempManagement App that instantiates the DataUtil object
 * inside the demo function and reads the json formatted data from the path 
 * and displays the sensor readable data in the console output
 */

public class TempManagementApp {

	/**
	 * demo() method is created for instantiating DataUtil object and for
	 * displaying the output
	 */
	
	public static void demo()
	{
		DataUtil sensor = new DataUtil();
		SensorData sen = sensor.JsonToSensorData(null, "C:\\Users\\ganes\\git\\connected-devices-python\\apps\\labs\\module05\\sensordata.txt");
		System.out.println(sen);
	}

}

