package packet;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
	private static final int port=19998;
	private static InetAddress local;
	private static DatagramSocket UDPS;
	private static DatagramSocket socks;
	
	public UDPServer(){
		initVar();
	}
	//Create-> Send-> Bind
	/**Creates the DatagramSocket*/
	private void initVar(){
		try{
			local=InetAddress.getLocalHost();
		}catch(UnknownHostException e){
			System.out.println(e.getStackTrace());
		}finally{try {
			socks=new DatagramSocket(port,local);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		}
		recvSock();
	}
	/**Receives Socket Address*/
	private void recvSock(){
		socks.receive(p);
	}
}
