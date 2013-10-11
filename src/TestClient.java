/**
 * @author yinxu
 * 
 */
public class TestClient {

	public static void main(String[] args) throws Exception {
		RMINaming rmiNaming = new RMINaming();
		TestRemoteObjectInterface tro = null;
		TestArgRemoteObjectInterface argument = null;
		String result = null;
		// System.out.println("Ordinary Test");
		// for (int i = 0; i < 3; i++) {
		// try {
		// tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
		// result = tro.sayHello("Kesden");
		// } catch (MyRemoteException e) {
		// e.printException();
		// continue;
		// }
		// System.out.println(result);
		// }

		// System.out.println("Server Timeout Test");
		// for (int i = 0; i < 3; i++) {
		// try {
		// tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
		// result = tro.sayHello("Kesden");
		// } catch (MyRemoteException e) {
		// e.printException();
		// continue;
		// }
		// System.out.println(result);
		// Thread.sleep(3000);
		// }
		//
		System.out.println("Remote Object Arguments Test");
		try {
			tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
			argument = (TestArgRemoteObjectInterface) rmiNaming.lookup("testArgObj");
			System.out.println("stub is: " + argument);
			// System.out.println(argument.demoOnly("arg"));
			System.out.println(tro.remoteArgTest(argument));
		} catch (MyRemoteException e) {
			e.printException();
		}

		// System.out.println("Exception Test");
		// try {
		// tro = (TestRemoteObjectInterface) rmiNaming.lookup("testObj");
		// result = tro.sayHello("Exception");
		// System.out.println(result);
		// } catch (MyRemoteException e1) {
		// e1.printException();
		// }

	}
}
