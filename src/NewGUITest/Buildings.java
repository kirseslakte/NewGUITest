package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Buildings{
	
	public static String[] buildinglist = {"","RGO","Road","Highway","Armoury","Signal Towers","Hospital","Supply Cache","Bureau",
			"Center of Trade","Palace","Public Order","Port"};
	public static String[] fortificationlist = {"","Hill Fort","Pallisade","Castle","Fortress","Citadel","Aerial Defenses"};
	public static String[] addonlist = {"","Moat","Motte","Glacis","Curtain Wall","Keep"};
	public static String[] walllist = {"","Primitive","Basic","Advanced"};
	public static String[] walladdonlist = {"","Bastion","Standard Wood Gate","Heavy Wood Gate","Reinforced Wood Gate","Standard Iron Gate",
			"Heavy Iron Gate","Reinforced Iron Gate"};
	public static int[] building_costs = {1000,1500,0,0,250,50,500,200,100,400,5000,7500,10000,200,400,500};//rgo1,2,rd,hw,ar,st,h,sc,b,cot,p1,2,3,po1,2,port
	public static int[] fortifications_costs = {100,125,200,400,800,50};//hill,pall,cast,fort,cita,aeri def
	public static int[] add_on_costs = {10,10,10,20,20};//(%) moat,motte,glacis,curtain,keep
	public static int[] wall_costs = {100,125,175};//prim,bas,adv
	public static int[] wall_add_on_costs = {200,100,200,300,400,500,600};//bas,std w,h w,rf w,std i,h i,rf i
	
	public List<String> built_buildings = new ArrayList<String>();	//BUILDINGS
	public List<JComboBox> buildings = new ArrayList<JComboBox>();		//input buildings
	public List<JTextField> building_tiers = new ArrayList<JTextField>();
	public List<JLabel> buildings_cost = new ArrayList<JLabel>();
	public List<JLabel> buildings_upkeep = new ArrayList<JLabel>();
	public List<String> built_fortifications = new ArrayList<String>();//FORTIFICATIONS
	public List<JComboBox> fortifications = new ArrayList<JComboBox>();	//input fortifications
	public List<JTextField> fortifications_capacity = new ArrayList<JTextField>();
	public List<JLabel> fortifications_cost = new ArrayList<JLabel>();
	public List<JLabel> fortifications_upkeep = new ArrayList<JLabel>();
	public List<String[]> built_add_on = new ArrayList<String[]>();//ADD-ONS
	public List<JComboBox[]> add_on = new ArrayList<JComboBox[]>();			//input add-ons
	public List<JLabel[]> add_on_cost = new ArrayList<JLabel[]>();
	public String built_walls = "";//WALLS
	public JComboBox walls = new JComboBox();	//input walls
	public JLabel walls_cost = new JLabel("");
	public JLabel walls_upkeep = new JLabel("");
	public List<String> built_wall_add_on = new ArrayList<String>();//WALL ADD-ONS
	public List<JComboBox> wall_add_on = new ArrayList<JComboBox>();	//input wall add-ons
	public List<JTextField> wall_add_on_amount = new ArrayList<JTextField>();
	public List<JLabel> wall_add_on_cost = new ArrayList<JLabel>();
	public List<JLabel> wall_add_on_upkeep = new ArrayList<JLabel>();
	
	static NationHandler getter = new NationHandler();
	public List<Button> add_ons_btn = new ArrayList<Button>();
	
	GridBagConstraints c = new GridBagConstraints();
	public Frame build = new Frame();
	public JPanel mainbuild;
	public Frame fortification;//fortifications plug-plug-in
	public Frame addon = new Frame();
	public Frame walladdon = new Frame();
	
	public Buildings() {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.ipady = 20;
	}
	
	public String[] doneBuilding() {
		String[] built = new String[2];
		return built;
	}
	
	public int findCost(String building) {
		int cost = 0;
		for (int i=0;i<buildinglist.length;i++) {
			if (buildinglist[i].equals(building)) {
				if (i==0)
					cost = 0;
				else
					cost = building_costs[i-1];
			}
		}
		for (int i=0;i<fortificationlist.length;i++) {
			if (fortificationlist[i].equals(building)) {
				if (i==0)
					cost = 0;
				else
					cost = fortifications_costs[i-1];
			}
		}
		for (int i=0;i<walllist.length;i++) {
			if (walllist[i].equals(building)) {
				if (i==0)
					cost = 0;
				else
					cost = wall_costs[i-1];
			}
		}
		for (int i=0;i<addonlist.length;i++) {
			if (addonlist[i].equals(building)) {
				if (i==0)
					cost = 0;
				else
					cost = add_on_costs[i-1];
			}
		}
		for (int i=0;i<walladdonlist.length;i++) {
			if (walladdonlist[i].equals(building)) {
				if (i==0)
					cost = 0;
				else
					cost = wall_add_on_costs[i-1];
			}
		}
		return cost;
	}
	
	public void buildingsPopup(String hex_name) {//the building pop-up
		build = new Frame();
		mainbuild = new JPanel(new GridBagLayout());
		build.setSize(600,500);//setting where the frame is
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - build.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - build.getHeight()) / 2);
	    build.setLocation(x, y);
		build.setTitle(hex_name+"'s Buildings and Fortifications");
	    build.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				getBuilding();
				getter.request = true;
				getter.hex_request = true;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					System.out.println(e);
				}
				build.dispose();//destroys frame on exit
			}
		});
	    //add building things
	    c.gridy = 0;//first row
	    c.gridwidth = 1;
	    c.gridx = 4;
	    Button update_building_btn = new Button("Update");
	    update_building_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
				getBuilding();
	    	}
	    });
	    mainbuild.add(update_building_btn,c);
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Buildings"),c);
	    c.gridx = 1;
	    mainbuild.add(new JLabel("Tier/Unit eq. Cost/Capacity"),c);
	    c.gridx = 2;
	    mainbuild.add(new JLabel("Cost"),c);
	    c.gridx = 3;
	    mainbuild.add(new JLabel("Upkeep"),c);
	    for (int j=0;j<11;j++) {
	    	c.gridy = 2+j;
	    	c.gridx = 0;
	    	buildings.add(new JComboBox<>(buildinglist));
	    	mainbuild.add(buildings.get(j),c);
	    	c.gridx = 1;
	    	building_tiers.add(new JTextField("0"));
	    	mainbuild.add(building_tiers.get(j),c);
	    	c.gridx = 2;
	    	buildings_cost.add(new JLabel(""));
	    	mainbuild.add(buildings_cost.get(j),c);
	    	c.gridx = 3;
	    	buildings_upkeep.add(new JLabel(""));
	    	mainbuild.add(buildings_upkeep.get(j),c);
	    }//all building rows added
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
	    for (int j=0;j<5;j++) {
	    	c.gridy = 14+j;
	    	c.gridx = 0;
	    	fortifications.add(new JComboBox<>(fortificationlist));
	    	mainbuild.add(fortifications.get(j),c);
	    	c.gridx = 1;
	    	fortifications_capacity.add(new JTextField("0"));
	    	mainbuild.add(fortifications_capacity.get(j),c);
	    	c.gridx = 2;
	    	add_ons_btn.add(new Button("Add-on"));
	    	int k = j;
	    	add_ons_btn.get(j).addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			if (!addon.isVisible())
	    				addOnPopup(k);
	    		}
	    	});
	    	mainbuild.add(add_ons_btn.get(j),c);
	    	c.gridx = 3;
	    	fortifications_cost.add(new JLabel(""));
	    	mainbuild.add(fortifications_cost.get(j),c);
	    	c.gridx = 4;
	    	fortifications_upkeep.add(new JLabel(""));
	    	mainbuild.add(fortifications_upkeep.get(j),c);
		    JComboBox[] temp = new JComboBox[5];	//add add-on arrays for each fortification
		    add_on.add(temp);
		    JLabel[] tem = new JLabel[5];
		    add_on_cost.add(tem);
		    for (int m=0;m<5;m++){//initializing the add-on stuff
		    	add_on.get(j)[m] = new JComboBox<>(addonlist);
		    	add_on_cost.get(j)[m]= new JLabel("0");
		    }
	    }//fortification rows added
	    c.gridy = 19;//walls
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Walls"),c);/*
	    c.gridx = 1;
	    mainbuild.add(new JLabel("Capacity"),c);*/
	    c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
    	c.gridy = 20;
    	c.gridx = 0;
    	walls = new JComboBox<>(walllist);
    	mainbuild.add(walls,c);/*
    	c.gridx = 1;
    	fortifications_capacity.add(new JTextField("0"));
    	mainbuild.add(fortifications_capacity.get(5),c);*/
    	c.gridx = 2;
    	add_ons_btn.add(new Button("Add-on"));
    	add_ons_btn.get(5).addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (!walladdon.isVisible())
    				wallAddOnPopup();
    		}
    	});
    	mainbuild.add(add_ons_btn.get(5),c);
    	c.gridx = 3;
    	fortifications_cost.add(new JLabel(""));
    	mainbuild.add(fortifications_cost.get(5),c);
    	c.gridx = 4;
    	fortifications_upkeep.add(new JLabel(""));
    	mainbuild.add(fortifications_upkeep.get(5),c);//walls added
	    
	    build.add(new JScrollPane(mainbuild));
		build.setVisible(true);
	}
	public void addOnPopup(int i) {//there is a problem where there only exists one frame
		addon = new Frame();		//so the next fortification to add add-ons to gives the same frame as the first.
		JPanel addonpanel = new JPanel(new GridBagLayout());
		addon.setSize(600,500);//setting where the frame is
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - addon.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - addon.getHeight()) / 2);
	    addon.setLocation(x, y);
	    if (fortifications.get(i).getSelectedItem().equals("")){
	    	JOptionPane.showMessageDialog(null, "There is no fortification set!","Fortification Error",JOptionPane.INFORMATION_MESSAGE);
	    }else if  (fortifications.get(i).getSelectedItem().equals(fortificationlist[6])){
	    	JOptionPane.showMessageDialog(null, fortificationlist[6]+" has no available add-ons!","Fortification Error",JOptionPane.INFORMATION_MESSAGE);
		}else{
	    	addon.setTitle("Add-ons for "+fortifications.get(i).getSelectedItem());
		    addon.addWindowListener(new WindowAdapter() {//close frame on closing window
				public void windowClosing(WindowEvent windowEvent){
					getBuilding();
					getter.request = true;
					getter.hex_request = true;
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						System.out.println(e);
					}
					addon.dispose();//destroys frame on exit
				}
			});
		    c.gridwidth = 1;//adding the first row
		    c.gridy = 0;
		    c.gridx = 0;
		    addonpanel.add(new JLabel("Add-on"),c);
		    c.gridx = 1;
		    addonpanel.add(new JLabel("Cost"),c);
		    for (int j=0;j<5;j++) {
		    	c.gridy = 1+j;
		    	c.gridx = 0;
		    	int k = j;
		    	add_on.get(i)[j].addActionListener(new ActionListener() {
		    		public void actionPerformed (ActionEvent e){
		    			add_on_cost.get(i)[k].setText(Double.toString(findCost((String) fortifications.get(i).getSelectedItem())*
		    					findCost((String) ((JComboBox) e.getSource()).getSelectedItem())/100));
		    		}//instantly see the cost of the add-on
		    	});
		    	addonpanel.add(add_on.get(i)[j],c);
		    	c.gridx = 1;
		    	addonpanel.add(add_on_cost.get(i)[j],c);
		    }
		    addon.add(new JScrollPane(addonpanel));
		    addon.setVisible(true);
		}
	}
	public void wallAddOnPopup() {
		walladdon = new Frame();
		JPanel walladdonpanel = new JPanel(new GridBagLayout());
		walladdon.setSize(600,500);//setting where the frame is
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - walladdon.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - walladdon.getHeight()) / 2);
	    walladdon.setLocation(x, y);
	    walladdon.setTitle("Wall add-ons");
	    walladdon.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				getBuilding();
				getter.request = true;
				getter.hex_request = true;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					System.out.println(e);
				}
				walladdon.dispose();//destroys frame on exit
			}
		});
	    c.gridy = 0;//first row!
	    c.gridx = 0;
	    walladdonpanel.add(new JLabel("Add-On"),c);
	    c.gridx = 1;
	    walladdonpanel.add(new JLabel("Amount"),c);
	    c.gridx = 2;
	    walladdonpanel.add(new JLabel("Cost"),c);
	    c.gridx = 3;
	    walladdonpanel.add(new JLabel("Upkeep"),c);
	    for (int i=0;i<5;i++) {
	    	c.gridy = 1+i;
	    	c.gridx = 0;
	    	int k = i;
	    	wall_add_on.add(new JComboBox<>(walladdonlist));
	    	wall_add_on_amount.add(new JTextField("0"));
	    	wall_add_on_cost.add(new JLabel(""));
	    	wall_add_on_upkeep.add(new JLabel(""));
	    	wall_add_on.get(i).addActionListener(new ActionListener() {
	    		public void actionPerformed (ActionEvent e){
	    			wall_add_on_cost.get(k).setText(Double.toString(findCost((String) walls.getSelectedItem())*
	    					findCost((String) ((JComboBox) e.getSource()).getSelectedItem())/100));
	    			wall_add_on_upkeep.get(k).setText(Double.toString(findCost((String) walls.getSelectedItem())*
	    					findCost((String) ((JComboBox) e.getSource()).getSelectedItem())/100*0.2));
	    		}//instantly see the cost of the add-on
	    	});
	    	walladdonpanel.add(wall_add_on.get(i),c);
	    	c.gridx = 1;
	    	walladdonpanel.add(wall_add_on_amount.get(i),c);
	    	c.gridx = 2;
	    	walladdonpanel.add(wall_add_on_cost.get(i),c);	    	
	    	c.gridx = 3;
	    	walladdonpanel.add(wall_add_on_upkeep.get(i),c);
	    }
	    walladdon.add(new JScrollPane(walladdonpanel));
	    walladdon.setVisible(true);
	}
	public void updateBuilding() {//should be the same as setBuildings
		
	}
	public void getBuilding() {
		built_buildings.clear();
		built_fortifications.clear();
		built_add_on.clear();
		built_wall_add_on.clear();
		for (int i=0;i<buildings.size();i++) {
			if (!(buildings.get(i).getSelectedItem().equals("")))
				built_buildings.add((String) buildings.get(i).getSelectedItem()+" "+Integer.parseInt(building_tiers.get(i).getText()));
		}
		String fortificate;
		for (int i=0;i<fortifications.size();i++) {
			if (!(fortifications.get(i).getSelectedItem().equals(""))){
				fortificate = (String) fortifications.get(i).getSelectedItem()+" "+Integer.parseInt(fortifications_capacity.get(i).getText());
				for (int j=0;j<add_on.get(i).length;j++) {
					built_add_on.add(new String[add_on.get(i).length]);
					if (!add_on.get(i)[j].getSelectedItem().equals(""))
						built_add_on.get(i)[j] = (String) add_on.get(i)[j].getSelectedItem();
						fortificate = fortificate+","+(String) add_on.get(i)[j].getSelectedItem();
				}
				built_fortifications.add(fortificate);
			}
		}
		if (!(walls.getSelectedItem().equals(""))) {
			built_walls = (String) walls.getSelectedItem();
			for (int i=0;i<wall_add_on.size();i++) {
				if (!(wall_add_on.get(i).getSelectedItem().equals(""))) {
					built_wall_add_on.add((String) wall_add_on.get(i).getSelectedItem()+" "+wall_add_on_amount.get(i).getText());
					built_walls = built_walls+","+(String) wall_add_on.get(i).getSelectedItem()+" "+wall_add_on_amount.get(i).getText();
				}
			}
		}
	}
}