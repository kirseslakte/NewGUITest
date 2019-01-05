package NewGUITest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadWindow extends Frame {
	public String nation_name = "";
	public String feedback = "";
	public boolean running = false;
	ReadNWrite writer = new ReadNWrite();
	
	public LoadWindow() {
		this.setTitle("Load Nation");
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//center frame on screen
	    this.setSize(600, (int) Math.floor(writer.n_saves/3+1)*75);//x,y
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    this.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		Panel loadPnl = new Panel();
		loadPnl.setLayout(new GridLayout((int) Math.floor(writer.n_saves/3+1),3));//setting up grid for loading buttons
		List<Button> btnList = new ArrayList<Button>();
		ActionListener listener = new ActionListener() {//creating standardized action listeners for all buttons in arraylist
			public void actionPerformed(ActionEvent e){
				if (e.getSource() instanceof Button){
					nation_name = ((Button) e.getSource()).getName();
					stop();
					feedback = "Load";
				}
			}
		};
		for (int i=0;i<writer.n_saves;i++){//adding all buttons
			Button btn = new Button(writer.save_names[i]);
			btn.setName(writer.save_names[i]);
			btnList.add(btn);
			btnList.get(i).addActionListener(listener);//add action event to new button
			loadPnl.add(btn);
		}
		this.add(loadPnl);	
	}
	
	public void start() {
		this.running = true;
		if (writer.n_saves==0) {
			this.feedback = "Back";
			JOptionPane.showMessageDialog(null, "There are no available saves!","Load Error",JOptionPane.INFORMATION_MESSAGE);
		}else {
			this.setVisible(true);
		}
	}
	
	public void stop() {
		this.running = false;
		this.feedback = "";
		this.setVisible(false);
	}
	// new nation pop-up
	public void startNation() {
		ReadNWrite writer = new ReadNWrite();
		TextField name = new TextField("Type Nation Name Here");
		Panel newNation = new Panel(new GridLayout(0,1));
		newNation.add(name);
		this.running = true;
		boolean new_nation_creation = true;
		writer.updateSaves();
		int result = JOptionPane.showConfirmDialog(null, newNation, "Create a new nation",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result==JOptionPane.OK_OPTION){
			nation_name = name.getText();
			for (String s: writer.save_names) {
				if (s.equals(nation_name)) {
					JOptionPane.showMessageDialog(null, "That Nation already exists!","Save error",
							JOptionPane.INFORMATION_MESSAGE);
					this.feedback = "Back";
					new_nation_creation = false;
				}
			}
			if (new_nation_creation) {
				this.feedback = "New";
			}
		} else {
			this.feedback = "Back";
		}
	}
}