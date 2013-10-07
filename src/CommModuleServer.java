import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class CommModuleServer {

	private static HashMap<String, MyRemote> warehouse;
	private static String serverIPAddr;
	private static String registryIPAddr;
	private static int serverPort;
	private static int registryPort;

	public CommModuleServer() throws UnknownHostException {
		warehouse = new HashMap<String, MyRemote>();
		serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		registryIPAddr = Inet4Address.getLocalHost().getHostAddress();
		serverPort = 15640;
		registryPort = 1099;
	}

	public static void main(String[] args) throws IOException {

		// add test object
		CommModuleServer cm = new CommModuleServer();
		cm.warehouse.put("testObj", new TestRemoteObject());

		// create a ror and register it into registry server
		// hardcoding remote interface name and key
		RemoteObjectReference rorReg = new RemoteObjectReference(serverIPAddr,
				serverPort, "TestRemoteObjectInterface", "testObj");
		RMIMessageReg msg = new RMIMessageReg(rorReg);
		registerObject(msg);
		
		startService();
	}

	private static void startService() {
		ServerSocket ss = null;
		Socket socket = null;
		try {
			ss = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// input/output stream
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		while (true) {
			try {
				socket = ss.accept();
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());

				// do unmarshalling directly and invokes that object directly
				// now only can from client
				RMIMessage request = (RMIMessage) input.readObject();

				// Multi-thread starts here if needed, can use an inner thread
				// class
				RemoteObjectReference ror = request.getRor();
				Object callee = warehouse.get(ror.getObjName());

				request.invokeMethod(callee);

				// send back RMIMessage
				output.writeObject(request);
				output.flush();
				
				output.close();
				input.close();
				socket.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void registerObject(RMIMessage msg)
			throws UnknownHostException, IOException {
		Socket regSock = new Socket(registryIPAddr, registryPort);
		ObjectOutputStream outReg = new ObjectOutputStream(
				regSock.getOutputStream());
		outReg.writeObject(msg);
		outReg.flush();
		outReg.close();
		regSock.close();
	}
}
