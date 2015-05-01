package packet;

import interfaces.Book;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

public class UDPClient extends Thread{
	
	public static final int port = 1802;
	
	public String[] chat = new String[4];
	
	public void addChat(String s){
		chat[3] = chat[2];
		chat[2] = chat[1];
		chat[1] = chat[0];
		chat[0] = s;
	}
	
	public List<Vector4f> players = new ArrayList<Vector4f>();
	public List<String> usernames = new ArrayList<String>();
	
	public List<Book> books = new ArrayList<Book>();
	
	private InetAddress ip;
	private DatagramSocket socket;
	
	public static UDPClient debugClient(){
		return new UDPClient();
	}
	
	private UDPClient(){}
	
	public UDPClient(String ip){
		try {
			this.ip = InetAddress.getByName(ip);
			this.socket = new DatagramSocket();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
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
			Packet.parsePacketClient(id, message, this);
		}
	}
	
	public void sendData(int id, String data){
		byte[] dataToSend = (id + "|" + data).getBytes();
		DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
