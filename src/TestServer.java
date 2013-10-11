import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.util.HashMap;

public class TestServer {

	private static HashMap<String, MyRemote> warehouse = new HashMap<String, MyRemote>();
	private static String serverIPAddr;
	private static int serverPort = 15640;
	private static int serverDownloadPort = 15440;

	public static void main(String[] args) throws IOException {


		// user may specify port number here
		serverPort = 15640;
		serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		System.err.println("Client should connect to " + serverIPAddr);

		CommModuleServer commModuleServer = new CommModuleServer(serverPort, 
				serverDownloadPort);
		
		// add test object1
		warehouse.put("testObj", new TestRemoteObject());
		// add test object using as an argument
		warehouse.put("testArgObj", new TestArgRemoteObject());

		// create a ror and register it into registry server
		// hard-code remote interface name and key
		RemoteObjectReference rorReg = new RemoteObjectReference(serverIPAddr,
				serverPort, "TestRemoteObjectInterface", "testObj", commModuleServer.getStubURL());
		RMIMessageReg msg = new RMIMessageReg(rorReg);
		commModuleServer.registerObject(msg);

		commModuleServer.startService();
	}

	public static HashMap<String, MyRemote> getWarehouse() {
		return warehouse;
	}
	
//	/**
//	 * return the path to the stub class file
//	 */
//	public static String getStubURL() {
//		String fileName = "TestRemoteObjectInterface"+"_stub.class";
//		String path = null;
//		URL url = Server.class.getProtectionDomain().getCodeSource().getLocation();
//		path = url.getFile();
//		return "http://" + serverIPAddr + ":" + serverDownloadPort + path + fileName;
//		
//	}

}
