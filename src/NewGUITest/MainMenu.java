package NewGUITest;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends Frame {
	//add components that will go in the frame
	private Frame menuFrame;
	public String feedback = "";
	public boolean running = false;
	
	public MainMenu() {//constructor constructs the frame
		menuFrame = new Frame("Main Menu");
		menuFrame.setSize(400,600);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//center frame on screen
	    int x = (int) ((dimension.getWidth() - menuFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - menuFrame.getHeight()) / 2);
	    menuFrame.setLocation(x, y);
		menuFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		Panel menuPnl = new Panel();//set out panel
		menuPnl.setLayout(new GridLayout(3,1));//set layout
		Button newBtn = new Button("New Nation");//adding buttons
		menuPnl.add(newBtn);
		Button loadBtn = new Button("Load Nation");
		menuPnl.add(loadBtn);
		Button quitBtn = new Button("Quit");
		menuPnl.add(quitBtn);
		menuFrame.add(menuPnl);
		newBtn.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				feedback = "New";
			}
		});
		loadBtn.addActionListener(new ActionListener() {//add action event to load button
			public void actionPerformed(ActionEvent e){
				feedback = "Load";
			}
		});
		quitBtn.addActionListener(new ActionListener() {//add action event to quit button
			public void actionPerformed(ActionEvent e){
				feedback = "Quit";
			}
		});
	}
	
	public void start() {
		running = true;
		menuFrame.setVisible(true);
	}
	
	public void stop() {
		running = false;
		feedback = "";
		menuFrame.setVisible(false);
	}
}