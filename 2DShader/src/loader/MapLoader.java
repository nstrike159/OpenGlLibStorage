package loader;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;

import render.Light;
import render.Tri;

public class MapLoader {
	
	private static List<Integer> textures = new ArrayList<Integer>();
	
	public static void setUp() throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("map"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("i")){
		    		int tex;
		    		String filename = line.substring(line.indexOf('{')+1, line.indexOf('}'));
		    		BufferedImage image = ImageIO.read(new File(filename ));
		    		tex = glGenTextures();
		    		int[] pixels = new int[image.getWidth() * image.getHeight()];
		            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB
		            
		            for(int y = 0; y < image.getHeight(); y++){
		                for(int x = 0; x < image.getWidth(); x++){
		                    int pixel = pixels[y * image.getWidth() + x];
		                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
		                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
		                    buffer.put((byte) (pixel & 0xFF));               // Blue component
		                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
		                }
		            }

		            buffer.flip();
		            
		            glBindTexture(GL_TEXTURE_2D, tex);
		            
		            glGenerateMipmap(GL_TEXTURE_2D);
		            
		            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		            
		            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		            
		            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -0.4f);
		            
		            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		            glBindTexture(GL_TEXTURE_2D, 0);
		            textures.add(tex);
		    	}
		    }
		}
	}

	public static void getMapTris(List<Tri> toAddTo) throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("map"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("t")){
		    		float[] data = parseData(line);
		    		toAddTo.add(new Tri(new Vector2f(data[0], data[1]), new Vector2f(data[2], data[3]), new Vector2f(data[4], data[5]),
		    				new Vector2f(data[6], data[7]), new Vector2f(data[8], data[9]), new Vector2f(data[10], data[11]), textures.get((int) data[12])));
		    	}
		    }
		}
	}

	public static void getMapLight(List<Light> toAddTo) throws FileNotFoundException, IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("map"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("l")){
		    		float[] data = parseData(line);
		    		toAddTo.add(new Light(new Vector2f(data[0], data[1]), data[2], data[3], data[4]));
		    	}
		    }
		}
	}
	
	private static float[] parseData(String s){
		String dataString = s.substring(s.indexOf('{')+1, s.indexOf('}'));
		String[] split = dataString.split(",");
		float[] data = new float[split.length];
		for(int i = 0; i < split.length; i++){
			data[i] = Float.parseFloat(split[i]);
		}
		return data;
	}
}