package packet;

import interfaces.Book;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import entity.AABB;
import entity.Player;

public class UDPClient extends Thread{
	private static final int port=19998;
	private static MulticastSocket mSock;
	private static byte[] maxByte=new byte[1024];
	private static InetAddress inet;
	public Player[] players;
	
	public AABB[] map;
	
	@Override
	public void run() {
		
	}
	
	private void recvUDP(DatagramPacket pa){
		String data = new String(pa.getData()).trim();
		System.out.println(data);
	}
	public String[] chat;
	
	public List<Book> books = new ArrayList<Book>();
	
	public static UDPClient debugClient() throws IOException{
		UDPClient mSock =  new UDPClient();
		//Set debug info
		return mSock;
	}
	
	private UDPClient(){}
	
	public UDPClient(String inets) throws IOException{
		mSock=new MulticastSocket(port);
		inet=InetAddress.getByName(inets);
		mSock.joinGroup(inet);
		while(mSock.isConnected()){
			byte[] nByte=maxByte.clone();
			DatagramPacket p=new DatagramPacket(nByte,nByte.length);
			mSock.receive(p);
			recvUDP(p);
		}
	}
	
	public void sendData(String dat)throws UnknownHostException,IOException{
		byte[] data = dat.getBytes();
		DatagramPacket p=new DatagramPacket(data,data.length,inet,port);
		mSock.send(p);
	}
	
	public void disconnect()
	{
		//mSock.close();
		mSock.disconnect();
	}
	
}
