package org.watson.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class NewLang {

	public static final int stdout = 0, stderr = 1, NULL = 0;

	private static List<List<String>> streams = new ArrayList<List<String>>();

	public static final char newline = (OsCheck.getOperatingSystemType() == OSType.Windows ? '\n'
			: '\r');

	public static void exit(int status) {
		print("exit code:" + status, stdout);
		System.exit(status);
	}

	public static Object[] malloc(int size) {
		return new Object[size];
	}

	public static int createStream() {
		List<String> stream = new ArrayList<String>();
		streams.add(stream);
		return streams.indexOf(stream) + 2;
	}

	public static void print(String toPut, int place) {
		if (place == 0) {
			System.out.print(toPut);
		} else if (place == 1) {
			System.err.print(toPut);
		} else {
			streams.get(place - 2).add(toPut);
		}
	}

	public static void printStream(int from, int to) {
		from -= 2;
		if (from < 0)
			return;
		for (String s : streams.get(from))
			print(s, to);
	}

	public static String[] shorten(String[] toShorten, int len) {
		if (len >= toShorten.length)
			return toShorten;
		String[] toret = new String[len];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = toShorten[i];
		}
		return toret;
	}

	public static String[] truncate(String[] orig, int toTruncate) {
		String[] toret = new String[orig.length - 1];
		int pos = 0;
		for (String s : orig) {
			if (s == orig[toTruncate])
				continue;
			toret[pos] = s;
			pos++;
		}
		return toret;
	}

	public static float[] shorten(float[] toShorten, int len) {
		if (len >= toShorten.length)
			return toShorten;
		float[] toret = new float[len];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = toShorten[i];
		}
		return toret;
	}

	public static int[] shorten(int[] toShorten, int len) {
		if (len >= toShorten.length)
			return toShorten;
		int[] toret = new int[len];
		for (int i = 0; i < toret.length; i++) {
			toret[i] = toShorten[i];
		}
		return toret;
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
		for (int i = length - 1; i >= 0; i--) {
			sb = (copy & 1) + sb;
			copy = copy >>>= 1;
		}
		return sb;
	}

	public static boolean intToBoolean(int x) {
		return !(x == 0);
	}

	public static boolean charToBoolean(char x) {
		return !(x == '0');
	}

	public static void print(Object[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i].toString(), out);
		}
		print("}", out);
	}

	public static void print(Boolean[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i].toString() + (i == array.length - 1 ? "" : ", ")
					+ "", out);
		}
		print("}", out);
	}

	public static void print(String[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i].toString() + (i == array.length - 1 ? "" : ", ")
					+ "", out);
		}
		print("}", out);
	}

	public static void print(Integer[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i].toString() + (i == array.length - 1 ? "" : ", ")
					+ "", out);
		}
		print("}", out);
	}

	public static void print(int[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i] + (i == array.length - 1 ? "" : ", ") + "", out);
		}
		print("}", out);
	}

	public static void print(Float[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i].toString() + (i == array.length - 1 ? "" : ", ")
					+ "", out);
		}
		print("}", out);
	}

	public static void print(float[] array, int out) {
		print("{", out);
		for (int i = 0; i < array.length; i++) {
			print(array[i] + (i == array.length - 1 ? "" : ", ") + "", out);
		}
		print("}", out);
	}

	public static Boolean[] boolTable(int table, int len) {
		Boolean[] bits = new Boolean[len];
		String intto = intToBinary(table, len);
		for (int i = 0; i < bits.length; i++) {
			bits[i] = charToBoolean(intto.charAt(i));
		}
		return bits;
	}

	public static float log(float f) {
		return (float) Math.log(f);
	}

	public static float sqrt(float f) {
		return (float) Math.sqrt(f);
	}

	private static Scanner scan = null;

	private static void init() {
		if (scan == null)
			scan = new Scanner(System.in);
	}

	public static String takeInputString() {
		init();
		return scan.nextLine();
	}

	public static int takeInputInteger() throws Exception {
		init();
		return Int(scan.nextLine());
	}

	public static String[] takeInputStringArray(int amnt) {
		init();
		String[] strings = new String[amnt];
		for (int i = 0; i < amnt; i++) {
			strings[i] = scan.nextLine();
		}
		return strings;
	}

	public static int[] takeInputIntegerArray(String splitter, String[] toRemove)
			throws Exception {
		init();
		String orig = scan.nextLine();
		for (String s : toRemove) {
			orig = orig.replace(s, "");
		}
		String[] val = orig.split(splitter);
		int[] values = new int[val.length];
		for (int i = 0; i < val.length; i++) {
			values[i] = Int(val[i]);
		}
		return values;
	}

	public static float[] takeInputFloatArray(String splitter, String[] toRemove)
			throws Exception {
		init();
		String orig = scan.nextLine();
		for (String s : toRemove) {
			orig = orig.replace(s, "");
		}
		String[] val = orig.split(splitter);
		float[] values = new float[val.length];
		for (int i = 0; i < val.length; i++) {
			values[i] = Float(val[i]);
		}
		return values;
	}

	public static int Int(String s) throws Exception {
		init();
		return Integer.parseInt(s);
	}

	public static float Float(String s) throws Exception {
		init();
		return Float.parseFloat(s);
	}

	public static String readFile(String fileName) throws IOException {
		{
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			return new String(encoded, Charset.defaultCharset());
		}
	}

	public static String[] readFileLineByLine(String fileName)
			throws IOException {
		return readFile(fileName).split("\n");
	}

	public static long lrandom(long seed) {
		long lseed = (seed * 1103515245 + 12345) & 0x7FFFFFFF;
		return lseed;
	}

	private static long lastTime = System.currentTimeMillis();
	private static int times = 0;

	public static long lrandom() {
		long lseed;
		if (System.currentTimeMillis() == lastTime) {
			lseed = (System.currentTimeMillis() - lastTime + times * 1103515245 + 12345) & 0x7FFFFFFF;
			times++;
		} else {
			times = 0;
			lseed = (System.currentTimeMillis() - lastTime * 1103515245 + 12345) & 0x7FFFFFFF;
		}
		lastTime = System.currentTimeMillis();
		return lseed;
	}

	public static float frandom(long seed) {
		long r = lrandom(seed) >> (31 - 24);
		return r / (float) (1 << 24);
	}

	private static float y2;
	private static int use_last = 0;

	public static float grandom(float mean, float stdDeviation, long seed) {
		float x1, x2, w, y1;

		if (use_last != 0) {
			y1 = y2;
			use_last = 0;
		} else {
			do {
				x1 = 2.0f * frandom(seed) - 1.0f;
				x2 = 2.0f * frandom(seed) - 1.0f;
				w = x1 * x1 + x2 * x2;
			} while (w >= 1.0f);
			w = sqrt((-2.0f * log(w)) / w);
			y1 = x1 * w;
			y2 = x2 * w;
			use_last = 1;
		}
		return mean + y1 * stdDeviation;
	}

	private static enum OSType {
		Windows, MacOS, Linux, Other
	};

	private static final class OsCheck {
		/**
		 * types of Operating Systems
		 */

		// cached result of OS detection
		protected static OSType detectedOS;

		/**
		 * detect the operating system from the os.name System property and
		 * cache the result
		 * 
		 * @returns - the operating system detected
		 */
		public static OSType getOperatingSystemType() {
			if (detectedOS == null) {
				String OS = System.getProperty("os.name", "generic")
						.toLowerCase(Locale.ENGLISH);
				if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
					detectedOS = OSType.MacOS;
				} else if (OS.indexOf("win") >= 0) {
					detectedOS = OSType.Windows;
				} else if (OS.indexOf("nux") >= 0) {
					detectedOS = OSType.Linux;
				} else {
					detectedOS = OSType.Other;
				}
			}
			return detectedOS;
		}
	}

	public static int exec(String command, int iostream, int errstream)
			throws IOException {
		Process cmdProc = Runtime.getRuntime().exec(command);

		BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(
				cmdProc.getInputStream()));
		String line;
		while ((line = stdoutReader.readLine()) != null) {
			print(line, iostream);
		}

		BufferedReader stderrReader = new BufferedReader(new InputStreamReader(
				cmdProc.getErrorStream()));
		while ((line = stderrReader.readLine()) != null) {
			print(line, errstream);
		}

		int retValue = cmdProc.exitValue();
		return retValue;
	}

	public static boolean isStreamEmpty(int stream) {
		stream -= 2;
		if (stream < 0)
			return false;
		return streams.get(stream).isEmpty();
	}
	
	public static String getStream(int stream, int pos){
		if(stream < 2)return "";
		return streams.get(stream-2)
				.get(pos);
	}

}
