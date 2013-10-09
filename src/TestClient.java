import java.net.UnknownHostException;

/**
 * @author yinxu
 *
 */
public class TestClient {
	
	public static void main(String[] args) throws UnknownHostException {
		RMINaming rmiNaming = new RMINaming();
		TestRemoteObjectInterface tro = null;
		try {
				for (int i = 0; i < 10; i++) {
					tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
					String result = tro.sayHello("Hitler");
					System.out.println(result);
				}
				
				
//				result = tro.sayHello("Exception");
//				System.out.println(result);
		} catch (MyRemoteException e1) {
			e1.printException();
		}
		
	}
}
