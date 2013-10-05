import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Serialize and deserialize the RMIMessage, communicate between client and
 * server. Marshalling and Unmarshalling.
 */

/**
 * @author yinxu
 *
 */
public class CommModuleClient {
	//private RMIMessage request;
	//private RMIMessage reply;
	
	
	public CommModuleClient() {
		
	}
	
	public static RMIMessage sendRequest(RMIMessage request) {
		//get ror first
		RemoteObjectReference ror = request.getRor();
		RMIMessage reply = null;
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(ror.getIpAddr()), 
					ror.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			
			output.writeObject(request);
			output.flush();
			output.close();
			
			input = new ObjectInputStream(socket.getInputStream());
			reply = (RMIMessage) input.readObject();
			input.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
		
		return reply;
	}

}
