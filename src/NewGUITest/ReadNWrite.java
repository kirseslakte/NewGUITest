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
	
	public List<Lord> loadLords(File k) {//reading a save file with nation file directory as input
		List<Lord> listoflords = new ArrayList<Lord>();//list of all lords in the savefile
		//overlord always exist and vassals go as vassal1, vassal2, ...
		//vassals of vassals go as vassal1_1, vassal1_2,... or vassal1_1_1, vassal1_1_2,... and so on
		for (String s: k.list()) {//time to import some lords bois
			if (s.contains("lord")||s.contains("vassal")) {
				//listoflords.add(lord);
			}
		}											
		return listoflords;
	}
	
	//load hexes
	
	public List<Hex> loadHexes() {//this is a hot mess!
		List<Hex> listofhexes = new ArrayList<Hex>();//since we don't know exactly the length of each saved hex
		String s = directory+"/hexes"+filetype;		//it is difficult to extract them in a predictable manner
		File file = new File(s);					//this is causing some issues. I will try commenting as best I can
		Hex hex = new Hex();
		List<String> hexlist = new ArrayList<String>();//what we feed from input file it is an array since we don't know the size of each hex
		List<String[]> hexstring = new ArrayList<String[]>();//what we want to go into the hex generator
		boolean same_hex;//triggers on the hex separator
		int i;//counting the number of lines in each hex save
		s = "";//placeholder feeder to check for the separator
		try {
			Scanner sc = new Scanner(file);
			i = 0;
			while(sc.hasNextLine()){//okok I must stop now or else I will go crazy, but I had a crazy idea
				same_hex = true;	//maybe it is possible to read all the lines into an array (which we then know the size of)
				while (same_hex) {	//then count the number of hexes in the array, and the 'length' of each hex
					s = sc.nextLine();//and then create String s = new String[lenght_of_the_hex] which will then work, and it
					if (s.equals(separator)){//will only take longer to execute, but will drain less memory, which is the big issue
						same_hex = false;
					}else {
						hexlist.add(s);
					}
				}
				//hexstring. = new String[hexlist.size()];
				hex.setHex(hexstring.get(i));
				listofhexes.add(hex);
				hexlist.removeAll(hexlist);
				i++;
			}
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
		String s = directory+"/hexes"+filetype;
		File file = new File(s);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);//write all the data for the lord in order (see hexKey.txt)
			for (Hex hex: listofhexes){
				fw.write(separator+System.getProperty("line.separator"));
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
	
	public void saveUnit(Unit unit) {
		
	}
	
	//save routes
	
	public void saveRoute(Route route){
		
	}
	
	//save officials
	
	public void saveOfficail(Official official) {
		
	}
}