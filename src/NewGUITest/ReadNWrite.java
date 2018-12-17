package NewGUITest;

import java.io.File;

public class ReadNWrite {
	
	private String filetype = ".dat";
	public int save_length = 10;
	public int n_saves = 0;
	public String[] save_names;
	
	public ReadNWrite() {
		updateSaves();
	}
	
	
	public int updateSaves() {
		save_names = new File("Nations").list();
		n_saves = new File("Nations").list().length;//gets the number of saves existing.
		return n_saves;
	}
	
	public double[] readSaveFile(File k) {//reading a save file with nation file directory as input
		double[] savedata = new double[save_length];//overlord is presumed to always exist and vassals go as vassal1, vassal2, ...
		//vassals of vassals go as vassal1_1, vassal1_2,... or vassal1_1_1, vassal1_1_2,... and so on
		
		return savedata;
	}
	
	private double[] readVassalFile(File k) {//read a vassal file (only ever called from readSaveFile
		double[] savedata = new double[save_length];
		
		return savedata;
	}
	
	public void writeSaveFile(String k,int depth) {//Writing the save file
		File file = new File("Nations/"+k);
		file.mkdir();
		file = new File("Nations/"+k+"/overlord"+filetype);
		try {
			file.createNewFile();
		} catch (Exception e){
			
		}
	}
	
	public void setSaveLength(int a) {
		save_length = a;
	}
}