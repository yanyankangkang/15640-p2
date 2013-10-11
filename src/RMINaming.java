import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * @author yinxu
 * 
 */
public class RMINaming {

	private HashMap<String, MyRemote> warehouse = new HashMap<String, MyRemote>();
	private String registryIPAddr;
	private int registryPort;

	public RMINaming() throws UnknownHostException {
		this.registryIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.registryPort = 1099;
	}

	public MyRemote lookup(String serviceName) throws Exception {
		// check if the table contains the stub object of the ror
		if (warehouse.containsKey(serviceName)) {
			return warehouse.get(serviceName);
		} else {
			RemoteObjectReference ror = CommModuleClient.lookup(registryIPAddr, registryPort,
					new RMIMessageLookup(serviceName)).getRor();
			MyRemote stub = null;
			
			if (ror == null) {
				System.out.println("lookup failed");
				throw new MyRemoteException("lookup failed");
			} else {
				stub = (MyRemote) ror.localise();
				warehouse.put(serviceName, stub);
			}
			
			return stub;
		}
	}
	
	

}
