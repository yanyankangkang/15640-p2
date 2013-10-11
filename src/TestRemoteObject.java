/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestRemoteObject implements TestRemoteObjectInterface{
	
	public String sayHello(String name) throws MyRemoteException {
		if (name.equals("Exception"))
			throw new MyRemoteException("Test Exception");
		return "Hello hehe " + name;
	}
	
	public String remoteArgTest(TestArgRemoteObjectInterface remoteArg) throws MyRemoteException {
		return remoteArg.demoOnly("arg");
	}

	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RemoteObjectReference getRemoteObjectReference() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
