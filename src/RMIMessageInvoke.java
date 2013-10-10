import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RMIMessage class encapsulates a method invocation, it represents the
 * communication cross the network.
 */

/**
 * @author yinxu
 * 
 */
public class RMIMessageInvoke extends RMIMessage {

	private static final long serialVersionUID = -367053433960840040L;
	// variables need to give to server
	private String methodName;
	private Object[] args;
	private Class<?>[] argsTypes;

	// variables need to get back from server
	private Object returnValue;

	public RMIMessageInvoke() {

	}

	public RMIMessageInvoke(String methodName, Object[] args) {
		this.methodName = methodName;
		this.args = args;

		if (args != null) {
			int argsLength = args.length;
			this.argsTypes = new Class[argsLength];
			for (int i = 0; i < argsLength; i++) {
				this.argsTypes[i] = args[i].getClass();
			}
		}
	}

	public Object invokeMethod(Object callee) throws MyRemoteException {
		Method method = null;
		try {
			method = callee.getClass().getMethod(methodName, argsTypes);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		try {
			this.returnValue = method.invoke(callee, this.args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
//			this.exceptionThrown = true;
//			this.exception = new MyRemoteException("InvocationTargetException");
//			return null;
			throw new MyRemoteException(e.getTargetException().getMessage());
		}

		return this.returnValue;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

}
