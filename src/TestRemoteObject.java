/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestRemoteObject implements TestRemoteObjectInterface{
	public String sayHello(String name) throws MyRemoteException {
		return "Hello" + name;
	}
}
