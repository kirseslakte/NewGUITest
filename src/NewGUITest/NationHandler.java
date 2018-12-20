package NewGUITest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class NationHandler {
	public List<LordWindow> listoflords;
	public List<Hex> listofhexes;
	private LordWindow active_lord;
	private int min = 0;
	private ReadNWrite write = new ReadNWrite();
	
	public NationHandler() {
		
	}
	
	public void mainThread() {
		try {
			TimeUnit.SECONDS.sleep(1);
			min++;
			if (min==3600)//reset every hour
				min = 0;
		}catch (InterruptedException e){
			System.out.println(e);
		}
	}
	
	public void createNation(String s) {//only ever called from MainThread when a new nation is created
		write.setSaveName(s);
		newLord(s,true);//pretty much done
	}
	
	public void loadNation (String s) {//only ever called from MainThread when an old nation is loaded
		write.setSaveName(s);//DONT CALL newLord!! handle the entire loading from here!!
		for (Lord lord: write.readSaveFile(write.directory)){
			listoflords.add(new LordWindow(lord));
			//CONTINE HERE!!
		}
	}
	
	public void changeActiveLord(LordWindow active,LordWindow new_active) {//just changing which frame to show
		listoflords.get(listoflords.indexOf(active)).stop();
		listoflords.get(listoflords.indexOf(new_active)).start();
	}
	
	public void newLord(String s,boolean new_nation) {//create a new lordWindow
		if (new_nation){//NOT SURE WHAT TO DO HERE, LOADS WILL PROBABLY BE HANDLED FROM loadNation!!
			listoflords.add(new LordWindow(new Lord(s)));//add lord
			listoflords.get(1).start();
		}else {//if creating a new vassal
			for (String existing_lords:write.save_names) {
				if (s==existing_lords)//if lord with same name already exist
					JOptionPane.showMessageDialog(new Frame(),"That Lord already exist!","Vassal allocation error",JOptionPane.PLAIN_MESSAGE);
				else {
					listoflords.add(new LordWindow(new Lord(s)));//add the new lord
					changeActiveLord(active_lord,listoflords.get(listoflords.size()));//make that lord the new active lord
				}
			}
		}
	}
	
}