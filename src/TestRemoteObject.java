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

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		
	}
	
}
