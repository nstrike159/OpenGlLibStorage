package org.watson.renderapi;

import org.lwjgl.util.vector.Vector3f;

public interface Readable {
	
	public String getTitle();
	public byte[] getContents();
	public Vector3f getColor();
	
	public void setTitle(String title);
	public void setContents(byte[] contents);
	public void setColor(Vector3f color);
	
	public int getID();
	public void setID(int id);
	public boolean isThere();
	public void setThere(boolean bool);
	public int getType();
	
}
