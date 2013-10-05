import java.io.Serializable;

/**
 * RMIMessage class encapsulates a method invocation, it represents the
 * communication cross the network.
 */

/**
 * @author yinxu
 *
 */
public class RMIMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2131510874174393140L;
	
	private String methodName;
	private RemoteObjectReference ror;
	//temporary arg format using String
	private String arg;
	private Object ret;
	
	public RMIMessage(){}
	public RMIMessage(String methodName, String arg) {
		this.methodName = methodName;
		this.arg = arg;
	}
	
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the ror
	 */
	public RemoteObjectReference getRor() {
		return ror;
	}
	/**
	 * @param ror the ror to set
	 */
	public void setRor(RemoteObjectReference ror) {
		this.ror = ror;
	}
	/**
	 * @return the arg
	 */
	public String getArg() {
		return arg;
	}
	/**
	 * @param arg the arg to set
	 */
	public void setArg(String arg) {
		this.arg = arg;
	}
	public Object getRet() {
		return ret;
	}
	public void setRet(Object ret) {
		this.ret = ret;
	}
	
	
	
}
