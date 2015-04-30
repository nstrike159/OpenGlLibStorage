package interfaces;

public interface Book {
	/**Get global ID*/
	public int getISBN();
	/**Returns value in $JC*/
	public int getCost();
	/**0 if available, otherwise is the human id*/
	public int getState();
	/**Returns a string array of each page*/
	public String[] getContents();
	/**Returns the book title "Book;Author"*/
	public String getTitle();
	/**Returns the genre of the book*/
	public String getGenre();
	/**Get the date and time when it was last checked out*/
	public int getCheckOutTime();
	/**Get date the book is due*/
	public int getDueDate();
	/**Set date the book is due*/
	public int setDueDate();
}
