import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 */

/**
 * @author remonx
 *
 */
public class SocketInfo {
		
		public Socket socket;
		public long recentUsedTimestamp;
		public ObjectOutputStream output;
		public ObjectInputStream input;
		
		public SocketInfo() {}
		public SocketInfo(Socket s, ObjectInputStream in, ObjectOutputStream out) {
			socket = s;
			output = out;
			input = in;
		}
}