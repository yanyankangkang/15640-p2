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


	public RMIRegistry(String regPort) {
		warehouse = new HashMap<String, RemoteObjectReference>();
        this.port = Integer.parseInt(regPort);
	}

	public static void main(String[] args) {

        RMIRegistry reg = null; 
        if (args.length == 2 && args[0].equals("-p")) {
            reg = new RMIRegistry(args[1]);
        } else {
            System.err.println("incorrect args");
        }
        
		System.out.println("RMIRegistry started");

        

		try {
			ServerSocket socket = new ServerSocket(reg.port);
			Socket ss = null;
			ObjectInputStream input = null;
			Object inputObj = null;
			Class<?> c = null;
			while (true) {
				ss = socket.accept();
				input = new ObjectInputStream(ss.getInputStream());
				inputObj = input.readObject();
				c = inputObj.getClass();

				// from client for lookup, then send back the ror
				if (c.equals(Class.forName("RMIMessageLookup"))) {
					RMIMessageLookup msg = (RMIMessageLookup) inputObj;
					RemoteObjectReference ror = reg.warehouse.get(msg.getRemoteObjName());
					msg.setRor(ror);
					ObjectOutputStream output = new ObjectOutputStream(
							ss.getOutputStream());
					output.writeObject(msg);
					output.flush();
					output.close();
					System.out.println("successfully lookup "
							+ msg.getRemoteObjName());

				}
				
				// from server for registry
				// return nothing to server?
				else if (c.equals(Class.forName("RMIMessageReg"))) {
					RMIMessageReg msg = (RMIMessageReg) inputObj;
					RemoteObjectReference ror = msg.getRor();
					reg.warehouse.put(ror.getObjName(), ror);
					System.out.println("successfully registered "
							+ ror.getObjName());
				} 
				
				else {
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
