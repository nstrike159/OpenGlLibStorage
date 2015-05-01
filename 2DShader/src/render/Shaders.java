package render;

public class Shaders {
	
	public static final String lightShader = "uniform vec2 lightLocation;\n" + 
			"uniform vec3 lightColor;\n" + 
			"uniform float screenHeight;\n" + 
			"\n" + 
			"void main() {\n" + 
			"	float distance = length(lightLocation - gl_FragCoord.xy);\n" + 
			"	float attenuation = 1.0 / distance;\n" + 
			"	vec4 color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3)) * vec4(lightColor, 1);\n" + 
			"\n" + 
			"	gl_FragColor = color;\n" + 
			"}\n";
	
	
	
}
