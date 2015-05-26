package org.watson.renderapi;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import packet.DataParser;
import packet.UDPClient;
import static org.lwjgl.opengl.GL11.*;
import static org.watson.lang.NewLang.*;
import static org.watson.renderapi.Final.*;

public class SimpleRender {

	private static List<Readable> books = new ArrayList<Readable>();
	private static List<Readable> inv = new ArrayList<Readable>();

	public static void start() {
		Window.createWindow(_title, 800, 600);
	}

	public static int addBook(Readable book) {
		int put = -1;
		for (int i = 0; i < books.size(); i++) {
			if (!books.get(i).isThere()) {
				put = i;
				break;
			}
		}
		if (put == -1) {
			books.add(book);
		} else {
			books.set(put, book);
		}
		return books.indexOf(book);
	}

	public static int addInv(Readable book) {
		int put = -1;
		for (int i = 0; i < inv.size(); i++) {
			if (!inv.get(i).isThere()) {
				put = i;
				break;
			}
		}
		if (put == -1) {
			inv.add(book);
		} else {
			inv.set(put, book);
		}
		return inv.indexOf(book);
	}

	public static void addBook(int i, Readable book) {
		books.set(i, book);
	}
	
	public static void removeBook(int i){
		for(Readable read : books){
			if(read.getID() == i){
				read.setThere(false);
			}
		}
	}

	private static void update() {
		Window.update(60);
	}
	
	private static boolean isAdding = false, hasTitle = false;
	private static String curr = "";
	private static String title = "";

	public static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		int pos = 0;

		// Draw Add
		if (Mouse.getX() < Display.getWidth() - plusmult
				&& Mouse.getX() > Display.getWidth() - 4 * plusmult
				&& Mouse.getY() < Display.getHeight() - plusmult
				&& Mouse.getY() > Display.getHeight() - 4 * plusmult) {
			glColor3f(0.5f, 1, 0.5f);
			if (Mouse.isButtonDown(0)) {
				isAdding = true;
			}
		} else
			glColor3f(0, 1, 0);
		glBegin(GL_QUADS);
		glVertex2f(Display.getWidth() - 4 * plusmult, Display.getHeight() - 2
				* plusmult);
		glVertex2f(Display.getWidth() - plusmult, Display.getHeight() - 2
				* plusmult);
		glVertex2f(Display.getWidth() - plusmult, Display.getHeight() - 3
				* plusmult);
		glVertex2f(Display.getWidth() - 4 * plusmult, Display.getHeight() - 3
				* plusmult);
		glVertex2f(Display.getWidth() - 3 * plusmult, Display.getHeight()
				- plusmult);
		glVertex2f(Display.getWidth() - 2 * plusmult, Display.getHeight()
				- plusmult);
		glVertex2f(Display.getWidth() - 2 * plusmult, Display.getHeight() - 4
				* plusmult);
		glVertex2f(Display.getWidth() - 3 * plusmult, Display.getHeight() - 4
				* plusmult);
		glEnd();

		glColor3f(1, 1, 1);

		if (isAdding) {
			if(hasTitle){
				SimpleText.drawString("Enter Contents. Press <TAB> to finish.", Display.getWidth()/2-150, Display.getHeight()-20);
			}else{
				SimpleText.drawString("Enter Title. Press <RETURN> or <TAB> to finish.", Display.getWidth()/2-200, Display.getHeight()-20);
			}
			SimpleText.drawString(title, 20, Display.getHeight() - 50);
			curr = Util.addString(curr);
			SimpleText.drawString(curr, 50, Display.getHeight() - 100);
			if (curr.contains("\t") || (curr.contains("\n") && !hasTitle)) {
				curr.replace("\t", "");
				if (hasTitle) {
					hasTitle = false;
					Readable read = new Book(new Vector3f(frandom(lrandom()),
							frandom(lrandom()), frandom(lrandom())), title,
							curr, 0, true);
					byte[] sendable = DataParser.getSendableBook(read);
					UDPClient.send(sendable);
					curr = "";
					title = "";
					isAdding = false;
				} else {
					curr.replace("\n", "");
					hasTitle = true;
					title = curr;
					curr = "";
				}
			}
		}

		glColor3f(brown.x, brown.y, brown.z);

		glBegin(GL_QUADS);
		glVertex2f(start - spacing, ypos - spacing);
		glVertex2f(start - spacing, ypos - bookShelfHeight - spacing);
		glVertex2f((start - spacing) + bookShelfLength, ypos - bookShelfHeight
				- spacing);
		glVertex2f((start - spacing) + bookShelfLength, ypos - spacing);
		glEnd();
		
		glColor3f(1, 1, 1);
		
		SimpleText.drawString("library", 20, 135);
		SimpleText.drawString("inventory", 20, 50);

		for (Readable read : books) {
			if (read.isThere()) {
				glColor3f(read.getColor().x, read.getColor().y,
						read.getColor().z);
				glBegin(GL_QUADS);
				glVertex2f(pos * mult + start, ypos);
				glVertex2f(pos * mult + start, ypos + bookHeight);
				glVertex2f(pos * mult + start + (mult - spacing), ypos
						+ bookHeight);
				glVertex2f(pos * mult + start + (mult - spacing), ypos);
				glEnd();
				if (Mouse.getX() > pos * mult + start
						&& Mouse.getX() < pos * mult + start + (mult - spacing)
						&& Mouse.getY() > ypos
						&& Mouse.getY() < ypos + bookHeight) {
					glColor3f(read.getColor().x, read.getColor().y, read.getColor().z);
					if (read.getColor().x + read.getColor().y
							+ read.getColor().z < 1)
						glColor3f(1, 1, 1);
					SimpleText.drawString(read.getTitle(), 20,
							Display.getHeight() - 50);
					if(read.getType() == 0)
					SimpleText.drawString(new String(read.getContents()).trim(), 50,
							Display.getHeight() - 100);
					if (Mouse.isButtonDown(0)) {
						addInv(new Book(read.getColor(), read.getTitle(),
								new String(read.getContents()).trim(), read.getID(), true));
						UDPClient.send(DataParser.getSendableInteger(read.getID()));
					}
				}
			}
			pos++;
		}
		pos = 0;
		for (Readable read : inv) {
			if (read.isThere()) {
				glColor3f(read.getColor().x, read.getColor().y,
						read.getColor().z);
				glBegin(GL_QUADS);
				glVertex2f(pos * mult + start, ypos - bookShelfHeight
						- bookHeight - spacing * 2);
				glVertex2f(pos * mult + start, ypos - bookShelfHeight - spacing
						* 2);
				glVertex2f(pos * mult + start + (mult - spacing), ypos
						- bookShelfHeight - spacing * 2);
				glVertex2f(pos * mult + start + (mult - spacing), ypos
						- bookShelfHeight - spacing * 2 - bookHeight);
				glEnd();
				if (Mouse.getX() > pos * mult + start
						&& Mouse.getX() < pos * mult + start + (mult - spacing)
						&& Mouse.getY() > ypos - bookShelfHeight - spacing * 2
								- bookHeight
						&& Mouse.getY() < ypos - bookShelfHeight - spacing * 2) {
					glColor3f(read.getColor().x, read.getColor().y, read.getColor().z);
					if (read.getColor().x + read.getColor().y
							+ read.getColor().z < 1)
						glColor3f(1, 1, 1);
					
					SimpleText.drawString(read.getTitle(), 20,
							Display.getHeight() - 50);
					if(read.getType() == 0)
					SimpleText.drawString(new String(read.getContents()).trim(), 50,
							Display.getHeight() - 100);
					if (Mouse.isButtonDown(0)) {
						byte[] sendable = DataParser.getSendableBook(read);
						UDPClient.send(sendable);
						read.setThere(false);
					}
				}
			}
			pos++;
		}

		try {
		} catch (Exception e) {
		}
		update();
	}

}
