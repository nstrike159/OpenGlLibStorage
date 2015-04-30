package render;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Tri {
	
	public Vector2f a, b, c;
	public Vector2f uva, uvb, uvc;
	public int texture;

	public Tri(Vector2f a, Vector2f b, Vector2f c, Vector2f uva, Vector2f uvb, Vector2f uvc, int texture) {
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.uva = uva;
		this.uvb = uvb;
		this.uvc = uvc;
		
		this.texture = texture;
	}
	
	public void render(){
		glEnable(GL_TEXTURE_2D);//TODO
		glBindTexture(GL_TEXTURE_2D, texture);
		int index = glGetAttribLocation(0, "");
		glBegin(GL_TRIANGLES);
		glVertexAttrib2f(index, uva.x, uva.y);
		glVertex2f(a.x, a.y);
		glTexCoord2f(uvb.x, uvb.y);
		glVertex2f(b.x, b.y);
		glTexCoord2f(uvc.x, uvc.y);
		glVertex2f(c.x, c.y);
		glEnd();
	}
	
	public Vector2f[] getVertices(){
		return new Vector2f[]{
				new Vector2f(a.x, a.y),
				new Vector2f(b.x, b.y),
				new Vector2f(c.x, c.y)
		};
	}
	
}
