/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestRemoteObject implements TestRemoteObjectInterface{
	
	public String sayHello(String name) throws MyRemoteException {
		return "Hello " + name;
	}

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		
	}
}
