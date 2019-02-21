package NewGUITest;

import javax.swing.*;

public class UnitWindow extends JFrame{
	static int current_unit_number;
	static Unit current_unit;
	
	public UnitWindow() {
		
	}
	
	
	public static void loadActiveUnit() {
		current_unit = NationHandler.listofunits.get(current_unit_number);
		
	}
	
	public void start(int unit) {
		current_unit_number = unit;
		loadActiveUnit();
		this.setVisible(true);
	}
	
	public void stop() {
		this.setVisible(false);
	}
}