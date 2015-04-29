package loader;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import render.Light;
import render.Tri;

public class MapLoader {

	public static void getMapTris(List<Tri> toAddTo){
		toAddTo.add(new Tri(new Vector2f(20,20), new Vector2f(50,20), new Vector2f(20,50)));
	}

	public static void getMapLight(List<Light> toAddTo) {
		toAddTo.add(new Light(new Vector2f(50, 50), 10, 0, 0));
	}
}
