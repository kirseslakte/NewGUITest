package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BuildingsAddOns extends JFrame{

	public static String[] addonlist = {"","Moat","Motte","Glacis","Curtain Wall","Keep","Bastion","Standard Wood Gate","Heavy Wood Gate",
			"Reinforced Wood Gate","Standard Iron Gate","Heavy Iron Gate","Reinforced Iron Gate"};
	public static int[] add_on_costs = {10,10,10,20,20,200,100,200,300,400,500,600};//(%) moat,motte,glacis,curtain,keep,bastion(0-5)
	public List<JLabel> add_on_upkeep = new ArrayList<JLabel>();				//5-10

	public List<String> built_add_on = new ArrayList<String>();		//ADD-ONS
	public List<JComboBox> add_on = new ArrayList<JComboBox>();			//input add-ons
	public List<JLabel> add_on_cost = new ArrayList<JLabel>();
	
	GridBagConstraints c = new GridBagConstraints();
	
	public double mod = 1;
	public int hex_index = 0;
	public int base_cost = 0;
	
	//outputs
	public int cost = 0;

	NationHandler getter = new NationHandler();
	
	public BuildingsAddOns() {
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.weightx = 0.5;
		this.c.ipady = 20;
		this.initialize();
	}
	
	public void start(int hex_index, String fort_name) {
		//System.out.println("BUILDINGADDON! start");
		this.hex_index = hex_index;
		for (int i=0;i<this.add_on.size();i++) {
			try {
				this.built_add_on.get(i);
			} catch (IndexOutOfBoundsException e) {
				this.add_on.get(i).setSelectedItem("");
			}
		}
	    if (fort_name.equals("")||fort_name.equals("Aerial Defenses")) {
	    	JOptionPane.showMessageDialog(null,"There is no fortification to add add-ons to!","Fortification Error",JOptionPane.INFORMATION_MESSAGE);
	    }else {
	    	this.setVisible(true);
	    }
	}
	public void stop() {
		//System.out.println("BUILDINGADDON! stop");
		this.setVisible(false);
	}
	
	public void initialize() {
		//System.out.println("BUILDINGADDON! initialize");
	    this.setFrame();
	}
	
	public void setFrame() {//setting the visuals of the frame
		//System.out.println("BUILDINGADDON! setFrame");
		//this.removeAll();
		JPanel panel = new JPanel(new GridBagLayout());
		this.setSize(600,500);
	    this.setLocationRelativeTo(null);
    	this.setTitle("Fortification add-ons");
    	this.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				update();
				//HexPane.buildings.get(hex_index).update();
			}
		});
    	this.c.gridy = 0;//first row!
    	this.c.gridx = 0;
	    panel.add(new JLabel("Add-On"),c);
	    this.c.gridx = 2;
	    panel.add(new JLabel("Cost"),c);
	    this.c.gridx = 3;
	    panel.add(new JLabel("Upkeep"),c);
	    Button updater = new Button("Update");
	    updater.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		update();
	    	}
	    });
	    this.c.gridx = 4;
	    panel.add(updater,c);
	    for (int i=0;i<5;i++) {
	    	this.add_on.add(new JComboBox<>(addonlist));
	    	this.add_on_cost.add(new JLabel("0"));
	    	this.add_on_upkeep.add(new JLabel("0"));
	    	this.c.gridy = 1+i;
	    	this.c.gridx = 0;
	    	panel.add(this.add_on.get(i),c);
	    	this.c.gridx = 2;
	    	panel.add(this.add_on_cost.get(i),c);	    	
	    	this.c.gridx = 3;
	    	panel.add(this.add_on_upkeep.get(i),c);
	    }
	    this.add(new JScrollPane(panel));
	}

	public void setAddOn(String[] s, int fort_cost, double mod) {//set takes input into code and visual layers (should only be called when loading/opening pop-up!)
		//System.out.println("BUILDINGADDON! setAddOn");
		this.base_cost = fort_cost;
		this.mod = mod;
		//System.out.println("ADDON BASE COST IS "+fort_cost);
		this.built_add_on.clear();
		for (int i=0;i<s.length;i++) {
			String addon = s[i];
			this.built_add_on.add(s[i]);
			this.add_on.get(i).setSelectedItem(addon);
			//System.out.println("SETTING COST OF "+addon+" TO "+findCost(addon));
			this.add_on_cost.get(i).setText(Integer.toString(findCost(addon,i)));
			this.add_on_cost.get(i).setToolTipText(findOfficialCost(findCost(addon,i)));
		}
	}
	
	public void update() {//called to update the visual layer as well as to put info from visual->code
		//System.out.println("BUILDINGADDON! update");
		this.cost = 0;
		this.built_add_on.clear();
		for (int i=0;i<this.add_on.size();i++) {
			String addon = (String) this.add_on.get(i).getSelectedItem();
			if (!(addon.equals(""))) {
				this.built_add_on.add(addon);
				this.cost += findCost(addon,i);
				this.add_on_cost.get(i).setText(Integer.toString(findCost(addon,i)));
			}else {
				this.add_on_cost.get(i).setText("0");
				this.add_on_upkeep.get(i).setText("0");
			}
		}
	}
	
	public int findCost(String s, int index) {// helper function
		//System.out.println("BUILDINGADDON! findCost");
		int build_cost = 0;
		for (int i=0;i<add_on_costs.length;i++){
			if (s.equals(addonlist[i+1])){
				if (i<5)
					build_cost = (int) Math.round(add_on_costs[i]*this.base_cost*0.01);
				else {
					build_cost = (int) Math.round(add_on_costs[i]*this.mod);
					this.add_on_upkeep.get(index).setText(Integer.toString((int) Math.round(build_cost*0.2)));
				}
			}
		}
		return build_cost;
	}
	
	public String findOfficialCost(int add_on_cost) {
		String output = "<html>";
		for (Official o:NationHandler.listofofficials) {
			if (o.lord.equals(NationHandler.listofhexes.get(this.hex_index).owner)) {
				if (o.job.equals("Build Fortification")) {
					output += o.name+" is currently building buildings for "+o.lord+" and is reducing the cost of buildings by "+o.effect+"%. This building would therefore"
							+ " cost "+Integer.toString((int) Math.round(add_on_cost*(100-o.effect)/100))+".<br>";
				}
			}
		}
		output += "</html";
		return output;
	}
}