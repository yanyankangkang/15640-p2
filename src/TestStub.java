/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestStub implements TestRemoteObjectInterface{
	
	
	public String sayHello(String name) throws MyRemoteException {
		//form a message object
		RMIMessage message = new RMIMessage("sayHello", name);
		
		RMIMessage reply = CommModuleClient.sendRequest(message);
		return (String)reply.getRet();
	}
	
}
