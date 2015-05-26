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
	public byte[] getContents() {
		return contents.getBytes();
	}
	
	@Override
	public Vector3f getColor(){
		return color;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setContents(byte[] contents) {
		this.contents = new String(contents).trim();
	}

	@Override
	public void setColor(Vector3f color) {
		this.color = color;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setID(int id) {
		this.id = id;
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
	
}
