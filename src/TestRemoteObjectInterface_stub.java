/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestRemoteObjectInterface_stub implements TestRemoteObjectInterface{
	private RemoteObjectReference ror;
	
	public TestRemoteObjectInterface_stub() {
		
	}
	
	public String sayHello(String name) throws MyRemoteException {
		//form a message object
		Object[] args = new String[1];
		args[0] = name;
		RMIMessage message = new RMIMessage(null, "sayHello", args);
		message.setRor(ror);
		
		RMIMessage reply = CommModuleClient.sendRequest(message);
		System.out.println("replay is "+reply);
		return (String)reply.getReturnValue();
	}

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		this.ror = ror;
	}
	
}
