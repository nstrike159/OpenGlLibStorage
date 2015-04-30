package loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import render.Light;
import render.Tri;

public class MapLoader {

	public static void getMapTris(List<Tri> toAddTo) throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("map"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("t")){
		    		float[] data = parseData(line);
		    		toAddTo.add(new Tri(new Vector2f(data[0], data[1]), new Vector2f(data[2], data[3]), new Vector2f(data[4], data[5])));
		    	}
		    }
		}
	}

	public static void getMapLight(List<Light> toAddTo) throws FileNotFoundException, IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("map"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("t")){
		    		float[] data = parseData(line);
		    		toAddTo.add(new Light(new Vector2f(data[0], data[1]), data[2], data[3], data[4]));
		    	}
		    }
		}
	}
	
	private static float[] parseData(String s){
		String dataString = s.substring(s.indexOf('{'), s.indexOf('}'));
		String[] split = dataString.split(",");
		float[] data = new float[split.length];
		System.out.println(dataString);
		return new float[]{0.0f};
	}
}