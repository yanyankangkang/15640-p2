import java.io.Serializable;

/**
 * 
 */

/**
 * @author yinxu
 *
 */
// no need to extends RMIMessage ?
public class RMIMessageLookup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8776322466025700255L;

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
