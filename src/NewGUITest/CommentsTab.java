package NewGUITest;

import java.awt.*;
import javax.swing.JLabel;

public class CommentsTab {
	
	static Panel text_panel = new Panel(new GridLayout(1,1));
	static JLabel text = new JLabel("");
	
	public CommentsTab() {
		
	}
	public static void init() {
		text_panel.add(text);
		text_panel.revalidate();
	}
}