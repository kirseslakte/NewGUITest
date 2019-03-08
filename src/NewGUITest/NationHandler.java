package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NationHandler extends JFrame{
	static List<Lord> listoflords = new ArrayList<Lord>();
	static List<Hex> listofhexes = new ArrayList<Hex>();
	static List<Unit> listofunits = new ArrayList<Unit>();
	static List<Route> listofroutes = new ArrayList<Route>();
	static List<Official> listofofficials = new ArrayList<Official>();
	static JFrame mainframe = new JFrame();
	private static JTabbedPane mainPane = new JTabbedPane();
	public static HexPane hexpanel = new HexPane();
	//public static OfficialTab officialpanel = new OfficialTab();
	//public static RouteTab routepanel = new RouteTab();
	Culture bonuses = new Culture();
	//private MilitaryWindow milwindow = new MilitaryWindow();
	
	public NationHandler() {
		
	}
	
	public static void createNation(String s) {//only ever called from MainThread when a new nation is created
		//System.out.println("NATIONHANDLER! createNation");
		ReadNWrite.setNationName(s);
		ReadNWrite.createDummyNation(s);
		loadNation(s);
	}
	
	public static void loadNation (String s) {//only ever called from MainThread when an old nation is loaded
		//System.out.println("NATIONHANDLER! loadNation");
		ReadNWrite.setNationName(s);
		List<String> listofloads = new ArrayList<String>();
		for (String loadstr:ReadNWrite.directory.list()) {
			loadstr = loadstr.replace(ReadNWrite.filetype, "");
			listofloads.add(loadstr);//load all files in directory to list
		}
		listofloads.remove("hexes");listofloads.remove("units");listofloads.remove("officials");
		listofloads.remove("culture");listofloads.remove("routes");listofloads.remove("races");//remove all non-lords
		int i = 0;
		for (String lord:listofloads){//lords SHOULD be sorted alphabetically meaning overlord is first,
			String[] loaded_lord = ReadNWrite.loadLord(lord);//then vassal1, then vassals of 1, then vassal2, then vassals of 2, aso
			listoflords.add(new Lord(loaded_lord[0],loaded_lord[9]));//name, master_title
			if (lord.equals("overlord"))
				listoflords.get(listoflords.size()-1).title = lord;
			else {
				i++;
				listoflords.get(listoflords.size()-1).title = "vassal"+Integer.toString(i);
			}
			listoflords.get(listoflords.size()-1).loadGovernment(loaded_lord);
			if (lord.equals("overlord")) {
				Culture.loadCulture(ReadNWrite.loadCulture());
				mainSetup(s);
				listoflords.get(listoflords.size()-1).setGovernment();
			} else {
				addVassalTab(loaded_lord[0]);
				listoflords.get(listoflords.size()-1).setGovernment();
			}
			listoflords.get(listoflords.size()-1).loadModifiers();
		}
		//all lords have been loaded!!
		listofhexes = ReadNWrite.loadHexes();
		mainPane.addTab("Hexes", new JScrollPane(hexpanel.hexPane()));//loaded after all the lords
		mainPane.addTab("Culture & Portfolio", new JScrollPane(Culture.culturePane()));
		unitTab();
		recalibrateLords();
		//System.out.println("first recalibration of hexes");
		recalibrateHexes();
		listofunits = ReadNWrite.loadUnits();
		try {
			listofunits.get(0);
		} catch (IndexOutOfBoundsException e) {
			listofunits.add(new Unit());
		}
		UnitTab.loadUnits();
		//mainPane.addTab("Units", new JScrollPane(unitpanel.unitPane()));
		listofofficials = ReadNWrite.loadOfficials();
		listofroutes = ReadNWrite.loadRoutes();
		for (Lord lord:listoflords) {//load all officials into the lords
			lord.official.loadOfficials();
			lord.route.loadRoutes();
			lord.updateLord();
		}
		listoflords.get(0).updateLord();//extra call in order to update the overlord
			
		//update the nationpanes of the lords
		//load notes
		//mainPane.addTab("Notes");
		mainPane.revalidate();
	}
	
	public static void saveNation() {//only ever called from save_request
		//System.out.println("NATIONHANDLER! saveNation");
		updateNation();
		for (Lord lord: listoflords) {
			ReadNWrite.saveLord(lord);
		}
		ReadNWrite.saveHexes(listofhexes);
		ReadNWrite.saveUnit(listofunits);
		ReadNWrite.saveRoute(listofroutes);
		ReadNWrite.saveOfficial(listofofficials);
		UnitTab.save();
	}//that was pretty straight forward, right?
	
	public static void removeLord(int lordindex) {
		mainPane.remove(listoflords.get(lordindex).mainPnl);
		listoflords.remove(lordindex);
	}
	
	public static void newLord(String new_lord,String master) {//create a new lordWindow
		//System.out.println("NATIONHANDLER! newLord");
		if (master.equals("")){//this is a new nation
			listoflords.add(new Lord(new_lord,master));//add lord
			listoflords.get(0).title = "overlord";//set title to overlord
			//System.out.println("Created lord "+new_lord);
		}else {//if creating a new vassal
			boolean ok = true;
			for (int i=0;i<listoflords.size();i++) {
				String existing_lords = listoflords.get(i).name;
				if (new_lord.equals(existing_lords)) {//if lord with same name already exist
					JOptionPane.showMessageDialog(new Frame(),"That Lord already exist!","Vassal allocation error",JOptionPane.PLAIN_MESSAGE);
					ok = false;
				}
			}
			if (ok) {
					listoflords.add(new Lord(new_lord,master));//add the new lord
					int vassal_count = 0;//set title of vassal
					for (int i=0;i<listoflords.size();i++) {
						if (listoflords.get(i).master_title.equals(master))
							vassal_count++;//get the number of vassals with the same master
					}
					if (master.equals("overlord"))
						listoflords.get(listoflords.size()-1).title = "vassal"+vassal_count;//if master is overlord
					else																	
						listoflords.get(listoflords.size()-1).title = master+"_"+vassal_count;//if master is vassal
					addVassalTab(new_lord);
			}
		}
	}
	
	public static void updateNation() {
		//System.out.println("NATIONHANDLER! updateNation");
		for (Lord lord:listoflords) {
			lord.updateLord();
		}
		recalibrateLords();
		recalibrateHexes();
		getOfficials();
	}
	
	public static void mainSetup(String s) {
		//System.out.println("NATIONHANDLER! mainSetup");
		mainframe.setSize(1500,400);
		mainframe.setLocationRelativeTo(null);
		mainframe.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				ReadNWrite.clean();
				System.exit(0);
			}
		});
		mainframe.setTitle(s);
		mainPane.addTab(s,listoflords.get(listoflords.size()-1).setPanel(true));
		mainframe.add(mainPane);
		mainframe.setVisible(true);
	}
	
	public static void addVassalTab(String lord) {
		//System.out.println("NATIONHANDLER! addVassalTab");
		mainPane.addTab(lord,listoflords.get(Utility.findLord(lord)).setPanel(false));
	}
	
	public static void unitTab() {
		mainPane.addTab("Units", new JScrollPane(UnitTab.unitTab()));
	}
	
	public static void recalibrateHexes() {
		//System.out.println("NATIONHANDLER! recalibrateHexes");
		for (int i=0;i<listoflords.size();i++)
			listoflords.get(i).loadModifiers();
		List<String[]> hexes = new ArrayList<String[]>();
		//boolean exists;
		hexes = hexpanel.getHex();
		listofhexes.clear();
		for (int i=0;i<hexes.size();i++) {
			//exists = false;
			if ((!(hexes.get(i)[0].equals("")))){
				listofhexes.add(new Hex(hexes.get(i)));
			}
		}
	}
	public static void recalibrateLords() {
		//System.out.println("NATIONHANDLER! recalibrateLords");
		for (Lord lord:listoflords) {
			for (int i=0;i<Hex.passive_resources.length;i++)
				lord.accessed_resources[i] = false;
		}
		for (int i=0;i<listofhexes.size();i++) {//updating resources for lords
			int r = 0;
			String resource = (String) HexPane.resource_list.get(i).getSelectedItem();
			boolean any_resource = false;
			for (int j=0;j<Hex.passive_resources.length;j++) {
				if (resource.equals(Hex.passive_resources[j])) {
					r = j;
					any_resource = true;
				}
			}
			if (any_resource) {
				for (int j=0;j<listoflords.size();j++) {
					if (listoflords.get(j).name.equals(HexPane.owner_list.get(i).getSelectedItem()))
						if (HexPane.resource_check_list.get(i).isSelected()) {
							resource = listoflords.get(j).master_title;
							for (int k=0;k<listoflords.size();k++) {
								if (listoflords.get(k).title.equals(resource))
									listoflords.get(k).accessed_resources[r] = true;
							}
						} else
							listoflords.get(j).accessed_resources[r] = true;
					listoflords.get(j).loadModifiers();
				}
			}
		}
	}

	public static void saveUnit(int i,Unit u) {
		List<Unit> tempunitlist = listofunits;
		for (int k=i;k<listofunits.size();k++)
			listofunits.remove(k);
		listofunits.add(u);
		for (int k=i+1;k<tempunitlist.size();k++)
			listofunits.add(tempunitlist.get(k));
		ReadNWrite.saveUnit(listofunits);
	}
	
	public void initializeHex() {
		//System.out.println("NATIONHANDLER! initializeHex");
		for (int i=0;i<listofhexes.size();i++) {
			HexPane.buildings.get(i).start(i);
			for (int j=0;j<HexPane.buildings.get(i).built_fortifications.size();j++){
				String fort_name = HexPane.buildings.get(i).built_fortifications.get(j);
				HexPane.buildings.get(i).addonframes.get(j).start(i, fort_name);
				HexPane.buildings.get(i).addonframes.get(j).stop();
			}
			if (!(HexPane.buildings.get(i).built_walls.equals(""))) {
				String wall = HexPane.buildings.get(i).built_walls;
				int lordy = Utility.findLord(HexPane.buildings.get(i).hex.owner);
				double mod = listoflords.get(lordy).modifiers[26];
				HexPane.buildings.get(i).wallframe.start(i,wall,mod);
				HexPane.buildings.get(i).wallframe.stop();
			}
			HexPane.buildings.get(i).stop();
		}
	}
	
	public static void getOfficials() {//get all of the lords officials
		listofofficials.clear();
		for (Lord lord:listoflords)
			getOfficials(lord.name);
	}
	public static void getOfficials(String s) {//lord-specific
		for (int i=0;i<listofofficials.size();i++) {//remove all officials of that lord
			if (listofofficials.get(i).lord.equals(s))
				listofofficials.remove(i);
		}
		for (Official o:listoflords.get(Utility.findLord(s)).official.listofofficials)
			listofofficials.add(o);//add all officials of that lord
	}
	public static void getRoutes() {//get all of the lords routes
		//listofroutes.clear();
		for (Lord lord:listoflords)
			getRoutes(lord.name);
	}
	public static void getRoutes(String s) {//lord-specific
		for (int i=0;i<listofroutes.size();i++) {//remove that lords routes
			if (listofroutes.get(i).lord.equals(s)) {
				listofroutes.remove(i);
				i--;
			}
		}
		for (Route r:listoflords.get(Utility.findLord(s)).route.listofroutes)
			listofroutes.add(r);//add that lords routes
	}
}