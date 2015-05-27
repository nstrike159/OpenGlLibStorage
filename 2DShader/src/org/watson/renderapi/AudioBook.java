package org.watson.renderapi;

import org.lwjgl.util.vector.Vector3f;

public class AudioBook implements Readable {

	Vector3f color;
	String title;
	double[] audio;
	int id = 0;
	boolean isThere = true;

	public AudioBook(Vector3f color, String title, double[] audio, int id,
			boolean isThere) {
		this.color = color;
		this.title = title;
		this.audio = audio;
		this.id = id;
		this.isThere = isThere;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getContents() {
		return null;
	}

	@Override
	public double[] getAudio() {
		return audio;
	}

	@Override
	public Vector3f getColor() {
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
		return 1;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public float getWidth() {
		return 5;
	}

}
