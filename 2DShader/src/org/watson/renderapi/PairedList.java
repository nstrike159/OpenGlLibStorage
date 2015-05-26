package org.watson.renderapi;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.MinimalHTMLWriter;

import static org.watson.renderapi.CplusplusFunctions.*;

public class PairedList<I,O>{
	
	private List<I> inputOne = new ArrayList<I>();;
	private List<O> inputTwo = new ArrayList<O>();
	
	public static class PairedListItem{
		public final String item_0;
		public final String item_1;
		public PairedListItem(String i1, String i2){
			item_0 = i1;
			item_1 = i2;
		}
	}
	
	public int length(){
		return min(inputOne.size(), inputTwo.size());
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
	
	public I getFrontInd(int id){
		return inputOne.get(id);
	}
	
	public O getBackInd(int id){
		return inputTwo.get(id);
	}
}