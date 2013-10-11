import java.io.IOException;
import java.net.Inet4Address;
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
		TestRemoteObject rm1 = new TestRemoteObject();
		//System.out.println(rm1.getClass().getInterfaces()[0]);
		String intName1 = rm1.getClass().getInterfaces()[0].toString();
		warehouse.put("testObj", rm1);
		// add test object using as an argument
		TestArgRemoteObject rm2 = new TestArgRemoteObject();
		String intName2 = rm2.getClass().getInterfaces()[0].toString();
		warehouse.put("testArgObj", rm2);

		// create a ror and register it into registry server
		// hard-code remote interface name and key
		RemoteObjectReference rorReg1 = new RemoteObjectReference(serverIPAddr,
				serverPort, intName1, "testObj", commModuleServer.getStubURL(intName1));
		RMIMessageReg msg1 = new RMIMessageReg(rorReg1);
		commModuleServer.registerObject(msg1);
		
		// for the second remote object
		RemoteObjectReference rorReg2 = new RemoteObjectReference(serverIPAddr,
				serverPort, intName2, "testArgObj", commModuleServer.getStubURL(intName2));
		RMIMessageReg msg2 = new RMIMessageReg(rorReg2);
		commModuleServer.registerObject(msg2);

		commModuleServer.startService();
	}

	public static HashMap<String, MyRemote> getWarehouse() {
		return warehouse;
	}
	

}
