package NewGUITest;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadNWrite {
	
	public static String filetype = ".dat";
	private static String separator = "xxxxxx";
	public static String[] save_names;
	public static int n_saves = 0;
	public int number_of_building_slots = 18;
	public static File directory = new File("");
	LordPanes panes = new LordPanes();
	Culture bonuses = new Culture();
	
	public ReadNWrite() {
		//updateSaves();
	}
	
	public static int updateSaves() {//number of saves
		System.out.println("READNWRITE! updateSaves");
		String[] save_list = new File("Nations").list();//increased the requirements to be counted as a save
		List <String> complete_save_list = new ArrayList<String>();
		for (int i=0;i<save_list.length;i++) {//it has to have at least 3 files in its directories
			if (new File("Nations\\"+save_list[i]).list().length>3)
				complete_save_list.add(save_list[i]);
		}
		if (complete_save_list.size()==0){
			save_names = new String[0];
			n_saves = 0;
		}else {
			save_names = new String[complete_save_list.size()];
			save_names = complete_save_list.toArray(save_names);
			n_saves = save_names.length;//gets the number of existing saves.	
		}
		return n_saves;
	}
	
	public void setNationName(String s) {//create and name the current nation save
		System.out.println("READNWRITE! setNationName");
		directory = new File("Nations/"+s);
		directory.mkdir();
	}
	
	public void clean() {
		System.out.println("READNWRITE! clean");
		File file = new File("Nations");
		File nation;
		for (String s:file.list()) {
			nation = new File(file+"\\"+s);
			if (nation.list().length<6)
				nation.delete();
		}
	}
	////LOAD METHODS////
	
	//load lords
	
	public String[] loadLord(String title) {//reading a save file with nation file directory as input
		System.out.println("READNWRITE! loadLord");
		String[] lord = new String[26];
		File file = new File(directory+"\\"+title+filetype);
		try {
			Scanner sc = new Scanner(file);
			for (int i=0;i<lord.length;i++) {
				lord[i] = sc.nextLine();
			}
			sc.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lord;
	}
	
	//load culture
	
	public String[] loadCulture() {
		System.out.println("READNWRITE! loadCulture");
		String[] culture = new String[Culture.culture_names.length];
		File file = new File(directory+"\\culture"+filetype);
		try {
			Scanner sc = new Scanner(file);
			for (int i=0;i<Culture.culture_names.length;i++) {
				culture[i] = sc.nextLine();
			}
			sc.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return culture;
	}
	
	//load hexes
	
	public List<Hex> loadHexes() {//loading hexes!
		System.out.println("READNWRITE! loadHexes");
		List<Hex> listofhexes = new ArrayList<Hex>();
		String s = directory+"\\hexes"+filetype;
		File file = new File(s);
		int max_hex_length = number_of_building_slots+9;
		String[] hex_input = new String[max_hex_length];
		int hex_length = 0;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				s = sc.nextLine();
				if (s.equals(separator)){
					String[] ready_input = new String[hex_length];
					for (int i=0;i<hex_length;i++)
						ready_input[i] = hex_input[i];
					listofhexes.add(new Hex(ready_input));
					hex_length = 0;
					hex_input = new String[max_hex_length];
				}else {
					hex_input[hex_length] = s;
					hex_length++;
				}
			}
			sc.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return listofhexes;
	}
	
	//load units
	
	public List<Unit> loadUnits(){
		System.out.println("READNWRITE! loadUnits");
		List<Unit> listofunits = new ArrayList<Unit>();
		String s = directory+"\\units"+filetype;
		File file = new File(s);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
						
				//listofunits.add(hex);
			}
			sc.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return listofunits;
	}
	
	//load routes
	
	public List<Route> loadRoutes(File k) {
		System.out.println("READNWRITE! loadRoutes");
		List<Route> listofroutes = new ArrayList<Route>();
		String s = directory+"\\routes"+filetype;
		File file = new File(s);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
						
				//listofroutes.add(hex);
			}
			sc.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return listofroutes;
	}
	
	//load officials
	
	public List<Official> loadOfficials() {
		System.out.println("READNWRITE! loadOfficials");
		List<Official> listofofficials = new ArrayList<Official>();
		String s = directory+"/officials"+filetype;
		File file = new File(s);
		String[] official = new String[5];
		int i = 0;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				s = sc.nextLine();
				if (s.equals(separator)){
					listofofficials.add(new Official(official));
					i = 0;
				}else {
					official[i] = s;
					i++;
				}
			}
			sc.close();
		} catch(Exception e){
			System.out.println(e);
		}
		return listofofficials;
	}
	
	////SAVE METHODS////
	
	//Saving hex
	
	public void saveHexes(List<Hex> listofhexes) {//writing 
		System.out.println("READNWRITE! saveHexes");
		String s = directory+"\\hexes"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);//write all the data for the lord in order (see hexKey.txt)
			for (Hex hex: listofhexes){
				fw.write(hex.name+System.getProperty("line.separator"));
				fw.write(hex.owner+System.getProperty("line.separator"));
				fw.write(hex.habitability+System.getProperty("line.separator"));
				fw.write(hex.alignment+System.getProperty("line.separator"));
				fw.write(hex.religion+System.getProperty("line.separator"));
				fw.write(hex.pop_size+System.getProperty("line.separator"));
				fw.write(hex.unrest+System.getProperty("line.separator"));
				fw.write(hex.resource+System.getProperty("line.separator"));
				fw.write(hex.resource_check+System.getProperty("line.separator"));
				fw.write(hex.building_upkeep+System.getProperty("line.separator"));
				fw.write(hex.building_upgrade+System.getProperty("line.separator"));
				for (String building: hex.built_buildings){
					fw.write(building+System.getProperty("line.separator"));
				}
				fw.write(separator+System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	//saving lord
	
	public void saveLord(Lord lord) {//Writing the save file. needs to be called for each vassal?
		System.out.println("READNWRITE! saveLord");
		String s = directory+"\\"+lord.title+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);//write all the data for the lord in order (see lordKey.txt)
			fw.write(lord.name+System.getProperty("line.separator"));
			fw.write(lord.government.sys+System.getProperty("line.separator"));
			fw.write(lord.government.struc+System.getProperty("line.separator"));
			fw.write(lord.government.ruler+System.getProperty("line.separator"));
			fw.write(lord.government.style+System.getProperty("line.separator"));
			fw.write(lord.government.cent+System.getProperty("line.separator"));
			fw.write(lord.government.culture+System.getProperty("line.separator"));
			fw.write(lord.government.religion+System.getProperty("line.separator"));
			fw.write(lord.government.legitimacy+System.getProperty("line.separator"));
			fw.write(lord.master_title+System.getProperty("line.separator"));
			for (int i=0;i<lord.max_number_of_institutions;i++){
				fw.write(lord.institutes.active_institutions[i]+System.getProperty("line.separator"));
			}
			for (int i=0;i<lord.government.eco.length;i++) {
				fw.write(lord.government.eco[i]+System.getProperty("line.separator"));
			}
			for (int i=0;i<4;i++){
				fw.write(lord.government.histocratic_choices[i]+System.getProperty("line.separator"));
				fw.write(lord.government.hist_val[i]+System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
		if (lord.master_title.equals(""))//if the lord is overlord extract culture bonuses!
			saveCulture(lord);
	}
	
	//saving culture
	
	public void saveCulture(Lord lord) {
		System.out.println("READNWRITE! saveCulture");
		String s = directory+"\\culture"+filetype;
		File file = new File(s);
		file.delete();
		String[] cultures = bonuses.readCulture();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			for (int i=0;i<Culture.culture_names.length;i++){
				fw.write(cultures[i]+System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//saving units
	
	public void saveUnit(List<Unit> listofunits) {
		System.out.println("READNWRITE! saveUnit");
		String s = directory+"\\units"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			for (Unit unit:listofunits){
				fw.write(unit.name+System.getProperty("line.separator"));
				for (int i=0;i<unit.stats.length;i++) {//stats
					fw.write(Integer.toString(unit.stats[i])+System.getProperty("line.separator"));
				}
				for (int i=0;i<unit.stats.length;i++) {
					fw.write(Integer.toString(unit.stats_racial_mods[i])+System.getProperty("line.separator"));
				}
				for (int i=0;i<unit.stats.length;i++) {
					fw.write(Boolean.toString(unit.stats_used[i])+System.getProperty("line.separator"));
				}
				fw.write(unit.type+System.getProperty("line.separator"));
				fw.write(unit.subtype+System.getProperty("line.separator"));
				fw.write(unit.training+System.getProperty("line.separator"));
				fw.write(unit.training_type+System.getProperty("line.separator"));
				fw.write(unit.size+System.getProperty("line.separator"));
				fw.write(unit.footing+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.natural_armour)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.misc_armour_bonus)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.dr_bps)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.dr_mm)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.natural_weapons_dice)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.number_of_attacks)+System.getProperty("line.separator"));
				for (int i=0;i<unit.feats.length;i++) {
					fw.write(unit.feats[i]+System.getProperty("line.separator"));
				}
				for (int i=0;i<unit.feats.length;i++) {
					fw.write(unit.unit_feats[i]+System.getProperty("line.separator"));
				}
				fw.write(unit.weapon1.name+System.getProperty("line.separator"));//weapon1
				fw.write(Integer.toString(unit.weapon1.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon1.damage_dice)+System.getProperty("line.separator"));
				fw.write(unit.weapon1.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon1.weight)+System.getProperty("line.separator"));
				fw.write(unit.weapon2.name+System.getProperty("line.separator"));//weapon2
				fw.write(Integer.toString(unit.weapon2.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon2.damage_dice)+System.getProperty("line.separator"));
				fw.write(unit.weapon2.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon2.weight)+System.getProperty("line.separator"));
				fw.write(unit.weapon3.name+System.getProperty("line.separator"));//weapon3
				fw.write(Integer.toString(unit.weapon3.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon3.damage_dice)+System.getProperty("line.separator"));
				fw.write(unit.weapon3.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.weapon3.weight)+System.getProperty("line.separator"));
				fw.write(unit.armour.name+System.getProperty("line.separator"));//armour
				fw.write(Integer.toString(unit.armour.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.armour.max_dex)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.armour.ac)+System.getProperty("line.separator"));
				fw.write(unit.armour.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.armour.weight)+System.getProperty("line.separator"));
				fw.write(unit.shield.name+System.getProperty("line.separator"));//shield
				fw.write(Integer.toString(unit.shield.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.shield.max_dex)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.shield.ac)+System.getProperty("line.separator"));
				fw.write(unit.shield.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.shield.weight)+System.getProperty("line.separator"));
				fw.write(unit.mount.name+System.getProperty("line.separator"));//mount
				fw.write(unit.mount.type+System.getProperty("line.separator"));
				fw.write(unit.mount.hd_type+System.getProperty("line.separator"));
				for (int i=0;i<unit.stats.length;i++) {
					fw.write(Integer.toString(unit.mount.stats[i])+System.getProperty("line.separator"));
				}
				for (int i=0;i<unit.stats.length;i++) {
					fw.write(Boolean.toString(unit.mount.stats_used[i])+System.getProperty("line.separator"));
				}
				fw.write(unit.mount.size+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.natural_armour)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.number_of_hd)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.damage_dice)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.number_of_attacks)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.cost_of_one_mount)+System.getProperty("line.separator"));
				fw.write(unit.mount.footing+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.base_speed)+System.getProperty("line.separator"));
				fw.write(unit.mount.mount_armour.name+System.getProperty("line.separator"));//mount armour
				fw.write(Integer.toString(unit.mount.mount_armour.cost)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.mount_armour.max_dex)+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.mount_armour.ac)+System.getProperty("line.separator"));
				fw.write(unit.mount.mount_armour.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(unit.mount.mount_armour.weight)+System.getProperty("line.separator"));
				fw.write(unit.unit_lord+System.getProperty("line.separator"));//general information
				fw.write(Integer.toString(unit.number_of_units)+System.getProperty("line.separator"));
				fw.write(separator+System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//save routes
	
	public void saveRoute(List<Route> listofroutes){//self-explanatory save procedure
		System.out.println("READNWRITE! saveRoute");
		String s = directory+"\\routes"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			for (Route route:listofroutes){
				fw.write(route.name+System.getProperty("line.separator"));
				fw.write(route.lord+System.getProperty("line.separator"));
				fw.write(Boolean.toString(route.active)+System.getProperty("line.separator"));
				fw.write(Integer.toString(route.lord_TAR)+System.getProperty("line.separator"));
				fw.write(Integer.toString(route.partner_TAR)+System.getProperty("line.separator"));
				fw.write(Integer.toString(route.partner_BP)+System.getProperty("line.separator"));
				fw.write(separator+System.getProperty("line.separator"));//might remove this, depends on how writing load methods go
			}
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	//save officials
	
	public void saveOfficial(List<Official> listofofficials) {//self-explanatory save procedure
		System.out.println("READNWRITE! saveOfficial");
		String s = directory+"\\officials"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			for (Official official:listofofficials){
				fw.write(official.name+System.getProperty("line.separator"));
				fw.write(official.job+System.getProperty("line.separator"));
				fw.write(Integer.toString(official.roll)+System.getProperty("line.separator"));
				fw.write(official.lord+System.getProperty("line.separator"));
				fw.write(official.free+System.getProperty("line.separator"));
				fw.write(separator+System.getProperty("line.separator"));//use separator since that might be easier to load?
			}						//might not be, but just remove it then
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
}