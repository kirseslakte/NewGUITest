package NewGUITest;

import java.util.List;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
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
	public String[] built_buildings;
	public String owner;
	
	public int[] building_cost;	//all of these are in a sense read variables
	public int upgrade_cost;	//not sure if these should be here and include some sort of update method
	public int upkeep;			//or if it should be rolled into hexmap
	public int base_production;	//probably here since we actually have space here
	public int base_bp;
	public int population_value;
	public int unit_cap;
	public int govnm_upkeep;
	
	public int[] list_pv = {1,2,4,8,16,32,50,70,80,100};//all of these are rules
	public double[] list_pm = {0.25,0.5,1,1.5,2,2.75,3.5,4.25,5.25,6.5};
	public double[] list_upgrade_cost = {500,1000,5000,20000,70000,150000,300000,400000,600000};
	public String[] alignments = {"LG","NG","CG","LN","NN","CN","LE","NE","CE"};
	public double base_pm = 250;
	
	public Hex() {

	}
	
	public void setHex(String[] s){//assumes you have erased the separator
		this.name = s[0];
		this.owner = s[1];
		this.habitability = Double.parseDouble(s[2]);
		this.alignment = s[3];
		this.religion = s[4];
		this.pop_size = Integer.parseInt(s[5]);
		this.unrest = Integer.parseInt(s[6]);
		this.resource = s[7];
		String[] build_holder = Arrays.copyOfRange(s, 8, s.length);
		int number_of_buildings = 0;
		for (int i=0;i<build_holder.length;i++){
			if (build_holder[i].equals("")){
				number_of_buildings = i;
				break;
			}		//buildings look like this String building Int tier
		}			//fortifications look like this String fortification Int tier String 
		this.built_buildings = new String[number_of_buildings];
		for (int i=0;i<number_of_buildings;i++){
			this.built_buildings[i] = build_holder[i];
		}
		//by passing a string to Hex it will automatically create the hex
		this.population_value = list_pv[this.pop_size-1];
		this.base_bp = (int) Math.round(list_pm[this.pop_size-1]*base_pm*this.habitability);
		if (!(this.pop_size==10))
			this.upgrade_cost = (int) Math.round(list_upgrade_cost[this.pop_size-1]);//will be affected by buildings and culture as well
		else
			this.upgrade_cost = 0;
		this.base_production = this.base_bp;//affected by culture boni,culture/religion,unrest,government,resources,buildings
	}
}