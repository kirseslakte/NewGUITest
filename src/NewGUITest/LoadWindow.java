package NewGUITest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Frame;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadWindow extends Frame {
	private Frame loadFrame;
	public String nation_name = "";
	public String feedback = "";
	public boolean running = false;
	ReadNWrite writer = new ReadNWrite();
	
	public LoadWindow() {
		loadFrame = new Frame("Load Nation");
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//center frame on screen
	    int x = (int) ((dimension.getWidth() - loadFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - loadFrame.getHeight()) / 2);
	    loadFrame.setLocation(x, y);
		loadFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		ReadNWrite write = new ReadNWrite();
		loadFrame.setSize(600, (int) Math.floor(write.n_saves/3+1)*75);//x,y
		Panel loadPnl = new Panel();
		loadPnl.setLayout(new GridLayout((int) Math.floor(write.n_saves/3+1),3));//setting up grid for loading buttons
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
		for (int i=0;i<write.n_saves;i++){//adding all buttons
			Button btn = new Button(write.save_names[i]);
			btn.setName(write.save_names[i]);
			btnList.add(btn);
			btnList.get(i).addActionListener(listener);//add action event to new button
			loadPnl.add(btn);
		}
		loadFrame.add(loadPnl);	
	}
	
	public void start() {
		running = true;
		loadFrame.setVisible(true);
	}
	
	public void stop() {
		running = false;
		loadFrame.setVisible(false);
	}
	// new nation pop-up
	public void startNation() {
		ReadNWrite writer = new ReadNWrite();
		TextField name = new TextField("Type Nation Name Here");
		Panel newNation = new Panel(new GridLayout(0,1));
		newNation.add(name);
		running = true;
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
					feedback = "Back";
					new_nation_creation = false;
				}
			}
			if (new_nation_creation) {
				writer.setNationName(nation_name);
				feedback = "New";
			}
		} else {
			feedback = "Back";
		}
	}
}