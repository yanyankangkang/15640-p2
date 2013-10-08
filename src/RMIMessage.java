import java.io.Serializable;

/**
 * RMIMessage class encapsulates a method invocation, it represents the
 * communication cross the network.
 */

/**
 * @author yinxu
 * 
 */
public abstract class RMIMessage implements Serializable {

	private static final long serialVersionUID = 2131510874174393140L;

	protected RemoteObjectReference ror;
	protected boolean exceptionThrown = false;
	protected MyRemoteException exception = null;

	public RMIMessage() {
		
	}
	
	public boolean ifExceptionThrown() {
		return this.exceptionThrown;
	}

	public void setExceptionThrown(boolean exceptionThrown) {
		this.exceptionThrown = exceptionThrown;
	}

	public void setException(MyRemoteException exception) {
		this.exception = exception;
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
	
}
