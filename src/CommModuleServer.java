import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class CommModuleServer {

	private String serverIPAddr;
	private String registryIPAddr;
	private int serverPort;
	private int registryPort;
	private int CONN_TIMEOUT = 1000;

	public CommModuleServer(int serverPort) throws UnknownHostException {
		this.serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.registryIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.serverPort = serverPort;
		this.registryPort = 1099;
	}

	public CommModuleServer(int serverPort, String registryIPAddr, int registryPort)
			throws UnknownHostException {
		this.serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.registryIPAddr = registryIPAddr;
		this.serverPort = serverPort;
		this.registryPort = registryPort;
	}

	public void startService() throws IOException {
		ServerSocket ss = null;
		Socket socket = null;

		ss = new ServerSocket(serverPort);

		while (true) {
			socket = ss.accept();
			ServingThread servingThread = new ServingThread(socket);
			servingThread.run();
		}

	}

	public void registerObject(RMIMessageReg msg) throws UnknownHostException, IOException {
		Socket regSock = new Socket(registryIPAddr, registryPort);
		ObjectOutputStream outReg = new ObjectOutputStream(regSock.getOutputStream());
		outReg.writeObject(msg);
		outReg.flush();
		outReg.close();
		regSock.close();
	}

	private class ServingThread extends Thread {

		public ServingThread(Socket socket) throws SocketException {
			this.socket = socket;
			this.socket.setSoTimeout(CONN_TIMEOUT);
		}

		public void run() {
			try {
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());

				// do unmarshalling directly and invokes that object directly
				// now only can from client
				while (true) {
					RMIMessageInvoke request = null;
					try {
						request = (RMIMessageInvoke) input.readObject();
					} catch (SocketTimeoutException e ) {
						input.close();
						output.close();
						socket.close();
						System.err.println("server socket read time out");
						return;
					} catch (EOFException e ) {
						input.close();
						output.close();
						socket.close();
						return;
					}
	
					RemoteObjectReference ror = request.getRor();
					Object callee = Server.getWarehouse().get(ror.getObjName());
	
					try {
						request.invokeMethod(callee);
					} catch (MyRemoteException e) {
						// TODO Auto-generated catch block
						request.setExceptionThrown(true);
						request.setException(e);
					}
	
					// send back RMIMessage
					output.writeObject(request);
					output.flush();
	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private Socket socket = null;
		private ObjectInputStream input = null;
		private ObjectOutputStream output = null;
	}
}
