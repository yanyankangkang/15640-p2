
/**
 * 
 */

/**
 * @author yinxu
 *
 */
public class RMIMessageLookup extends RMIMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4241134599099905418L;
	private String remoteObjName;
	
	public RMIMessageLookup(String name) {
		remoteObjName = name;
	}

	public String getRemoteObjName() {
		return remoteObjName;
	}

	public void setRemoteObjName(String remoteObjName) {
		this.remoteObjName = remoteObjName;
	}
	
}
