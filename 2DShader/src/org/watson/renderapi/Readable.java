package org.watson.renderapi;

import org.lwjgl.util.vector.Vector3f;

public interface Readable {
	/**Title of Book*/
	public String getTitle();
	/**Contents of Book*/
	public String getContents();
	/**Contents of Audio*/
	@Deprecated
	public double[] getAudio();
	/**Color of book*/
	public Vector3f getColor();
	
	/**Set the identifier*/
	public void setID(int id);
	/**Get the identifier*/
	public int getID();
	/**Whether or not the book is on the shelf*/
	public boolean isThere();
	/**Take the book of the shelf*/
	public void setThere(boolean bool);
	/**Get type of readable*/
	public int getType();
	/**Get book width*/
	public float getWidth();
	
}
