package schooldomain.studentname.connecteddevices.common;

import com.google.gson.Gson;
import schooldomain.studentname.connecteddevices.labs.module05.FileTransaction;

/**
 * Class DataUtil is used to capture the json formatted data from the text document
 * created by the FileWriter function to sensor readable data . This is done by the
 * FileReader function
 */

public class DataUtil {
	/**
	 * This method is used transform the sensor data to json data
	 * @param sensordata indicated the data from the sensor
	 * @return jsonSd
	 */
	
	public String SensorDataToJson(SensorData sensordata)
	{
		String jsonSd;
		Gson gson = new Gson();
		jsonSd = gson.toJson(sensordata);
		return jsonSd;
	}
	
	/**
	 * This method reads the data from the text document of the sensorData which
	 * is a json formatted data and transforms that to sensor readable data 
	 * @param jsondata indicates the json formatted data
	 * @param filename indicates the filename
	 * @return SensorData
	 */
	
	public SensorData JsonToSensorData(String jsondata,String filename)
	{
		SensorData sensorData=null;
		if(filename==null)
		{
			
			Gson gson = new Gson();
			sensorData = gson.fromJson(jsondata, SensorData.class);
			return sensorData;
		}
		else
		{
			Gson gson = new Gson();
			String data = FileTransaction.FileReader(filename);
			sensorData = gson.fromJson(data, SensorData.class);
			return sensorData;
			
		}
			
	}

}