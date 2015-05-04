package packet;

import interfaces.Book;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import entity.AABB;
import entity.Player;

public class UDPClient extends Thread{
	private static final int port=19998;
	private static DatagramSocket UDP;
	public Player[] players;
	
	public AABB[] map;
	
	@Override
	public void run() {
		
	}
	
	private void recvUDP(){
		
	}
	public String[] chat;
	
	public List<Book> books = new ArrayList<Book>();
	
	public static UDPClient debugClient(){
		UDPClient client =  new UDPClient();
		//Set debug info
		return client;
	}
	
	private UDPClient(){
		
	}
	
	public UDPClient(String inet) throws SocketException{
		UDP=new DatagramSocket(port);
		
	}
	
}
