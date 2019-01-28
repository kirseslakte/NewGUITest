package NewGUITest;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadWindow extends Frame {
	public static String nation_name = "";
	static MainMenu main = new MainMenu();
	
	public LoadWindow() {
		//System.out.println("LOADWINDOW! LoadWindow");
		ReadNWrite.updateSaves();
		this.setTitle("Load Nation");
	    this.setSize(600, (int) Math.floor(ReadNWrite.n_saves/3+1)*75);
	    this.setLocationRelativeTo(null);
	    this.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				stop();
				main.start();
			}
		});
		Panel loadPnl = new Panel();
		loadPnl.setLayout(new GridLayout((int) Math.floor(ReadNWrite.n_saves/3+1),3));//setting up grid for loading buttons
		List<Button> btnList = new ArrayList<Button>();
		ActionListener listener = new ActionListener() {//creating standardized action listeners for all buttons in arraylist
			public void actionPerformed(ActionEvent e){
				if (e.getSource() instanceof Button){
					nation_name = ((Button) e.getSource()).getName();
					stop();
					NationHandler.loadNation(nation_name);
				}
			}
		};
		for (int i=0;i<ReadNWrite.n_saves;i++){//adding all buttons
			Button btn = new Button(ReadNWrite.save_names[i]);
			btn.setName(ReadNWrite.save_names[i]);
			btnList.add(btn);
			btnList.get(i).addActionListener(listener);//add action event to new button
			loadPnl.add(btn);
		}
		this.add(loadPnl);	
	}
	
	public void start() {
		//System.out.println("LOADWINDOW! start");
		if (ReadNWrite.n_saves==0) {
			main.start();
			JOptionPane.showMessageDialog(null, "There are no available saves!","Load Error",JOptionPane.INFORMATION_MESSAGE);
		}else {
			this.setVisible(true);
		}
	}
	
	public void stop() {
		//System.out.println("LOADWINDOW! stop");
		this.setVisible(false);
	}
	
	// new nation pop-up
	public void startNation() {
		//System.out.println("LOADWINDOW! startNation");
		TextField name = new TextField("Type Nation Name Here");
		Panel newNation = new Panel(new GridLayout(0,1));
		newNation.add(name);
		boolean new_nation_creation = true;
		ReadNWrite.updateSaves();
		int result = JOptionPane.showConfirmDialog(null, newNation, "Create a new nation",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result==JOptionPane.OK_OPTION){
			nation_name = name.getText();
			for (String s: ReadNWrite.save_names) {
				if (s.equals(nation_name)) {
					JOptionPane.showMessageDialog(null, "That Nation already exists!","Save error",
							JOptionPane.INFORMATION_MESSAGE);
					main.start();
					new_nation_creation = false;
				}
			}
			if (new_nation_creation) {
				NationHandler.createNation(nation_name);
			}
		} else {
			main.start();
		}
	}
}