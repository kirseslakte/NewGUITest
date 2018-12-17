package NewGUITest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadWindow extends Frame {
	private Frame loadFrame;
	
	public LoadWindow() {
		loadFrame = new Frame();
		loadFrame.setSize(200,200);//x,y
		loadFrame.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
	}
	
	
	public void Start() {
		loadFrame.setVisible(true);
	}
	
	public void Stop() {
		loadFrame.setVisible(false);
	}
}