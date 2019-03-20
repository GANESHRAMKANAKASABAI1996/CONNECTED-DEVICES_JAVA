package schooldomain.studentname.connecteddevices.labs.module07;

import java.net.SocketException;

import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

/**
 * This is an application that runs a CoAP server
 * 
 * @author GANESHRAM KANAKASABAI
 *
 */
public class CoapServerTestApp {
	public static void main(String[] args)
	{
		System.out.println("Server Started Running...");
		
		ConfigUtil config = ConfigUtil.getInstance();
		config.loadConfig();
		
		try {
			CoapServerConnector coapServer = new CoapServerConnector(config);
			coapServer.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
