/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestClient {

	public static void main(String[] args) {
		// lookup from registry, hardcoding
		RemoteObjectReference ror = CommModuleClient.lookup("128.2.246.44",
				1099, new RMIMessageLookup("testObj"));
		if (ror == null) {
			System.out.println("lookup failed");
			return;
		}
		//RemoteObjectReference ror = msg.getRor();
		TestRemoteObjectInterface tro = (TestRemoteObjectInterface)ror.localise();
		try {
			String result = tro.sayHello("Hitler");
			System.out.println(result);
		} catch (MyRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("remote execution failed");
		}
	}
}
