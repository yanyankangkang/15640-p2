/**
 * 
 */

/**
 * @author yinxu
 * 
 */
public class TestArgRemoteObjectInterface_stub implements TestArgRemoteObjectInterface{
	private RemoteObjectReference ror;
	
	
	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		this.ror = ror;
	}
	
	@Override
	public RemoteObjectReference getRemoteObjectReference() {
		// TODO Auto-generated method stub
		return this.ror;
	}
	
	public Object invoke(String methodName, Object[] args)
			throws MyRemoteException, InterruptedException {

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
	public String demoOnly(String arg) throws MyRemoteException {
		// unimplemented, doesn't need this method for client side testing
		return null;
	}
}
