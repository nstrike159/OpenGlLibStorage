package attribstorage;
import java.util.List;

public class PairedList<I,O>{
	private List<I> inputOne;
	private List<O> inputTwo;
	
	public static class PairedListItem{
		public final String item_0;
		public final String item_1;
		public PairedListItem(String i1, String i2){
			item_0 = i1;
			item_1 = i2;
		}
	}
	
	public void remove(int i){
		inputOne.remove(i);
		inputTwo.remove(i);
	}
	
	public void add(I k, O v){
		inputOne.add(k);
		inputTwo.add(v);
	}
	
	public O getFromFront(I s){
		if(!inputOne.contains(s))return null;
		return inputTwo.get(inputOne.indexOf(s));
	}
	
	public I getFromBack(O s){
		if(!inputTwo.contains(s))return null;
		return inputOne.get(inputTwo.indexOf(s));
	}
}