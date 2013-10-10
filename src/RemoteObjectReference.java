import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class RemoteObjectReference implements Serializable{

	private static final long serialVersionUID = -4362993247531924695L;
	private String ipAddr;
	private int port;
	private String remoteInterfaceName;
	//need a local reference or other identifier
	private String objName;
	// for stub class download, add path to stub class file
	private String stubURL;
	
	public RemoteObjectReference(String ip, int port, String riname, 
			String objName, String stubURL) {
		this.ipAddr = ip;
		this.port = port;
		this.remoteInterfaceName = riname;
		this.objName = objName;
		this.stubURL = stubURL;
	}
	
	/**
	 * generates a stub class
	 * @return
	 */
	public Object localise() {
		//create a stub class as needed. if classNotFound, download from server 
		Object stubObject = null;
		String stubName = remoteInterfaceName + "_stub";
		Class<?> stubClass = null;
		try {
			stubClass = Class.forName(stubName);
			
		} catch (ClassNotFoundException c) {
			System.out.println("Could not find class " + stubName);
			System.out.println("Downloading class file " + stubName + ".class...");
			//c.printStackTrace();
			downloadStub(stubName);
			System.out.println("Download succeeded");
			try {
				stubClass = Class.forName(stubName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Download failed");
				e.printStackTrace();
			}
			
		}
		
		try {
			stubObject = stubClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((MyRemote)stubObject).setRemoteObjectReference(this);
		return stubObject;
	}
	
	/**
	 * Download the stub class file from remote server
	 * @return
	 */
	public void downloadStub(String fileName) {
		HttpURLConnection httpConn = null;
		try {
			URL url = new URL(stubURL);
			httpConn = (HttpURLConnection) url.openConnection();
			int responseCode = httpConn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream input = httpConn.getInputStream();
				FileOutputStream output = new FileOutputStream("./"+fileName+".class");
				byte[] buffer = new byte[4096];
				int read = -1;
				while ((read = input.read(buffer)) != -1) {
					output.write(buffer, 0, read);
				}
				output.close();
				input.close();
				System.out.println("File transfer done");
			} else {
				System.out.println("HTTP request failed: "+ responseCode);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpConn.disconnect();
	}
	
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

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
}
