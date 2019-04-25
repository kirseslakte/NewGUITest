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
	public static Panel hex_panel = new Panel(new GridBagLayout());
	public static int[] paddyx = {20,10,5,5,5,5,5,8,5,10,10,10,10,5,5,5};
	static GridBagConstraints c = new GridBagConstraints();
	public static List<String> lordnames = new ArrayList<String>();
	public static List<String[]> built_buildings = new ArrayList<String[]>();
	static List<Button> rmv_btns = new ArrayList<Button>();
	static Button add_hex_btn = new Button("Add hex");
	
	public HexPane() {
		
	}
	
	public Panel hexPane() {
		//System.out.println("HEXPANE! hexPane");
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
				if (correct)
					setHexVisuals();
				else
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
		for (int i=0;i<NationHandler.listoflords.size();i++) {
			lordnames.add(NationHandler.listoflords.get(i).name);
		}
		add_hex_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHex();
			}
		});
		Buildings.initialize();
		setHexVisuals();
		updateCombos();
		
		return hex_panel;
	}
	
	public static void updateCombos() {
		//System.out.println("HEXPANE! updateHexPane");
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
		}
	}
	
	public static void setHexVisuals() {
		//System.out.println("HEXPANE! setHexVisuals "+NationHandler.listofhexes.size());
		for (int i=0;i<NationHandler.listofhexes.size();i++) {
			try {
				NationHandler.listofhexes.get(i).updateHex();
				hex_list.get(i).setText(NationHandler.listofhexes.get(i).name);
				owner_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).owner);
				hab_list.get(i).setText(Double.toString(NationHandler.listofhexes.get(i).habitability));
				culture_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).alignment);
				religion_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).religion);
				pop_size_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).pop_size));
				unrest_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).unrest));
				resource_list.get(i).setSelectedItem(NationHandler.listofhexes.get(i).resource);
				upgrade_cost_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).upgrade_cost));
				bp_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).base_production));
				upkeep_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).upkeep));
				gov_upkeep_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).govnm_upkeep));
				pv_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).population_value));
				unit_cap_list.get(i).setText(Integer.toString(NationHandler.listofhexes.get(i).unit_cap));
			} catch (IndexOutOfBoundsException e) {
				addHex();i--;
			}
		}
		hex_panel.revalidate();
		addHex();
	}
	public static void addHex() {//simply adds another row along with the structure
		//System.out.println("HEXPANE! addHex");
		hex_panel.remove(add_hex_btn);
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
		culture_list.add(new JComboBox<>(Hex.alignments));
		hex_panel.add(culture_list.get(i),c);//added culture
		c.gridx = 4;
		c.ipadx = paddyx[4];
		religion_list.add(new JComboBox<>(Hex.alignments));
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
		resource_list.add(new JComboBox<>(Hex.resources));
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
		//int k = NationHandler.listofhexes.get(i).hex_id;
		building_btn_list.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				Buildings.stop();
				Buildings.start(i);
			}
		});
		hex_panel.add(building_btn_list.get(i),c);//added building button
		c.gridx++;
		rmv_btns.add(new Button("X"));
		rmv_btns.get(i).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				removeHex(i);
			}
		});
		c.gridy++;c.gridx=0;
		hex_panel.add(add_hex_btn,c);
		NationHandler.listofhexes.add(new Hex());
		updateCombos();
	}
	
	public static void removeHex(int i) {
		getHexVisuals();
		NationHandler.listofhexes.remove(i);
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
			building_btn_list.remove(k);
			rmv_btns.remove(k);
		}
		
		for (int k=i;k<NationHandler.listofhexes.size();k++) {
			addHex();
		}
		c.gridx=0;
		c.gridy=2+hex_list.size();
		hex_panel.add(add_hex_btn,c);
		setHexVisuals();
	}	
	
	public static void getHexVisuals() {
		//System.out.println("HEXPANE! getHex");
		for (int i=0;i<hex_list.size()-1;i++) {
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
		}
	}
}