package org.watson.renderapi;

import java.nio.charset.Charset;

import org.lwjgl.util.vector.Vector3f;

public class Book implements Readable{
	
	public Book(Vector3f color, String title, String contents, int id,
			boolean isThere) {
		super();
		this.color = color;
		this.title = title;
		System.out.println(contents);
		this.contents = contents;
		this.id = id;
		this.isThere = isThere;
	}

	Vector3f color;
	String title; 
	String contents;
	int id = 0;
	boolean isThere = true;
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public Vector3f getColor(){
		return color;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean isThere() {
		return isThere;
	}

	@Override
	public void setThere(boolean bool) {
		isThere = bool;
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public String getContents() {
		return contents;
	}

	@Override
	public double[] getAudio() {
		return null;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public float getWidth() {
		return 19;
	}
	
}
