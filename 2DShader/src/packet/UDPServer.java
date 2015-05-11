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
	private static final int port=19998;
	private static InetAddress local;
	private static InetAddress MULTI_ADDR;
	private static MulticastSocket multiS;
	private static DatagramSocket mSockS;
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
		multiS=new MulticastSocket();
		try{
			MULTI_ADDR = InetAddress.getByName("224.0.0.3");
			local=InetAddress.getLocalHost();
		}catch(UnknownHostException e){
			System.out.println(e.getStackTrace());
		}finally{try {
			mSockS=new DatagramSocket(port);
		}catch(SocketException e) {
			e.printStackTrace();
		}
		}
		multiRecv();
		UDPRecv();
		multiS.joinGroup(MULTI_ADDR);
	}
	private void UDPRecv() throws IOException{
		while(mSockS.isConnected()){
			byte[] mByte=maxByte.clone();
			DatagramPacket p1=new DatagramPacket(mByte, mByte.length);
			mSockS.receive(p1);
			recvDatM(p1);
		}
	}
	
	private void recvDatM(DatagramPacket p) {
		String data = new String(p.getData()).trim();
		String id = data.substring(0,1);
		String player = data.substring(1,2);
		String finalData = data.substring(2);
		
	}
	private void multiRecv() throws IOException{
		while(multiS.isConnected()){
			byte[] mByte=maxByte.clone();
			DatagramPacket p1=new DatagramPacket(mByte, mByte.length);
			multiS.receive(p1);
			recvDat(p1);
		}
	}
	/**Sends DatagramPacket to MulticastSocket
	 * @param toSend String to send to MulticastSocket received by all connected to MulticastSocket group*/
	@SuppressWarnings("unused")
	private boolean sendData(String toSend) {
		byte[] buf = toSend.getBytes();
		DatagramPacket p1 = new DatagramPacket(buf, 1024);
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
		
	}
}
