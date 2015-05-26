package packet;

import org.watson.renderapi.SimpleRender;

import jexxus.common.Connection;
import jexxus.common.ConnectionListener;
import jexxus.common.Delivery;
import jexxus.server.ServerConnection;

import org.watson.renderapi.Readable;

import static org.watson.lang.NewLang.*;

public class ClientListener implements ConnectionListener{
	@Override
	public void connectionBroken(Connection broken, boolean forced) {
		exit(0);
	}

	@Override
	public void receive(byte[] data, Connection from) {
		Object parsed = DataParser.getSentData(data);
		if(parsed instanceof Readable){
			SimpleRender.addBook((Readable) parsed);
		}else if(parsed instanceof Integer){
			SimpleRender.removeBook((Integer) parsed);
		}
		
	}

	@Override
	public void clientConnected(ServerConnection conn) {
		
	}
}
