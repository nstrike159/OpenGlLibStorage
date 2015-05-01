package interfaces;

import java.util.ArrayList;
import java.util.List;

public abstract class Human {
	private int userId;
	private String name;
	private List<Book> inv = new ArrayList<>();
	/**Gets the user ID of the person using the interface*/
	public int getUserID(){
		return userId;
	}
	/**Gets the name of the user*/
	public String getName(){
		return name;
	}
	/**Checks out books given a name or a ID*/
	public boolean checkOut(String name){
		boolean success=false;
		
		return success;
	}
	/**Checks out books given a name or a ID*/
	public boolean checkOut(int id){
		boolean success=false;
		
		
		if(success)
			;//add book by id
		return success;
	}
	/**Returns the book needed*/
	public boolean checkIn(int id){
		boolean success=false;
		
		
		if(success)
			;//add book by id
		return success;
	}
	/**Gets the inventory of the user*/
	public List<Book> getInventory(){
		return inv;
	}
	
	public void jklja(int a, String b){
		userId = a;
		name = b;
	}
}