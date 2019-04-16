package NewGUITest;

import java.awt.*;
import javax.swing.JLabel;

public class CommentsTab {
	
	static JLabel text = new JLabel("");
	
	public CommentsTab() {
		
	}
	public static Panel init() {
		Panel text_panel = new Panel(new GridLayout(0,3));
		text_panel.add(text);
		text_panel.add(new JLabel(""));
		text_panel.add(new JLabel(""));
		text_panel.add(new JLabel(""));
		return text_panel;
	}
}