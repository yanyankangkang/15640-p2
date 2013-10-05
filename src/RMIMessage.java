import java.io.Serializable;
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
public class RMIMessage implements Serializable {

	private static final long serialVersionUID = 2131510874174393140L;

	// variables need to give to server
	private RemoteObjectReference ror;
	private Object callee;
	private String methodName;
	private Object[] args;
	private Class[] argsTypes;
	

	// variables need to get back from server
	private Object returnValue;
	private boolean exceptionThrown = false;
	private Exception exception = null;

	public RMIMessage() {
		
	}
	public RMIMessage(Object callee, String methodName, Object[] args) {
		this.callee = callee;
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
	

	public Object invokeMethod() {
		Method method = null;
		try {
			method = this.callee.getClass().getMethod(methodName, argsTypes);
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
			this.returnValue = method.invoke(this.callee, this.args);
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
			this.exceptionThrown = true;
			this.exception = e;
			return null;
		}

		return this.returnValue;
	}

	public boolean ifExceptionThrown() {
		return this.exceptionThrown;
	}

	public Exception getException() {
		return this.exception;
	}

	public RemoteObjectReference getRor() {
		return ror;
	}

	public void setRor(RemoteObjectReference ror) {
		this.ror = ror;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	public Object getCallee() {
		return callee;
	}
	public void setCallee(Object callee) {
		this.callee = callee;
	}
	
}
