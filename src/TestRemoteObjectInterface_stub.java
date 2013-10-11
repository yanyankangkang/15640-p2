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

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		this.ror = ror;
	}

	@Override
	public String remoteArgTest(TestArgRemoteObjectInterface remoteArg)
			throws Exception {
		// TODO Auto-generated method stub
		// handle remote object using as arguments
		Object[] args = { remoteArg.getRemoteObjectReference() };
		System.out.println("remote ror is: "+remoteArg.getRemoteObjectReference());
		
		return (String) invoke("remoteArgTest", args);
	}

	public Object invoke(String methodName, Object[] args)
			throws Exception {

		RMIMessageInvoke message = new RMIMessageInvoke(methodName, args);
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
