package NewGUITest;

import java.util.List;
import java.util.Arrays;

public class Hex {
	public String name;//all of these are in a sense input variables
	public double habitability;
	public String alignment;
	public String religion;
	public int pop_size;
	public int unrest;
	public String resource;
	public String[] buildings;
	
	public int[] building_cost;	//all of these are in a sense read variables
	public int upgrade_cost;	//not sure if these should be here and include some sort of update method
	public int upkeep;			//or if it should be rolled into hexmap
	public int base_production;	//probably here since we actually have space here
	public int population_value;
	public int unit_cap;
	public int govnm_upkeep;
	
	public Hex() {

	}
	
	public void setHex(List<String> list){//assumes you have erased the separator
		String[] s = new String[list.size()];
		s = list.toArray(s);
		this.name = s[0];
		this.habitability = Double.parseDouble(s[1]);
		this.alignment = s[2];
		this.religion = s[3];
		this.pop_size = Integer.parseInt(s[4]);
		this.unrest = Integer.parseInt(s[5]);
		this.resource = s[6];
		this.buildings = Arrays.copyOfRange(s, 7, s.length);//by passing a string to Hex it will automatically create the hex
	}
}