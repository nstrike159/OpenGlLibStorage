package packet;

import org.lwjgl.util.vector.Vector4f;

public class Packet {

	public static enum PacketTypes {
		INVALID(-1), PING(0), LOGIN(1), DISCONNECT(2), SETPOS(3), CHAT(4);
		public final int id;

		PacketTypes(int id) {
			this.id = id;
		}

	}

	public static void parsePacketServer(int id, String p,
			ServerConnection connection, UDPServer server) {
		switch (getPacketType(id)) {
		case PING:
			for (int i = 0; i < server.connectionsList.size(); i++) {
				ServerConnection connect = server.connectionsList.get(i);
				if (connect == connection)
					continue;
				server.sendData(3, i + ";" + connect.username + ";" + connect.position.x + ";"
						+ connect.position.y + ";" + connect.direction.x + ";"
						+ connect.direction.y, connection.ip, connection.port);
			}
			break;
		case CHAT:
		case LOGIN:
			connection.username = p;
			break;
		case SETPOS:
			connection.position.x = Float.parseFloat(p.split(";")[0]);
			connection.position.y = Float.parseFloat(p.split(";")[1]);
			connection.direction.x = Float.parseFloat(p.split(";")[2]);
			connection.direction.y = Float.parseFloat(p.split(";")[3]);
			break;
		case DISCONNECT:
			server.connectionsList.remove(connection);
		default:
			break;
		}
	}

	public static void parsePacketClient(int id, String p, UDPClient client) {
		switch (getPacketType(id)) {
		case PING:
			break;
		case SETPOS:
			int playerId = Integer.parseInt(p.split(";")[0]);
			if (client.players.size() <= playerId){
				client.players.set(
						playerId,
						new Vector4f(Float.parseFloat(p.split(";")[2]), Float
								.parseFloat(p.split(";")[3]), Float
								.parseFloat(p.split(";")[4]), Float
								.parseFloat(p.split(";")[5])));
			}
			else {
				client.players.get(playerId).x = Float
						.parseFloat(p.split(";")[1]);
				client.players.get(playerId).y = Float
						.parseFloat(p.split(";")[2]);
				client.players.get(playerId).z = Float
						.parseFloat(p.split(";")[3]);
				client.players.get(playerId).w = Float
						.parseFloat(p.split(";")[4]);
			}
			client.usernames.set(playerId, p.split(";")[1]);
			break;
			
		}
	}

	private static PacketTypes getPacketType(int id) {
		for (PacketTypes p : PacketTypes.values())
			if (p.id == id)
				return p;
		return PacketTypes.INVALID;
	}

}
