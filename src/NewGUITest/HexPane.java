package NewGUITest;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class HexPane {
	
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
	public static JPanel hex_panel = new JPanel(new GridLayout(0,17));
	public static List<String> lordnames = new ArrayList<String>();
	public static List<String[]> built_buildings = new ArrayList<String[]>();
	static List<Button> rmv_btns = new ArrayList<Button>();
	static Button add_hex_btn = new Button("Add hex");
	
	public HexPane() {
		
	}
	
	public JPanel hexPane() {
		System.out.println("HEXPANE! hexPane");
		JPanel basepanel = new JPanel(new BorderLayout());
		JPanel updatepanel = new JPanel(new GridLayout(0,5));
		updatepanel.add(new JLabel(""));
		updatepanel.add(new JLabel(""));
		Button update = new Button("Update & Save hexes");
		update.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				System.out.println("UPDATE BUTTON");
				getHexVisuals();
				setHexVisuals();
				NationHandler.saveNation();
			}
		});
		updatepanel.add(update);
		updatepanel.add(new JLabel(""));
		updatepanel.add(new JLabel(""));
		basepanel.add(updatepanel,BorderLayout.PAGE_START);
		//setting up row 1	(the row of labels)
		hex_panel.add(new JLabel("Hex Name"));
		hex_panel.add(new JLabel("Owner"));
		hex_panel.add(new JLabel("Hab"));
		hex_panel.add(new JLabel("Culture"));
		hex_panel.add(new JLabel("Religion"));
		hex_panel.add(new JLabel("PS"));
		hex_panel.add(new JLabel("Unrest"));
		hex_panel.add(new JLabel("Resource"));
		hex_panel.add(new JLabel("Resource Transfer"));
		hex_panel.add(new JLabel("Upgrade Cost"));
		hex_panel.add(new JLabel("BP"));
		hex_panel.add(new JLabel("Upkeep"));
		hex_panel.add(new JLabel("Gvnm Upkeep"));
		hex_panel.add(new JLabel("PV"));
		hex_panel.add(new JLabel("Unit Cap"));
		hex_panel.add(new JLabel("Buildings"));
		hex_panel.add(new JLabel(""));
		for (int i=0;i<NationHandler.listoflords.size();i++) {
			lordnames.add(NationHandler.listoflords.get(i).name);
		}
		add_hex_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NationHandler.listofhexes.add(new Hex());
				getHexVisuals();
				addHex();
				setHexVisuals();
			}
		});
		basepanel.add(hex_panel,BorderLayout.CENTER);
		BuildingWindow.initialize();
		setHexVisuals();
		updateCombos();
		if (NationHandler.listofhexes.size()==0){
			NationHandler.listofhexes.add(new Hex());
			addHex();
		}
		setHexVisuals();
		
		return basepanel;
	}
	
	public static void updateCombos() {
		System.out.println("HEXPANE! updateCombos");
		lordnames.clear();//update the lordnames list to see if it has expanded
		for (int i=0;i<NationHandler.listoflords.size();i++) {
			lordnames.add(NationHandler.listoflords.get(i).name);
		}
		for (int i=0;i<owner_list.size();i++) {//update ownerlist
			String own = (String) owner_list.get(i).getSelectedItem();
			owner_list.get(i).removeAllItems();
			for (String s:lordnames) {
				owner_list.get(i).addItem(s);
			}
			owner_list.get(i).setSelectedItem(own);
			if (owner_list.get(i).getSelectedItem()==null)
				owner_list.get(i).setSelectedIndex(0);
		}
		System.out.println("number of visual hexes: "+hex_list.size()+" and number of code hexes: "+NationHandler.listofhexes.size());
	}
	
	public static void setHexVisuals() {
		System.out.println("HEXPANE! setHexVisuals "+NationHandler.listofhexes.size());
		for (int i=0;i<NationHandler.listofhexes.size();i++) {
			try {
				//System.out.println("index "+i+" in bounds");
				try {
					NationHandler.listofhexes.get(i).updateHex();
				} catch (Exception e) {System.out.println(e);}
				hex_list.get(i).setText(NationHandler.listofhexes.get(i).name);
				owner_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).owner);
				int hab = (int) Math.floor(NationHandler.listofhexes.get(i).habitability);
				if (NationHandler.listofhexes.get(i).habitability==hab)
					hab_list.get(i).setText(Integer.toString(hab));
				else
					hab_list.get(i).setText(Double.toString(NationHandler.listofhexes.get(i).habitability));
				culture_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).alignment);
				religion_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).religion);
				pop_size_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).pop_size));
				unrest_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).unrest));
				resource_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).resource);
				resource_check_list.get(i).setSelected(NationHandler.listofhexes.get(i).resource_check);
				upgrade_cost_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).upgrade_cost));
				bp_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).base_production));
				upkeep_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).upkeep));
				gov_upkeep_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).govnm_upkeep));
				pv_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).population_value));
				unit_cap_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).unit_cap));
			} catch (IndexOutOfBoundsException e) {
				//System.out.println("index "+i+" out of bounds: "+e);
				addHex();i--;
			}
		}
		hex_panel.revalidate();
	}
	public static void addHex() {//simply adds another row along with the structure
		System.out.println("HEXPANE! addHex");
		hex_panel.remove(add_hex_btn);
		int i = hex_list.size();
		System.out.println("Adding hex index "+i);
		hex_list.add(new JTextField(""));
		hex_list.get(i).setOpaque(true);
		hex_list.get(i).setBackground(Color.white);
		hex_panel.add(hex_list.get(i));//added hex name
		owner_list.add(new JComboBox<>());
		hex_panel.add(owner_list.get(i));//added owner of hex
		hab_list.add(new JTextField("1"));
		hex_panel.add(hab_list.get(i));//added habitability
		culture_list.add(new JComboBox<>(Hex.alignments));
		hex_panel.add(culture_list.get(i));//added culture
		religion_list.add(new JComboBox<>(Hex.alignments));
		hex_panel.add(religion_list.get(i));//added religion
		pop_size_list.add(new JTextField("1"));
		hex_panel.add(pop_size_list.get(i));//added pop size
		unrest_list.add(new JTextField("0"));
		hex_panel.add(unrest_list.get(i));//added unrest
		resource_list.add(new JComboBox<>(Hex.resources));
		hex_panel.add(resource_list.get(i));//added resource
		resource_check_list.add(new JCheckBox(""));
		hex_panel.add(resource_check_list.get(i));
		upgrade_cost_list.add(new JLabel("0"));
		hex_panel.add(upgrade_cost_list.get(i));//added upgrade costs
		bp_list.add(new JLabel("0"));
		hex_panel.add(bp_list.get(i));//added bp
		upkeep_list.add(new JLabel("0"));
		hex_panel.add(upkeep_list.get(i));//added upkeep
		gov_upkeep_list.add(new JLabel("0"));
		hex_panel.add(gov_upkeep_list.get(i));//added upkeep
		pv_list.add(new JLabel("0"));
		hex_panel.add(pv_list.get(i));//added pop value
		unit_cap_list.add(new JLabel("0"));
		hex_panel.add(unit_cap_list.get(i));//added unit cap
		building_btn_list.add(new Button("Buildings"));
		//int k = NationHandler.listofhexes.get(i).hex_id;
		building_btn_list.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				System.out.println("BUILDING PRESS");
				BuildingWindow.stop();
				BuildingWindow.start(i);
			}
		});
		hex_panel.add(building_btn_list.get(i));//added building button
		rmv_btns.add(new Button("X"));
		rmv_btns.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				System.out.println("!!!!!!!!!Removing hex index "+i);
				removeHex(i);
			}
		});
		hex_panel.add(rmv_btns.get(i));
		hex_panel.add(add_hex_btn);
		updateCombos();
	}
	
	public static void removeHex(int i) {
		System.out.println("HEXPANE! removeHex");
		//getHexVisuals();
		NationHandler.listofhexes.remove(i);
		System.out.println("removed hex index: "+i+" from code");
		hex_panel.remove(add_hex_btn);
		
		for (int k=hex_list.size()-1;k>=i;k--) {
			hex_panel.remove(hex_list.get(k));
			hex_panel.remove(owner_list.get(k));
			hex_panel.remove(hab_list.get(k));
			hex_panel.remove(culture_list.get(k));
			hex_panel.remove(religion_list.get(k));
			hex_panel.remove(pop_size_list.get(k));
			hex_panel.remove(unrest_list.get(k));
			hex_panel.remove(resource_list.get(k));
			hex_panel.remove(resource_check_list.get(k));
			hex_panel.remove(upgrade_cost_list.get(k));
			hex_panel.remove(bp_list.get(k));
			hex_panel.remove(upkeep_list.get(k));
			hex_panel.remove(gov_upkeep_list.get(k));
			hex_panel.remove(pv_list.get(k));
			hex_panel.remove(unit_cap_list.get(k));
			hex_panel.remove(building_btn_list.get(k));
			hex_panel.remove(rmv_btns.get(k));
			hex_list.remove(k);
			owner_list.remove(k);
			hab_list.remove(k);
			culture_list.remove(k);
			religion_list.remove(k);
			pop_size_list.remove(k);
			unrest_list.remove(k);
			resource_list.remove(k);
			resource_check_list.remove(k);
			upgrade_cost_list.remove(k);
			bp_list.remove(k);
			upkeep_list.remove(k);
			gov_upkeep_list.remove(k);
			pv_list.remove(k);
			unit_cap_list.remove(k);
			building_btn_list.remove(k);
			rmv_btns.remove(k);
			System.out.println("removed hex index: "+k+" from visual");
		}
		
		for (int k=i;k<NationHandler.listofhexes.size()-1;k++) {
			addHex();
		}
		hex_panel.add(add_hex_btn);
		setHexVisuals();
	}	
	
	public static void getHexVisuals() {
		System.out.println("HEXPANE! getHexVisuals");
		for (int i=0;i<hex_list.size();i++) {
			try {
				NationHandler.listofhexes.get(i).name = (String) hex_list.get(i).getText();
				NationHandler.listofhexes.get(i).owner = (String) owner_list.get(i).getSelectedItem();
				NationHandler.listofhexes.get(i).habitability = Double.parseDouble(hab_list.get(i).getText());
				NationHandler.listofhexes.get(i).alignment = (String) culture_list.get(i).getSelectedItem();
				NationHandler.listofhexes.get(i).religion = (String) religion_list.get(i).getSelectedItem();
				NationHandler.listofhexes.get(i).pop_size = Integer.parseInt(pop_size_list.get(i).getText());
				NationHandler.listofhexes.get(i).unrest = Integer.parseInt(unrest_list.get(i).getText());
				NationHandler.listofhexes.get(i).resource = (String) resource_list.get(i).getSelectedItem();
				NationHandler.listofhexes.get(i).resource_check = resource_check_list.get(i).isSelected();
				NationHandler.listofhexes.get(i).updateHex();
				hex_list.get(i).setBackground(Color.white);
			} catch (Exception e) {
				hex_list.get(i).setBackground(Color.red);}
		}
	}
}