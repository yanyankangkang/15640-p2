import java.net.UnknownHostException;

/**
 * @author yinxu
 * 
 */
public class TestClient {

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		RMINaming rmiNaming = new RMINaming();
		TestRemoteObjectInterface tro = null;
		String result = null;
		for (int i = 0; i < 20; i++) {
			try {
				tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
				result = tro.sayHello("Hitler");
			} catch (MyRemoteException e) {
				e.printException();
				continue;
			}
			System.out.println(result);
			Thread.sleep(3000);
		}

		try {
			tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
			result = tro.sayHello("Exception");
			System.out.println(result);
		} catch (MyRemoteException e1) {
			e1.printException();
		}

	}
}
