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
	public static String[] active_resources = {"Mithril","Adamantine","Baatorian Green Steel","Cold Iron","Astral Driftmetal"};
	public static int[] active_resources_income = {300,200,200,300,400,400};
	public static String[] passive_resources = {"Darkwood","Precious Metals","Spices","Silk","Incense","Cotton","Herbs","Marble","Sugar","Gemstones/Diamonds",
			"Ivory","High Quality Iron","High Quality Stone","High Quality Paper","High Quality Glass","Furs","Drugs"};
	public static String[] resources = {"","Mithril","Adamantine","Baatorian Green Steel","Cold Iron","Astral Driftmetal","Darkwood","Precious Metals",
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
	
	public Hex() {
		
	}
	
	public Hex(String[] s) {
		this.loadHex(s);
	}
	
	public void updateHex() {
		//System.out.println("HEX! updateHex");
		double rgo_mod = 0;
		double culturemod = this.findMod(1);
		double religionmod = this.findMod(0);
		double unrestmod = -1*this.unrest*0.05;
		double trademod = 0;
		try {
			trademod = findCenterOfTradeContribution();
		} catch (ArrayIndexOutOfBoundsException e) {
			//System.out.println("Hex not found");
		}
		for (String s:buildings) {
			String[] splitter = Utility.stringSplitter(s, "-");
			if (splitter[1].equals("RGO"))
				rgo_mod += 0.25*Integer.parseInt(splitter[2]);
			if (splitter[1].equals("Road")||splitter[1].equals("Highway"))
				rgo_mod += 0.1;
		}
		if (this.pop_size>9)
			this.upgrade_cost = 0;
		else
			this.upgrade_cost = (int) Math.round(list_upgrade_cost[this.pop_size-1]*NationHandler.listoflords.get(Utility.findLord(this.owner)).modifiers[23]);
		this.base_bp = (int) Math.round(base_pm*habitability*list_pm[this.pop_size-1]);
		this.upkeep = (int) Math.round(list_upkeep_cost[this.pop_size-1]*NationHandler.listoflords.get(Utility.findLord(owner)).modifiers[22])+
				this.building_upkeep;
		this.govnm_upkeep = (int) Math.round(base_pm*5*list_pm[this.pop_size-1]);
		this.population_value = list_pv[this.pop_size-1];
		this.unit_cap = (int) Math.round(list_unit_cap[this.pop_size-1]*(1+NationHandler.listoflords.get(Utility.findLord(owner)).modifiers[9]));
		this.base_production = (int) Math.round(this.base_bp*(1+rgo_mod+NationHandler.listoflords.get(Utility.findLord(owner)).modifiers[8]+culturemod+religionmod+unrestmod)+trademod);
		//^affected by culture boni,culture/religion,unrest,government,resources,buildings
	}
	
	public void loadHex(String[] s){//assumes you have erased the separator
		//System.out.println("HEX! loadHex");
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
		this.updateHex();
	}
	
	public double findMod(int i) {
		double mod = 0;
		Lord lord = NationHandler.listoflords.get(Utility.findLord(this.owner));
		String lord_align = "";
		String hex_align = "";
		if (i==0) {
			hex_align = this.religion;
			lord_align = lord.government.religion;
		}else if (i==1) {
			hex_align = this.alignment;
			lord_align = lord.government.culture;
		}
		if (lord_align.equals("LG")) {//done
			if (hex_align.equals("LG")) {
				mod = 0.1;
			} else if (hex_align.equals("NG")||hex_align.equals("LN")) {
				mod = 0.05;
			} else if (hex_align.equals("CN")||hex_align.equals("NE")) {
				mod = -0.05;
			}else if (hex_align.equals("CE")) {
				mod = -0.1;
			}
		}else if (lord_align.equals("NG")) {//done
			if (hex_align.equals("NG")) {
				mod = 0.1;
			} else if (hex_align.equals("CG")||hex_align.equals("LG")) {
				mod = 0.05;
			} else if (hex_align.equals("LE")||hex_align.equals("CE")) {
				mod = -0.05;
			}else if (hex_align.equals("NE")) {
				mod = -0.1;
			}
		}else if (lord_align.equals("CG")) {//done
			if (hex_align.equals("CG")) {
				mod = 0.1;
			} else if (hex_align.equals("NG")||hex_align.equals("CN")) {
				mod = 0.05;
			} else if (hex_align.equals("LN")||hex_align.equals("NE")) {
				mod = -0.05;
			}else if (hex_align.equals("LE")) {
				mod = -0.1;
			}
		}else if(lord_align.equals("LN")) {//done
			if (hex_align.equals("LN")) {
				mod = 0.1;
			} else if (hex_align.equals("LG")||hex_align.equals("LE")) {
				mod = 0.05;
			} else if (hex_align.equals("CG")||hex_align.equals("CE")) {
				mod = -0.05;
			}else if (hex_align.equals("CN")) {
				mod = -0.1;
			}
		}else if (lord_align.equals("NN")) {//done
			if (hex_align.equals("NN")) 
				mod = 0.05;
		}else if (lord_align.equals("CN")) {//done
			if (hex_align.equals("CN")) {
				mod = 0.1;
			} else if (hex_align.equals("LG")||hex_align.equals("LE")) {
				mod = 0.05;
			} else if (hex_align.equals("CG")||hex_align.equals("CE")) {
				mod = -0.05;
			}else if (hex_align.equals("LE")) {
				mod = -0.1;
			}
		}else if(lord_align.equals("LE")) {//done
			if (hex_align.equals("LE")) {
				mod = 0.1;
			} else if (hex_align.equals("LN")||hex_align.equals("EN")) {
				mod = 0.05;
			} else if (hex_align.equals("CN")||hex_align.equals("NG")) {
				mod = -0.05;
			}else if (hex_align.equals("CG")) {
				mod = -0.1;
			}
		}else if (lord_align.equals("NE")) {//done
			if (hex_align.equals("NE")) {
				mod = 0.1;
			} else if (hex_align.equals("LE")||hex_align.equals("CE")) {
				mod = 0.05;
			} else if (hex_align.equals("LG")||hex_align.equals("CG")) {
				mod = -0.05;
			}else if (hex_align.equals("NG")) {
				mod = -0.1;
			}
		}else if (lord_align.equals("CE")) {//done
			if (hex_align.equals("CE")) {
				mod = 0.1;
			} else if (hex_align.equals("NE")||hex_align.equals("CN")) {
				mod = 0.05;
			} else if (hex_align.equals("NG")||hex_align.equals("LN")) {
				mod = -0.05;
			}else if (hex_align.equals("LG")) {
				mod = -0.1;
			}
		}
		return mod;
	}
	
	public double findCenterOfTradeContribution() {
		Lord lord = NationHandler.listoflords.get(Utility.findLord(this.owner));
		Lord overlord = NationHandler.listoflords.get(0);
		int k = Utility.findHex(this.name);
		boolean exists = false;
		for (String s:NationHandler.listofhexes.get(k).built_buildings) {
			if (s.contains("Center of Trade"))
				exists = true;
		}
		if (exists)
			return Math.max(lord.total_trade_value,overlord.total_trade_value)*0.2*lord.modifiers[2];
		return 0;
	}
}