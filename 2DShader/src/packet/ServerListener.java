package packet;

import java.util.ArrayList;
import java.util.List;

import jexxus.common.Connection;
import jexxus.common.ConnectionListener;
import jexxus.common.Delivery;
import jexxus.server.ServerConnection;

import org.watson.renderapi.Readable;

public class ServerListener implements ConnectionListener {

	private static int id = 0;

	private static List<Readable> books = new ArrayList<Readable>();

	@Override
	public void connectionBroken(Connection broken, boolean forced) {
		UDPServer.removeClient(broken);
	}

	@Override
	public void receive(byte[] data, Connection from) {
		UDPServer.addClient(from);
		Object parsed = DataParser.getSentData(data);
		if (parsed instanceof Readable) {
			Readable parsedR = (Readable) parsed;
			parsedR.setID(id);
			byte[] data0 = DataParser.getSendableBook(parsedR);
			UDPServer.send(data0);
			books.add(parsedR);
			id++;
		} else if (parsed instanceof Integer) {
			UDPServer.send(data);
		} else if (parsed instanceof String) {
			for (Readable read : books) {
				from.send(DataParser.getSendableBook(read), Delivery.RELIABLE);
			}
		}
	}

	@Override
	public void clientConnected(ServerConnection conn) {
	}
}
