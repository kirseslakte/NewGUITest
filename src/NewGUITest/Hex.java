package NewGUITest;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Hex {
	public String name;//all of these are in a sense input variables
	public int habitability;
	public String alignment;
	public String religion;
	public int pop_size;
	public int unrest;
	public String resource;
	public List<String> buildings = new ArrayList<String>();
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
	
	public Buildings construction = new Buildings();
	
	public int[] list_pv = {1,2,4,8,16,32,50,70,80,100};//all of these are rules
	public double[] list_pm = {0.25,0.5,1,1.5,2,2.75,3.5,4.25,5.25,6.5};
	public double[] list_upgrade_cost = {500,1000,5000,20000,70000,150000,300000,400000,600000};//if pop_size==10{upg_cost = 0}
	public String[] alignments = {"LG","NG","CG","LN","NN","CN","LE","NE","CE"};
	public String[] active_resources = {"Mithril","Adamantine","Baatorian Green Steel","Cold Iron","Astral Driftmetanl"};
	public int[] active_resources_income = {300,200,200,300,400,400};
	public String[] passive_resources = {"Darkwood","Precious Metals","Spices","Silk","Incense","Cotton","Herbs","Marble","Sugar","Gemstones/Diamonds",
			"Ivory","High Quality Iron","High Quality Stone","High Quality Paper","High Quality Glass","Furs","Drugs"};
	public String[] resources;;
	public double base_pm = 250;
	public String[] buildinglist;
	public String[] fortificationlist;
	public String[] wallslist;
	public String[] addonlist;
	public String[] walladdonlist;
	
	public Hex() {
		List <String> temp = new ArrayList<String>(Arrays.asList(active_resources));
		temp.addAll(Arrays.asList(passive_resources));
		resources = new String[temp.size()+1];
		resources[0] = "";
		for (int i=1;i<temp.size()+1;i++)
			resources[i] = temp.get(i-1);//all resources in a string array
		buildinglist = new String[construction.available_buildings.length+1];
		buildinglist[0] = "";
		for (int i=1;i<construction.available_buildings.length+1;i++)
			buildinglist[i] = construction.available_buildings[i-1];//buildingslist done
		fortificationlist = new String[construction.available_fortifications.length+1];
		fortificationlist[0] = "";
		for (int i=1;i<construction.available_fortifications.length+1;i++)
			fortificationlist[i] = construction.available_fortifications[i-1];//fortificationslist done
		wallslist = new String[construction.available_walls.length+1];
		wallslist[0] = "";
		for (int i=1;i<construction.available_walls.length+1;i++)
			wallslist[i] = construction.available_walls[i-1];//wallslist done
		addonlist = new String[construction.available_add_ons.length+1];
		addonlist[0] = "";
		for (int i=1;i<construction.available_add_ons.length+1;i++)
			addonlist[i] = construction.available_add_ons[i-1];//addonlist done
		walladdonlist = new String[construction.available_wall_add_ons.length+1];
		walladdonlist[0] = "";
		for (int i=1;i<construction.available_wall_add_ons.length+1;i++)
			walladdonlist[i] = construction.available_wall_add_ons[i-1];//walladdonlist done
		
	}
	
	public void setHex() {
		
	}
	
	public void loadHex(String[] s){//assumes you have erased the separator
		this.name = s[0];
		this.owner = s[1];
		this.habitability = Integer.parseInt(s[2]);
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
		}			//fortifications look like this String fortification Int tier String add-on Int amount
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