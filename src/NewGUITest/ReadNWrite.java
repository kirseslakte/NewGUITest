package NewGUITest;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ReadNWrite {
	
	private String filetype = ".dat";
	private String separator = "xxxxxx";
	public String[] save_names;
	public int n_saves = 0;
	public int number_of_building_slots = 18;
	public File directory = new File("");
	
	public ReadNWrite() {
		updateSaves();
	}
	
	
	public int updateSaves() {//number of saves
		save_names = new File("Nations").list();
		n_saves = new File("Nations").list().length;//gets the number of existing saves.
		return n_saves;
	}
	
	public void setNationName(String s) {//create and name the current nation save
		directory = new File("Nations/"+s);
		directory.mkdir();
	}
	
	////LOAD METHODS////
	
	//load lords
	
	public List<Lord> loadLords() {//reading a save file with nation file directory as input
		List<Lord> listoflords = new ArrayList<Lord>();//list of all lords in the savefile
		//overlord always exist and vassals go as vassal1, vassal2, ...
		//vassals of vassals go as vassal1_1, vassal1_2,... or vassal1_1_1, vassal1_1_2,... and so on
		//for (String s: k.list()) {//time to import some lords bois
			//if (s.contains("lord")||s.contains("vassal")) {
				//listoflords.add(lord);
			//}
		//}											
		return listoflords;
	}
	
	//load hexes
	
	public List<Hex> loadHexes() {//loading hexes!
		List<Hex> listofhexes = new ArrayList<Hex>();
		String s = directory+"/hexes"+filetype;
		File file = new File(s);
		List<String> hexreader = new ArrayList<String>();
		int max_hex_length = number_of_building_slots+7;
		String[] hex_input = new String[max_hex_length];
		int hex_length = 0;
		//int number_of_hexes = 0;
		Hex hex = new Hex();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				s = sc.nextLine();
				hexreader.add(s);			//scanning the entire document into hexlist
				if (s.equals(separator)){
					hex.setHex(hex_input);
					listofhexes.add(hex);
					//number_of_hexes++;	//count the number of hexes
					for (int i=0;i<hex_input.length;i++) {
						hex_input[i] = "";//reset the hex_input
					}
				}else {
					hex_input[hex_length] = s;
					hex_length++;
				}
			}
			sc.close();/*
			List<String>[] hexstring = new List[number_of_hexes];//what we want to go into the Hex method
			number_of_hexes = 0;//reset the counter
			for (int i=0;i<hexreader.size();i++) {//track the positions of the hexes
				if (hexreader.get(i).equals(separator)){
					number_of_hexes++;//count the number of hexes
				} else
					hexstring[number_of_hexes].add(hexreader.get(i));//put all but the separator into a string array
			}
			for (int i=0;i<number_of_hexes;i++) {//loop over all the hexes
				listofhexes.add(new Hex());				//add the hex to the list
				//listofhexes.get(i).setHex(hexstring[i]);//fill the hex with the loaded data
			}*/
		} catch(Exception e){
			System.out.println(e);
		}
		return listofhexes;
	}
	
	//load units
	
	public List<Unit> loadUnits(){
		List<Unit> listofunits = new ArrayList<Unit>();
		String s = directory+"/units"+filetype;
		File file = new File(s);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
						
				//listofunits.add(hex);
			}
		} catch(Exception e){
			System.out.println(e);
		}
		return listofunits;
	}
	
	//load routes
	
	public List<Route> loadRoutes(File k) {
		List<Route> listofroutes = new ArrayList<Route>();
		String s = directory+"/routes"+filetype;
		File file = new File(s);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
						
				//listofroutes.add(hex);
			}
		} catch(Exception e){
			System.out.println(e);
		}
		return listofroutes;
	}
	
	//load officials
	
	public List<Official> loadOfficials() {
		List<Official> listofofficials = new ArrayList<Official>();
		String s = directory+"/officials"+filetype;
		File file = new File(s);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
						
				//listofofficials.add(hex);
			}
		} catch(Exception e){
			System.out.println(e);
		}
		return listofofficials;
	}
	
	////SAVE METHODS////
	
	//Saving hex
	
	public void saveHexes(List<Hex> listofhexes) {//writing 
		String s = directory+"\\hexes"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			System.out.println("created "+s);
			FileWriter fw = new FileWriter(file);//write all the data for the lord in order (see hexKey.txt)
			for (Hex hex: listofhexes){
				fw.write(hex.name+System.getProperty("line.separator"));
				fw.write(hex.habitability+System.getProperty("line.separator"));
				fw.write(hex.alignment+System.getProperty("line.separator"));
				fw.write(hex.religion+System.getProperty("line.separator"));
				fw.write(hex.pop_size+System.getProperty("line.separator"));
				fw.write(hex.unrest+System.getProperty("line.separator"));
				fw.write(hex.resource+System.getProperty("line.separator"));
				for (String building: hex.buildings){
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
		String s = directory+"\\"+lord.title+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			System.out.println("created "+s);
			FileWriter fw = new FileWriter(file);//write all the data for the lord in order (see lordKey.txt)
			fw.write(lord.name+System.getProperty("line.separator"));
			fw.write(lord.sys+System.getProperty("line.separator"));
			fw.write(lord.society+System.getProperty("line.separator"));
			fw.write(lord.rule+System.getProperty("line.separator"));
			fw.write(lord.life+System.getProperty("line.separator"));
			fw.write(lord.cent+System.getProperty("line.separator"));
			fw.write(lord.alignment+System.getProperty("line.separator"));
			fw.write(lord.religion+System.getProperty("line.separator"));
			fw.write(lord.ruler_name+System.getProperty("line.separator"));
			fw.write(lord.legitimacy+System.getProperty("line.separator"));
			fw.write(Boolean.toString(lord.is_vassal)+System.getProperty("line.separator"));
			for (int i=0;i<lord.max_number_of_institutions;i++){//here an error is generated
				if (i<lord.institutions.length)					//there is??!?
					fw.write(lord.institutions[i]+System.getProperty("line.separator"));
				else
					fw.write("None"+System.getProperty("line.separator"));
			}
			for (int i=0;i<lord.number_of_culture_bonuses;i++){
				fw.write(lord.culture_bonuses[i]+System.getProperty("line.separator"));
			}
			for (int i=0;i<lord.eco.length;i++) {
				fw.write(lord.eco[i]+System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	//saving units
	
	public void saveUnit(List<Unit> listofunits) {
		
	}
	
	//save routes
	
	public void saveRoute(List<Route> listofroutes){//self-explanatory save procedure
		String s = directory+"\\routes"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			System.out.println("created "+s);
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
	
	public void saveOfficail(List<Official> listofofficials) {//self-explanatory save procedure
		String s = directory+"\\officials"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			System.out.println("created "+s);
			FileWriter fw = new FileWriter(file);
			for (Official official:listofofficials){
				fw.write(official.name+System.getProperty("line.separator"));
				fw.write(official.type+System.getProperty("line.separator"));
				fw.write(Integer.toString(official.roll)+System.getProperty("line.separator"));
				fw.write(official.hex+System.getProperty("line.separator"));
				fw.write(official.lord+System.getProperty("line.separator"));
				fw.write(separator+System.getProperty("line.separator"));//use separator since that might be easier to load?
			}						//might not be, but just remove it then
			fw.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
}