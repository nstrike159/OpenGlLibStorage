package entity;

import interfaces.Book;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class AABB {
	
	Vector2f min, max;
	int id;
	List<Integer> data = new ArrayList<Integer>();
	
	public void setData(int pos, int dat){
		data.set(pos, dat);
	}

	public AABB(Vector2f min, Vector2f max, int id) {
		this.min = min;
		this.max = max;
		this.id = id;
	}
}
