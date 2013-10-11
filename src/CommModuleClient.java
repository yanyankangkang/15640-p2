import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

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
	 * @param request
	 *            contains the ip address and port number of the server, as well
	 *            as the service name
	 * @return add the return value into the message
	 * @throws Exception
	 */
	public static RMIMessageInvoke sendRequest(RMIMessageInvoke request)
			throws Exception {
		// get remote object reference first
		RemoteObjectReference ror = request.getRor();
		RMIMessageInvoke reply = (RMIMessageInvoke) sendMessage(
				ror.getIpAddr(), ror.getPort(), request);
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
	 *         talk to server laterw
	 * @throws Exception
	 */
	public static RMIMessageLookup lookup(String ipAddr, int port,
			RMIMessageLookup msg) throws Exception {
		RMIMessageLookup reply = (RMIMessageLookup) sendMessage(ipAddr, port,
				msg);
		return reply;
	}

	private static Object sendMessage(String ipAddr, int port, Object msg)
			throws Exception {
		Object reply = null;
		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;

		// open the socket to talk to registry/server
		try {
			// socket cache hit here
			if (socketCache.containsSocket(ipAddr, port)) {
				SocketInfo socketInfo = socketCache.getSocketInfo(ipAddr, port);
				output = socketInfo.output;
				try {
					output.writeObject(msg);
					output.flush();

					input = socketInfo.input;
					reply = input.readObject();
				}
				// if fails to connect the cached socket and its streams, try
				// another time
				catch (Exception ex) {

					if (ex instanceof java.io.EOFException
							|| ex instanceof java.net.SocketException) {
						socket = new Socket(ipAddr, port);
						output = new ObjectOutputStream(
								socket.getOutputStream());
						output.writeObject(msg);
						output.flush();

						input = new ObjectInputStream(socket.getInputStream());
						reply = input.readObject();
						socketCache.putSocket(socket, input, output);
					} else {
						throw ex;
					}
				}
			}
			// socket cache miss here
			else {
				socket = new Socket(ipAddr, port);
				output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(msg);
				output.flush();

				input = new ObjectInputStream(socket.getInputStream());
				reply = input.readObject();
				socketCache.putSocket(socket, input, output);

			}

		} catch (java.net.ConnectException e) {
			System.err.println("Server is down");
			return null;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException ce) {
			// TODO Auto-generated catch block
			ce.printStackTrace();
			return null;
		}

		return reply;
	}

	private static SocketCache socketCache = new SocketCache();

}
