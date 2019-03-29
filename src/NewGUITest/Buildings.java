package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Buildings extends JFrame{
	
	public static String[] buildinglist = {"","RGO","Road","Highway","Armoury","Signal Towers","Hospital","Supply Cache","Bureau",
			"Palace","Public Order","Port","Center of Trade"};
	public static String[] fortificationlist = {"","Aerial Defenses","Pallisade","Hill Fort","Castle","Fortress","Citadel","Bastion"};
	public static String[] walllist = {"","Primitive","Basic","Advanced"};
	public static int[] fortifications_costs = {50,125,100,200,400,800,200};//aeri def,hill,pall,cast,fort,cita,bast
	public static int[] wall_costs = {100,125,175};//prim,bas,adv
	public String[] active_buildings;
	public String[] active_forts;
	public String[] active_walls;
	
	public List<String> built_buildings = new ArrayList<String>();	//BUILDINGS syntax:RGO-1 Road-0
	public List<JComboBox> buildings = new ArrayList<JComboBox>();		//input buildings
	public List<JTextField> building_tiers = new ArrayList<JTextField>();
	public List<JLabel> buildings_cost = new ArrayList<JLabel>();
	public List<JLabel> buildings_upkeep = new ArrayList<JLabel>();
	public List<String> built_fortifications = new ArrayList<String>();//FORTIFICATIONS syntax: Hill Fort-3,Moat,Motte
	public List<JComboBox> fortifications = new ArrayList<JComboBox>();	//input fortifications
	public List<JTextField> fortifications_capacity = new ArrayList<JTextField>();
	public List<JLabel> fortifications_cost = new ArrayList<JLabel>();
	public List<JLabel> fortifications_upkeep = new ArrayList<JLabel>();
	public String built_walls = "";//WALLS								syntax: Basic-Bastion,1-Heavy Wooden Gate,2
	public JComboBox walls = new JComboBox();	//input walls
	public JLabel walls_cost = new JLabel("0");
	public JLabel walls_upkeep = new JLabel("0");
	
	public List<Button> add_ons_btn = new ArrayList<Button>();
	public List<BuildingsAddOns> addonframes = new ArrayList<BuildingsAddOns>();
	public WallAddOns wallframe;
	public double[] modifiers = {1,1,1,1,1};//RGO,Building,Unit Equipment,Fortification,Palace
	public Hex hex;
	public int hex_number;
	
	GridBagConstraints c = new GridBagConstraints();
	public Frame fortification;			//fortifications plug-plug-in
	
	//outputs
	public String[] all_buildings;//same syntax as in save file (B-RGO-1/W-Basic-Bastion,1)
	public int upgrade_cost;
	public int upkeep_cost;
	
	public Buildings() {
		//System.out.println("BUILDINGS! Buildings");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.weightx = 0.5;
		this.c.ipady = 20;
	}
	
	public void initialize() {
		//System.out.println("BUILDINGS! initialize");
		this.setFrame();
	}
	
	public void start(int hex_number) {
		//System.out.println("BUILDINGS! start");
		this.getInputs(hex_number);
		this.hex_number = hex_number;
		this.setTitle(this.hex.name+": Buildings and Fortifications");
	    this.setVisible(true);
	    //loadBuildings();
	}
	
	public void stop() {
		//System.out.println("BUILDINGS! stop");
	    this.setVisible(false);
	}
	
	public void getInputs(int hex_number) {
		//System.out.println("BUILDINGS! getInputs");
		this.hex = NationHandler.listofhexes.get(hex_number);
		this.all_buildings = new String[this.hex.buildings.size()];
		for (int i=0;i<this.hex.buildings.size();i++)
			this.all_buildings[i] = this.hex.buildings.get(i);
		int lord = Utility.findLord(this.hex.owner);
		double[] tempmod = NationHandler.listoflords.get(lord).modifiers;
		String lifestyle = NationHandler.listoflords.get(lord).government.style;
		if (lifestyle.equals("Nomadic")){
			this.active_forts = new String[4];
			this.active_forts[0] = "";
			this.active_forts[1] = "Aerial Defenses";
			this.active_forts[2] = "Pallisade";
			this.active_forts[3] = "Bastion";
			this.active_walls = new String[2];
			this.active_walls[0] = "";
			this.active_walls[1] = "Primitive";
		}else if (lifestyle.equals("Tribalistic")){
			this.active_forts = new String[6];
			this.active_forts[0] = "";
			this.active_forts[1] = "Aerial Defenses";
			this.active_forts[2] = "Pallisade";
			this.active_forts[3] = "Hill Fort";
			this.active_forts[4] = "Castle";
			this.active_forts[5] = "Bastion";
			this.active_walls = new String[3];
			this.active_walls[0] = "";
			this.active_walls[1] = "Primitive";
			this.active_walls[2] = "Basic";
		}else {
			this.active_forts = fortificationlist;
			this.active_walls = walllist;
		}
		if (this.hex.pop_size<4) {
			this.active_buildings = new String[buildinglist.length-1];
			for (int i=0;i<buildinglist.length-1;i++)
				this.active_buildings[i] = buildinglist[i];
		}else
			this.active_buildings = buildinglist;
		this.setCombos();
		this.modifiers[0] = tempmod[20];//rgo/road
		this.modifiers[1] = tempmod[21];//buildings
		this.modifiers[2] = tempmod[25];//equipment
		this.modifiers[3] = tempmod[26];//fortification
		this.modifiers[4] = tempmod[27];//palace
		this.loadBuildings();
	}

	public void setCombos() {
		//System.out.println("BUILDINGS! setCombos");
		for (int i=0;i<this.buildings.size();i++) {
			this.buildings.get(i).removeAllItems();
			for (String s:this.active_buildings)
				this.buildings.get(i).addItem(s);
		}
		for (int i=0;i<this.fortifications.size();i++){
			this.fortifications.get(i).removeAllItems();
			for (String s:this.active_forts)
				this.fortifications.get(i).addItem(s);
		}
		this.walls.removeAllItems();
		for (String s:this.active_walls)
			this.walls.addItem(s);
	}
	
	public void setFrame() {
		//System.out.println("BUILDINGS! setFrame");
		JPanel mainbuild = new JPanel(new GridBagLayout());
		this.setSize(600, 950);
		this.setLocationRelativeTo(null);
	    this.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				update();
				NationHandler.hexpanel.getBuildings(hex_number);
				NationHandler.saveNation();
				stop();
				for (int i=0;i<addonframes.size();i++)
					addonframes.get(i).stop();
				wallframe.stop();
			}
		});
	    this.c.gridwidth = 1;
		this.c.gridy = 0;//starting with the first row
	    this.c.gridx = 4;
	    Button update_building_btn = new Button("Update");
	    update_building_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
				update();
	    	}
	    });
	    mainbuild.add(update_building_btn,c);
	    this.c.gridx = 0;
	    mainbuild.add(new JLabel("Buildings"),c);
	    this.c.gridx = 1;
	    mainbuild.add(new JLabel("Tier/Unit eq. Cost/Capacity"),c);
	    this.c.gridx = 2;
	    mainbuild.add(new JLabel("Cost"),c);
	    this.c.gridx = 3;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    this.active_buildings = buildinglist;
	    for (int j=0;j<11;j++) {
	    	this.buildings.add(new JComboBox<>(this.active_buildings));
	    	this.building_tiers.add(new JTextField("0"));
	    	this.buildings_cost.add(new JLabel("0"));
	    	this.buildings_upkeep.add(new JLabel("0"));
	    	//ADDED CODE LAYER OF BUILDINGS
	    	this.c.gridy = 2+j;
	    	this.c.gridx = 0;
	    	mainbuild.add(buildings.get(j),c);
	    	this.c.gridx = 1;
	    	mainbuild.add(building_tiers.get(j),c);
	    	this.c.gridx = 2;
	    	mainbuild.add(buildings_cost.get(j),c);
	    	this.c.gridx = 3;
	    	mainbuild.add(buildings_upkeep.get(j),c);
	    	//ADDED VISUAL LAYER OF BUILDINGS
	    }//all building rows added
	    this.c.gridy = 13;//fortifications
	    this.c.gridx = 0;
	    mainbuild.add(new JLabel("Fortifications"),c);
	    this.c.gridx = 1;
	    mainbuild.add(new JLabel("Capacity"),c);
	    this.c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    this.c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    this.c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    this.active_forts = fortificationlist;
	    if (this.addonframes.size()==0)
	    	this.wallframe = new WallAddOns();
	    for (int j=this.addonframes.size();j<5;j++) {
	    	this.addonframes.add(new BuildingsAddOns());
	    }
	    for (int j=0;j<5;j++) {
	    	this.fortifications.add(new JComboBox<>(this.active_forts));/*
	    	this.fortifications.get(j).addActionListener(new ActionListener() {
	    		public void actionPerformed (ActionEvent e) {
					update();
	    		}
	    	});*/
	    	this.fortifications_capacity.add(new JTextField("0"));
	    	this.add_ons_btn.add(new Button("Add-on"));
	    	int k = j;
	    	this.add_ons_btn.get(j).addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			update();
	    			if (!addonframes.get(k).isVisible()) {
	    				String b = (String) fortifications.get(k).getSelectedItem();
	    				addonframes.get(k).start(hex_number,b);
	    			}
	    		}
	    	});
	    	this.fortifications_cost.add(new JLabel("0"));
	    	this.fortifications_upkeep.add(new JLabel("0"));
	    	//ADDED CODE LAYER FOR FORTIFICATIONS
	    	this.c.gridy = 14+j;
	    	this.c.gridx = 0;
	    	mainbuild.add(this.fortifications.get(j),c);
	    	this.c.gridx = 1;
	    	mainbuild.add(this.fortifications_capacity.get(j),c);
	    	this.c.gridx = 2;
	    	mainbuild.add(this.add_ons_btn.get(j),c);
	    	this.c.gridx = 3;
	    	mainbuild.add(this.fortifications_cost.get(j),c);
	    	this.c.gridx = 4;
	    	mainbuild.add(this.fortifications_upkeep.get(j),c);
	    	//ADDED VISUAL LAYER FOR FORTIFICATIONS
	    	try {
	    		int t = this.all_buildings.length;//see if all_buildings is initialized
	    	} catch (NullPointerException e) {
	    		this.all_buildings = new String[0];//set it to a null string 
	    	}
	    }
	    this.active_walls = walllist;
	    this.walls = new JComboBox<>(this.active_walls);
	    this.walls.setSelectedItem("");
	    Button wall_btn = new Button("Add-on");
	    wall_btn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			update();
    			if (!wallframe.isVisible()) {
    				String b = (String) walls.getSelectedItem();
    				wallframe.start(hex_number,b,modifiers[3]);
    			}
    		}
    	});
	    //ADDED CODE LAYER FOR WALLS
	    this.c.gridy = 19;
	    this.c.gridx = 0;
	    mainbuild.add(new JLabel("Walls"),c);
	    this.c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    this.c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    this.c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    this.c.gridy = 20;
	    this.c.gridx = 0;
	    mainbuild.add(walls,c);
	    this.c.gridx = 2;
	    mainbuild.add(wall_btn,c);
	    this.c.gridx = 3;
	    mainbuild.add(walls_cost,c);
	    this.c.gridx = 4;
	    mainbuild.add(walls_upkeep,c);
	    //ADDED VISUAL LAYER FOR WALLS
	    
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
	    this.add(new JScrollPane(mainbuild));
	}

	public void getVisuals() {//gets info from the visual layer and updates code accordingly (as well as updating visual layer)
		//System.out.println("BUILDINGS! getVisuals");
		this.built_buildings.clear();
		for (int i=0;i<this.buildings.size();i++) {
			String build = (String) this.buildings.get(i).getSelectedItem();
			if (!(build.equals(""))) {
				int tier = 0;
				try {
					tier = Integer.parseInt(this.building_tiers.get(i).getText());
				} catch (Exception e ) { System.out.println(e);}
				this.built_buildings.add(build+"-"+Integer.toString(tier));
				tier = findBuildingCost(build,tier);
				this.buildings_cost.get(i).setText(Integer.toString(tier));
				this.buildings_cost.get(i).setToolTipText(findOfficialBuildingCost(tier));
				if (build.equals("RGO")||build.equals("Supply Cache")||build.equals("Bureau")||build.equals("Center of Trade"))
					this.buildings_upkeep.get(i).setText("0");
				else
					this.buildings_upkeep.get(i).setText(Integer.toString((int) Math.round(tier*0.2)));
			} else {
				this.buildings_cost.get(i).setText("0");
				this.buildings_upkeep.get(i).setText("0");
			}
		}//done with buildings
		this.built_fortifications.clear();
		for (int i=0;i<this.fortifications.size();i++) {
			String built = (String) this.fortifications.get(i).getSelectedItem();
			if (!(built.equals(""))) {
				int tier = 0;
				try {
					tier = Integer.parseInt(this.fortifications_capacity.get(i).getText());
				} catch (Exception e ){System.out.println(e);}
				String addons = "";
				for (int j=0;j<this.addonframes.get(i).built_add_on.size();j++) {
					if (j==0)
						addons = "-";
					else
						addons += ",";
					addons += this.addonframes.get(i).built_add_on.get(j);
				}
					this.built_fortifications.add(built+"-"+tier+addons);
					tier = findFortificationCost(built,tier);
					double add_upk = 0;
					for (int j=0;j<this.addonframes.get(i).add_on_upkeep.size();j++)
						add_upk += Integer.parseInt(this.addonframes.get(i).add_on_upkeep.get(j).getText());
					this.fortifications_upkeep.get(i).setText(Integer.toString((int) Math.round(tier*0.2+add_upk)));
					tier += this.addonframes.get(i).cost;
					this.fortifications_cost.get(i).setText(Integer.toString(tier));
					this.fortifications_cost.get(i).setToolTipText(findOfficialFortificationCost(tier));
			}else {
				this.fortifications_cost.get(i).setText("0");
				this.fortifications_upkeep.get(i).setText("0");
			}
		}
		String built = (String) this.walls.getSelectedItem();
		String addons = "";
		for (int j=0;j<this.wallframe.built_add_on.size();j++) {
			addons += "-"+this.wallframe.built_add_on.get(j);
		}
		int tier = 0;
		if (!(built.equals("")))
			if (addons.equals(""))
				this.built_walls = built;
			else
				this.built_walls = built+addons;
		else
			this.built_walls = "";
		tier = findFortificationCost(built,0);
		tier += this.wallframe.cost;
		this.walls_cost.setText(Integer.toString(tier));
		this.walls_cost.setToolTipText(findOfficialFortificationCost(tier));
		this.walls_upkeep.setText(Integer.toString((int) Math.round(tier*0.2)));
		int wall = 0;
		if (!(this.built_walls.equals("")))
			wall = 1;
		this.all_buildings = new String[this.built_buildings.size()+this.built_fortifications.size()+wall];
		for (int i=0;i<this.built_buildings.size();i++)
			this.all_buildings[i] = "B-"+this.built_buildings.get(i);
		for (int i=0;i<this.built_fortifications.size();i++)
			this.all_buildings[this.built_buildings.size()+i] = "F-"+this.built_fortifications.get(i);
		if (!(this.built_walls.equals("")))
			this.all_buildings[this.all_buildings.length-1] = "W-"+built_walls;
	}
	
	public void update() {//calls getVisuals and updates outputs
		//System.out.println("BUILDINGS! update");
		this.getVisuals();
		//System.out.println("Visuals got");
		//fixing remaining outputs
		this.upgrade_cost = 0;
		if (this.hex.pop_size<10){
			for (int i=0;i<this.built_buildings.size();i++) {
				String[] splitter = Utility.stringSplitter(this.built_buildings.get(i), "-");
				if (!(this.built_buildings.get(i).equals(""))) {
					int tier = Integer.parseInt(splitter[1]);
					if (splitter[0].equals("Supply Cache"))
						this.upgrade_cost += (int) Math.round(200*tier*this.modifiers[1]*(Hex.list_pm[this.hex.pop_size]-Hex.list_pm[this.hex.pop_size-1]));
					if (splitter[0].equals("Bureau"))
						this.upgrade_cost += (int) Math.round(100*this.modifiers[1]*(Math.pow(Hex.list_pm[this.hex.pop_size],2)-
								Math.pow(Hex.list_pm[this.hex.pop_size-1],2)));
					if (splitter[0].equals("Center of Trade"))
						this.upgrade_cost += (int) Math.round(400*this.modifiers[1]*(Hex.list_pm[this.hex.pop_size]-Hex.list_pm[this.hex.pop_size-1]));
					if (splitter[0].equals("Public Order"))
						this.upgrade_cost += (int) Math.round(100*2*tier*this.modifiers[1]*(Hex.list_pm[this.hex.pop_size]-Hex.list_pm[this.hex.pop_size-1]));
					if (splitter[0].equals("Port"))
						this.upgrade_cost += (int) Math.round(500*this.modifiers[1]*(Math.pow(Hex.list_pm[this.hex.pop_size],2)-
								Math.pow(Hex.list_pm[this.hex.pop_size-1],2)));
				}
			}
		}//upgrade costs for buildings
		if (!(this.built_walls.equals(""))){
			String[] splitter = Utility.stringSplitter(this.built_walls, "-");
			int caphelper = Hex.list_unit_cap[this.hex.pop_size]-this.hex.unit_cap;
			if (splitter[0].equals(walllist[1]))
				caphelper = (int) Math.round(caphelper*wall_costs[0]*this.modifiers[3]);
			else if (splitter[0].equals(walllist[2]))
				caphelper = (int) Math.round(caphelper*wall_costs[1]*this.modifiers[3]);
			else if (splitter[0].equals(walllist[3]))
				caphelper = (int) Math.round(caphelper*wall_costs[2]*this.modifiers[3]);
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
							addoncost += (int) Math.round(WallAddOns.wall_add_on_costs[i]*this.modifiers[3]*wall_int[j-1]);
					}
				}
			}
			this.upgrade_cost += caphelper;
		}//all upgrade costs done
		this.upkeep_cost = 0;
		for (int i=0;i<this.buildings_upkeep.size();i++)
			this.upkeep_cost += Integer.parseInt(this.buildings_upkeep.get(i).getText());
		for (int i=0;i<this.fortifications_upkeep.size();i++)
			this.upkeep_cost += Integer.parseInt(this.fortifications_upkeep.get(i).getText());
		this.upkeep_cost += Integer.parseInt(walls_upkeep.getText());
		//all upkeep costs done
	}
	
	public void loadBuildings() {//should be called whenever this window is opened (loads buildings from hex and sets it into visual and code layer)
		//System.out.println("BUILDINGS! loadBuildings");
		String[] splitter;
		int b = 0;
		int f = 0;
		this.built_buildings.clear();
		this.built_fortifications.clear();
		this.built_walls = "";
		for (int i=0;i<this.all_buildings.length;i++) {
			String input = this.all_buildings[i];
			splitter = (Utility.stringSplitter(input,"-"));
			if (splitter[0].equals("B")) {
				this.buildings.get(b).setSelectedItem(splitter[1]);
				this.building_tiers.get(b).setText(splitter[2]);
				this.built_buildings.add(splitter[1]+"-"+splitter[2]);
				b++;
			}
			if (splitter[0].equals("F")) {
				this.fortifications.get(f).setSelectedItem(splitter[1]);
				this.fortifications_capacity.get(f).setText(splitter[2]);
				int costy = findFortificationCost(splitter[1],Integer.parseInt(splitter[2]));
				if (splitter.length>3) {
					String[] splittar = Utility.stringSplitter(splitter[3],",");
					this.addonframes.get(f).setAddOn(splittar,costy,this.modifiers[3]);
					this.built_fortifications.add(splitter[1]+"-"+splitter[2]+"-"+splitter[3]);
				} else
					this.built_fortifications.add(splitter[1]+"-"+splitter[2]);
				f++;
			}
			if (splitter[0].equals("W")) {
				this.walls.setSelectedItem(splitter[1]);
				String[] wall_string = new String[splitter.length-2];
				int[] wall_int = new int[splitter.length-2];
				this.built_walls = splitter[1];
				for (int j=2;j<splitter.length;j++) {
					String[] wall_splitter = Utility.stringSplitter(splitter[j],",");
					wall_string[j-2] = wall_splitter[0];
					wall_int[j-2] = Integer.parseInt(wall_splitter[1]);
					this.built_walls += "-"+wall_splitter[0]+","+wall_splitter[1];
				}
				this.wallframe.setAddOn(wall_string,wall_int);
			}
		}
		this.update();
	}

	public int findFortificationCost(String s, int tier) {//find the cost of a fortification/wall
		//System.out.println("BUILDINGS! findFortificationCost");
		int build_cost = 0;
		for (int i=1;i<active_forts.length;i++){
			if (s.equals(active_forts[i])) {
				if (s.equals("Aerial Defenses"))
					build_cost = (int) Math.round(fortifications_costs[i-1]*this.modifiers[3]);
				else
					build_cost = (int) Math.round(tier*fortifications_costs[i-1]*this.modifiers[3]);
			}
		}
		for (int i=1;i<active_walls.length;i++){
			if (s.equals(active_walls[i]))
				build_cost = (int) Math.round(wall_costs[i-1]*this.modifiers[3]*this.hex.unit_cap);//that is done
		}
		return build_cost;
	}
	
	public int findBuildingCost(String s, int tier) {//find the cost of a building at position pos in the list
		//System.out.println("BUILDINGS! findBuildingCost");
		int build_cost = 0;
		if (s.equals(buildinglist[1])){//RGO
			if (tier>2||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum logging rate is 2 and the minimum is 1.\nMore or less is not allowed.","RGO Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round((500+500*tier)*this.modifiers[0]*this.modifiers[1]);
		}else if (s.equals(buildinglist[2])){//Road
			build_cost = (int) Math.round(((12-this.hex.habitability)*75)*this.modifiers[0]*this.modifiers[1]);
		}else if (s.equals(buildinglist[3])){//Highway
			build_cost = (int) Math.round(((12-this.hex.habitability)*150)*this.modifiers[0]*this.modifiers[1]);
		}else if (s.equals(buildinglist[4])){//Armoury
			build_cost = (int) Math.round(250*this.modifiers[1]+tier*this.modifiers[2]);
		}else if (s.equals(buildinglist[5])){//Signal Towers
			build_cost = (int) Math.round(50*this.modifiers[1]);
		}else if (s.equals(buildinglist[6])){//Hospital
			build_cost = (int) Math.round(500*tier*this.modifiers[1]);
		}else if (s.equals(buildinglist[7])){//Supply Cache
			if (tier>13)
				JOptionPane.showMessageDialog(null, "The maximum supply rate is 13.\nMore is not allowed.","Supply Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round(200*tier*Hex.list_pm[this.hex.pop_size-1]*this.modifiers[1]);
		}else if (s.equals(buildinglist[8])){//Bureau
			build_cost = (int) Math.round(100*Hex.list_pm[this.hex.pop_size-1]*Hex.list_pm[this.hex.pop_size-1]*this.modifiers[1]);
		}else if (s.equals(buildinglist[12])){//Center of Trade
			build_cost = (int) Math.round(400*Hex.list_pm[this.hex.pop_size-1]*this.modifiers[1]);
		}else if (s.equals(buildinglist[9])){//Palace
			if (tier>3||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum legitimacy enforcement is 3 and the minimum is 1."
						+ "\nMore or less is not allowed.","Palace Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round((2500+2500*tier)*this.modifiers[1]*this.modifiers[4]);
		}else if (s.equals(buildinglist[10])){//Public Order
			if (tier>2||tier<1)
				JOptionPane.showMessageDialog(null,"The maximum unrest enforcement is 2 and the minimum is 1."
						+ "\nMore or less is not allowed.","Public Order Error",JOptionPane.INFORMATION_MESSAGE);
			build_cost = (int) Math.round(100*2*tier*Hex.list_pm[this.hex.pop_size-1]*this.modifiers[1]);
		}else if (s.equals(buildinglist[11])){//Port
			build_cost = (int) Math.round(500*Hex.list_pm[this.hex.pop_size-1]*Hex.list_pm[this.hex.pop_size-1]*this.modifiers[1]);
		}
		//findOfficialBuildingCost(build_cost);
		return build_cost;
	}

	public String findOfficialBuildingCost(int building_cost) {//find an official of the lord building buildings and see what the building cost would be for them
		String output = "<html>";
		for (Official o:NationHandler.listofofficials) {
			if (o.lord.equals(this.hex.owner)) {
				if (o.job.equals("Build Building")) {
					output += o.name+" is currently building buildings for "+this.hex.owner+" and is reducing the cost of buildings by "+o.effect+"%. This building would therefore"
							+ " cost "+Integer.toString((int) Math.round(building_cost*(100-o.effect)/100))+".<br>";
				}
			}
		}
		output += "</html>";
		return output;
	}
	public String findOfficialFortificationCost(int fortification_cost) {
		String output = "<html>";
		for (Official o:NationHandler.listofofficials) {
			if (o.lord.equals(this.hex.owner)) {
				if (o.job.equals("Build Fortification")) {
					output += o.name+" is currently building fortifications for "+this.hex.owner+" and is reducing the cost of fortifications by "+o.effect+"%. This building would"
							+ " therefore cost "+Integer.toString((int) Math.round(fortification_cost*(100-o.effect)/100))+".<br>";
				}
			}
		}
		output += "</html>";
		return output;
	}
}