import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class CommModuleServer {
	
	private HashMap<String,MyRemote> warehouse;
	public CommModuleServer() {
		warehouse = new HashMap<String,MyRemote>();
	}
	
	public static void main(String[] args) {
		
		CommModuleServer cm = new CommModuleServer();
		// add test object
		cm.warehouse.put("testObj", new TestRemoteObject());
		/* construct ror */
		// get localhost ip
		String ipAddr = Inet4Address.getLocalHost().getHostAddress();
		// hardcoding port number
		int port = 15640;
		// hardcoding remote interface name and key
		RemoteObjectReference ror = new RemoteObjectReference(ipAddr, port,
				"TestRemoteObjectInterface", 0);
		
		ServerSocket ss = null;
		Socket socket = null;
		try {																																																																																																																																																																																													
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//input/output stream
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		while (true) {
			try {
				socket = ss.accept();
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
				
				//do unmarshalling directly and invokes that object directly
				RMIMessage request = (RMIMessage) input.readObject();
				socket.close();
				// Multi-thread starts here if needed, can use an inner thread
				//class
				RemoteObjectReference ror = request.getRor();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}
}
