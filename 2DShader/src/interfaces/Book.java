package interfaces;

public interface Book {
	/**Sets the state for when the book is checked in*/
	public int setState(int State);
	/**Get global ID*/
	public long getISBN();
	/**Returns value in $JC*/
	public double getCost();
	/**0 if available, otherwise is the human id*/
	public int getState();
	/**Returns a string array of each page*/
	public String[] getContents();
	/**Returns the book title "Book;Author"*/
	public String getTitle();
	/**Returns the genre of the book*/
	public String getGenre();
	/**Get the date and time when it was last checked out*/
	public long getCheckOutTime();
	/**Get date the book is due*/
	public long getDueDate();
	/**Set date the book is due*/
	public long setDueDate(long time);
	/**Sets the cost of the item*/
	public double setCost(double cost);
}
