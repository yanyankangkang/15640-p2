import java.io.Serializable;

/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class RemoteObjectReference implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4362993247531924695L;
	private String ipAddr;
	private int port;
	private String remoteInterfaceName;
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRemoteInterfaceName() {
		return remoteInterfaceName;
	}

	public void setRemoteInterfaceName(String remoteInterfaceName) {
		this.remoteInterfaceName = remoteInterfaceName;
	}

	public int getObjKey() {
		return objKey;
	}

	public void setObjKey(int objKey) {
		this.objKey = objKey;
	}

	//need a local reference or other identifier
	private int objKey;
	
	public RemoteObjectReference(String ip, int port, String riname, int key) {
		ipAddr = ip;
		this.port = port;
		remoteInterfaceName = riname;
		objKey = key;
	}
	
	/**
	 * generates a stub class
	 * @return
	 */
	public Object localise() {
		//create a stub class as needed
		Object o = null;
		String stubName = remoteInterfaceName + "_stub";
		try {
			Class<?> c = Class.forName(stubName);
			o = c.newInstance();
		} catch (ClassNotFoundException c) {
			System.out.println("Could not find class " + stubName);
			c.printStackTrace();
			return null;
		} catch (InstantiationException ie) {
			System.out.println("Instantiation Exception for " + stubName);
			ie.printStackTrace();
			return null;
		} catch (IllegalAccessException iae) {
			System.out.println("IllegalAccessException for " + stubName);
			iae.printStackTrace();
			return null;
		}
		return o;
	}
}
