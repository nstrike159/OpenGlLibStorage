package packet;

import jexxus.common.Connection;
import jexxus.common.ConnectionListener;
import jexxus.server.ServerConnection;

public class ClientListener implements ConnectionListener{
	@Override
	public void connectionBroken(Connection broken, boolean forced) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(byte[] data, Connection from) {
		System.out.println(new String(data));
		
	}

	@Override
	public void clientConnected(ServerConnection conn) {
		// TODO Auto-generated method stub
		
	}
}
