/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestArgRemoteObject implements TestArgRemoteObjectInterface {
	public void demoOnly(String arg) {
		if (arg.equals("arg"))
			System.out.println("I am a remote object argument.");
		else if (arg.equals("ret"))
			System.out.println("I am a remote object return value.");
	}

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		
	}
}
