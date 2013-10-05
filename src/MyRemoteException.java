/**
 * 
 */

/**
 * A customized remote exception
 * @author yinxu
 *
 */
public class MyRemoteException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1877000407999943333L;
	private String message;
	public MyRemoteException(String message) {
		this.message = message;
	}
	
	public void printException() {
		System.out.println("MyRemoteException: " + message);
	}
}
