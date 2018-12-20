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
	
	public void setSaveName(String s) {//create and name the current nation save
		directory = new File("Nations/"+s);
		directory.mkdir();
	}
	
	////LOAD METHODS////
	public List<Lord> readSaveFile(File k) {//reading a save file with nation file directory as input
		List<Lord> listoflords = new ArrayList<Lord>();//list of all lords in the savefile
		//overlord always exist and vassals go as vassal1, vassal2, ...
		//vassals of vassals go as vassal1_1, vassal1_2,... or vassal1_1_1, vassal1_1_2,... and so on
		List<Hex> listofhexes = new ArrayList<Hex>();
		for (String s: k.list()) {//call the method readLordFile for each lord in the file directory
			if (s.contains("lord")||s.contains("vassal"))//only import lords and not hexes into listoflords
				listoflords.add(readLordFile(k,s));		//ADD MORE EXCEPTIONS AS THEY DEVELOP
			else if (s.contains("hex")==true)			//PROBABLY BUILDINGS UNLESS THEY ARE PART OF Hex OBJECT
				listofhexes.add(readHexFile(k));		//onlyimport the hexes into listofhexes
		}											
		return listoflords;
	}
	//load lord
	private Lord readLordFile(File k, String s) {//read a lord file (only ever called from readSaveFile
		Lord lord = new Lord(s);//import the lord
		return lord;
	}
	//Load hex
	private Hex readHexFile(File k) {//reads the hex-file and imports a hex. will have to call this once for every hex probs
		Hex hex = new Hex();//import the hex
		
		return hex;
	}
	////SAVE METHODS////
	public void writeSaveFile(File k, List<Lord> listoflords, List<Hex> listofhexes) {//the 'proper save method' which
												//properly clears the directory and then saves.
	}											//called when the user presses 'save nation' button
	//the quick save which is called every x secs
	public void quickSaveFile(File k, List<Lord> listoflords, List<Hex> listofhexes) {
		for (Lord lord: listoflords){
			writeLordFile(lord);
		}
		for (Hex hex: listofhexes){
			writeHexFile(hex);
		}
	}
	//Saving hex
	public void writeHexFile(Hex hex) {//writing 
		
	}
	//saving lord
	public void writeLordFile(Lord lord) {//Writing the save file. needs to be called for each vassal?
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
}