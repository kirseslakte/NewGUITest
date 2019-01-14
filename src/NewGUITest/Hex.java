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
	public boolean resource_check;
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
	public int building_upkeep;
	public int building_upgrade;
	
	public static int[] list_pv = {1,2,4,8,16,32,50,70,80,100};//all of these are rules
	public static double[] list_pm = {0.25,0.5,1,1.5,2,2.75,3.5,4.25,5.25,6.5};
	public static int[] list_upgrade_cost = {500,1000,5000,20000,70000,150000,300000,400000,600000};//if pop_size==10{upg_cost = 0}
	public static int[] list_upkeep_cost = {100,150,225,1000,1500,2500,3600,4800,6000,8000};
	public static int[] list_unit_cap = {5,8,12,17,24,34,46,60,100,175};
	public static String[] alignments = {"LG","NG","CG","LN","NN","CN","LE","NE","CE"};
	public static String[] active_resources = {"Mithril","Adamantine","Baatorian Green Steel","Cold Iron","Astral Driftmetanl"};
	public static int[] active_resources_income = {300,200,200,300,400,400};
	public static String[] passive_resources = {"Darkwood","Precious Metals","Spices","Silk","Incense","Cotton","Herbs","Marble","Sugar","Gemstones/Diamonds",
			"Ivory","High Quality Iron","High Quality Stone","High Quality Paper","High Quality Glass","Furs","Drugs"};
	public static String[] resources = {"","Mithril","Adamantine","Baatorian Green Steel","Cold Iron","Astral Driftmetanl","Darkwood","Precious Metals",
			"Spices","Silk","Incense","Cotton","Herbs","Marble","Sugar","Gemstones/Diamonds","Ivory","High Quality Iron","High Quality Stone",
			"High Quality Paper","High Quality Glass","Furs","Drugs"};
	public static double base_pm = 250;
	public String[] buildinglist;
	public String[] fortificationlist;
	public String[] wallslist;
	public String[] addonlist;
	public String[] walladdonlist;
	public int[] building_costs;
	public int[] fortifications_costs;
	public int[] add_ons_costs;
	public int[] walls_costs;
	public int[] walls_add_costs;
	
	NationHandler getter = new NationHandler();
	
	public Hex() {
		
	}
	
	public Hex(String[] s) {
		this.loadHex(s);
	}
	
	public void updateHex() {
		System.out.println("HEX! updateHex");
		Utility ut = new Utility();
		double rgo_mod = 0;
		for (String s:buildings) {
			String[] splitter = ut.stringSplitter(s, "-");
			if (splitter[1].equals("RGO"))
				rgo_mod += 0.25*Integer.parseInt(splitter[2]);
			if (splitter[1].equals("Road")||splitter[1].equals("Highway"))
				rgo_mod += 0.1;
		}
		if (this.pop_size>9)
			this.upgrade_cost = 0;
		else
			this.upgrade_cost = (int) Math.round(list_upgrade_cost[this.pop_size-1]*getter.listoflords.get(ut.findLord(owner)).modifiers[23]);
		this.base_bp = (int) Math.round(base_pm*habitability*list_pm[this.pop_size-1]);
		this.upkeep = (int) Math.round(list_upkeep_cost[this.pop_size-1]*getter.listoflords.get(ut.findLord(owner)).modifiers[22]);
		this.govnm_upkeep = (int) Math.round(base_pm*10*list_pm[this.pop_size-1]);
		this.population_value = list_pv[this.pop_size-1];
		this.unit_cap = (int) Math.round(list_unit_cap[this.pop_size-1]*(1+getter.listoflords.get(ut.findLord(owner)).modifiers[9]));
		this.base_production = (int) Math.round(this.base_bp*(1+rgo_mod+getter.listoflords.get(ut.findLord(owner)).modifiers[19]));
		//^affected by culture boni,culture/religion,unrest,government,resources,buildings
	}
	
	public void loadHex(String[] s){//assumes you have erased the separator
		System.out.println("HEX! loadHex");
		this.name = s[0];
		this.owner = s[1];
		this.habitability = Integer.parseInt(s[2]);
		this.alignment = s[3];
		this.religion = s[4];
		this.pop_size = Integer.parseInt(s[5]);
		this.unrest = Integer.parseInt(s[6]);
		this.resource = s[7];
		this.resource_check = Boolean.parseBoolean(s[8]);
		this.building_upkeep = Integer.parseInt(s[9]);
		this.building_upgrade = Integer.parseInt(s[10]);
		built_buildings = new String[s.length-11];//Arrays.copyOfRange(s, 9, s.length);
		for (int i=0;i<s.length-11;i++){
			built_buildings[i] = s[11+i];
			if (!(built_buildings[i].equals("")))
				buildings.add(built_buildings[i]);
		}
		updateHex();
	}
}