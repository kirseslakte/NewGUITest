package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HexPane extends Hex{
	
	public List<JTextField> hex_list = new ArrayList<JTextField>();
	public List<JComboBox> owner_list = new ArrayList<JComboBox>();
	public List<JTextField> hab_list = new ArrayList<JTextField>();
	public List<JComboBox> culture_list = new ArrayList<JComboBox>();
	public List<JComboBox> religion_list = new ArrayList<JComboBox>();
	public List<JTextField> pop_size_list = new ArrayList<JTextField>();
	public List<JTextField> unrest_list = new ArrayList<JTextField>();
	public List<JComboBox> resource_list = new ArrayList<JComboBox>();
	public List<JCheckBox> resource_check_list = new ArrayList<JCheckBox>();
	public List<JLabel> upgrade_cost_list = new ArrayList<JLabel>();
	public List<JLabel> bp_list = new ArrayList<JLabel>();
	public List<JLabel> upkeep_list = new ArrayList<JLabel>();
	public List<JLabel> gov_upkeep_list = new ArrayList<JLabel>();
	public List<JLabel> pv_list = new ArrayList<JLabel>();
	public List<JLabel> unit_cap_list = new ArrayList<JLabel>();
	public List<Button> building_btn_list = new ArrayList<Button>();
	public Panel hex_panel = new Panel(new GridBagLayout());
	public int[] paddyx = {20,10,5,5,5,5,5,8,5,10,10,10,10,5,5,5};
	GridBagConstraints c = new GridBagConstraints();
	public List<String> lordnames = new ArrayList<String>();
	
	public List<String> built_buildings_list = new ArrayList<String>();//building plug-in		String building int tier/unit eq.cost/capacity
	public List<String> built_fortifications_list = new ArrayList<String>();//String fort int capacity String add-on int amount String add-on int amount...
	public List<JComboBox> buildings = new ArrayList<JComboBox>();
	public List<JTextField> building_tiers = new ArrayList<JTextField>();
	public List<JLabel> buildings_cost = new ArrayList<JLabel>();
	public List<JLabel> buildings_upkeep = new ArrayList<JLabel>();
	public List<JComboBox> fortifications = new ArrayList<JComboBox>();
	public List<JTextField> fortifications_capacity = new ArrayList<JTextField>();
	public List<Button> add_ons_btn = new ArrayList<Button>();
	public List<JLabel> fortifications_cost = new ArrayList<JLabel>();
	public List<JLabel> fortifications_upkeep = new ArrayList<JLabel>();
	
	public Frame build = new Frame();
	public JPanel mainbuild;
	public Frame fortification;//fortifications plug-plug-in
	public Frame addon = new Frame();
	public Frame walladdon = new Frame();
	
	public HexPane() {
		
	}
	
	public Panel hexPane(List<Hex> listofhexes,List<Lord> listoflords) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.ipady = 20;
		//setting up row 0 
		c.gridy = 0;
		c.gridx = 7;
		c.gridwidth = 2;
		Button update = new Button("Update hexes!");
		update.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				updateHexPane(listoflords);
			}
		});
		hex_panel.add(update,c);
		//setting up row 1	(the row of labels)
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;
		c.ipadx = paddyx[0];
		hex_panel.add(new JLabel("Hex Name"), c);
		c.gridx = 1;
		c.ipadx = paddyx[1];
		hex_panel.add(new JLabel("Owner"), c);
		c.gridx = 2;
		c.ipadx = paddyx[2];
		hex_panel.add(new JLabel("Hab"), c);
		c.gridx = 3;
		c.ipadx = paddyx[3];
		hex_panel.add(new JLabel("Culture"), c);
		c.gridx = 4;
		c.ipadx = paddyx[4];
		hex_panel.add(new JLabel("Religion"), c);
		c.gridx = 5;
		c.ipadx = paddyx[5];
		hex_panel.add(new JLabel("PS"), c);
		c.gridx = 6;
		c.ipadx = paddyx[6];
		hex_panel.add(new JLabel("Unrest"), c);
		c.gridx = 7;
		c.ipadx = paddyx[7];
		hex_panel.add(new JLabel("Resource"), c);
		c.gridx = 8;
		c.ipadx = paddyx[8];
		hex_panel.add(new JLabel("Resource Transfer"),c);
		c.gridx = 9;
		c.ipadx = paddyx[9];
		hex_panel.add(new JLabel("Upgrade Cost"), c);
		c.gridx = 10;
		c.ipadx = paddyx[10];
		hex_panel.add(new JLabel("BP"), c);
		c.gridx = 11;
		c.ipadx = paddyx[11];
		hex_panel.add(new JLabel("Upkeep"), c);
		c.gridx = 12;
		c.ipadx = paddyx[12];
		hex_panel.add(new JLabel("Gvnm Upkeep"), c);
		c.gridx = 13;
		c.ipadx = paddyx[13];
		hex_panel.add(new JLabel("PV"), c);
		c.gridx = 14;
		c.ipadx = paddyx[14];
		hex_panel.add(new JLabel("Unit Cap"), c);
		c.gridx = 15;
		c.ipadx = paddyx[15];
		hex_panel.add(new JLabel("Buildings"), c);
		for (int i=0;i<listoflords.size();i++) {
			lordnames.add(listoflords.get(i).name);
		}
		loadHexPane(listofhexes,true);
		updateHexPane(listoflords);
		
		return hex_panel;
	}
	
	public void updateHexPane(List<Lord> listoflords) {
		if (hex_list.size()==0) {//check if we need to add another hex line
			addHex();
		}else {
			if (!(hex_list.get(hex_list.size()-1).getText().equals(""))) {
				addHex();
			}
		}//done with adding new hex line
		lordnames.clear();//update the lordnames list to see if it has expanded
		for (int i=0;i<listoflords.size();i++) {
			lordnames.add(listoflords.get(i).name);
		}
		for (int i=0;i<owner_list.size();i++) {
			owner_list.get(i).removeAllItems();
			for (String s:lordnames) {
				owner_list.get(i).addItem(s);
			}
		}
		//need to update the values
		hex_panel.revalidate();//redraw the pane
	}
	
	public void loadHexPane(List<Hex> listofhexes, boolean loading) {
		for (int i=0;i<listofhexes.size();i++) {
			if (loading)//if this is a load or update
				addHex();
			hex_list.get(i).setText(listofhexes.get(i).name);
			owner_list.get(i).setSelectedItem(listofhexes.get(i).owner);
			hab_list.get(i).setText(Integer.toString(listofhexes.get(i).habitability));
			culture_list.get(i).setSelectedItem(listofhexes.get(i).alignment);
			religion_list.get(i).setSelectedItem(listofhexes.get(i).religion);
			pop_size_list.get(i).setText(Integer.toString(listofhexes.get(i).pop_size));
			unrest_list.get(i).setText(Integer.toString(listofhexes.get(i).unrest));
			resource_list.get(i).setSelectedItem(listofhexes.get(i).resource);
			upgrade_cost_list.get(i).setText(Integer.toString(listofhexes.get(i).upgrade_cost));
			bp_list.get(i).setText(Integer.toString(listofhexes.get(i).base_production));
			upkeep_list.get(i).setText(Integer.toString(listofhexes.get(i).upkeep));
			gov_upkeep_list.get(i).setText(Integer.toString(listofhexes.get(i).govnm_upkeep));
			pv_list.get(i).setText(Integer.toString(listofhexes.get(i).population_value));
			unit_cap_list.get(i).setText(Integer.toString(listofhexes.get(i).unit_cap));
		}
	}
	public void addHex() {//simply adds another row along with the structure
		int i = hex_list.size();
		c.gridy = 2+i;//starting at the 3rd row (2)
		c.gridx = 0;
		c.ipadx = paddyx[0];
		hex_list.add(new JTextField(""));
		hex_panel.add(hex_list.get(i),c);//added hex name
		c.gridx = 1;
		c.ipadx = paddyx[1];
		owner_list.add(new JComboBox<>());
		hex_panel.add(owner_list.get(i),c);//added owner of hex
		c.gridx = 2;
		c.ipadx = paddyx[2];
		hab_list.add(new JTextField(""));
		hex_panel.add(hab_list.get(i),c);//added habitability
		c.gridx = 3;
		c.ipadx = paddyx[3];
		culture_list.add(new JComboBox<>(alignments));
		hex_panel.add(culture_list.get(i),c);//added culture
		c.gridx = 4;
		c.ipadx = paddyx[4];
		religion_list.add(new JComboBox<>(alignments));
		hex_panel.add(religion_list.get(i),c);//added religion
		c.gridx = 5;
		c.ipadx = paddyx[5];
		pop_size_list.add(new JTextField(""));
		hex_panel.add(pop_size_list.get(i),c);//added pop size
		c.gridx = 6;
		c.ipadx = paddyx[6];
		unrest_list.add(new JTextField(""));
		hex_panel.add(unrest_list.get(i),c);//added unrest
		c.gridx = 7;
		c.ipadx = paddyx[7];
		resource_list.add(new JComboBox<>(resources));
		hex_panel.add(resource_list.get(i),c);//added resource
		c.gridx = 8;
		c.ipadx = paddyx[8];
		resource_check_list.add(new JCheckBox(""));
		hex_panel.add(resource_check_list.get(i),c);
		c.gridx = 9;
		c.ipadx = paddyx[9];
		upgrade_cost_list.add(new JLabel(""));
		hex_panel.add(upgrade_cost_list.get(i),c);//added upgrade costs
		c.gridx = 10;
		c.ipadx = paddyx[10];
		bp_list.add(new JLabel(""));
		hex_panel.add(bp_list.get(i),c);//added bp
		c.gridx = 11;
		c.ipadx = paddyx[11];
		upkeep_list.add(new JLabel(""));
		hex_panel.add(upkeep_list.get(i),c);//added upkeep
		c.gridx = 12;
		c.ipadx = paddyx[12];
		gov_upkeep_list.add(new JLabel(""));
		hex_panel.add(gov_upkeep_list.get(i),c);//added upkeep
		c.gridx = 13;
		c.ipadx = paddyx[13];
		pv_list.add(new JLabel(""));
		hex_panel.add(pv_list.get(i),c);//added pop value
		c.gridx = 14;
		c.ipadx = paddyx[14];
		unit_cap_list.add(new JLabel(""));
		hex_panel.add(unit_cap_list.get(i),c);//added unit cap
		c.gridx = 15;
		c.ipadx = paddyx[15];
		building_btn_list.add(new Button("Buildings"));
		building_btn_list.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (!build.isVisible())
					buildingsPopup(i);
			}
		});
		hex_panel.add(building_btn_list.get(i),c);//added building button
	}
	public void buildingsPopup(int i) {//the building pop-up
		build = new Frame();
		mainbuild = new JPanel(new GridBagLayout());
		build.setSize(600,500);//setting where the frame is
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - build.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - build.getHeight()) / 2);
	    build.setLocation(x, y);
		build.setTitle(hex_list.get(i).getText()+"'s Buildings and Fortifications");
	    build.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				build.dispose();//destroys frame on exit
			}
		});
	    //add building things
	    c.gridy = 0;//first row
	    c.gridwidth = 2;
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Buildings"),c);
	    c.gridx = 3;
	    c.gridwidth = 1;
	    Button update_building_btn = new Button("Update");
	    update_building_btn.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		updateBuilding();
	    	}
	    });
	    mainbuild.add(update_building_btn,c);
	    c.gridy = 1;//second row
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Buildining and Fortification"),c);
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
	    }//fortification rows added
	    c.gridy = 19;//walls
	    c.gridx = 0;
	    mainbuild.add(new JLabel("Walls"),c);
	    c.gridx = 1;
	    mainbuild.add(new JLabel("Capacity"),c);
	    c.gridx = 2;
	    mainbuild.add(new JLabel("Add-on"),c);
	    c.gridx = 3;
	    mainbuild.add(new JLabel("Cost"),c);
	    c.gridx = 4;
	    mainbuild.add(new JLabel("Upkeep"),c);
    	c.gridy = 20;
    	c.gridx = 0;
    	fortifications.add(new JComboBox<>(wallslist));
    	mainbuild.add(fortifications.get(5),c);
    	c.gridx = 1;
    	fortifications_capacity.add(new JTextField("0"));
    	mainbuild.add(fortifications_capacity.get(5),c);
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
	public void updateBuilding() {
		
	}
	public void addOnPopup(int i) {
		addon = new Frame();
		JPanel addonpanel = new JPanel(new GridBagLayout());
		addon.setSize(600,500);//setting where the frame is
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - addon.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - addon.getHeight()) / 2);
	    addon.setLocation(x, y);
	    addon.setTitle("Add-ons for "+fortifications.get(i).getSelectedItem());
	    addon.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				addon.dispose();//destroys frame on exit
			}
		});
	    
	    addon.add(addonpanel);
	    addon.setVisible(true);
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
				walladdon.dispose();//destroys frame on exit
			}
		});
	    
	    walladdon.add(walladdonpanel);
	    walladdon.setVisible(true);
	}
}