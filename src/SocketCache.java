import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class SocketCache {
	
	public boolean containsSocket(String ipAddr, int port) {
		String socketKey = "/" + ipAddr + ":" + port;
		return cachePool.containsKey(socketKey);
	}
	
	public SocketInfo getSocketInfo(String ipAddr, int port) {
		String socketKey = "/" + ipAddr + ":" + port;
		SocketInfo socketInfo = cachePool.get(socketKey);
		
		// update the SocketInfo entry
		socketInfo.recentUsedTimestamp = System.currentTimeMillis();
		cachePool.put(socketKey, socketInfo);

		return socketInfo;
	}
	
	public void putSocket(Socket socket, ObjectInputStream input, ObjectOutputStream output) throws IOException {
		// if the cache pool reaches the max size, remove an entry first
		if (cachePool.size() > this.maxSockets )
			this.removeLeastRecentlyUsedSocket();
		
		// put the new socket into the cache pool
		String socketKey = socket.getInetAddress()+ ":" + socket.getPort();
		SocketInfo socketInfo = new SocketInfo(socket, input, output);
		socketInfo.recentUsedTimestamp = System.currentTimeMillis();
		cachePool.put(socketKey, socketInfo);
	}
	
	private void removeLeastRecentlyUsedSocket() throws IOException {
		String LRUKey = "";
		long LRUTimestamp = Long.MAX_VALUE;
		
		for (Map.Entry<String, SocketInfo> entry : cachePool.entrySet()) {
			if (entry.getValue().recentUsedTimestamp < LRUTimestamp) {
				LRUTimestamp = entry.getValue().recentUsedTimestamp;
				LRUKey = entry.getKey();
			}
		}
		
		cachePool.get(LRUKey).socket.close();
		cachePool.remove(LRUKey);
	}
	
	private HashMap<String, SocketInfo> cachePool = new HashMap<String, SocketInfo>();
	private final int maxSockets = 10;
	
}
