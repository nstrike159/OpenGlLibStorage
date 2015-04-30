package main;

import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loader.MapLoader;
import loader.ShaderLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import render.Block;
import render.Light;
import render.Tri;
import render.Window;

public class Main {

	private static final int WIDTH = 800, HEIGHT = 600;
	
	private static final String frag = "uniform vec2 lightLocation;\nuniform vec3 lightColor;\nuniform float screenHeight;\nvoid main() {\n	float distance = length(lightLocation - gl_FragCoord.xy);\n	float attenuation = 1.0 / distance;\n	vec4 color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3)) * vec4(lightColor, 1);\ngl_FragColor = color;\n}\n";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, LWJGLException {
		Window.startWindow(WIDTH, HEIGHT);
		MapLoader.setUp();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_STENCIL_TEST);
		glClearColor(0, 0, 0, 0);
		menu();
	}

	public static void menu() throws LWJGLException {
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			if (isKeyDown(KEY_RETURN)) {
				arena();
				break;
			}
			Window.updateWindow();
		}
		Window.stopWindow();
	}

	public static void arena() throws LWJGLException {
		int shader = ShaderLoader.loadFragmentOnlySrc(frag);
		List<Light> lights = new ArrayList<Light>();
		List<Tri> tris = new ArrayList<>();
		try {
			MapLoader.getMapTris(tris);
			MapLoader.getMapLight(lights);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Block> players = new ArrayList<Block>();
		Vector2f player = new Vector2f(100, 100);
		float red = 3.5f,green = 0.0f,blue = 0.0f;
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			glUseProgram(shader);
			if(isKeyDown(KEY_UP))
				player.y--;
			if(isKeyDown(KEY_DOWN))
				player.y++;
			if(isKeyDown(KEY_LEFT))
				player.x--;
			if(isKeyDown(KEY_RIGHT))
				player.x++;
			for(Tri block : tris){
				block.render();
			}
			{
				glColorMask(false, false, false, false);
				glStencilFunc(GL_ALWAYS, 1, 1);
				glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

				for (Tri block : tris) {
					Vector2f[] vertices = block.getVertices();
					for (int i = 0; i < vertices.length; i++) {
						Vector2f currentVertex = vertices[i];
						Vector2f nextVertex = vertices[(i + 1) % vertices.length];
						Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
						Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
						Vector2f lightToCurrent = Vector2f.sub(currentVertex, player, null);
						if (Vector2f.dot(normal, lightToCurrent) > 0) {
							Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, player, null).scale(800), null);
							Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, player, null).scale(800), null);
							glBegin(GL_QUADS); {
								glVertex2f(currentVertex.getX(), currentVertex.getY());
								glVertex2f(point1.getX(), point1.getY());
								glVertex2f(point2.getX(), point2.getY());
								glVertex2f(nextVertex.getX(), nextVertex.getY());
							} glEnd();
						}
					}
				}
				
				for (Block block : players) {
					Vector2f[] vertices = block.getVertices();
					for (int i = 0; i < vertices.length; i++) {
						Vector2f currentVertex = vertices[i];
						Vector2f nextVertex = vertices[(i + 1) % vertices.length];
						Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
						Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
						Vector2f lightToCurrent = Vector2f.sub(currentVertex, player, null);
						if (Vector2f.dot(normal, lightToCurrent) > 0) {
							Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, player, null).scale(800), null);
							Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, player, null).scale(800), null);
							glBegin(GL_QUADS); {
								glVertex2f(currentVertex.getX(), currentVertex.getY());
								glVertex2f(point1.getX(), point1.getY());
								glVertex2f(point2.getX(), point2.getY());
								glVertex2f(nextVertex.getX(), nextVertex.getY());
							} glEnd();
						}
					}
				}

				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				glStencilFunc(GL_EQUAL, 0, 1);
				glColorMask(true, true, true, true);

				glUseProgram(shader);
				glUniform2f(glGetUniformLocation(shader, "lightLocation"), player.getX(), HEIGHT - player.getY());
				glUniform3f(glGetUniformLocation(shader, "lightColor"), red, green, blue);
				glEnable(GL_BLEND);
				glBlendFunc(GL_ONE, GL_ONE);

				glBegin(GL_QUADS); {
					glVertex2f(0, 0);
					glVertex2f(0, HEIGHT);
					glVertex2f(WIDTH, HEIGHT);
					glVertex2f(WIDTH, 0);
				} glEnd();

				glDisable(GL_BLEND);
				glUseProgram(0);
				glClear(GL_STENCIL_BUFFER_BIT);
			}
			for (Light light : lights) {
				glColorMask(false, false, false, false);
				glStencilFunc(GL_ALWAYS, 1, 1);
				glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

				for (Tri block : tris) {
					Vector2f[] vertices = block.getVertices();
					for (int i = 0; i < vertices.length; i++) {
						Vector2f currentVertex = vertices[i];
						Vector2f nextVertex = vertices[(i + 1) % vertices.length];
						Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
						Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
						Vector2f lightToCurrent = Vector2f.sub(currentVertex, light.location, null);
						if (Vector2f.dot(normal, lightToCurrent) > 0) {
							Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, light.location, null).scale(800), null);
							Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, light.location, null).scale(800), null);
							glBegin(GL_QUADS); {
								glVertex2f(currentVertex.getX(), currentVertex.getY());
								glVertex2f(point1.getX(), point1.getY());
								glVertex2f(point2.getX(), point2.getY());
								glVertex2f(nextVertex.getX(), nextVertex.getY());
							} glEnd();
						}
					}
				}
				
				for (Block block : players) {
					Vector2f[] vertices = block.getVertices();
					for (int i = 0; i < vertices.length; i++) {
						Vector2f currentVertex = vertices[i];
						Vector2f nextVertex = vertices[(i + 1) % vertices.length];
						Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
						Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
						Vector2f lightToCurrent = Vector2f.sub(currentVertex, light.location, null);
						if (Vector2f.dot(normal, lightToCurrent) > 0) {
							Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, light.location, null).scale(800), null);
							Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, light.location, null).scale(800), null);
							glBegin(GL_QUADS); {
								glVertex2f(currentVertex.getX(), currentVertex.getY());
								glVertex2f(point1.getX(), point1.getY());
								glVertex2f(point2.getX(), point2.getY());
								glVertex2f(nextVertex.getX(), nextVertex.getY());
							} glEnd();
						}
					}
				}

				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				glStencilFunc(GL_EQUAL, 0, 1);
				glColorMask(true, true, true, true);

				glUseProgram(shader);
				glUniform2f(glGetUniformLocation(shader, "lightLocation"), light.location.getX(), HEIGHT - light.location.getY());
				glUniform3f(glGetUniformLocation(shader, "lightColor"), light.red, light.green, light.blue);
				glEnable(GL_BLEND);
				glBlendFunc(GL_ONE, GL_ONE);

				glBegin(GL_QUADS); {
					glVertex2f(0, 0);
					glVertex2f(0, HEIGHT);
					glVertex2f(WIDTH, HEIGHT);
					glVertex2f(WIDTH, 0);
				} glEnd();

				glDisable(GL_BLEND);
				glUseProgram(0);
				glClear(GL_STENCIL_BUFFER_BIT);
			}
			
			glUseProgram(0);
			Window.updateWindow();
		}
	}
	
}
