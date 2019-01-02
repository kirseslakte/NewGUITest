package NewGUITest;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MainThread {
	public static String nation_name;
	
	public static void main(String[] args) {
		//FIRST INITIALIZATIONS
		File nations = new File("Nations");
		nations.mkdirs();
		MainMenu menuWindow = new MainMenu();
		LoadWindow loadWindow = new LoadWindow();
		NationHandler handler = new NationHandler();
		menuWindow.start();
		boolean thread_handover = false;
		//THE MAIN THREAD
		while(!thread_handover){
			
			//THE MAIN MENU LOOP
			
			while (menuWindow.running){
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}//three alternatives from main menu
				if (menuWindow.feedback.equals("New")){//new->startNation in LoadWindow
					loadWindow.startNation();
					menuWindow.stop();
				}else if (menuWindow.feedback.equals("Load")){//load->LoadWindow
					menuWindow.stop();
					loadWindow.start();
				}else if (menuWindow.feedback.equals("Quit"))//quit
					System.exit(0);
			}
			
			//THE LOAD WINDOW LOOP
			
			while (loadWindow.running){
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch (InterruptedException e){
					System.out.println(e);
				}//three alternatives from load menu
				if (loadWindow.feedback.equals("New")){//pass over responsibility to nationhandlers main thread
					handler.createNation(loadWindow.nation_name);//first we create a new nation
					loadWindow.stop();
					handler.mainThread();
					thread_handover = true;
				}else if (loadWindow.feedback.equals("Load")){//pass over responsibility to nationhandlers main thread
					handler.loadNation(loadWindow.nation_name);
					loadWindow.stop();
					handler.mainThread();
					thread_handover = true;
				}else if (loadWindow.feedback.equals("Back")) {//quit
					loadWindow.stop();
					menuWindow.start();
				}
			}			
		}//END OF MAIN THREAD
	}
}