package org.watson.lang;

import static org.watson.lang.NewLang.*;

import java.io.IOException;

public class NewlangTest {

	public static void main(String[] args) throws IOException {
		if (_LINUX_)
			System.out.println("linux");
		if (_WINDOWS_)
			System.out.println("windows");
		if (_MAC_)
			System.out.println("mac");
	}

}
