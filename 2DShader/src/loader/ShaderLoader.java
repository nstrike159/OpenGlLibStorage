package loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderLoader {
	
	public static int loadShader(){
		return 0;
	}
	
	public static int loadFragmentOnly(String frag){
		int shaderProgram = glCreateProgram();
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		StringBuilder fragmentShaderSource = new StringBuilder();

		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(frag));
			while ((line = reader.readLine()) != null) {
				fragmentShaderSource.append(line).append("\n");
			}
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
		glDeleteShader(fragmentShader);
		return shaderProgram;
	}
	
}
