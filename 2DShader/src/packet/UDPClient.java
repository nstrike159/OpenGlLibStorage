package packet;

import java.io.IOException;

import jexxus.client.ClientConnection;
import jexxus.common.Connection;
import jexxus.common.ConnectionListener;
import jexxus.common.Delivery;
import jexxus.server.ServerConnection;
import interfaces.Book;

/*import java.io.IOException;
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
	private static final int port=1234;
	private static MulticastSocket mSock;
	private static DatagramSocket dSock;
	private static InetAddress MULTI_ADDR;
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
	
	private UDPClient() throws IOException,UnknownHostException{
		MULTI_ADDR = InetAddress.getByName("224.0.0.3");
		mSock=new MulticastSocket(port);
		//dSock=new DatagramSocket(port);
		mSock.connect(MULTI_ADDR, port);
		mSock.joinGroup(MULTI_ADDR);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					byte[] nByte=maxByte.clone();
					DatagramPacket p=new DatagramPacket(nByte,nByte.length);
					try {
						mSock.receive(p);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					recvUDP(p);
					}}
		}).start();
	}
	
	public UDPClient(String inets) throws IOException,UnknownHostException{
		MULTI_ADDR = InetAddress.getByName("224.0.0.3");
		mSock=new MulticastSocket(port);
		inet=InetAddress.getByName(inets);
		dSock=new DatagramSocket(port);
		mSock.connect(MULTI_ADDR, port);
		mSock.joinGroup(MULTI_ADDR);
		while(mSock.isConnected()){
			byte[] nByte=maxByte.clone();
			DatagramPacket p=new DatagramPacket(nByte,nByte.length);
			mSock.receive(p);
			recvUDP(p);
		}
	}
	
	public void sendData(String dat)throws UnknownHostException,IOException{
		byte[] data = dat.getBytes();
		//Set the data length to 1024
		byte[] data1=new byte[1024];
		for(int i=0;i<data.length;i++)
			data1[i]=data[i];
		
		DatagramPacket p=new DatagramPacket(data1,1024,MULTI_ADDR,port);
		mSock.send(p);
	}
	
	public void disconnect()
	{
		//mSock.close();
		mSock.disconnect();
	}
	public static void main(String[] args){
		try {
			UDPClient cl=new UDPClient();
			System.out.println("Sending Client Data");
			while(true){
			cl.sendData("testing client send");
			System.out.println("Sent Client Data");
			UDPClient.sleep(1000);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/
public class UDPClient {
	DebugConnectionListener DCL=new DebugConnectionListener();
	public static void main(String[] args){
		new UDPClient();
	}
	public UDPClient() {
		ClientConnection conn = new ClientConnection(new DebugConnectionListener(), "localhost", 15652, 15652, false);
		try {
			conn.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.send("Hello UDP".getBytes(), Delivery.UNRELIABLE);
	}
}
