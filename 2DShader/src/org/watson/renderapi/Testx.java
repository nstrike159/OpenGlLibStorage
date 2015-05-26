package org.watson.renderapi;

import static org.watson.lang.NewLang.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import packet.DataParser;
import packet.UDPClient;
import packet.UDPServer;
import static org.lwjgl.opengl.GL11.*;

public class Testx {
	
	public static void main(String[] args){
		SimpleRender.start();
		String s = "";
		glColor3f(1, 1, 1);
		while(!Window.shouldClose() && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			glClear(GL_COLOR_BUFFER_BIT);
			s = Util.addString(s);
			SimpleText.drawString("Enter IP Address", 20, Display.getHeight()-20);
			SimpleText.drawString(s, 20, Display.getHeight()-80);
			Window.update(60);
		}if(Window.shouldClose()){
			Window.destroyWindow();
			exit(0);
		}
		if (!UDPClient.connect(s)){
			UDPServer.start();
			UDPClient.connect("localhost");
		};
		UDPClient.send(DataParser.getSendableString("Hello"));
		while(!Window.shouldClose()){
			SimpleRender.render();
		}
		Window.destroyWindow();
		exit(0);
	}
	
}
