import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class RMIRegistry {
	private HashMap<String, RemoteObjectReference> warehouse;
	private int port = 1099;
	
	public RMIRegistry() {
		warehouse = new HashMap<String, RemoteObjectReference>();
	}
	
	public static void main(String[] args) {
		RMIRegistry reg = new RMIRegistry();
		System.out.println("RMIRegistry started");
		
		try {
			ServerSocket socket = new ServerSocket(reg.port);
			Socket ss = null;
			ObjectInputStream input = null;
			//RMIMessage msg = null;
			Object inputObj = null;
			Class<?> c = null;
			while (true) {
				ss = socket.accept();
				input = new ObjectInputStream(ss.getInputStream());
				inputObj = input.readObject(); 
				c = inputObj.getClass();
				if (c.equals(Class.forName("RMIMessageLookup"))) {
					// from client for lookup
					RMIMessageLookup msg = (RMIMessageLookup)inputObj;
					// send back the ror
					ObjectOutputStream output = 
							new ObjectOutputStream(ss.getOutputStream());
					output.writeObject(reg.warehouse.get(msg.getRemoteObjName()));
					output.flush();
					output.close();
					System.out.println("successfully lookup "+ msg.getRemoteObjName());
					
				} else if (c.equals(Class.forName("RMIMessageReg"))) {
					// from server for registry
					RMIMessageReg msg = (RMIMessageReg)inputObj;
					RemoteObjectReference ror = msg.getRor();
					reg.warehouse.put(ror.getObjName(), ror);
					System.out.println("successfully registered "+ror.getObjName());
					// return nothing to server?
				} else {
					System.out.println("Weird");
				}
				
				input.close();
				ss.close();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
