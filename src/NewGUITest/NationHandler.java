package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NationHandler extends JFrame{
	static List<Lord> listoflords = new ArrayList<Lord>();
	static List<Hex> listofhexes = new ArrayList<Hex>();
	static List<Unit> listofunits = new ArrayList<Unit>();
	static List<Route> listofroutes = new ArrayList<Route>();
	static List<Official> listofofficials = new ArrayList<Official>();
	private static int min = 0;
	private static JTabbedPane mainPane = new JTabbedPane();
	private static ReadNWrite write = new ReadNWrite();
	private static HexPane hexpanel = new HexPane();
	static boolean request = false;
	static boolean vassal_request = false;
	public static boolean save_request = false;
	public static boolean hex_request = false;
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
				if (request) {
					request = false;
					if (vassal_request){
						
						vassal_request = false;
					}else if (save_request) {
						updateNation();
						System.out.println("saving");
						saveNation();
						save_request = false;
					}else if (hex_request) {
						hexpanel.getBuildings();
						recalibrateHexes();
						hex_request = false;
					}
				}
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}
	}
	
	public void createNation(String s) {//only ever called from MainThread when a new nation is created
		write.setNationName(s);
		newLord(s,"");
		mainSetup(s);
	}
	
	public void loadNation (String s) {//only ever called from MainThread when an old nation is loaded
		write.setNationName(s);
		List<String> listofloads = new ArrayList<String>();
		for (String loadstr:write.directory.list()) {
			loadstr = loadstr.replace(write.filetype, "");
			listofloads.add(loadstr);//load all files in directory to list
		}
		listofloads.remove("hexes");listofloads.remove("units");listofloads.remove("officials");
		listofloads.remove("culture");listofloads.remove("routes");//remove all non-lords
		for (String lord:listofloads){//lords SHOULD be sorted alphabetically meaning overlord is first,
			String[] loaded_lord = write.loadLord(lord);//then vassal1, then vassals of 1, then vassal2, then vassals of 2, aso
			listoflords.add(new Lord(loaded_lord[0],loaded_lord[9]));//name, master_title
			listoflords.get(listoflords.size()-1).title = lord;
			listoflords.get(listoflords.size()-1).loadGovernment(write.loadLord(lord));
			if (lord.equals("overlord")) {
				listoflords.get(listoflords.size()-1).loadCulture(write.loadCulture());
				mainSetup(s);
				listoflords.get(listoflords.size()-1).setCulture();
				listoflords.get(listoflords.size()-1).setGovernment();
			} else {
				addVassalTab(loaded_lord[0]);
				listoflords.get(listoflords.size()-1).setGovernment();
			}
		}
		//all lords have been loaded!!
		listofhexes = write.loadHexes();
		mainPane.addTab("Hexes", new JScrollPane(hexpanel.hexPane()));//loaded after all the lords
		listofunits = write.loadUnits();
		//mainPane.addTab("Units");
		listofofficials = write.loadOfficials();
		//update the nationpanes of the lords
		//load notes
		//mainPane.addTab("Notes");
		mainPane.revalidate();
	}
	
	public void saveNation() {//only ever called from save_request
		for (Lord lord: listoflords) {
			write.saveLord(lord);
		}
		write.saveHexes(listofhexes);
		write.saveUnit(listofunits);
		write.saveRoute(listofroutes);
		write.saveOfficial(listofofficials);
	}//that was pretty straight forward, right?
	
	public void newLord(String new_lord,String master) {//create a new lordWindow
		if (master.equals("")){//this is a new nation
			listoflords.add(new Lord(new_lord,master));//add lord
			listoflords.get(0).title = "overlord";//set title to overlord
			System.out.println("Created lord "+new_lord);
		}else {//if creating a new vassal
			for (int i=0;i<listoflords.size();i++) {
				String existing_lords = listoflords.get(i).name;
				if (new_lord==existing_lords)//if lord with same name already exist
					JOptionPane.showMessageDialog(new Frame(),"That Lord already exist!","Vassal allocation error",JOptionPane.PLAIN_MESSAGE);
				else {
					listoflords.add(new Lord(new_lord,master));//add the new lord
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
	
	public void updateNation() {
		for (Lord lord:listoflords) {
			lord.getGovernment();
			if (lord.title.equals("overlord"))
				lord.getCulture();
		}
	}
	
	public void mainSetup(String s) {
		this.setSize(1500,1000);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    this.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		this.setTitle(s);
		mainPane.addTab(s,listoflords.get(listoflords.size()-1).setPanel(true));
		this.add(mainPane);
		this.setVisible(true);
	}
	
	public void addVassalTab(String s) {
		mainPane.addTab(s,listoflords.get(listoflords.size()-1).setPanel(false));
	}
	
	public void recalibrateHexes() {
		List<String[]> hexes = new ArrayList<String[]>();
		boolean exists;
		hexes = hexpanel.getHex();
		listofhexes.clear();
		for (int i=0;i<hexes.size();i++) {
			exists = false;
			if ((!(hexes.get(i)[0].equals("")))){
				listofhexes.add(new Hex(hexes.get(i)));
			}
		}
	}
}