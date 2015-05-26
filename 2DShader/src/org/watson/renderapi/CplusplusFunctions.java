package org.watson.renderapi;

import java.io.PrintStream;

public class CplusplusFunctions {

	public static final int stdout = 0, stderr = 1, NULL = 0;

	public static void exit(int status) {
		System.exit(status);
	}

	public static Object[] malloc(int size) {
		return new Object[size];
	}

	public static void fputs(String toPut, int place) {
		switch (place) {
		case stdout:
			System.out.print(toPut);
			break;
		case stderr:
			System.err.print(toPut);
			break;
		default:
			break;
		}
	}

	public static float cos(float f) {
		return (float) Math.cos(f);
	}

	public static float sin(float f) {
		return (float) Math.sin(f);
	}

	public static float acos(float f) {
		return (float) Math.acos(f);
	}

	public static float asin(float f) {
		return (float) Math.asin(f);
	}

	public static float tan(float f) {
		return (float) Math.tan(f);
	}

	public static float atan(float f) {
		return (float) Math.atan(f);
	}

	public static float atan2(float a, float b) {
		return (float) Math.atan2(a, b);
	}

	public static float cosh(float f) {
		return (float) Math.cosh(f);
	}

	public static float sinh(float f) {
		return (float) Math.sinh(f);
	}

	public static float tanh(float f) {
		return (float) Math.tanh(f);
	}

	public static float exp(float a) {
		return (float) Math.exp(a);
	}

	public static float pow(float a, float b) {
		float exp = 1;
		for (int i = 1; i < b; i++) {
			exp *= a;
		}
		return exp;
	}

	public static float max(float a, float b) {
		return a > b ? a : b;
	}

	public static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public static int min(int a, int b) {
		return a < b ? a : b;
	}
	
	public static String intToBinary(int num, int length) {
	    int copy = num;
	    String sb = "";
	    for(int i=length-1; i>=0; i--) {
	        sb = (copy&1) + sb;
	        copy = copy >>>=1;
	    }
	    return sb;
	}
	
	public static boolean intToBoolean(int x){
		return !(x == 0);
	}
	
	public static boolean charToBoolean(char x){
		return !(x == '0');
	}
	
	public static void printArray(Object[] array, int out){
		for(int i = 0; i < array.length; i++){
			fputs(array[i].toString(), stdout);
		}
	}
	
	public static void printArray(Boolean[] array, int out){
		for(int i = 0; i < array.length; i++){
			fputs(array[i].toString() + " ", stdout);
		}
	}

	public static Boolean[] boolTable(int table, int len) {
		Boolean[] bits = new Boolean[len];
		String intto = intToBinary(table, len);
		for(int i = 0; i < bits.length; i++){
			bits[i] = charToBoolean(intto.charAt(i));
		}
		return bits;
	}
}
