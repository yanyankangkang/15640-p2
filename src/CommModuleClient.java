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

	/**
	 * 
	 * @param request contains the ip address and port number of the server, as well as the service name
	 * @return add the return value into the message
	 */
	public static RMIMessage sendRequest(RMIMessage request) {
		// get remote object reference first
		RemoteObjectReference ror = request.getRor();
		RMIMessage reply = (RMIMessage) sendMessage(ror.getIpAddr(), ror.getPort(), request);
		return reply;
	}

	/**
	 * client use this method to get remote object reference from registry
	 * server
	 * 
	 * @param ipAddr
	 *            ip address of registry server
	 * @param port
	 *            port number of registry server
	 * @param msg
	 *            message containing service name being sent to registry server
	 * @return remote object reference from registry server, client use this to
	 *         talk to server later
	 */
	public static RemoteObjectReference lookup(String ipAddr, int port,	RMIMessageLookup msg) {
		RemoteObjectReference reply = (RemoteObjectReference) sendMessage(ipAddr, port, msg);
		return reply;
	}

	private static Object sendMessage(String ipAddr, int port, Object msg) {
		Object reply = null;
		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;

		try {
			// open the socket to talk to registry server
			socket = new Socket(InetAddress.getByName(ipAddr), port);

			// send the message to specific host and port
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(msg);
			output.flush();

			// get the reply back from the server
			input = new ObjectInputStream(socket.getInputStream());
			reply = input.readObject();

			input.close();
			output.close();
			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

		return reply;
	}
}
