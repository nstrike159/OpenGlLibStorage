package org.watson.renderapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) throws IOException{
		for(int i = 11; i < 45; i++){
			if(i == 43 || (i < 20 && i > 15) || (i < 30 && i > 21) || (i < 40 && i > 33))continue;
			System.out.println("import static org.lwjgl.opengl.GL"+ i + ".*;");
		}
		
		File file = new File("file.file");
		
		Scanner scan = new Scanner(System.in);
		
		String filename = scan.nextLine().replace('\n', ' ');
		
		String firstline = "public static final String " + filename + " = \n";
		
		String spacing = "";
		
		for(int i = 0; i < firstline.length(); i++){
			spacing += " ";
		}
		
		String finale = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	finale += "\n" + spacing + "\"" + line + "\" +";
		    }
		}
		
		finale = finale.substring(0, finale.length()-2);
		
		System.out.println(firstline + finale + ";");
	}
	
}
