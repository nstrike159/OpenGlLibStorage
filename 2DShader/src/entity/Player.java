package entity;

import org.lwjgl.util.vector.Vector2f;

public class Player {
	
	Vector2f direction;
	Vector2f location;
	String username;
	
	public Player(Vector2f direction, Vector2f location) {
		this.direction = direction;
		this.location = location;
	}
	
}
