package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Buildings {
	
	public static String[] buildinglist = {"","RGO","Road","Highway","Armoury","Signal Towers","Hospital","Supply Cache","Bureau",
			"Palace","Public Order","Port","Center of Trade"};
	public static String[] fortificationlist = {"","Aerial Defenses","Pallisade","Hill Fort","Castle","Fortress","Citadel","Bastion"};
	public static String[] walllist = {"","Primitive","Basic","Advanced"};
	public static int[] fortifications_costs = {50,125,100,200,400,800,200};//aeri def,hill,pall,cast,fort,cita,bast
	public static int[] wall_costs = {100,125,175};//prim,bas,adv
	static String[] active_buildings;//these three are for the comboboxes
	static String[] active_forts;
	static String[] active_walls;
	
	static JFrame buildingsframe = new JFrame();
	
	static JPanel buildingpanel = new JPanel(new GridLayout(0,5));	
	static List<JComboBox<String>> building = new ArrayList<JComboBox<String>>();		//input buildings
	static List<JTextField> building_tier = new ArrayList<JTextField>();
	static List<JLabel> building_cost = new ArrayList<JLabel>();
	static List<JLabel> building_upkeep = new ArrayList<JLabel>();
	static List<Button> rmv_building = new ArrayList<Button>();
	static Button add_building = new Button("Add Building");
	
	static JPanel fortificationpanel = new JPanel(new GridBagLayout());
	static List<JComboBox<String>> fortification = new ArrayList<JComboBox<String>>();	//input fortifications
	static List<JTextField> fortification_capacity = new ArrayList<JTextField>();
	static List<JLabel> fortification_addon = new ArrayList<JLabel>();
	static List<JLabel> fortification_cost = new ArrayList<JLabel>();
	static List<JLabel> fortification_upkeep = new ArrayList<JLabel>();
	static List<Button> rmv_fortification = new ArrayList<Button>();
	static Button add_fortification = new Button("Add Fortification");
	
	static JPanel wallpanel = new JPanel(new GridBagLayout());		
	static JComboBox<String> wall = new JComboBox<String>();	//input walls
	static List<JLabel> wall_addon = new ArrayList<JLabel>();
	static JLabel wall_cost = new JLabel("0");
	static JLabel wall_upkeep = new JLabel("0");
	static List<Button> add_ons_btn = new ArrayList<Button>();
	
	static List<BuildingsAddOns> addonframes = new ArrayList<BuildingsAddOns>();
	static WallAddOns wallframe;

	static List<Building> listofbuildings = new ArrayList<Building>();
	static List<Building> listoffortifications = new ArrayList<Building>();
	static List<String> built_buildings = new ArrayList<String>();	//BUILDINGS syntax:RGO-1 Road-0
	static List<String> built_fortifications = new ArrayList<String>();//FORTIFICATIONS syntax: Hill Fort-3,Moat,Motte
	static String built_walls = "";//WALLS								syntax: Basic-Bastion,1-Heavy Wooden Gate,2
	
	static double[] modifiers = {1,1,1,1,1};//RGO,Building,Unit Equipment,Fortification,Palace
	static int active_hex_index;
	
	static GridBagConstraints c = new GridBagConstraints();
	public Frame fortificationframe;			//fortifications plug-plug-in
	
	//outputs
	static String[] all_buildings;//same syntax as in save file (B-RGO-1/W-Basic-Bastion,1)
	static int upgrade_cost;
	static int upkeep_cost;
	
	public Buildings() {
		//System.out.println("BUILDINGS! Buildings");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.ipady = 20;
	}
	
	public static void initialize() {
		//System.out.println("BUILDINGS! initialize");
		setFrame();
	}
	
	public static void getLordinfo() {
		//System.out.println("BUILDINGS! getInputs");
		all_buildings = NationHandler.listofhexes.get(active_hex_index).built_buildings;
		int lord = Utility.findLord(NationHandler.listofhexes.get(active_hex_index).owner);
		double[] tempmod = NationHandler.listoflords.get(lord).modifiers;
		String lifestyle = NationHandler.listoflords.get(lord).government.style;
		if (lifestyle.equals("Nomadic")){
			active_forts = new String[4];
			active_forts[0] = "";
			active_forts[1] = "Aerial Defenses";
			active_forts[2] = "Pallisade";
			active_forts[3] = "Bastion";
			active_walls = new String[2];
			active_walls[0] = "";
			active_walls[1] = "Primitive";
		}else if (lifestyle.equals("Tribalistic")){
			active_forts = new String[6];
			active_forts[0] = "";
			active_forts[1] = "Aerial Defenses";
			active_forts[2] = "Pallisade";
			active_forts[3] = "Hill Fort";
			active_forts[4] = "Castle";
			active_forts[5] = "Bastion";
			active_walls = new String[3];
			active_walls[0] = "";
			active_walls[1] = "Primitive";
			active_walls[2] = "Basic";
		}else {
			active_forts = fortificationlist;
			active_walls = walllist;
		}
		if (NationHandler.listofhexes.get(active_hex_index).pop_size<4) {
			active_buildings = new String[buildinglist.length-1];
			for (int i=0;i<buildinglist.length-1;i++)
				active_buildings[i] = buildinglist[i];
		}else
			active_buildings = buildinglist;
		setCombos();
		modifiers[0] = tempmod[20];//rgo/road
		modifiers[1] = tempmod[21];//buildings
		modifiers[2] = tempmod[25];//equipment
		modifiers[3] = tempmod[26];//fortification
		modifiers[4] = tempmod[27];//palace
		//loadBuildings();
	}

	public static void setCombos() {
		//System.out.println("BUILDINGS! setCombos");
		for (int i=0;i<building.size();i++) {
			building.get(i).removeAllItems();
			for (String s:active_buildings)
				building.get(i).addItem(s);
		}
		for (int i=0;i<fortification.size();i++){
			fortification.get(i).removeAllItems();
			for (String s:active_forts)
				fortification.get(i).addItem(s);
		}
		wall.removeAllItems();
		for (String s:active_walls)
			wall.addItem(s);
	}
	
	public static void setFrame() {
		//System.out.println("BUILDINGS! setFrame");
		JPanel mainbuild = new JPanel(new BorderLayout());
		Panel headerpanel = new Panel(new GridLayout(0,5));
		mainbuild.add(headerpanel,BorderLayout.PAGE_START);
		mainbuild.add(buildingpanel,BorderLayout.LINE_START);
		mainbuild.add(fortificationpanel,BorderLayout.CENTER);
		mainbuild.add(wallpanel,BorderLayout.LINE_END);
		buildingsframe.setSize(600, 950);
		buildingsframe.setLocationRelativeTo(null);
		buildingsframe.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				//update();
				NationHandler.saveNation();
				stop();
				for (int i=0;i<addonframes.size();i++)
					addonframes.get(i).stop();
				wallframe.stop();
			}
		});
	    Button update_building_btn = new Button("Update");
	    update_building_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
				//update();
	    	}
	    });
	    headerpanel.add(new JLabel(""));
	    headerpanel.add(new JLabel(""));
	    headerpanel.add(new JLabel(""));
	    headerpanel.add(new JLabel(""));
	    headerpanel.add(update_building_btn);
	    //buildings panel
	    buildingpanel.add(new JLabel("Building"));
	    buildingpanel.add(new JLabel("Tier"));
	    buildingpanel.add(new JLabel("Cost"));
	    buildingpanel.add(new JLabel("Upkeep"));
	    buildingpanel.add(new JLabel(""));
	    addBuildingRow();
	    //fortifications panel
/*
	    c.gridwidth = 1;
		c.gridy = 0;
	    c.gridx = 0;
	    c.gridwidth = 2;
	    c.gridx++;
	    buildingpanel.add(new JLabel("Add-Ons"), c);
	    c.gridy = 13;//fortifications
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Fortifications"),c);
	    c.gridx = 1;
	    mainbuild.add(new JLabel("Capacity"),c);
	    c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    active_forts = fortificationlist;
	    if (addonframes.size()==0)
	    	wallframe = new WallAddOns();
	    for (int j=addonframes.size();j<5;j++) {
	    	addonframes.add(new BuildingsAddOns());
	    }
	    for (int j=0;j<5;j++) {
	    	fortifications.add(new JComboBox<>(active_forts));/*
	    	this.fortifications.get(j).addActionListener(new ActionListener() {
	    		public void actionPerformed (ActionEvent e) {
					update();
	    		}
	    	});*//*
	    	fortifications_capacity.add(new JTextField("0"));
	    	add_ons_btn.add(new Button("Add-on"));
	    	int k = j;
	    	add_ons_btn.get(j).addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			update();
	    			if (!addonframes.get(k).isVisible()) {
	    				String b = (String) fortifications.get(k).getSelectedItem();
	    				addonframes.get(k).start(active_hex_index,b);
	    			}
	    		}
	    	});
	    	fortifications_cost.add(new JLabel("0"));
	    	fortifications_upkeep.add(new JLabel("0"));
	    	//ADDED CODE LAYER FOR FORTIFICATIONS
	    	c.gridy = 14+j;
	    	c.gridx = 0;
	    	mainbuild.add(fortifications.get(j),c);
	    	c.gridx = 1;
	    	mainbuild.add(fortifications_capacity.get(j),c);
	    	c.gridx = 2;
	    	mainbuild.add(add_ons_btn.get(j),c);
	    	c.gridx = 3;
	    	mainbuild.add(fortifications_cost.get(j),c);
	    	c.gridx = 4;
	    	mainbuild.add(fortifications_upkeep.get(j),c);
	    	//ADDED VISUAL LAYER FOR FORTIFICATIONS
	    	try {
	    		int t = all_buildings.length;//see if all_buildings is initialized
	    	} catch (NullPointerException e) {
	    		all_buildings = new String[0];//set it to a null string 
	    	}
	    }
	    active_walls = walllist;
	    walls = new JComboBox<>(active_walls);
	    walls.setSelectedItem("");
	    Button wall_btn = new Button("Add-on");
	    wall_btn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			update();
    			if (!wallframe.isVisible()) {
    				String b = (String) walls.getSelectedItem();
    				wallframe.start(active_hex_index,b,modifiers[3]);
    			}
    		}
    	});
	    //ADDED CODE LAYER FOR WALLS
	    c.gridy = 19;
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Walls"),c);
	    c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    c.gridy = 20;
	    c.gridx = 0;
	    mainbuild.add(walls,c);
	    c.gridx = 2;
	    mainbuild.add(wall_btn,c);
	    c.gridx = 3;
	    mainbuild.add(walls_cost,c);
	    c.gridx = 4;
	    mainbuild.add(walls_upkeep,c);
	    //ADDED VISUAL LAYER FOR WALLS
	    */
	    /*ADDED CODE LAYER FOR GATES
	    this.c.gridy = 21;
	    this.c.gridx = 0;
	    mainbuild.add(new JLabel("Gates (UNDERGROUND HEX)"),c);
	    this.c.gridx = 1;
	    mainbuild.add(new JLabel("Amount"),c);
	    this.c.gridx = 2;
	    mainbuild.add(new JLabel("Cost"),c);
	    this.c.gridx = 3;
	    mainbuild.add(new JLabel("Upkeep"),c);*/
	    //ADDED VISUAL LAYER FOR GATES
	    buildingsframe.add(new JScrollPane(mainbuild));
	}
/*
	public static void getVisuals() {//gets info from the visual layer and updates code accordingly (as well as updating visual layer)
		//System.out.println("BUILDINGS! getVisuals");
		built_buildings.clear();
		for (int i=0;i<buildings.size();i++) {
			String build = (String) buildings.get(i).getSelectedItem();
			if (!(build.equals(""))) {
				int tier = 0;
				try {
					tier = Integer.parseInt(building_tiers.get(i).getText());
				} catch (Exception e ) { System.out.println(e);}
				built_buildings.add(build+"-"+Integer.toString(tier));
				tier = findBuildingCost(build,tier);
				buildings_cost.get(i).setText(Integer.toString(tier));
				buildings_cost.get(i).setToolTipText(findOfficialBuildingCost(tier));
				if (build.equals("RGO")||build.equals("Supply Cache")||build.equals("Bureau")||build.equals("Center of Trade"))
					buildings_upkeep.get(i).setText("0");
				else
					buildings_upkeep.get(i).setText(Integer.toString((int) Math.round(tier*0.2)));
			} else {
				buildings_cost.get(i).setText("0");
				buildings_upkeep.get(i).setText("0");
			}
		}//done with buildings
		built_fortifications.clear();
		for (int i=0;i<fortifications.size();i++) {
			String built = (String) fortifications.get(i).getSelectedItem();
			if (!(built.equals(""))) {
				int tier = 0;
				try {
					tier = Integer.parseInt(fortifications_capacity.get(i).getText());
				} catch (Exception e ){System.out.println(e);}
				String addons = "";
				for (int j=0;j<addonframes.get(i).built_add_on.size();j++) {
					if (j==0)
						addons = "-";
					else
						addons += ",";
					addons += addonframes.get(i).built_add_on.get(j);
				}
					built_fortifications.add(built+"-"+tier+addons);
					tier = findFortificationCost(built,tier);
					double add_upk = 0;
					for (int j=0;j<addonframes.get(i).add_on_upkeep.size();j++)
						add_upk += Integer.parseInt(addonframes.get(i).add_on_upkeep.get(j).getText());
					fortifications_upkeep.get(i).setText(Integer.toString((int) Math.round(tier*0.2+add_upk)));
					tier += addonframes.get(i).cost;
					fortifications_cost.get(i).setText(Integer.toString(tier));
					fortifications_cost.get(i).setToolTipText(findOfficialFortificationCost(tier));
			}else {
				fortifications_cost.get(i).setText("0");
				fortifications_upkeep.get(i).setText("0");
			}
		}
		String built = (String) walls.getSelectedItem();
		String addons = "";
		for (int j=0;j<wallframe.built_add_on.size();j++) {
			addons += "-"+wallframe.built_add_on.get(j);
		}
		int tier = 0;
		if (!(built.equals("")))
			if (addons.equals(""))
				built_walls = built;
			else
				built_walls = built+addons;
		else
			built_walls = "";
		tier = findFortificationCost(built,0);
		tier += wallframe.cost;
		walls_cost.setText(Integer.toString(tier));
		walls_cost.setToolTipText(findOfficialFortificationCost(tier));
		walls_upkeep.setText(Integer.toString((int) Math.round(tier*0.2)));
		int wall = 0;
		if (!(built_walls.equals("")))
			wall = 1;
		all_buildings = new String[built_buildings.size()+built_fortifications.size()+wall];
		for (int i=0;i<built_buildings.size();i++)
			all_buildings[i] = "B-"+built_buildings.get(i);
		for (int i=0;i<built_fortifications.size();i++)
			all_buildings[built_buildings.size()+i] = "F-"+built_fortifications.get(i);
		if (!(built_walls.equals("")))
			all_buildings[all_buildings.length-1] = "W-"+built_walls;
		NationHandler.listofhexes.get(active_hex_index).setBuildings(all_buildings);
	}
	
	public static void update() {//calls getVisuals and updates outputs
		//System.out.println("BUILDINGS! update");
		getVisuals();
		//System.out.println("Visuals got");
		//fixing remaining outputs
		upgrade_cost = 0;
		if (NationHandler.listofhexes.get(active_hex_index).pop_size<10){
			for (int i=0;i<built_buildings.size();i++) {
				String[] splitter = Utility.stringSplitter(built_buildings.get(i), "-");
				if (!(built_buildings.get(i).equals(""))) {
					int tier = Integer.parseInt(splitter[1]);
					if (splitter[0].equals("Supply Cache"))
						upgrade_cost += (int) Math.round(200*tier*modifiers[1]*(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size]-
								Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]));
					if (splitter[0].equals("Bureau"))
						upgrade_cost += (int) Math.round(100*modifiers[1]*(Math.pow(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size],2)-
								Math.pow(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1],2)));
					if (splitter[0].equals("Center of Trade"))
						upgrade_cost += (int) Math.round(400*modifiers[1]*(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size]-
								Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]));
					if (splitter[0].equals("Public Order"))
						upgrade_cost += (int) Math.round(100*2*tier*modifiers[1]*(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size]-
								Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]));
					if (splitter[0].equals("Port"))
						upgrade_cost += (int) Math.round(500*modifiers[1]*(Math.pow(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size],2)-
								Math.pow(Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1],2)));
				}
			}
		}//upgrade costs for buildings
		if (!(built_walls.equals(""))){
			String[] splitter = Utility.stringSplitter(built_walls, "-");
			int caphelper = Hex.list_unit_cap[NationHandler.listofhexes.get(active_hex_index).pop_size]-NationHandler.listofhexes.get(active_hex_index).unit_cap;
			if (splitter[0].equals(walllist[1]))
				caphelper = (int) Math.round(caphelper*wall_costs[0]*modifiers[3]);
			else if (splitter[0].equals(walllist[2]))
				caphelper = (int) Math.round(caphelper*wall_costs[1]*modifiers[3]);
			else if (splitter[0].equals(walllist[3]))
				caphelper = (int) Math.round(caphelper*wall_costs[2]*modifiers[3]);
			int addoncost = 0;
			//for (String s:splitter)
			if (splitter.length>1) {
				String[] wall_string = new String[splitter.length-1];
				int[] wall_int = new int[splitter.length-1];
				for (int j=1;j<splitter.length;j++) {
					String[] wall_splitter = Utility.stringSplitter(splitter[j],",");
					wall_string[j-1] = wall_splitter[0];
					wall_int[j-1] = Integer.parseInt(wall_splitter[1]);
					for (int i=0;i<WallAddOns.wall_add_on_costs.length;i++){
						if (wall_string[j-1].equals(WallAddOns.walladdonlist[i+1]))
							addoncost += (int) Math.round(WallAddOns.wall_add_on_costs[i]*modifiers[3]*wall_int[j-1]);
					}
				}
			}
			upgrade_cost += caphelper;
		}//all upgrade costs done
		upkeep_cost = 0;
		for (int i=0;i<buildings_upkeep.size();i++)
			upkeep_cost += Integer.parseInt(buildings_upkeep.get(i).getText());
		for (int i=0;i<fortifications_upkeep.size();i++)
			upkeep_cost += Integer.parseInt(fortifications_upkeep.get(i).getText());
		upkeep_cost += Integer.parseInt(walls_upkeep.getText());
		//all upkeep costs done
	}
	
	public static void loadBuildings() {//should be called whenever this window is opened (loads buildings from hex and sets it into visual and code layer)
		//System.out.println("BUILDINGS! loadBuildings");
		String[] splitter;
		int b = 0;
		int f = 0;
		built_buildings.clear();
		built_fortifications.clear();
		built_walls = "";
		for (int i=0;i<all_buildings.length;i++) {
			String input = all_buildings[i];
			splitter = (Utility.stringSplitter(input,"-"));
			if (splitter[0].equals("B")) {
				buildings.get(b).setSelectedItem(splitter[1]);
				building_tiers.get(b).setText(splitter[2]);
				built_buildings.add(splitter[1]+"-"+splitter[2]);
				b++;
			}
			if (splitter[0].equals("F")) {
				fortifications.get(f).setSelectedItem(splitter[1]);
				fortifications_capacity.get(f).setText(splitter[2]);
				int costy = findFortificationCost(splitter[1],Integer.parseInt(splitter[2]));
				if (splitter.length>3) {
					String[] splittar = Utility.stringSplitter(splitter[3],",");
					addonframes.get(f).setAddOn(splittar,costy,modifiers[3]);
					built_fortifications.add(splitter[1]+"-"+splitter[2]+"-"+splitter[3]);
				} else
					built_fortifications.add(splitter[1]+"-"+splitter[2]);
				f++;
			}
			if (splitter[0].equals("W")) {
				walls.setSelectedItem(splitter[1]);
				String[] wall_string = new String[splitter.length-2];
				int[] wall_int = new int[splitter.length-2];
				built_walls = splitter[1];
				for (int j=2;j<splitter.length;j++) {
					String[] wall_splitter = Utility.stringSplitter(splitter[j],",");
					wall_string[j-2] = wall_splitter[0];
					wall_int[j-2] = Integer.parseInt(wall_splitter[1]);
					built_walls += "-"+wall_splitter[0]+","+wall_splitter[1];
				}
				wallframe.setAddOn(wall_string,wall_int);
			}
		}
		update();
	}

	public static int findFortificationCost(String s, int tier) {//find the cost of a fortification/wall
		//System.out.println("BUILDINGS! findFortificationCost");
		int build_cost = 0;
		for (int i=1;i<active_forts.length;i++){
			if (s.equals(active_forts[i])) {
				if (s.equals("Aerial Defenses"))
					build_cost = (int) Math.round(fortifications_costs[i-1]*modifiers[3]);
				else
					build_cost = (int) Math.round(tier*fortifications_costs[i-1]*modifiers[3]);
			}
		}
		for (int i=1;i<active_walls.length;i++){
			if (s.equals(active_walls[i]))
				build_cost = (int) Math.round(wall_costs[i-1]*modifiers[3]*NationHandler.listofhexes.get(active_hex_index).unit_cap);//that is done
		}
		return build_cost;
	}
	
	public static int findBuildingCost(String s, int tier) {//find the cost of a building at position pos in the list
		//System.out.println("BUILDINGS! findBuildingCost");
		int build_cost = 0;
		if (s.equals(buildinglist[1])){//RGO
			if (tier>2||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum logging rate is 2 and the minimum is 1.\nMore or less is not allowed.","RGO Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round((500+500*tier)*modifiers[0]*modifiers[1]);
		}else if (s.equals(buildinglist[2])){//Road
			build_cost = (int) Math.round(((12-NationHandler.listofhexes.get(active_hex_index).habitability)*75)*modifiers[0]*modifiers[1]);
		}else if (s.equals(buildinglist[3])){//Highway
			build_cost = (int) Math.round(((12-NationHandler.listofhexes.get(active_hex_index).habitability)*150)*modifiers[0]*modifiers[1]);
		}else if (s.equals(buildinglist[4])){//Armoury
			build_cost = (int) Math.round(250*modifiers[1]+tier*modifiers[2]);
		}else if (s.equals(buildinglist[5])){//Signal Towers
			build_cost = (int) Math.round(50*modifiers[1]);
		}else if (s.equals(buildinglist[6])){//Hospital
			build_cost = (int) Math.round(500*tier*modifiers[1]);
		}else if (s.equals(buildinglist[7])){//Supply Cache
			if (tier>13)
				JOptionPane.showMessageDialog(null, "The maximum supply rate is 13.\nMore is not allowed.","Supply Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round(200*tier*Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*modifiers[1]);
		}else if (s.equals(buildinglist[8])){//Bureau
			build_cost = (int) Math.round(100*Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*
					Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*modifiers[1]);
		}else if (s.equals(buildinglist[12])){//Center of Trade
			build_cost = (int) Math.round(400*Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*modifiers[1]);
		}else if (s.equals(buildinglist[9])){//Palace
			if (tier>3||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum legitimacy enforcement is 3 and the minimum is 1."
						+ "\nMore or less is not allowed.","Palace Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round((2500+2500*tier)*modifiers[1]*modifiers[4]);
		}else if (s.equals(buildinglist[10])){//Public Order
			if (tier>2||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum unrest enforcement is 2 and the minimum is 1."
						+ "\nMore or less is not allowed.","Public Order Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round(100*2*tier*Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*modifiers[1]);
		}else if (s.equals(buildinglist[11])){//Port
			build_cost = (int) Math.round(500*Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*
					Hex.list_pm[NationHandler.listofhexes.get(active_hex_index).pop_size-1]*modifiers[1]);
		}
		//findOfficialBuildingCost(build_cost);
		return build_cost;
	}

	public static String findOfficialBuildingCost(int building_cost) {//find an official of the lord building buildings and see what the building cost would be for them
		String output = "<html>";
		for (Official o:NationHandler.listofofficials) {
			if (o.lord.equals(NationHandler.listofhexes.get(active_hex_index).owner)) {
				if (o.job.equals("Build Building")) {
					output += o.name+" is currently building buildings for "+NationHandler.listofhexes.get(active_hex_index).owner+
							" and is reducing the cost of buildings by "+o.effect+"%. This building would therefore"
							+ " cost "+Integer.toString((int) Math.round(building_cost*(100-o.effect)/100))+".<br>";
				}
			}
		}
		output += "</html>";
		return output;
	}
	public static String findOfficialFortificationCost(int fortification_cost) {
		String output = "<html>";
		for (Official o:NationHandler.listofofficials) {
			if (o.lord.equals(NationHandler.listofhexes.get(active_hex_index).owner)) {
				if (o.job.equals("Build Fortification")) {
					output += o.name+" is currently building fortifications for "+NationHandler.listofhexes.get(active_hex_index).owner+
							" and is reducing the cost of fortifications by "+o.effect+"%. This building would"
							+ " therefore cost "+Integer.toString((int) Math.round(fortification_cost*(100-o.effect)/100))+".<br>";
				}
			}
		}
		output += "</html>";
		return output;
	}
	*/
	public static void addBuildingRow() {
		int i = building.size();
		building.add(new JComboBox<>());
		building_tier.add(new JTextField(""));
		building_cost.add(new JLabel("0"));
		building_upkeep.add(new JLabel("0"));
		rmv_building.add(new Button("X"));
		rmv_building.get(i).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				removeBuildingRow(i);
			}
		});
		buildingpanel.remove(add_building);
		buildingpanel.add(building.get(i));
		buildingpanel.add(building_tier.get(i));
		buildingpanel.add(building_cost.get(i));
		buildingpanel.add(building_upkeep.get(i));
		buildingpanel.add(rmv_building.get(i));
		buildingpanel.add(add_building);
	}
	
	public static void removeBuildingRow(int i) {
		buildingpanel.remove(add_building);
		for (int k=building.size()-1;k>=i;k--) {
			buildingpanel.remove(building.get(k));
			buildingpanel.remove(building_tier.get(k));
			buildingpanel.remove(building_cost.get(k));
			buildingpanel.remove(building_upkeep.get(k));
			buildingpanel.remove(rmv_building.get(k));
			building.remove(k);
			building_tier.remove(k);
			building_cost.remove(k);
			building_upkeep.remove(k);
			rmv_building.remove(k);
		}
		listofbuildingsToVisuals();
	}
	
	public static void listofbuildingsToVisuals() {
		for (int i=0;i<listofbuildings.size()-1;i++) {
			try {
				building.get(i).setSelectedItem(listofbuildings.get(i).name);
				building_tier.get(i).setText(Integer.toString(listofbuildings.get(i).tier));
			} catch (IndexOutOfBoundsException e) {
				addBuildingRow();
				i--;
			}
		}
	}
	
	public static void addFortificationRow() {
		
	}
	
	public static void removeFortificationRow(int i) {
		
	}
	public static void saveBuildings() {
		//compiles the visuals and sends them into NationHandler.listofhexes.get(active_hex_index)
	}
	
	public static void loadActiveHex() {
		//takes whatever is in NationHandler.listofhex.get(active_hex_index) and puts it into visuals
	}
	public static void start(int hex_index) {
		//System.out.println("BUILDINGS! start");
		active_hex_index = hex_index;
		getLordinfo();
		buildingsframe.setTitle(NationHandler.listofhexes.get(active_hex_index).name+": Buildings and Fortifications");
	    loadActiveHex();
		buildingsframe.setVisible(true);
	}
	
	public static void stop() {
		//System.out.println("BUILDINGS! stop");
		saveBuildings();
		HexPane.setHexVisuals();
	    buildingsframe.setVisible(false);
	}
}