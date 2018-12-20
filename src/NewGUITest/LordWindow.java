package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LordWindow extends JFrame {
	private JFrame lordFrame;
	ReadNWrite write = new ReadNWrite();
	public Lord lord;
	public boolean save_request = false;
	
	public LordWindow() {
		
		lordFrame = new JFrame();		
		lordFrame.setSize(1500,1000);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - lordFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - lordFrame.getHeight()) / 2);
	    lordFrame.setLocation(x, y);
	    lordFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
	    
		////SETTING UP THE MENU BAR////
	    
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");		//file menu
		JMenuItem menuItem;					
		menuItem = new JMenuItem("Save");	//save button
		Action saveAction = new AbstractAction("Save") {
			public void actionPerformed(ActionEvent e) {
				save_request = true;
				//execute save things
			}
		};
		saveAction.putValue(Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menuItem.setAction(saveAction);//done setting the save button
		menuItem = new JMenuItem("Quit");	//quit button
		Action quitAction = new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		quitAction.putValue(Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		menuItem.setAction(quitAction);//done setting the quit button
		lordFrame.setJMenuBar(menuBar);
		
	    ////DONE WITH THE MENU BAR////
		////SETTING UP PANEL1////
		
		Panel mainPnl = new Panel();//set out panel
		mainPnl.setLayout(new GridLayout(4,1));//set layout
		Button newVassalBtn = new Button("New Vassal");//adding buttons
		mainPnl.add(newVassalBtn);
		Button saveBtn = new Button("Save Nation");
		mainPnl.add(saveBtn);//new vassal and save nation still has no functionality to them.
		Button loadBtn = new Button("Load Nation");
		mainPnl.add(loadBtn);
		Button quitBtn = new Button("Quit");
		mainPnl.add(quitBtn);
		lordFrame.add(mainPnl);
		
		////DONE SETTING UP PANEL1////
		
		/*
		newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
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
		});*/
	}
	
	
	public void Start(String s) {
		lordFrame.setTitle(s);
		lordFrame.setVisible(true);
	}
	
	public void Stop() {
		lordFrame.setVisible(false);
	}
}