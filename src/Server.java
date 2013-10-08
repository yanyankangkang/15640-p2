import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;

public class Server {

	private static HashMap<String, MyRemote> warehouse = new HashMap<String, MyRemote>();
	private static String serverIPAddr;
	private static int serverPort = 15640;

	public static void main(String[] args) throws IOException {


		// user may specify port number here
		serverPort = 15640;
		serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		System.err.println("Client should connect to " + serverIPAddr);

		CommModuleServer commModuleServer = new CommModuleServer(serverPort);
		
		// add test objects
		warehouse.put("testObj", new TestRemoteObject());

		// create a ror and register it into registry server
		// hard-code remote interface name and key
		RemoteObjectReference rorReg = new RemoteObjectReference(serverIPAddr,
				serverPort, "TestRemoteObjectInterface", "testObj");
		RMIMessageReg msg = new RMIMessageReg(rorReg);
		commModuleServer.registerObject(msg);

		commModuleServer.startService();
	}

	public static HashMap<String, MyRemote> getWarehouse() {
		return warehouse;
	}

}
