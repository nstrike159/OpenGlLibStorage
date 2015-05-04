package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import interfaces.Book;

import java.util.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;

import books.Books;
import packet.UDPClient;
import render.*;

import static org.lwjgl.input.Keyboard.*;

public class Main {

	public final int width = 800;
	public final int height = 600;
	
	private static final int start = 300, mult = 100;

	public ArrayList<Light> lights = new ArrayList<Light>();
	public ArrayList<Block> blocks = new ArrayList<Block>();

	public Light dynlight = null;

	UDPClient client;

	int[] stateData;
	private static final int stateDataLengths[] = { 1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0 };
	
	private static final String[] state0options = new String[]{
		"Start Game", "Settings", "Quit"
	};

	private int fragmentShader;
	private int shaderProgram;

	private int heldBook = 0;

	private int state = 0;
	private int prevState = -1;
	/*
	for (int i = 0; i < 4; i++) {
					blocks.add(new Block(width / 2 - 100, 100 + i * 200, 200, 5));
					blocks.add(new Block(width / 2 - 100, 150 + i * 200, 200, 5));
					blocks.add(new Block(width / 2 - 100, 100 + i * 200, 5, 50));
					blocks.add(new Block(width / 2 + 100, 100 + i * 200, 5, 50));
				}
	 */

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		if (state == 6) {
			glOrtho(0, width, 0, height, 1, -1);
			Book book = client.books.get(heldBook);
			glColor3f(1, 1, 1);
			SimpleText.drawString(book.getTitle().split(";")[0] + " by "
					+ book.getTitle().split(";")[1], 40, height - 60);
			for (int i = 0; i < book.getContents().length; i++) {
				SimpleText.drawString(book.getContents()[i], 50, height - 80
						- 20 * i);
			}
			glOrtho(0, width, height, 0, 1, -1);
		}
		if (dynlight != null) {
			glColorMask(false, false, false, false);
			glStencilFunc(GL_ALWAYS, 1, 1);
			glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

			for (Block block : blocks) {
				Vector2f[] vertices = block.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex,
							null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
					Vector2f lightToCurrent = Vector2f.sub(currentVertex,
							dynlight.location, null);
					if (Vector2f.dot(normal, lightToCurrent) > 0) {
						Vector2f point1 = Vector2f.add(
								currentVertex,
								(Vector2f) Vector2f.sub(currentVertex,
										dynlight.location, null).scale(800),
								null);
						Vector2f point2 = Vector2f.add(
								nextVertex,
								(Vector2f) Vector2f.sub(nextVertex,
										dynlight.location, null).scale(800),
								null);
						glBegin(GL_QUADS);
						{
							glVertex2f(currentVertex.getX(),
									currentVertex.getY());
							glVertex2f(point1.getX(), point1.getY());
							glVertex2f(point2.getX(), point2.getY());
							glVertex2f(nextVertex.getX(), nextVertex.getY());
						}
						glEnd();
					}
				}
			}

			glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
			glStencilFunc(GL_EQUAL, 0, 1);
			glColorMask(true, true, true, true);

			glUseProgram(shaderProgram);
			glUniform2f(glGetUniformLocation(shaderProgram, "lightLocation"),
					dynlight.location.getX(), height - dynlight.location.getY());
			glUniform3f(glGetUniformLocation(shaderProgram, "lightColor"),
					dynlight.red, dynlight.green, dynlight.blue);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);

			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, height);
				glVertex2f(width, height);
				glVertex2f(width, 0);
			}
			glEnd();

			glDisable(GL_BLEND);
			glUseProgram(0);
			glClear(GL_STENCIL_BUFFER_BIT);
		}
		
		if(state == 0){
			for(int j = 0; j < 4; j++){
				glColorMask(false, false, false, false);
				glStencilFunc(GL_ALWAYS, 1, 1);
				glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
				
				int speed = 6;
				
				int buffer = 21;
				
				float kies = ((System.currentTimeMillis()/speed)%((height-(2*buffer))*2) > (height-(2*buffer)) ? (height-(2*buffer)) - (System.currentTimeMillis()/speed)%((height-(2*buffer))) : (System.currentTimeMillis()/speed)%((height-(2*buffer))*2))+buffer;

				for (Block block : blocks) {
					Vector2f[] vertices = block.getVertices();
					for (int i = 0; i < vertices.length; i++) {
						Vector2f currentVertex = vertices[i];
						Vector2f nextVertex = vertices[(i + 1) % vertices.length];
						Vector2f edge = Vector2f.sub(nextVertex, currentVertex,
								null);
						Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
						Vector2f lightToCurrent = Vector2f.sub(currentVertex,
								new Vector2f((j > 1 ? j+1 : j)*(width/6)+width/6, kies), null);
						if (Vector2f.dot(normal, lightToCurrent) > 0) {
							Vector2f point1 = Vector2f.add(
									currentVertex,
									(Vector2f) Vector2f.sub(currentVertex,
											new Vector2f((j > 1 ? j+1 : j)*(width/6)+width/6, kies), null).scale(800),
									null);
							Vector2f point2 = Vector2f.add(
									nextVertex,
									(Vector2f) Vector2f.sub(nextVertex,
											new Vector2f((j > 1 ? j+1 : j)*(width/6)+width/6, kies), null).scale(800),
									null);
							glBegin(GL_QUADS);
							{
								glVertex2f(currentVertex.getX(),
										currentVertex.getY());
								glVertex2f(point1.getX(), point1.getY());
								glVertex2f(point2.getX(), point2.getY());
								glVertex2f(nextVertex.getX(), nextVertex.getY());
							}
							glEnd();
						}
					}
				}

				glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
				glStencilFunc(GL_EQUAL, 0, 1);
				glColorMask(true, true, true, true);

				glUseProgram(shaderProgram);
				glUniform2f(glGetUniformLocation(shaderProgram, "lightLocation"),
						(j > 1 ? j+1 : j)*(width/6)+width/6, height - kies);
				glUniform3f(glGetUniformLocation(shaderProgram, "lightColor"),
						2, 2, 5);
				glEnable(GL_BLEND);
				glBlendFunc(GL_ONE, GL_ONE);

				glBegin(GL_QUADS);
				{
					glVertex2f(0, 0);
					glVertex2f(0, height);
					glVertex2f(width, height);
					glVertex2f(width, 0);
				}
				glEnd();

				glDisable(GL_BLEND);
				glUseProgram(0);
				glClear(GL_STENCIL_BUFFER_BIT);
			}
		}

		for (Light light : lights) {
			glColorMask(false, false, false, false);
			glStencilFunc(GL_ALWAYS, 1, 1);
			glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

			for (Block block : blocks) {
				Vector2f[] vertices = block.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex,
							null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
					Vector2f lightToCurrent = Vector2f.sub(currentVertex,
							light.location, null);
					if (Vector2f.dot(normal, lightToCurrent) > 0) {
						Vector2f point1 = Vector2f.add(
								currentVertex,
								(Vector2f) Vector2f.sub(currentVertex,
										light.location, null).scale(800), null);
						Vector2f point2 = Vector2f.add(
								nextVertex,
								(Vector2f) Vector2f.sub(nextVertex,
										light.location, null).scale(800), null);
						glBegin(GL_QUADS);
						{
							glVertex2f(currentVertex.getX(),
									currentVertex.getY());
							glVertex2f(point1.getX(), point1.getY());
							glVertex2f(point2.getX(), point2.getY());
							glVertex2f(nextVertex.getX(), nextVertex.getY());
						}
						glEnd();
					}
				}
			}

			glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
			glStencilFunc(GL_EQUAL, 0, 1);
			glColorMask(true, true, true, true);

			glUseProgram(shaderProgram);
			glUniform2f(glGetUniformLocation(shaderProgram, "lightLocation"),
					light.location.getX(), height - light.location.getY());
			glUniform3f(glGetUniformLocation(shaderProgram, "lightColor"),
					light.red, light.green, light.blue);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);

			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, height);
				glVertex2f(width, height);
				glVertex2f(width, 0);
			}
			glEnd();

			glDisable(GL_BLEND);
			glUseProgram(0);
			glClear(GL_STENCIL_BUFFER_BIT);
		}
		glColor3f(0, 0, 0);
		for (Block block : blocks) {
			glBegin(GL_QUADS);
			{
				for (Vector2f vertex : block.getVertices()) {
					glVertex2f(vertex.getX(), vertex.getY());
				}
			}
			glEnd();
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

		client = UDPClient.debugClient();

		client.books.add(new Books());

		shaderProgram = glCreateProgram();
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentShaderSource = Shaders.lightShader;

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

	public void checkState() {
		if (prevState != state) {
			lights.clear();
			blocks.clear();
			stateData = new int[stateDataLengths[state]];
			for (int i = 0; i < stateData.length; i++)
				stateData[i] = 0;
			if (state == 6) {
				blocks.add(new Block(0, 0, width, 20));
				blocks.add(new Block(0, height - 20, width, 20));
				blocks.add(new Block(0, 20, 20, height - 40));
				blocks.add(new Block(width - 20, 20, 20, height - 40));
				lights.add(new Light(new Vector2f(21, 21), 10, 10, 10));
				lights.add(new Light(new Vector2f(width - 21, 21), 10, 10, 10));
				lights.add(new Light(new Vector2f(width - 21, height - 21), 10,
						10, 10));
				lights.add(new Light(new Vector2f(21, height - 21), 10, 10, 10));
			} else if (state == 0) {
				blocks.add(new Block(0, 0, width, 20));
				blocks.add(new Block(0, height - 20, width, 20));
				blocks.add(new Block(0, 20, 20, height - 40));
				blocks.add(new Block(width - 20, 20, 20, height - 40));
				lights.add(new Light(new Vector2f(21, 21), 10, 10, 10));
				lights.add(new Light(new Vector2f(width - 21, 21), 10, 10, 10));
				lights.add(new Light(new Vector2f(width - 21, height - 21), 10,
						10, 10));
				lights.add(new Light(new Vector2f(21, height - 21), 10, 10, 10));
				for (int i = 0; i < 4; i++) {
					blocks.add(new Block(width / 2 - 100, start + i * 100, 200, 5));
					blocks.add(new Block(width / 2 - 100, start + 50 + i * 100, 200, 5));
					blocks.add(new Block(width / 2 - 100, start + i * 100, 5, 50));
					blocks.add(new Block(width / 2 + 100, start + i * 100, 5, 50));
				}
			}
		}
		logic();
		prevState = state;
	}

	private boolean[] keydown = new boolean[256];
	private boolean set = false;

	private void logic() {
		if (!set) {
			for (int i = 0; i < keydown.length; i++)
				keydown[i] = false;
			set = true;
		}
		if (state == 0) {
			if (dynlight == null)
				dynlight = new Light(new Vector2f(width / 2 - 75, 0), 5, 2,
						0);
			
			dynlight.location.x = width / 2 - 75;
			dynlight.location.y = 125 + 200 * stateData[0];
			if (isKeyDown(KEY_DOWN) && !keydown[KEY_DOWN]) {
				stateData[0] = (stateData[0] + 1) % 3;
				keydown[KEY_DOWN] = true;
			}
			if (!isKeyDown(KEY_DOWN)) {
				keydown[KEY_DOWN] = false;
			}
			if (isKeyDown(KEY_UP) && !keydown[KEY_UP]) {
				stateData[0] = (stateData[0] - 1) % 4;
				keydown[KEY_UP] = true;
			}
			if (!isKeyDown(KEY_UP)) {
				keydown[KEY_UP] = false;
			}
			while (stateData[0] < 0)
				stateData[0] += 3;
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
