package NewGUITest;

import java.util.ArrayList;
import java.util.List;

import java.awt.Panel;
import java.awt.Frame;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadWindow extends Frame {
	private Frame loadFrame;
	private Panel loadPnl;
	public boolean running = false;
	public String choice = "";
	public String nation_name;
	
	public LoadWindow() {
		loadFrame = new Frame();
		loadFrame.setSize(200,200);//x,y
		loadFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		ReadNWrite write = new ReadNWrite();
		loadPnl = new Panel();
		loadPnl.setLayout(new GridLayout((int) Math.floor(write.n_saves/3+1),3));//
		System.out.println(Math.floor(write.n_saves/3));
		List<Button> btnList = new ArrayList<Button>();
		ActionListener listener = new ActionListener() {//creating standardized action listeners for all buttons in arraylist
			public void actionPerformed(ActionEvent e){
				if (e.getSource() instanceof Button){
					nation_name = ((Button) e.getSource()).getName();
					System.out.println("Getting "+((Button) e.getSource()).getName()+" ready");
					choice = "Main";
					running = false;
				}
			}
		};
		for (int i=0;i<write.n_saves;i++){//adding all buttons
			Button btn = new Button(write.save_names[i]);
			btn.setName(write.save_names[i]);
			System.out.println("Created button for "+write.save_names[i]);
			btnList.add(btn);
			btnList.get(i).addActionListener(listener);//add action event to new button
			loadPnl.add(btn);
		}
		loadFrame.add(loadPnl);	
	}
	
	

	
	public void Start() {
		running = true;
		loadFrame.setVisible(true);
	}
	
	public void Stop() {
		choice = "";
		loadFrame.setVisible(false);
	}
}