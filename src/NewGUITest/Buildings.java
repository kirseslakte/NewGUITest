package NewGUITest;

import java.util.List;
import java.util.ArrayList;

public class Buildings {
	
	String type = "";
	String name = "";
	String officialtext = "";
	int tier = 0;
	int cost = 0;
	int upkeep = 0;
	List<String> add_ons = new ArrayList<String>();
	
	double[] modifiers = new double[3];//bp%,bpflat,upk
	
	public Buildings() {
		for (int i=0;i<this.modifiers.length;i++)
			this.modifiers[i] = 0;
	}
	
	public class AddOn {
		
		String name = "";
		String officialtext = "";
		int amount = 0;
		int cost = 0;
		int upkeep = 0;
		
		public AddOn() {
			
		}
		
		//methods for finding the costs and upkeeps (and officials) for add-ons
	}
	
	//methods for finding the costs and upkeeps (and officials) for buildings
}