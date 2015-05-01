package packet;

import java.net.InetAddress;

import org.lwjgl.util.vector.Vector2f;

public class ServerConnection {

	public String username;
	public Vector2f position;
	public Vector2f direction;
	public boolean isEnemy;
	public InetAddress ip;
	public int port;

	public ServerConnection(String username, Vector2f position,
			Vector2f direction, boolean isEnemy, InetAddress ip, int port) {
		this.username = username;
		this.position = position;
		this.direction = direction;
		this.isEnemy = isEnemy;
		this.ip = ip;
		this.port = port;
	}
	
	
	
}
