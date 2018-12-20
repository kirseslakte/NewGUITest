package NewGUITest;

import java.awt.*;
import java.awt.event.*;

public class MainWindow extends Frame {
	private Frame mainFrame;
	private Panel mainPnl;
	private Button newVassalBtn;
	private Button loadBtn;
	private Button saveBtn;
	private Button quitBtn;
	public String choice = "";
	public boolean running = false;
	ReadNWrite write = new ReadNWrite();
	
	public MainWindow() {
		mainFrame = new Frame();		
		mainFrame.setSize(1500,1000);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
	    mainFrame.setLocation(x, y);
		mainFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		mainPnl = new Panel();//set out panel
		mainPnl.setLayout(new GridLayout(4,1));//set layout
		newVassalBtn = new Button("New Vassal");//adding buttons
		mainPnl.add(newVassalBtn);
		saveBtn = new Button("Save Nation");
		mainPnl.add(saveBtn);//new vassal and save nation still has no functionality to them.
		loadBtn = new Button("Load Nation");
		mainPnl.add(loadBtn);
		quitBtn = new Button("Quit");
		mainPnl.add(quitBtn);
		mainFrame.add(mainPnl);/*
		newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				choice = "Main";
				running = false;
			}
		});*/
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
	
	
	public void Start(String s) {
		running = true;
		mainFrame.setTitle(s);
		mainFrame.setVisible(true);
	}
	
	public void Stop() {
		choice = "";
		mainFrame.setVisible(false);
	}
}