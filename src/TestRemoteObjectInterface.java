

/**
 * A simple test remote object interface
 * 
 * @author yinxu
 *
 */
public interface TestRemoteObjectInterface extends MyRemote{
	public String sayHello(String name) throws MyRemoteException;
}
