package books;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import interfaces.Book;
public class Books implements Book{
	private long duedate;
	private long checkoutdate;
	private long ISBN=UUID.randomUUID().node();
	private int state=0;
	private double cost;
	private String genre;
	private String name="Spongebob's Life";
	private String author="Patrick";
	private String body="There was once a yellow sponge that lived in a pinaple under the sea. His best friend was named patrick who was a pink star fish living under a rock. He works at the krusty krab as a fry cook the best fry cook in bikini bottom to be exact. He works with a dull and drab quid named Squidward who just happens to live in the tiki head next to his home. Spongebob loves to try to play with Squidward";
	private int pagelength=90;
	@Override
	public long getISBN() {
		return ISBN;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public String[] getContents() {
		List<String> strings = new ArrayList<String>();
		String s = body.substring(0);
		while(s.length() >= pagelength){
			strings.add(s.substring(0,pagelength));
			s = s.substring(pagelength);
		}
		strings.add(s);
		String[] toRet = new String[strings.size()];
		for(int i = 0; i < strings.size(); i++){
			toRet[i] = strings.get(i);
		}
		return toRet;
	}

	@Override
	public String getTitle() {
		return name+";"+author;
	}

	@Override
	public String getGenre() {
		return genre;
	}

	@Override
	public long getCheckOutTime() {
		return checkoutdate;
	}

	@Override
	public long getDueDate() {
		return duedate;
	}

	@Override
	public long setDueDate(long time) {
		duedate=time;
		state=1;
		return duedate;
	}

	@Override
	public double setCost(double costs) {
		cost=costs;
		return cost;
	}

}
