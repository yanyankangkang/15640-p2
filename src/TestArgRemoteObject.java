/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class TestArgRemoteObject implements TestArgRemoteObjectInterface {
	public String demoOnly(String arg) {
		if (arg.equals("arg"))
			//System.out.println("I am a remote object argument.");
			return "I am a remote object argument";
		else if (arg.equals("ret"))
			//System.out.println("I am a remote object return value.");
			return "I am a remote object return value.";
		
		return null;
	}

	@Override
	public void setRemoteObjectReference(RemoteObjectReference ror) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RemoteObjectReference getRemoteObjectReference() {
		// TODO Auto-generated method stub
		return null;
	}
}
