package schooldomain.studentname.connecteddevices.labs.module05;
import java.io.*;

/**
 * 
 * FileTransaction class is created which first reads the json formatted data 
 * from the text file using the FileReader function
 * Exception handling is used to separate the error handling code from the regular
 * code and propagating errors upto call stack
 */

public class FileTransaction {
	public static String FileReader(String file)
	{
		String json = new String();
		try
		{
			FileReader fr = new FileReader(file);
			int ch;
			while((ch=fr.read())!=-1)
			{
				json = json + (char)ch;
			}
			fr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * FileWrite class is created to write the json formatted data
	 * @param fileWriteEnable is used to write the sensor data into json formatted data
	 * @param file indicates the file that is read
	 * @param json 
	 */
	
	public static void fileWrite(String fileWriteEnable, String file, String json)
	{
		File jsonFile = new File(file);
		
		try {
			jsonFile.createNewFile();
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
