package render;

import org.lwjgl.util.vector.Vector2f;

public class Block {

	public Vector2f a, b, c, d;

	public Block(Vector2f a, Vector2f b, Vector2f c, Vector2f d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Block(float x, float y, float width, float height){
		a = new Vector2f(x, y);
		b = new Vector2f(x+width, y);
		c = new Vector2f(x+width, y+height);
		d = new Vector2f(x, y+height);
	}

	public Vector2f[] getVertices() {
		return new Vector2f[] { new Vector2f(a.x, a.y), new Vector2f(b.x, b.y),
				new Vector2f(c.x, c.y), new Vector2f(d.x, d.y) };
	}

}
