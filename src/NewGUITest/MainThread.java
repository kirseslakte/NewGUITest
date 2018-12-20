package NewGUITest;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MainThread {
	public static String nation_name;
	
	public static void main(String[] args) {
		File nations = new File("Nations");
		nations.mkdirs();
		MainMenu menuWindow = new MainMenu();
		MainWindow mainWindow = new MainWindow();
		LoadWindow loadWindow = new LoadWindow();
		ReadNWrite writer = new ReadNWrite();
		menuWindow.Start();
		while(true){
			while (menuWindow.running){//main menu loop
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}
			}
			while (mainWindow.running){//main window loop
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}
				//will update every second and save every 30 seconds?
			}
			while (loadWindow.running){//load window loop
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}
				System.out.println(writer.updateSaves());//prints the number of save folders.
			}
			if (menuWindow.choice == "Main"||loadWindow.choice == "Main"){//going to the main window
				if ("".equals(loadWindow.nation_name))//setting the name of the current government displayed in mainWindow
					nation_name = menuWindow.nation_name;
				else if ("".equals(menuWindow.nation_name))
					nation_name = loadWindow.nation_name;
				menuWindow.Stop();
				loadWindow.Stop();
				mainWindow.Start(nation_name);
			}else if (menuWindow.choice == "Load"||mainWindow.choice=="Load"){//going to the load saves window
				mainWindow.Stop();
				menuWindow.Stop();
				loadWindow.Start();
			}else if (menuWindow.choice == "Quit"||mainWindow.choice=="Quit"){//quitting the application
				System.exit(0);
			}
		}
	}
	
	
	/*
	public MainThread() {
		MainWindow window = new MainWindow();
	}*/

}