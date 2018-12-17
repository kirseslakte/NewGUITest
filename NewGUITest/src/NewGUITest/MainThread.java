package NewGUITest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import java.awt.Frame;

public class MainThread {
	
	public static void main(String[] args) {
		File nations = new File("Nations");
		nations.mkdirs();
		MainMenu menuWindow = new MainMenu();
		MainWindow mainWindow = new MainWindow();
		LoadWindow loadWindow = new LoadWindow();
		ReadNWrite writer = new ReadNWrite();
		menuWindow.Start();
		while(true){
			while (menuWindow.running){
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}
				System.out.println(writer.updateSaves());
			}
			if (menuWindow.choice == "Main"){
				menuWindow.Stop();
				mainWindow.Start();
				menuWindow.choice = "";
			}else if (menuWindow.choice == "Load"){
				menuWindow.Stop();
				loadWindow.Start();
				menuWindow.choice = "";
			}else if (menuWindow.choice == ""){
				
			}else if (menuWindow.choice == "Quit"){
				System.exit(0);
			}
		}
	}
	
	public MainThread() {
		MainWindow window = new MainWindow();
	}
	
	
	
}