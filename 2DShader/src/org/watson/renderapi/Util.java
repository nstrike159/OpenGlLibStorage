package org.watson.renderapi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL44.*;
import static org.watson.renderapi.CplusplusFunctions.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class Util {

	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> vaos = new ArrayList<>();

	public static final int VERT = 1, FRAG = 2, VERTFRAG = 3, GEOM = 4,
			VERTGEOM = 5, GEOMFRAG = 6, VERTGEOMFRAG = 7;

	public static int genShaders(String[] srcs, int[] uses) {
		int shader = glCreateProgram();
		for (int i = 0; i < srcs.length; i++) {
			Boolean[] booleantable = boolTable(uses[i], 3);
			if (booleantable[0]) {
				int fragmentShader = glCreateShader(GL_VERTEX_SHADER);
				String fragmentShaderSource = srcs[i];

				glShaderSource(fragmentShader, fragmentShaderSource);
				glCompileShader(fragmentShader);
				if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
					System.err.println("vertex shader not compiled! \n src:"
							+ i);
				}
				glAttachShader(shader, fragmentShader);
			}
			if (booleantable[1]) {
				int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
				String fragmentShaderSource = srcs[i];

				glShaderSource(fragmentShader, fragmentShaderSource);
				glCompileShader(fragmentShader);
				if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
					System.err.println("fragment shader not compiled! \n src:"
							+ i);
				}
				glAttachShader(shader, fragmentShader);
			}
			if (booleantable[2]) {
				int fragmentShader = glCreateShader(GL_GEOMETRY_SHADER);
				String fragmentShaderSource = srcs[i];

				glShaderSource(fragmentShader, fragmentShaderSource);
				glCompileShader(fragmentShader);
				if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
					System.err.println("geometry shader not compiled! \n src:"
							+ i);
				}
				glAttachShader(shader, fragmentShader);
			}
		}

		glBindAttribLocation(shader, 0, "position");
		glBindAttribLocation(shader, 1, "texcoord");

		glLinkProgram(shader);
		glValidateProgram(shader);
		return shader;
	}

	public static int createGLObject2(float[] positions, int[] index) {
		int vao = GL30.glGenVertexArrays();
		glBindVertexArray(vao);
		cIndex(index);
		sdial(0, positions, 2);
		glBindVertexArray(0);
		vaos.add(vao);
		return vao;
	}

	private static void cIndex(int[] data) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer cint = tib(data);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, cint, GL_STATIC_DRAW);
	}

	private static void sdial(int num, float[] data, int step) {
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = tfb(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(num, step, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		vbos.add(vbo);
	}

	private static FloatBuffer tfb(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static IntBuffer tib(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static void cleanUp() {
		for (int i : vbos) {
			glDeleteBuffers(i);
		}
		for (int i : vaos) {
			glDeleteVertexArrays(i);
		}
	}

	public static final int[] alphabet = new int[] { Keyboard.KEY_A,
			Keyboard.KEY_B, Keyboard.KEY_C, Keyboard.KEY_D, Keyboard.KEY_E,
			Keyboard.KEY_F, Keyboard.KEY_G, Keyboard.KEY_H, Keyboard.KEY_I,
			Keyboard.KEY_J, Keyboard.KEY_K, Keyboard.KEY_L, Keyboard.KEY_M,
			Keyboard.KEY_N, Keyboard.KEY_O, Keyboard.KEY_P, Keyboard.KEY_Q,
			Keyboard.KEY_R, Keyboard.KEY_S, Keyboard.KEY_T, Keyboard.KEY_U,
			Keyboard.KEY_V, Keyboard.KEY_W, Keyboard.KEY_X, Keyboard.KEY_Y,
			Keyboard.KEY_Z, };

	public static final int[] numbers = new int[] { Keyboard.KEY_0,
			Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4,
			Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8,
			Keyboard.KEY_9, };

	public static final int[] special = new int[] { Keyboard.KEY_SPACE,
			Keyboard.KEY_BACK, Keyboard.KEY_PERIOD, Keyboard.KEY_RETURN,
			Keyboard.KEY_TAB, Keyboard.KEY_COMMA, Keyboard.KEY_SLASH,
			Keyboard.KEY_BACKSLASH, Keyboard.KEY_MINUS, Keyboard.KEY_EQUALS,
			Keyboard.KEY_SEMICOLON, Keyboard.KEY_APOSTROPHE, Keyboard.KEY_COLON };

	public static boolean[] keysdown = new boolean[] {
			// Alphabet
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false,
			false,
			// Numbers
			true, false, false, false, false, false, false, false, false,
			false,
			// Special Characters
			true, false, false, false, false, false, false, false, false,
			false, false, false, false };
	public static char[] shiftNum = { ')', '!', '@', '#', '$', '%', '^', '&',
			'*', '(' };

	public static String addString(String adder) {
		String s = adder;

		// *

		for (int i = 0; i < alphabet.length; i++) {
			if (Keyboard.isKeyDown(alphabet[i]) && !keysdown[i]) {
				keysdown[i] = true;
				s += (char) (i + 'A');
			} else if (!Keyboard.isKeyDown(alphabet[i])) {
				keysdown[i] = false;
			}

		}
		// *
		for (int j = 0; j < numbers.length; j++) {
			if (Keyboard.isKeyDown(numbers[j])
					&& !keysdown[alphabet.length + j]) {
				keysdown[alphabet.length + j] = true;
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
						|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					s += shiftNum[j];
				} else {
					s += (char) (j + '0');
				}
			} else if (!Keyboard.isKeyDown(numbers[j])) {
				keysdown[alphabet.length + j] = false;
			}

		}
		// */
		if (Keyboard.isKeyDown(special[0])
				&& !keysdown[alphabet.length + numbers.length]) {
			keysdown[alphabet.length + numbers.length] = true;
			s += " ";
		} else if (!Keyboard.isKeyDown(special[0])) {
			keysdown[alphabet.length + numbers.length] = false;
		}

		if (Keyboard.isKeyDown(special[1])
				&& !keysdown[alphabet.length + numbers.length + 1]) {
			keysdown[alphabet.length + numbers.length + 1] = true;
			s = s.substring(0, Math.max(0, s.length() - 1));
		} else if (!Keyboard.isKeyDown(special[1])) {
			keysdown[alphabet.length + numbers.length + 1] = false;
		}

		if (Keyboard.isKeyDown(special[2])
				&& !keysdown[alphabet.length + numbers.length + 2]) {
			keysdown[alphabet.length + numbers.length + 2] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += ">";
			} else {
				s += ".";
			}
		} else if (!Keyboard.isKeyDown(special[2])) {
			keysdown[alphabet.length + numbers.length + 2] = false;
		}

		if (Keyboard.isKeyDown(special[3])
				&& !keysdown[alphabet.length + numbers.length + 3]) {
			keysdown[alphabet.length + numbers.length + 3] = true;
			s += "\n";
		} else if (!Keyboard.isKeyDown(special[3])) {
			keysdown[alphabet.length + numbers.length + 3] = false;
		}

		if (Keyboard.isKeyDown(special[4])
				&& !keysdown[alphabet.length + numbers.length + 4]) {
			keysdown[alphabet.length + numbers.length + 4] = true;
			s += "\t";
		} else if (!Keyboard.isKeyDown(special[4])) {
			keysdown[alphabet.length + numbers.length + 4] = false;
		}

		if (Keyboard.isKeyDown(special[5])
				&& !keysdown[alphabet.length + numbers.length + 5]) {
			keysdown[alphabet.length + numbers.length + 5] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "<";
			} else {
				s += ",";
			}
		} else if (!Keyboard.isKeyDown(special[5])) {
			keysdown[alphabet.length + numbers.length + 5] = false;
		}

		if (Keyboard.isKeyDown(special[6])
				&& !keysdown[alphabet.length + numbers.length + 6]) {
			keysdown[alphabet.length + numbers.length + 6] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "?";
			} else {
				s += "/";
			}
		} else if (!Keyboard.isKeyDown(special[6])) {
			keysdown[alphabet.length + numbers.length + 6] = false;
		}

		if (Keyboard.isKeyDown(special[7])
				&& !keysdown[alphabet.length + numbers.length + 7]) {
			keysdown[alphabet.length + numbers.length + 7] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "|";
			} else {
				s += "\\";
				System.out.println("\\");
			}
		} else if (!Keyboard.isKeyDown(special[7])) {
			keysdown[alphabet.length + numbers.length + 7] = false;
		}

		if (Keyboard.isKeyDown(special[8])
				&& !keysdown[alphabet.length + numbers.length + 8]) {
			keysdown[alphabet.length + numbers.length + 8] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "_";
			} else {
				s += "-";
			}
		} else if (!Keyboard.isKeyDown(special[8])) {
			keysdown[alphabet.length + numbers.length + 8] = false;
		}

		if (Keyboard.isKeyDown(special[9])
				&& !keysdown[alphabet.length + numbers.length + 9]) {
			keysdown[alphabet.length + numbers.length + 9] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "+";
			} else {
				s += "=";
			}
		} else if (!Keyboard.isKeyDown(special[9])) {
			keysdown[alphabet.length + numbers.length + 9] = false;
		}

		if (Keyboard.isKeyDown(special[10])
				&& !keysdown[alphabet.length + numbers.length + 10]) {
			keysdown[alphabet.length + numbers.length + 10] = true;
			s += ";";
		} else if (!Keyboard.isKeyDown(special[10])) {
			keysdown[alphabet.length + numbers.length + 10] = false;
		}

		if (Keyboard.isKeyDown(special[11])
				&& !keysdown[alphabet.length + numbers.length + 11]) {
			keysdown[alphabet.length + numbers.length + 11] = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
					|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				s += "\"";
			} else {
				s += "\'";
			}
		} else if (!Keyboard.isKeyDown(special[11])) {
			keysdown[alphabet.length + numbers.length + 11] = false;
		}
		
		if (Keyboard.isKeyDown(special[12])
				&& !keysdown[alphabet.length + numbers.length + 12]) {
			keysdown[alphabet.length + numbers.length + 12] = true;
			s += ":";
		} else if (!Keyboard.isKeyDown(special[12])) {
			keysdown[alphabet.length + numbers.length + 12] = false;
		}

		return s;
	}

	public static Vector3f getColorHex(long hex) {
		float a = (hex & 0xFF000000L) >> 24;
		float r = (hex & 0xFF0000L) >> 16;
		float g = (hex & 0xFF00L) >> 8;
		float b = (hex & 0xFFL);

		return new Vector3f(r / 255f, g / 255f, b / 255f);
	}
}
