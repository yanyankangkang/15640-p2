/**
 * 
 */

/**
 * @author yinxu
 * 
 */
public class TestRemoteObjectInterface_stub implements
		TestRemoteObjectInterface {
	private RemoteObjectReference ror;

	public String sayHello(String name) throws Exception {
		// form a message object
		Object[] args = { name };
		return (String) invoke("sayHello", args);

	}

	public String sayHello(String name) throws Exception {
		// form a message object
		Object[] args = new String[1];
		args[0] = name;
		RMIMessageInvoke message = new RMIMessageInvoke("sayHello", args);
=======
	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		this.ror = ror;
	}

	@Override
	public String remoteArgTest(TestArgRemoteObjectInterface remoteArg)
			throws MyRemoteException, InterruptedException {
		// TODO Auto-generated method stub
		// handle remote object using as arguments
		Object[] args = { remoteArg.getRemoteObjectReference() };
		
		return (String) invoke("remoteArgTest", args);
	}

	public Object invoke(String methodName, Object[] args)
			throws MyRemoteException, InterruptedException {

		RMIMessageInvoke message = new RMIMessageInvoke(methodName, args);
>>>>>>> fb0632be8ee922864c821d758162d1e0c89c20d3
		message.setRor(ror);

		RMIMessageInvoke reply = CommModuleClient.sendRequest(message);
		if (reply == null) {
			throw new MyRemoteException("Error read reply message from server");
		}
		if (reply.exceptionThrown) {
			throw new MyRemoteException(reply.getException().getMessage());
		}
		return reply.getReturnValue();
	}

	@Override
	public RemoteObjectReference getRemoteObjectReference() {
		// TODO Auto-generated method stub
		return this.ror;
	}

}
