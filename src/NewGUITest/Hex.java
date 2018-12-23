package NewGUITest;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Hex {
	public String name;//all of these are in a sense input variables
	public double habitability;
	public String alignment;
	public String religion;
	public int pop_size;
	public int unrest;
	public String resource;
	//public List<String> buildings = new ArrayList<String>();
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
	
	public void setHex(String[] s){//assumes you have erased the separator
		this.name = s[0];
		this.habitability = Double.parseDouble(s[1]);
		this.alignment = s[2];
		this.religion = s[3];
		this.pop_size = Integer.parseInt(s[4]);
		this.unrest = Integer.parseInt(s[5]);
		this.resource = s[6];
		String[] build_holder = Arrays.copyOfRange(s, 7, s.length);
		int number_of_buildings = 0;
		for (int i=0;i<build_holder.length;i++){
			if (build_holder[i].equals("")){
				number_of_buildings = i;
				break;
			}		//buildings look like this String building Int tier
		}			//fortifications look like this String fortification Int tier String 
		this.buildings = new String[number_of_buildings];
		for (int i=0;i<number_of_buildings;i++){
			this.buildings[i] = build_holder[i];
		}
		//by passing a string to Hex it will automatically create the hex
	}
}