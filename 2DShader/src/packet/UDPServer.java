package packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
	private static final int port=1234;
	private static InetAddress local=null;
	private static InetAddress MULTI_ADDR=null;
	private static MulticastSocket multiS=null;
	private static DatagramSocket mSockS=null;
	private static byte[] maxByte=new byte[1024];
	public UDPServer(){
		try {
			initVar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**Creates the MulticastSocket
	 * @throws IOException */
	private void initVar() throws IOException{
		System.out.println("Setting Variables");
		multiS=new MulticastSocket(port);
		try{
			MULTI_ADDR = InetAddress.getByName("224.0.0.3");
			local=InetAddress.getLocalHost();
			mSockS=new DatagramSocket(port);
		}catch(UnknownHostException e){
			System.out.println(e.getStackTrace());
		}
		multiS.connect(MULTI_ADDR, port);
		multiS.joinGroup(MULTI_ADDR);
		new Thread(multiRecv).start();
		new Thread(UDPRecv).start();
		System.out.println("Set Variables");
	}
	
	private void recvDatM(DatagramPacket p) {
		System.out.println("Data recieved on multicast");
		String data = new String(p.getData()).trim();
		String id = data.substring(0,1);
		String player = data.substring(1,2);
		String finalData = data.substring(2);
		System.out.println(data);
	}
	
	private Runnable UDPRecv = new Runnable() {
		
		@Override
		public void run() {
			while(true){
				byte[] mByte=maxByte.clone();
				DatagramPacket p1=new DatagramPacket(mByte, mByte.length);
				try {
					mSockS.receive(p1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				recvDat(p1);
			}
		}
	};
	
	private Runnable multiRecv = new Runnable() {
		
		@Override
		public void run() {
			while(true){
				byte[] mByte=maxByte.clone();
				DatagramPacket p1=new DatagramPacket(mByte, mByte.length);
				try {
					multiS.receive(p1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				recvDatM(p1);
			}
		}
	};
	
	/**Sends DatagramPacket to MulticastSocket
	 * @param toSend String to send to MulticastSocket received by all connected to MulticastSocket group*/
	@SuppressWarnings("unused")
	private boolean sendData(String toSend) {
		byte[] buf = toSend.getBytes();
		DatagramPacket p1 = new DatagramPacket(buf, 1025);
		if(multiS.isConnected())
			try {
				multiS.send(p1);
				return true;
			}catch(IOException e) {
				e.printStackTrace();
			}
		return false;
	}
	/**Receives DatagramPacket from client*/
	private void recvDat(DatagramPacket p){
		String data = new String(p.getData()).trim();
		String id = data.substring(0,1);
		String player = data.substring(1,2);
		String finalData = data.substring(2);
		System.out.println(data);
	}
	public static void main(String[] args){
		new UDPServer();
	}
}
