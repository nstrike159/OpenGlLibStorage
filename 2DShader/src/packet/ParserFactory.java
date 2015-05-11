package packet;

import org.lwjgl.util.vector.Vector3f;

import static java.lang.Float.parseFloat;

public class ParserFactory {

	public static Vector3f getVector3f(String s) {
		String[] values = s.split(",");
		return new Vector3f(parseFloat(values[0]),
				parseFloat(values[1]), parseFloat(values[2]));
	}

}
