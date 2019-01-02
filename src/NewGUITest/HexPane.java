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
	public List<JTextField> resource_list = new ArrayList<JTextField>();
	public List<JLabel> built_buildings_list = new ArrayList<JLabel>();
	public List<JLabel> upgrade_cost_list = new ArrayList<JLabel>();
	public List<JLabel> bp_list = new ArrayList<JLabel>();
	public List<JLabel> upkeep_list = new ArrayList<JLabel>();
	public List<JLabel> gov_upkeep_list = new ArrayList<JLabel>();
	public List<JLabel> pv_list = new ArrayList<JLabel>();
	public List<JLabel> unit_cap_list = new ArrayList<JLabel>();
	public List<JButton> building_btn_list = new ArrayList<JButton>();
	public Panel hex_panel = new Panel(new GridBagLayout());
	public int number_of_hexes = 0;
	
	public HexPane() {
		
	}
	
	public Panel hexPanel(List<Hex> listofhexes) {
		number_of_hexes = listofhexes.size();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//setting up row 0 (the row of labels)
		c.gridy = 0;
		c.gridx = 0;
		c.ipadx = 20;
		hex_panel.add(new JLabel("Hex Name"), c);
		c.gridx = 1;
		c.ipadx = 10;
		hex_panel.add(new JLabel("Owner"), c);
		c.gridx = 2;
		c.ipadx = 5;
		hex_panel.add(new JLabel("Hab"), c);
		c.gridx = 3;
		hex_panel.add(new JLabel("Culture"), c);
		c.gridx = 4;
		hex_panel.add(new JLabel("Religion"), c);
		c.gridx = 5;
		hex_panel.add(new JLabel("PS"), c);
		c.gridx = 6;
		hex_panel.add(new JLabel("Unrest"), c);
		c.gridx = 7;
		c.ipadx = 8;
		hex_panel.add(new JLabel("Resource"), c);
		c.gridx = 8;
		c.ipadx = 15;
		hex_panel.add(new JLabel("Buildings"), c);
		c.gridx = 9;
		c.ipadx = 10;
		hex_panel.add(new JLabel("Upgrade Cost"), c);
		c.gridx = 10;
		hex_panel.add(new JLabel("BP"), c);
		c.gridx = 11;
		hex_panel.add(new JLabel("Upkeep"), c);
		c.gridx = 12;
		hex_panel.add(new JLabel("Gvnm Upkeep"), c);
		c.gridx = 13;
		c.ipadx = 5;
		hex_panel.add(new JLabel("PV"), c);
		c.gridx = 14;
		hex_panel.add(new JLabel("Unit Cap"), c);
		c.gridx = 15;
		hex_panel.add(new JLabel("Build"), c);
		
		
		
		return hex_panel;
	}
	public void addHex() {
		
	}
}