package NewGUITest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends Frame {
	//add components that will go in the frame
	private Frame menuFrame;
	private Panel menuPnl;
	private Button newBtn;
	private Button loadBtn;
	private Button quitBtn;
	public String choice = "";
	public boolean running = true;
	
	public MainMenu() {//constructor constructs the frame
		menuFrame = new Frame("Main Menu");
		menuFrame.setSize(200,600);//x,y
		menuFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		menuPnl = new Panel();//set out panel
		menuPnl.setLayout(new GridLayout(3,1));//set layout
		newBtn = new Button("New Nation");//adding buttons
		menuPnl.add(newBtn);
		loadBtn = new Button("Load Nation");
		menuPnl.add(loadBtn);
		quitBtn = new Button("Quit");
		menuPnl.add(quitBtn);
		menuFrame.add(menuPnl);
		newBtn.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				choice = "Main";
				running = false;
			}
		});
		loadBtn.addActionListener(new ActionListener() {//add action event to load button
			public void actionPerformed(ActionEvent e){
				choice = "Load";
				running = false;
			}
		});
		quitBtn.addActionListener(new ActionListener() {//add action event to quit button
			public void actionPerformed(ActionEvent e){
				choice = "Quit";
				running = false;
			}
		});
	}
	
	public void Start() {
		menuFrame.setVisible(true);
	}
	
	public void Stop() {
		menuFrame.setVisible(false);
	}
}