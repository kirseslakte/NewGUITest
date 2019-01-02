package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NationHandler extends JFrame{
	public List<Lord> listoflords = new ArrayList<Lord>();
	public List<Hex> listofhexes = new ArrayList<Hex>();
	public List<Unit> listofunits = new ArrayList<Unit>();
	public List<Route> listofroutes = new ArrayList<Route>();
	public List<Official> listofofficials = new ArrayList<Official>();
	public Lord active_lord;
	private int min = 0;
	private JTabbedPane mainPane = new JTabbedPane();
	private ReadNWrite write = new ReadNWrite();
	private HexPane hexpanel = new HexPane();
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
						updateNation();
						saveNation();
						System.out.println("saving");
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
				active_lord = listoflords.get(listoflords.size()-1);
				mainSetup(s);
				listoflords.get(listoflords.size()-1).setCulture();
				listoflords.get(listoflords.size()-1).setGovernment();
			} else {
				addVassalTab(loaded_lord[0]);
				listoflords.get(listoflords.size()-1).setGovernment();
			}
		}
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
	
	public void changeActiveLord(Lord active,Lord new_active) {//just changing which frame to show
		//listoflords.get(listoflords.indexOf(active)).stop();
		//listoflords.get(listoflords.indexOf(new_active)).start();
		active_lord = new_active;
	}
	
	public void newLord(String new_lord,String master) {//create a new lordWindow
		if (master.equals("")){//this is a new nation
			listoflords.add(new Lord(new_lord,master));//add lord
			active_lord = listoflords.get(0);//set lord as active
			listoflords.get(0).title = "overlord";//set title to overlord
			System.out.println("Created lord "+new_lord);
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
		mainPane.addTab("Hexes", new JScrollPane(hexpanel.hexPanel(listofhexes)));
		//mainPane.addTab("Units");
		//mainPane.addTab("Notes");
		this.add(mainPane);
		this.setVisible(true);
	}
	public void addVassalTab(String s) {
		mainPane.addTab(s,listoflords.get(listoflords.size()-1).setPanel(false));
	}
}