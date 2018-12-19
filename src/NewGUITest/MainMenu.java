package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class MainMenu extends Frame {
	//add components that will go in the frame
	private Frame menuFrame;
	private Panel menuPnl;
	private Button newBtn;
	private Button loadBtn;
	private Button quitBtn;
	public String choice = "";
	public boolean running = false;
	public String nation_name;
	NewNation namenation = new NewNation();
	
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
				choice = "New";
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
		running = true;
		menuFrame.setVisible(true);
	}
	
	public void Stop() {
		choice = "";
		nation_name = "";
		menuFrame.setVisible(false);
	}
	/////THIS IS ANOTHER FRAME AND WINDOW ALLTOGHETHER. IT IS HERE BECAUSE THERE WAS SPACE AND PALLA WITH NEW .JAVA FILES//////
	//These are the create new nation methods
	public void StartNation() {
		namenation.Start();;
	}
	public void StopNation() {
		namenation.Stop();
	}
	
	public class NewNation extends Frame{//called whenever a new nation is about to be named
		private Frame nameFrame;
		private Panel namePanel;
		private TextField txtfield;
		private Button btn;
		
		public NewNation() {
			ReadNWrite writer = new ReadNWrite();
			nameFrame = new Frame("Name Your Nation");
			nameFrame.setSize(200,600);//x,y
			nameFrame.addWindowListener(new WindowAdapter() {//close program on closing window
				public void windowClosing(WindowEvent windowEvent){
					System.exit(0);
				}
			});
			namePanel = new Panel();//set out panel
			namePanel.setLayout(new GridLayout(2,1));//set layout
			txtfield = new TextField("Type Nation Name Here");
			namePanel.add(txtfield);
			btn = new Button("Create Nation!");//adding buttons
			namePanel.add(btn);
			nameFrame.add(namePanel);
			btn.addActionListener(new ActionListener() {//add action event to new button
				public void actionPerformed(ActionEvent e){
					choice = "Main";
					nation_name = txtfield.getText();
					writer.setSaveName(nation_name);
					running = false;
				}
			});
		}
		
		public void Start() {
			nation_name = "";
			running = true;
			nameFrame.setVisible(true);
		}
		public void Stop() {
			nation_name = "";
			choice = "";
			nameFrame.setVisible(false);
		}
	}
}