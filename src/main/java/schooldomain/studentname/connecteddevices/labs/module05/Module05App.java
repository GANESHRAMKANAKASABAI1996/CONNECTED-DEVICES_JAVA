
package schooldomain.studentname.connecteddevices.labs.module05;

import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp;
import com.labbenchstudios.edu.connecteddevices.common.DeviceApplicationException;

/**
 *Module05App class is created and inside the main method an
 *object instance of this class is created 
 */

public class Module05App extends BaseDeviceApp
{
	/*
	 * static method is used so that it could be invoked without 
	 * creating the instance of the class
	 *  
	 */
	
	private static final Logger _Logger =
		Logger.getLogger(Module05App.class.getSimpleName());
	
	/**
	 * Inside the main method an Module05App instance is created and
	 * exceptional handling is used to separate the error handling code 
	 * from the regular code
	 */
	
	public static void main(String[] args)
	{
		Module05App app = new Module05App();
		try {
			app.start();
			app.stop();
		} catch (DeviceApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Default constructor 
	 * 
	 */
	
	public Module05App()
	{
		super();
	}
	
	/**
	 *Parameterized Constructor
	 * @param appName indicates the name of the application
	 */
	
	public Module05App(String appName)
	{
		super(appName);
	}
	
	/**
	 * parameterized Constructor.
	 * 
	 * @param appName indicates the name of the application
	 */
	
	public Module05App(String appName, String[] args)
	{
		super(appName, args);
	}
	
	// protected methods
	
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#start()
	 */
	
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - module05 here!-execution begins...");
		TempManagementApp.demo();
		
	}
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#stop()
	 */
	
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping the execution of module05 app...");
	}
	
}
