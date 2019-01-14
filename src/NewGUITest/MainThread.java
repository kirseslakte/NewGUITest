package NewGUITest;

import java.io.File;

public class MainThread {
	
	public static void main(String[] args) {
		File nations = new File("Nations");
		nations.mkdirs();
		MainMenu main = new MainMenu();
		main.start();
	}
}