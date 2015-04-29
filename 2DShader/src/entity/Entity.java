package entity;

import org.lwjgl.util.vector.Vector3f;

public interface Entity {
	
	public Vector3f getPosition();
	
	public void update();
	
}
