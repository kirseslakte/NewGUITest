package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class HexPane extends Hex{
	
	public static List<JTextField> hex_list = new ArrayList<JTextField>();
	public static List<JComboBox> owner_list = new ArrayList<JComboBox>();
	public static List<JTextField> hab_list = new ArrayList<JTextField>();
	public static List<JComboBox> culture_list = new ArrayList<JComboBox>();
	public static List<JComboBox> religion_list = new ArrayList<JComboBox>();
	public static List<JTextField> pop_size_list = new ArrayList<JTextField>();
	public static List<JTextField> unrest_list = new ArrayList<JTextField>();
	public static List<JComboBox> resource_list = new ArrayList<JComboBox>();
	public static List<JCheckBox> resource_check_list = new ArrayList<JCheckBox>();
	public static List<JLabel> upgrade_cost_list = new ArrayList<JLabel>();
	public static List<JLabel> bp_list = new ArrayList<JLabel>();
	public static List<JLabel> upkeep_list = new ArrayList<JLabel>();
	public static List<JLabel> gov_upkeep_list = new ArrayList<JLabel>();
	public static List<JLabel> pv_list = new ArrayList<JLabel>();
	public static List<JLabel> unit_cap_list = new ArrayList<JLabel>();
	public static List<Button> building_btn_list = new ArrayList<Button>();
	public static Panel hex_panel = new Panel(new GridBagLayout());
	public static int[] paddyx = {20,10,5,5,5,5,5,8,5,10,10,10,10,5,5,5};
	static GridBagConstraints c = new GridBagConstraints();
	public static List<String> lordnames = new ArrayList<String>();
	public static List<Buildings> buildings = new ArrayList<Buildings>();
	public static List<String[]> built_buildings = new ArrayList<String[]>();
	static NationHandler getter = new NationHandler();
	
	public HexPane() {
		
	}
	
	public Panel hexPane() {
		System.out.println("HEXPANE! hexPane");
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
				boolean correct = true;
				for (int i=0;i<hex_list.size();i++) {
					if (!(hex_list.get(i).equals(""))){
						if (Integer.parseInt(pop_size_list.get(i).getText())>10||Integer.parseInt(pop_size_list.get(i).getText())<1||
								pop_size_list.get(i).getText().equals(""))//check if pop size input is ok
							correct = false;
					}
				}
				if (correct) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception ge) {
						System.out.println(ge);
					}
					updateHexPane();
				} else
					JOptionPane.showMessageDialog(null, "Hex not correctly configured","Update Error",
							JOptionPane.INFORMATION_MESSAGE);
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
		for (int i=0;i<getter.listoflords.size();i++) {
			lordnames.add(getter.listoflords.get(i).name);
		}
		loadHexPane(true);
		updateHexPane();
		
		return hex_panel;
	}
	
	public void updateHexPane() {
		System.out.println("HEXPANE! updateHexPane");
		getter.request = true;
		getter.update_request = true;
		if (hex_list.size()==0) {//check if we need to add another hex line
			addHex();
		}else {
			if (!(hex_list.get(hex_list.size()-1).getText().equals(""))) {
				addHex();
			}
		}//done with adding new hex line
		lordnames.clear();//update the lordnames list to see if it has expanded
		for (int i=0;i<getter.listoflords.size();i++) {
			lordnames.add(getter.listoflords.get(i).name);
		}
		for (int i=0;i<owner_list.size();i++) {
			owner_list.get(i).removeAllItems();
			for (String s:lordnames) {
				owner_list.get(i).addItem(s);
			}
		}
		//need to update the values
		for (int i=0;i<getter.listofhexes.size();i++) {//need to fetch all the values from hex
			upgrade_cost_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).upgrade_cost));
			bp_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).base_production));
			upkeep_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).upkeep));
			gov_upkeep_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).govnm_upkeep));
			pv_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).population_value));
			unit_cap_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).unit_cap));
		}
		hex_panel.revalidate();//redraw the pane
	}
	
	public void loadHexPane(boolean loading) {
		System.out.println("HEXPANE! loadHexPane "+getter.listofhexes.size());
		for (int i=0;i<getter.listofhexes.size();i++) {
			System.out.println("hex index "+i);
			if (loading)//if this is a load or update
				addHex();
			buildings.get(i).getInputs(i);
			hex_list.get(i).setText(getter.listofhexes.get(i).name);
			owner_list.get(i).setSelectedItem(getter.listofhexes.get(i).owner);
			hab_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).habitability));
			culture_list.get(i).setSelectedItem(getter.listofhexes.get(i).alignment);
			religion_list.get(i).setSelectedItem(getter.listofhexes.get(i).religion);
			pop_size_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).pop_size));
			unrest_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).unrest));
			resource_list.get(i).setSelectedItem(getter.listofhexes.get(i).resource);
			upgrade_cost_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).upgrade_cost));
			bp_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).base_production));
			upkeep_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).upkeep));
			gov_upkeep_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).govnm_upkeep));
			pv_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).population_value));
			unit_cap_list.get(i).setText(Integer.toString(getter.listofhexes.get(i).unit_cap));
		}
	}
	public void addHex() {//simply adds another row along with the structure
		System.out.println("HEXPANE! addHex");
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
		hab_list.add(new JTextField("1"));
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
		pop_size_list.add(new JTextField("1"));
		hex_panel.add(pop_size_list.get(i),c);//added pop size
		c.gridx = 6;
		c.ipadx = paddyx[6];
		unrest_list.add(new JTextField("0"));
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
		buildings.add(new Buildings());
		buildings.get(i).initialize();
		building_btn_list.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if ((!buildings.get(i).isVisible())&&i<(hex_list.size()-1))
					buildingCaller(i);
			}
		});
		hex_panel.add(building_btn_list.get(i),c);//added building button
	}
	
	public void buildingCaller(int i) {
		System.out.println("HEXPANE! buildingCaller");
		getter.request = true;
		getter.update_request = true;
		buildings.get(i).start(i);
	}
	
	public void getBuildings(int hex_number) {
		System.out.println("HEXPANE! getBuildings");
		//for (int i=0;i<hex_list.size()-1;i++) 
			//buildings.get(i).getBuilding();
		int k = buildings.get(hex_number).all_buildings.length;
		getter.listofhexes.get(hex_number).built_buildings = new String[k];
		for (int i=0;i<k;i++){
			String string = buildings.get(hex_number).all_buildings[i];
			getter.listofhexes.get(hex_number).built_buildings[i] = string;
		}
		System.out.println("HexPane.getBuildings: Done");
	}
	
	
	public List<String[]> getHex() {
		System.out.println("HEXPANE! getHex");
		List<String[]> hexlist = new ArrayList<String[]>();
		boolean empty_buildings;
		for (int i=0;i<hex_list.size()-1;i++) {
			if (buildings.get(i).all_buildings.length==0){
				hexlist.add(new String[11]);
				empty_buildings = true;
			}else{
				hexlist.add(new String[11+buildings.get(i).all_buildings.length]);
				empty_buildings = false;
			}
			hexlist.get(i)[0] = hex_list.get(i).getText();
			hexlist.get(i)[1] = (String) owner_list.get(i).getSelectedItem();
			hexlist.get(i)[2] = hab_list.get(i).getText();
			hexlist.get(i)[3] = (String) culture_list.get(i).getSelectedItem();
			hexlist.get(i)[4] = (String) religion_list.get(i).getSelectedItem();
			hexlist.get(i)[5] = pop_size_list.get(i).getText();
			hexlist.get(i)[6] = unrest_list.get(i).getText();
			hexlist.get(i)[7] = (String) resource_list.get(i).getSelectedItem();
			hexlist.get(i)[8] = Boolean.toString(resource_check_list.get(i).isSelected());
			hexlist.get(i)[9] = Integer.toString(buildings.get(i).upkeep_cost);
			hexlist.get(i)[10] = Integer.toString(buildings.get(i).upgrade_cost);
			if (!empty_buildings){
				for (int j=0;j<buildings.get(i).all_buildings.length;j++) {
					hexlist.get(i)[11+j] = buildings.get(i).all_buildings[j];
				}
			}
		}
		return hexlist;
	}
}