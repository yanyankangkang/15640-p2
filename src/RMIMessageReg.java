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
	private static final long serialVersionUID = -2293301188007820198L;

	// RMIMessage for server-registry communcation
	public RMIMessageReg(RemoteObjectReference ror) {
		super.setRor(ror);
	}
}
