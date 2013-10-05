/**
 * 
 */

/**
 * @author yinxu
 * 
 */
public class RMIMessageReg extends RMIMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3232537102393240370L;

	// RMIMessage for server-registry communcation
	public RMIMessageReg(RemoteObjectReference ror) {
		super.setRor(ror);
	}
}
