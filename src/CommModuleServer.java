import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;


public class CommModuleServer {

	private String serverIPAddr;
	private String registryIPAddr;
	private int serverPort;
	private int registryPort;
	private int serverDownloadPort;
	private int CONN_TIMEOUT = (int) (1 * 1000);
	//move part of TestServer
//	private static HashMap<String, MyRemote> warehouse = new HashMap<String, MyRemote>();

	public CommModuleServer(int serverPort, int downloadPort, String regPort) throws UnknownHostException {
		this.serverIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.registryIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.serverPort = serverPort;
		this.registryPort = Integer.parseInt(regPort);
		this.serverDownloadPort = downloadPort;
	}

	public void startService() throws IOException {
		//new RMIThread
		ServerThread st = new ServerThread();
		st.start();
		//new DownloadThread
		DownloadThread dt = new DownloadThread();
		dt.start();
		
	}

	public void registerObject(RMIMessageReg msg) throws UnknownHostException, IOException {
		Socket regSock = new Socket(registryIPAddr, registryPort);
		ObjectOutputStream outReg = new ObjectOutputStream(regSock.getOutputStream());
		outReg.writeObject(msg);
		outReg.flush();
		outReg.close();
		regSock.close();
	}
	
	private class ServerThread extends Thread {
		
		@Override
		public void run() {
			try {
				ServerSocket ss = new ServerSocket(serverPort);
				Socket socket = null;
				while (true) {
					socket = ss.accept();
					ServingThread servingThread = new ServingThread(socket);
					servingThread.run();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
					RMIMessageInvoke reply = new RMIMessageInvoke();
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
					Object callee = TestServer.getWarehouse().get(ror.getObjName());
					System.out.println("callee is: " + callee.getClass().getName());
					parseArgs(request);
					
					try {
						reply = request.invokeMethod(callee);
					} catch (MyRemoteException e) {
						// TODO Auto-generated catch block
						reply.setExceptionThrown(true);
						reply.setException(e);
					}
	
					// send back RMIMessage
					output.writeObject(reply);
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
	
	private class DownloadThread extends Thread {
		@Override
		public void run() {
			Socket socket = null;
			ServerSocket dss;
			try {
				dss = new ServerSocket(serverDownloadPort);
				while (true) {
					socket = dss.accept();
					DownloadingThread dt = new DownloadingThread(socket);
					dt.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class DownloadingThread extends Thread {
		private Socket socket = null;
		private BufferedReader input;
		private PrintStream output;
		public DownloadingThread(Socket sock) {
			super();
			socket = sock;
			try {
				input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				output = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				// only read the first line
				String line = input.readLine();
				String path = parse(line);
				
				// write the HTTP header
				output.println("HTTP/1.1 200 OK");
				output.println();
				
				FileInputStream fis = new FileInputStream(path);
				byte[] data = new byte[fis.available()];
				fis.read(data);
				output.write(data);
				output.flush();
				output.close();
				fis.close();
				socket.close();
				
			} catch (FileNotFoundException e1) {
				output.println("HTTP/1.1 404 Not Found");
				output.println();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String parse(String line) {
		int start = line.indexOf("/");
		int end = line.indexOf(" ", start);
		String result = line.substring(start,end);
		return result;
	}
	
	/**
	 * return the path to the stub class file
	 */
	public String getStubURL(String intName) {
		String fileName = intName + "_stub.class";
		String path = null;
		URL url = TestServer.class.getProtectionDomain().getCodeSource().getLocation();
		path = url.getFile();
		return "http://" + serverIPAddr + ":" + serverDownloadPort + path + fileName;
		
	}
	
	/**
	 * parse the arguments of the RMIMessageInvoke
	 */
	public void parseArgs(RMIMessageInvoke request) {
		Object[] args = request.getArgs();
		Class<?>[] argsTypes = request.getArgsTypes();
		if (args != null) {
			argsTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof RemoteObjectReference) {
					System.out.println("found a remote argument");
					System.out.println("hashmap is: " + TestServer.getWarehouse());
					//hard coding
					args[i] = TestServer.getWarehouse().get(((RemoteObjectReference)args[i]).getObjName());
					System.out.println("replaced ror with: "+args[i]);
					argsTypes[i] = args[i].getClass().getInterfaces()[0];
					continue;
				}
				argsTypes[i] = args[i].getClass();
			}
			request.setArgs(args);
			request.setArgsTypes(argsTypes);
		}
		
	}
}
