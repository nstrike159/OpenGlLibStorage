package packet;

import org.lwjgl.util.vector.Vector3f;
import org.watson.renderapi.Book;
import org.watson.renderapi.Readable;

import static org.watson.lang.NewLang.*;

public class DataParser {

	public static byte[] getSendableVec(Vector3f vector) {
		return ("v" + vector.x + "," + vector.y + "," + vector.z).getBytes();
	}

	public static Vector3f getSentVec(byte[] sent) {
		String sent0 = new String(sent).trim().substring(1);
		return new Vector3f(Float.parseFloat(sent0.split(",")[0]),
				Float.parseFloat(sent0.split(",")[1]), Float.parseFloat(sent0
						.split(",")[2]));
	}

	public static byte[] getSendableInteger(Integer integer) {
		return ("i" + integer.toString()).getBytes();
	}

	public static Integer getSentInteger(byte[] sent) {
		String sent0 = new String(sent).trim().substring(1);
		return Integer.parseInt(sent0);
	}

	public static byte[] getSendableString(String string) {
		return ("s" + string).getBytes();
	}

	public static String getSentString(byte[] sent) {
		String sent0 = new String(sent).trim().substring(1);
		return sent0;
	}

	public static byte[] getSendableFloat(Float floating) {
		return ("f" + floating.toString()).getBytes();
	}

	public static Float getSentFloat(byte[] sent) {
		String sent0 = new String(sent).trim().substring(1);
		return Float.parseFloat(sent0);
	}

	public static Object getSentData(byte[] sent) {
		String id = new String(sent).trim().substring(0, 1);
		switch (id) {
		case "f":
			return getSentFloat(sent);
		case "i":
			return getSentInteger(sent);
		case "v":
			return getSentVec(sent);
		case "s":
			return getSentString(sent);
		case "b":
			return getSentBook(sent);
		default:
			return null;
		}
	}

	public static char getId(byte[] sent) {
		return new String(sent).trim().charAt(0);
	}

	public static Readable getSentBook(byte[] sent) {
		String sent0 = new String(sent).trim().substring(1);
		String[] sent1 = sent0.split("~");
		try{
		return (Readable)(
				new Book(
						new Vector3f(
								Float.parseFloat(
										sent1[0]
												.split(",")[0]),
				Float.parseFloat(sent1[0]
						.split(",")[1]),
				Float.parseFloat(sent1[0]
						.split(",")[2]))
						, sent1[1], 
						sent1[2],
				Integer.parseInt(sent1[3]), 
				Integer.parseInt(sent1[4]) != 0));
		}catch(Exception e){
			e.printStackTrace();
			for(String s : sent1)
			System.err.println(s);
			exit(-1);
			return null;
		}
	}
	
	public static byte[] getSendableBook(Readable read){
		String toSend = "b" + read.getColor().x + "," + read.getColor().y + "," + read.getColor().z + "~" + read.getTitle() + "~" + read.getContents() + "~" + read.getID() + "~" + (read.isThere() ? 1 : 0);
		return toSend.getBytes();
	}

}
