package NewGUITest;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends Frame {
	//add components that will go in the frame
	public LoadWindow load = new LoadWindow();
	
	public MainMenu() {//constructor constructs the frame
		System.out.println("MAINMENU! MainMenu");
		this.setTitle("Main Menu");
		this.setSize(400,600);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {//close program on closing window
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
		this.add(menuPnl);
		newBtn.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				stop();
				load.startNation();
			}
		});
		loadBtn.addActionListener(new ActionListener() {//add action event to load button
			public void actionPerformed(ActionEvent e){
				stop();
				load.start();
			}
		});
		quitBtn.addActionListener(new ActionListener() {//add action event to quit button
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
	
	public void start() {
		System.out.println("MAINMENU! start");
		this.setVisible(true);
	}
	
	public void stop() {
		System.out.println("MAINMENU! stop");
		this.setVisible(false);
	}
}