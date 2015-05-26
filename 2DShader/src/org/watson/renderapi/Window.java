package org.watson.renderapi;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Window {
	
	public static void createWindow(String title, int width, int height) {
		try {
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glMatrixMode (GL_PROJECTION);
		glOrtho(0, width, 0, height, -1, 1);
        glMatrixMode (GL_MODELVIEW); 
	}
	
	public static void destroyWindow(){
		Display.destroy();
	}
	
	public static boolean shouldClose(){
		return Display.isCloseRequested();
	}
	
	public static void update(int fps){
		Display.update();
		Display.sync(fps);
	}
}
