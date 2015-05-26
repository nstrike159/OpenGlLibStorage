package packet;

import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.SliderUI;

import jexxus.client.ClientConnection;
import jexxus.common.Connection;
import jexxus.common.Delivery;
import jexxus.server.Server;

import org.watson.renderapi.Readable;

/*
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
 * @throws IOException *//*
private void initVar() throws IOException{
System.out.println("Setting Variables");
multiS=new MulticastSocket(1987);
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
byte[] mByte=new byte[1024];
DatagramPacket p1=new DatagramPacket(mByte,mByte.length);
try {
mSockS.receive(p1);
System.out.println("Found");
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
byte[] mByte=new byte[1024];
DatagramPacket p1=new DatagramPacket(mByte,mByte.length);

try {
multiS.receive(p1);
System.out.println("Found");
} catch (IOException e) {
e.printStackTrace();
}
recvDatM(p1);
}
}
};

/**Sends DatagramPacket to MulticastSocket
 * @param toSend String to send to MulticastSocket received by all connected to MulticastSocket group*//*
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
/**Receives DatagramPacket from client*//*
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
}*/
public class UDPServer {
	private static ServerListener sl = new ServerListener();

	private static Server server = new Server(sl, 15652, 15652, false);
	private static List<Connection> conns = new ArrayList<Connection>();
	public static List<Readable> library = new ArrayList<Readable>();
	public static void main(String[] args) {
		UDPServer.start();
	}

	public static void start() {
		server.startServer();
	}

	public static boolean send(String dat) {
		boolean ret = true;
		for (Connection i : conns) {
			i.send(dat.getBytes(), Delivery.RELIABLE);
		}
		return ret;
	}
	
	public static boolean send(byte[] dat) {
		boolean ret = true;
		for (Connection i : conns) {
			i.send(dat, Delivery.RELIABLE);
		}
		return ret;
	}

	public static boolean addClient(Connection cl) {
		if (!conns.contains(cl)){
			conns.add(cl);
			return true;
		}
		return false;
	}
	public static boolean removeClient(Connection cl){
			boolean res=conns.remove(cl);
			return res;
	}
	public static void processData(String data){
		
	}
}
