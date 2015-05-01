package packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import entity.Player;
import attribstorage.PairedList;

public class UDPServer extends Thread {

	private DatagramSocket socket;

	public UDPServer() {
		try {
			this.socket = new DatagramSocket(UDPClient.port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private Map<InetAddress, ServerConnection> connections = new HashMap<InetAddress, ServerConnection>();

	public List<ServerConnection> connectionsList = new ArrayList<ServerConnection>();

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String messageUntrim = new String(packet.getData()).trim();
			String message = messageUntrim.split("|")[1];
			int id = Integer.parseInt(messageUntrim.split("|")[0]);
			if (!connections.containsKey(packet.getAddress())) {
				ServerConnection connection = new ServerConnection("",
						new Vector2f(), new Vector2f(), false,
						packet.getAddress(), packet.getPort());
				connections.put(packet.getAddress(), connection);
				connectionsList.add(connection);
			}
			Packet.parsePacketServer(id, message,
					connections.get(packet.getAddress()), this);
		}
	}

	public void sendData(int id, String data, InetAddress ip, int port) {
		byte[] dataToSend = (id + "|" + data).getBytes();
		DatagramPacket packet = new DatagramPacket(dataToSend,
				dataToSend.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(int id, String message){
		for(ServerConnection connect : connectionsList){
			sendData(id, message, connect.ip, connect.port);
		}
	}
}
