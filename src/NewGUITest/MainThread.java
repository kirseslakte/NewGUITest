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
			while (menuWindow.running||mainWindow.running||loadWindow.running){
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}
				System.out.println(writer.updateSaves());
			}
			if (menuWindow.choice == "Main"||loadWindow.choice == "Main"){//going to the main window
				System.out.println(menuWindow.nation_name);
				System.out.println(loadWindow.nation_name);
				if ("".equals(loadWindow.nation_name))
					nation_name = menuWindow.nation_name;
				else if ("".equals(menuWindow.nation_name))
					nation_name = loadWindow.nation_name;
				System.out.println("Setting main frame name to "+nation_name);
				menuWindow.Stop();
				menuWindow.StopNation();
				loadWindow.Stop();
				mainWindow.Start(nation_name);
			}else if (menuWindow.choice == "Load"||mainWindow.choice=="Load"){//going to the load saves window
				mainWindow.Stop();
				menuWindow.StopNation();
				menuWindow.Stop();
				loadWindow.Start();
			}else if (menuWindow.choice == "New"){
				mainWindow.Stop();
				menuWindow.Stop();
				loadWindow.Stop();
				menuWindow.StartNation();
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