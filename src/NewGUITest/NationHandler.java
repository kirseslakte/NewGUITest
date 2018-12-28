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
	public Lord active_lord;
	private int min = 0;
	private ReadNWrite write = new ReadNWrite();
	//private MilitaryWindow milwindow = new MilitaryWindw();
	//private HexWindow hexwindow = new HexWindow();
	
	public NationHandler() {
		
	}
	
	public void mainThread() {
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(1);
				min++;
				if (min==3600)//reset every hour
					min = 0;
				if (active_lord.request_flag) {
					if (active_lord.new_request){

						active_lord.resetRequest();						
					}else if (active_lord.save_request) {
						saveNation();
						System.out.println("saving");
						active_lord.resetRequest();
					}else if (active_lord.generate_request) {
						System.out.println("generating");
						active_lord.resetRequest();
					}
				}
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}
	}
	
	public void createNation(String s) {//only ever called from MainThread when a new nation is created
		write.setNationName(s);
		newLord(s,"");//DONE
	}
	
	public void loadNation (String s) {//only ever called from MainThread when an old nation is loaded
		write.setNationName(s);//DONT CALL newLord!! handle the entire loading from here!!
		List<String> listofloads = new ArrayList<String>();
		for (String loadstr:write.directory.list()) {
			loadstr = loadstr.replace(write.filetype, "");
			listofloads.add(loadstr);//load all files in directory to list
		}
		listofloads.remove("hexes");listofloads.remove("units");listofloads.remove("officials");
		listofloads.remove("culture");listofloads.remove("routes");//remove all non-lords
		String[] loaded_lord = new String[26];//lord should contain 27 strings
		String[] gov = new String[15];//going into setGovernment
		for (String lord:listofloads){//lords should be sorted alphabetically meaning overlord is first,
			loaded_lord = write.loadLord(lord);//then vassal1, then vassals of 1, then vassal2, then vassals of 2, aso
			listoflords.add(new Lord(loaded_lord[0],loaded_lord[9]));//name, master_title
			gov[0] = loaded_lord[15];//banked rp
			gov[1] = loaded_lord[16];//banked dev
			gov[2] = loaded_lord[14];//tax rate
			gov[3] = loaded_lord[1];//system
			gov[4] = loaded_lord[2];//societal structure
			gov[5] = loaded_lord[3];//rule
			gov[6] = loaded_lord[4];//life style
			gov[7] = loaded_lord[5];//centralisation
			gov[8] = loaded_lord[6];//culture
			gov[9] = loaded_lord[7];//religion
			gov[10] = loaded_lord[8];//legitimacy
			for (int i=0;i<4;i++)
				gov[11+i] = loaded_lord[10+i];//institutions
			listoflords.get(listoflords.size()-1).setGovernment(gov);//gov is now 15 long, so put it into setGovernment!
			
			//left to load: tax_rate_overlord
			//and histocracy stuff, the rest is loaded
		}
	}
	
	public void saveNation() {//only ever called from within Lord objects
		for (Lord lord: listoflords) {
			write.saveLord(lord);
		}
		write.saveHexes(listofhexes);
		write.saveUnit(listofunits);
		write.saveRoute(listofroutes);
		write.saveOfficial(listofofficials);
	}//that was pretty straight forward, right?
	
	public void changeActiveLord(Lord active,Lord new_active) {//just changing which frame to show
		listoflords.get(listoflords.indexOf(active)).stop();
		listoflords.get(listoflords.indexOf(new_active)).start();
		active_lord = new_active;
	}
	
	public void newLord(String new_lord,String master) {//create a new lordWindow
		if (master.equals("")){//this is a new nation
			listoflords.add(new Lord(new_lord,master));//add lord
			listoflords.get(0).start();//display lord
			active_lord = listoflords.get(0);//set lord as active
			listoflords.get(0).title = "overlord";//set title to overlord
		}else {//if creating a new vassal
			for (int i=0;i<listoflords.size();i++) {
				String existing_lords = listoflords.get(i).name;
				if (new_lord==existing_lords)//if lord with same name already exist
					JOptionPane.showMessageDialog(new Frame(),"That Lord already exist!","Vassal allocation error",JOptionPane.PLAIN_MESSAGE);
				else {
					listoflords.add(new Lord(new_lord,master));//add the new lord
					changeActiveLord(active_lord,listoflords.get(listoflords.size()-1));//make that lord the new active lord
					int vassal_count = 0;
					for (int j=0;j<listoflords.size();j++) {
						if (listoflords.get(i).master_title.equals(master))
							vassal_count++;//get the number of vassals with the same master
					}
					listoflords.get(listoflords.size()-1).master_title = master;//set the title of the master
					if (master.equals("overlord"))
						listoflords.get(listoflords.size()-1).title = "vassal"+vassal_count;//if master is overlord
					else																	
						listoflords.get(listoflords.size()-1).title = master+"_"+vassal_count;//if master is vassal
				}
			}
		}
	}	
}