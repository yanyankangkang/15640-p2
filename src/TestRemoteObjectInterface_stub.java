/**
 * 
 */

/**
 * @author yinxu
 * 
 */
public class TestRemoteObjectInterface_stub implements TestRemoteObjectInterface {
	private RemoteObjectReference ror;

	public TestRemoteObjectInterface_stub() {

	}

	public String sayHello(String name) throws MyRemoteException {
		// form a message object
		Object[] args = new String[1];
		args[0] = name;
		RMIMessageInvoke message = new RMIMessageInvoke("sayHello", args);
		message.setRor(ror);
		RMIMessageInvoke reply = CommModuleClient.sendRequest(message);
		if (reply.exceptionThrown) {
			throw new MyRemoteException(reply.getException().getMessage());
		}

		return (String) reply.getReturnValue();
	}

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		this.ror = ror;
	}

}
