package render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {
	
	public static void startWindow(int width, int height){
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopWindow(){
		Display.destroy();
	}
	
	public static void updateWindow(){
		Display.sync(60);
		Display.update();
	}
	
	public static void setWindowTitle(String title){
		Display.setTitle(title);
	}
	
}
