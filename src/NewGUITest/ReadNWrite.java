package NewGUITest;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

public class ReadNWrite {
	
	private String filetype = ".dat";
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
	
	public List<Hex> loadHexes(File k) {
		List<Hex> listofhexes = new ArrayList<Hex>();
		for (String s: k.list()){
			if (s.contains("hex")){//find the file called hexes
				s = directory+"/hexes"+filetype;
				k = new File(s);
			}
		}
		boolean more = true;//are there more hexes to load
		while(more){
			//listofhexes.add(hex);
		}
		return listofhexes;
	}
	
	//load units
	
	public List<Unit> loadUnits(File k){
		List<Unit> listofunits = new ArrayList<Unit>();
		for (String s: k.list()){
			if (s.contains("unit")){
				s = directory+"/units"+filetype;
				k = new File(s);
			}
		}
		boolean more = true;//are there more units to load
		while(more) {
			//listofunits.add(unit);
		}
		return listofunits;
	}
	
	//load routes
	
	public List<Route> loadRoutes(File k) {
		List<Route> listofroutes = new ArrayList<Route>();
		for (String s: k.list()){
			if (s.contains("route")){
				s = directory+"/routes"+filetype;
				k = new File(s);
			}
		}
		boolean more = true;//are there more routes to load
		while(more) {
			//listofroute.add(route);
		}
		return listofroutes;
	}
	
	//load officials
	
	public List<Official> loadOfficials(File k) {
		List<Official> listofofficials = new ArrayList<Official>();
		for (String s: k.list()){
			if (s.contains("official")){
				s = directory+"/officials"+filetype;
				k = new File(s);
			}
		}
		boolean more = true;//are there more officials to load
		while(more) {
			//listofofficials.add(official);
		}
		return listofofficials;
	}
	
	////SAVE METHODS////
	
	//Saving hex
	
	public void saveHex(Hex hex) {//writing 
		
	}
	
	//saving lord
	
	public void saveLord(Lord lord) {//Writing the save file. needs to be called for each vassal?
		String s = directory+"\\"+lord.title+filetype;
		File file = new File(s);
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
				if (i<lord.institutions.length)
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