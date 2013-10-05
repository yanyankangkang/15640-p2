import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;


public class CommModuleServer {
	
	private HashMap<String,MyRemote> warehouse;
	public CommModuleServer() {
		warehouse = new HashMap<String,MyRemote>();
	}
	
	public static void main(String[] args) throws IOException {
		
		CommModuleServer cm = new CommModuleServer();
		// add test object
		cm.warehouse.put("testObj", new TestRemoteObject());
		/* start comm with registry, construct ror */
		// get localhost ip
		String ipAddr = Inet4Address.getLocalHost().getHostAddress();
		// hardcoding port number
		int port = 15640;
		// hardcoding remote interface name and key
		RemoteObjectReference rorReg = new RemoteObjectReference(ipAddr, port,
				"TestRemoteObjectInterface", "testObj");
		RMIMessageReg msg = new RMIMessageReg(rorReg);
		// write to registry server, hardcoding registry port number to 1099
		Socket regSock = new Socket(ipAddr, 1099);
		ObjectOutputStream outReg = 
				new ObjectOutputStream(regSock.getOutputStream());
		outReg.writeObject(msg);
		outReg.flush();
		outReg.close();
		regSock.close();
		/* finish comm with registry */
		
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
				//now only can from client
				RMIMessage request = (RMIMessage) input.readObject();
				
				// Multi-thread starts here if needed, can use an inner thread
				//class
				RemoteObjectReference ror = request.getRor();
				Object callee = cm.warehouse.get(ror.getObjName());
				
				request.invokeMethod(callee);
				
//				RMIMessage reply = new RMIMessage();
//				reply.setReturnValue(request.getReturnValue());
				//send back RMIMessage
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
}
