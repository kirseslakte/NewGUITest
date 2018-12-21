package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class NationHandler {
	public List<Lord> listoflords = new ArrayList<Lord>();
	public List<Hex> listofhexes = new ArrayList<Hex>();
	public List<Unit> listofunits = new ArrayList<Unit>();
	public List<Route> listofroutes = new ArrayList<Route>();
	public List<Official> listofofficials = new ArrayList<Official>();
	private Lord active_lord;
	private int min = 0;
	private ReadNWrite write = new ReadNWrite();
	//private MilitaryWindow milwindow = new MilitaryWindw();
	//private HexWindow hexwindow = new HexWindow();
	
	public NationHandler() {
		
	}
	
	public void mainThread() {/*
		try {
			TimeUnit.SECONDS.sleep(1);
			min++;
			if (min==3600)//reset every hour
				min = 0;
		}catch (InterruptedException e){
			System.out.println(e);
		}
		}*/
	}
	
	public void createNation(String s) {//only ever called from MainThread when a new nation is created
		write.setNationName(s);
		newLord(s,true);//DONE
	}
	
	public void loadNation (String s) {//only ever called from MainThread when an old nation is loaded
		write.setNationName(s);//DONT CALL newLord!! handle the entire loading from here!!
		for (Hex hex: write.loadHexes()){
			listofhexes.add(hex);//import hexes to nationhandler
		}
		//hexwindow.loadHexes(listofhexes);//load hexes
		for (Lord lord: write.loadLords()){
			listoflords.add(lord);//import lord in nationhandler
			listoflords.get(listoflords.size()-1).loadLord();//load lordwindow
			if (lord.is_vassal==false){
				listoflords.get(listoflords.size()-1).start();//set overlord window to visible
			}
		}
	}
	
	public void saveNation() {//only ever called from within Lord objects
		for (Lord lord: listoflords) {
			write.saveLord(lord);
		}
		write.saveHexes(listofhexes);
		for (Unit unit: listofunits) {
			write.saveUnit(unit);
		}
		for (Route route: listofroutes) {
			write.saveRoute(route);
		}
		for (Official official: listofofficials) {
			write.saveOfficail(official);
		}
	}//that was pretty straight forward, right?
	
	public void changeActiveLord(Lord active,Lord new_active) {//just changing which frame to show
		listoflords.get(listoflords.indexOf(active)).stop();
		listoflords.get(listoflords.indexOf(new_active)).start();
	}
	
	public void newLord(String s,boolean new_nation) {//create a new lordWindow
		if (new_nation){//NOT SURE WHAT TO DO HERE, LOADS WILL PROBABLY BE HANDLED FROM loadNation!!
			System.out.println(s);
			listoflords.add(new Lord(s));//add lord
			listoflords.get(0).start();
		}else {//if creating a new vassal
			for (String existing_lords:write.save_names) {
				if (s==existing_lords)//if lord with same name already exist
					JOptionPane.showMessageDialog(new Frame(),"That Lord already exist!","Vassal allocation error",JOptionPane.PLAIN_MESSAGE);
				else {
					listoflords.add(new Lord(s));//add the new lord
					changeActiveLord(active_lord,listoflords.get(listoflords.size()-1));//make that lord the new active lord
				}
			}
		}
	}
	
}