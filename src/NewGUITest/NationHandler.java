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
				for (int i=0;i<listoflords.size();i++) {
					if (listoflords.get(i).request_flag) {
						if (listoflords.get(i).new_request){

							listoflords.get(i).resetRequest();						
						}else if (listoflords.get(i).save_request) {
							saveNation();
							System.out.println("saving");
							listoflords.get(i).resetRequest();
						}else if (listoflords.get(i).generate_request) {
							generateLord();
							System.out.println("generating");
							listoflords.get(i).resetRequest();
						}
					}
				}
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}
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
		write.saveUnit(listofunits);
		write.saveRoute(listofroutes);
		write.saveOfficail(listofofficials);
	}//that was pretty straight forward, right?
	
	public void changeActiveLord(Lord active,Lord new_active) {//just changing which frame to show
		listoflords.get(listoflords.indexOf(active)).stop();
		listoflords.get(listoflords.indexOf(new_active)).start();
	}
	
	public void newLord(String s,boolean new_nation) {//create a new lordWindow
		if (new_nation){//this is a new nation
			System.out.println(s);
			listoflords.add(new Lord(s));//add lord
			listoflords.get(0).start();
			active_lord = listoflords.get(0);
			System.out.println(listoflords.size());
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
	
	//THIS IS A DUMMY METHOD WHICH GENERATES A LORD TO CHECK IF SAVE/LOAD WORKS PROPERLY//
	public void generateLord() {
		String[] govern = {"Histocratic","Classisist","Monarchy","Settled","Highly","LE","NE","Dennis","10","3","1","100","3",
				"0.9","overlord"};//starting with lord
		listoflords.get(0).setGovernment(govern);
		String[] insti = new String[listoflords.get(0).max_number_of_institutions];
		for (int i=0;i<listoflords.get(0).max_number_of_institutions;i++) {
			insti[i] = "";
		}
		listoflords.get(0).setInstitutions(insti);
		double[] cult = new double[listoflords.get(0).number_of_culture_bonuses];
		for (int i=0;i<listoflords.get(0).number_of_culture_bonuses;i++) {
			cult[i] = i;
		}
		listoflords.get(0).setCultureBonuses(cult);
		double[] eco = {2.1,1.6,7.6,0.0,5.1};
		listoflords.get(0).setEconomyAndOfficials(eco);
		String[] hex1 = {"StrangTown","8","LE","CE","4","2","Gold","RGO_Level1","Manor","","","","",""};//hexes
		String[] hex2 = {"DangeTown","9","LN","NE","2","0","Emeralds","RGO_Level2","Work Camp","Mega Store","","","",""};
		listofhexes.add(new Hex());
		listofhexes.add(new Hex());
		listofhexes.get(0).setHex(hex1);;
		listofhexes.get(1).setHex(hex2);
		String[] official1 = {"Roger Bättrerumpa","Tax Collector","23","StrangTown","overlord"};//officials
		String[] official2 = {"Peter Snopen","High Priesting","19","Not_in_hex","overlord"};
		listofofficials.add(new Official(official1));
		listofofficials.add(new Official(official2));
		String[] route1 = {"handelsled1","overlord","active","23","19","2054"};//routes
		String[] route2 = {"handelsled2","overlord","passive","21","8","1527"};
		listofroutes.add(new Route(route1));
		listofroutes.add(new Route(route2));
	}	
}