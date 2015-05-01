package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.*;
import java.util.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;

import render.*;

public class Main {

	public final int width = 800;
	public final int height = 600;

	public ArrayList<Light> lights = new ArrayList<Light>();
	public ArrayList<Block> blocks = new ArrayList<Block>();

	private int fragmentShader;
	private int shaderProgram;

	private int state = 0;
	private int prevState = -1;

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		for (Light light : lights) {
			glColorMask(false, false, false, false);
			glStencilFunc(GL_ALWAYS, 1, 1);
			glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

			for (Block block : blocks) {
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

			glUseProgram(shaderProgram);
			glUniform2f(glGetUniformLocation(shaderProgram, "lightLocation"), light.location.getX(), height - light.location.getY());
			glUniform3f(glGetUniformLocation(shaderProgram, "lightColor"), light.red, light.green, light.blue);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);

			glBegin(GL_QUADS); {
				glVertex2f(0, 0);
				glVertex2f(0, height);
				glVertex2f(width, height);
				glVertex2f(width, 0);
			} glEnd();

			glDisable(GL_BLEND);
			glUseProgram(0);
			glClear(GL_STENCIL_BUFFER_BIT);
		}
		glColor3f(0, 0, 0);
		for (Block block : blocks) {
			glBegin(GL_QUADS); {
				for (Vector2f vertex : block.getVertices()) {
					glVertex2f(vertex.getX(), vertex.getY());
				}
			} glEnd();
		}
		Display.update();
		Display.sync(60);
	}

	private void initialize() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Librarian");
			Display.create(new PixelFormat(0, 16, 1));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		shaderProgram = glCreateProgram();
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		StringBuilder fragmentShaderSource = new StringBuilder();

		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("shader.frag"));
			while ((line = reader.readLine()) != null) {
				fragmentShaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Fragment shader not compiled!");
		}

		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);


		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_STENCIL_TEST);
		glClearColor(0, 0, 0, 0);
	}
	
	public void checkState(){
		logic();
		if(prevState != state){
			lights.clear();
			blocks.clear();
		}
		prevState = state;
	}
	
	private void logic(){
		if(state == 0){
			
		}
	}

	private void cleanup() {
		glDeleteShader(fragmentShader);
		glDeleteProgram(shaderProgram);
		Display.destroy();
	}

	public static void main(String[] args) {
		Main main = new Main();
		
		main.initialize();

		while (!Display.isCloseRequested()) {
			main.checkState();
			main.render();
		}

		main.cleanup();
	}
}
