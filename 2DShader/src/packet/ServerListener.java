package packet;

import jexxus.common.Connection;
import jexxus.common.ConnectionListener;
import jexxus.server.ServerConnection;

public class ServerListener implements ConnectionListener{
	@Override
	public void connectionBroken(Connection broken, boolean forced) {
		UDPServer.removeClient(broken);
	}

	@Override
	public void receive(byte[] data, Connection from) {
		UDPServer.addClient(from);	
	}

	@Override
	public void clientConnected(ServerConnection conn) {}
}
