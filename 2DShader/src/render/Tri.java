package render;

import org.lwjgl.util.vector.Vector2f;

public class Tri {
	
	public Vector2f a, b, c;

	public Tri(Vector2f a, Vector2f b, Vector2f c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Vector2f[] getVertices(){
		return new Vector2f[]{
				new Vector2f(a.x, a.y),
				new Vector2f(b.x, b.y),
				new Vector2f(c.x, c.y)
		};
	}
	
}
