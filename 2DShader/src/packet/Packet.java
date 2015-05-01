package packet;

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
			for (ServerConnection connect : server.connectionsList) {
				if (connect == connection)
					continue;
				server.sendData(3, "" + connect.position.x + ";"
						+ connect.position.y + ";" + connect.direction.x + ";"
						+ connect.direction.y, connection.ip, connection.port);
			}
			break;
		case CHAT:
		case LOGIN:
			server.sendDataToAllClients(1, p);
			break;
		case SETPOS:
			connection.position.x = Float.parseFloat(p.split(";")[0]);
			connection.position.y = Float.parseFloat(p.split(";")[1]);
			connection.direction.x = Float.parseFloat(p.split(";")[2]);
			connection.direction.y = Float.parseFloat(p.split(";")[3]);
			break;
		default:
			break;
		}
	}

	public static void parsePacketClient(int id, String p, UDPClient client) {
		switch (getPacketType(id)) {
		case PING:
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
