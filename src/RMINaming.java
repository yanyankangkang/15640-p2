import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * @author yinxu
 *
 */
public class RMINaming {

	private HashMap<RemoteObjectReference, MyRemote> warehouse = 
			new HashMap<RemoteObjectReference, MyRemote>();
	private String registryIPAddr;
	private int registryPort;
	
	public RMINaming () throws UnknownHostException {
		this.registryIPAddr = Inet4Address.getLocalHost().getHostAddress();
		this.registryPort = 1099;
	}
	
	public  MyRemote lookup(String serviceName) throws MyRemoteException {
		RemoteObjectReference ror = CommModuleClient.lookup(registryIPAddr, registryPort, 
				new RMIMessageLookup(serviceName)).getRor();
		if (ror == null) {
			System.out.println("lookup failed");
			throw new MyRemoteException("lookup failed");
		}
		// check if the table contains the stub object of the ror
		MyRemote stub = warehouse.get(ror);
		if (stub == null) {
			stub = (MyRemote) ror.localise();
			warehouse.put(ror, stub);
		}
		return stub;
	}
	
	
}
