import java.net.UnknownHostException;

/**
 * @author yinxu
 *
 */
public class TestClient {
	
	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		RMINaming rmiNaming = new RMINaming();
		TestRemoteObjectInterface tro = null;
		try {
				for (int i = 0; i < 3; i++) {
					tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
					String result = tro.sayHello("Hitler");
					System.out.println(result);
					Thread.sleep(5 * 1000);
				}
				
				
				
				String result = tro.sayHello("Exception");
				System.out.println(result);
		} catch (MyRemoteException e1) {
			e1.printException();
		}
		
	}
}
