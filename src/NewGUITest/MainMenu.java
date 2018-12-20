package NewGUITest;

import java.awt.*;
import javax.swing.JOptionPane;
import java.awt.event.*;

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
	//NewNation namenation = new NewNation();
	
	public MainMenu() {//constructor constructs the frame
		menuFrame = new Frame("Main Menu");
		menuFrame.setSize(200,600);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - menuFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - menuFrame.getHeight()) / 2);
	    menuFrame.setLocation(x, y);
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
				startNation();
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
	//new nation pop-up
	public void startNation() {
		ReadNWrite writer = new ReadNWrite();
		TextField name = new TextField("Type Nation Name Here");
		Panel newNation = new Panel(new GridLayout(0,1));
		newNation.add(name);
		int result = JOptionPane.showConfirmDialog(null, newNation, "Create a new nation",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result==JOptionPane.OK_OPTION){
			choice = "Main";
			nation_name = name.getText();
			writer.setSaveName(nation_name);
			running = false;
		}else{
			choice = "Menu";
		}
	}
}