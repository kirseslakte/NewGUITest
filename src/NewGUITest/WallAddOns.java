package NewGUITest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class WallAddOns extends JFrame{

	public static String[] walladdonlist = {"","Bastion","Standard Wood Gate","Heavy Wood Gate","Reinforced Wood Gate","Standard Iron Gate",
			"Heavy Iron Gate","Reinforced Iron Gate"};
	public static int[] wall_add_on_costs = {200,100,200,300,400,500,600};//bas,std w,h w,rf w,std i,h i,rf i
	
	public List<String> built_add_on = new ArrayList<String>();		//ADD-ONS
	public List<JComboBox> add_on = new ArrayList<JComboBox>();			//input add-ons
	public List<JLabel> add_on_cost = new ArrayList<JLabel>();
	public List<JTextField> add_on_amount = new ArrayList<JTextField>();
	public List<JLabel> add_on_upkeep = new ArrayList<JLabel>();
	
	GridBagConstraints c = new GridBagConstraints();
	public double wall_mod;
	int hex_index;
	
	//outputs
	public int cost = 0;

	NationHandler getter = new NationHandler();
		
	public WallAddOns() {
		//System.out.println("WALLADDON! WallAddOns");
		this.c.fill = GridBagConstraints.HORIZONTAL;
		this.c.weightx = 0.5;
		this.c.ipady = 20;
		this.initialize();
	}
	
	public void setFrame() {
		//System.out.println("WALLADDON! setFrame");
		JPanel panel = new JPanel(new GridBagLayout());
		this.setSize(600,500);
	    this.setLocationRelativeTo(getter);
    	this.setTitle("Wall add-ons");
    	this.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				update();
				//getter.hexpanel.buildings.get(hex_index).update();
			}
		});
    	this.c.gridy = 0;//first row!
    	this.c.gridx = 0;
	    panel.add(new JLabel("Add-On"),c);
	    this.c.gridx = 1;
	    panel.add(new JLabel("Amount"),c);
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
	    	this.add_on.add(new JComboBox<>(this.walladdonlist));
	    	this.add_on_cost.add(new JLabel("0"));
	    	this.add_on_upkeep.add(new JLabel("0"));
	    	this.add_on_amount.add(new JTextField("0"));
	    	this.c.gridy = 1+i;
	    	this.c.gridx = 0;
	    	panel.add(this.add_on.get(i),c);
	    	//System.out.println("add_on "+i+" is set on "+add_on.get(i).getSelectedItem());
	    	this.c.gridx = 1;
	    	panel.add(this.add_on_amount.get(i),c);
	    	this.c.gridx = 2;
	    	panel.add(this.add_on_cost.get(i),c);	    	
	    	this.c.gridx = 3;
	    	panel.add(this.add_on_upkeep.get(i),c);
	    }
	    this.add(new JScrollPane(panel));
	    //this.validate();
	}
	
	public void update() {	
		//System.out.println("WALLADDON! update");	
		int amount = 1;
		this.cost = 0;
		this.built_add_on.clear();
		for (int i=0;i<this.add_on.size();i++) {
			String addon = (String) this.add_on.get(i).getSelectedItem();
			if (!(addon.equals(""))) {
				amount = Integer.parseInt(this.add_on_amount.get(i).getText());
				this.built_add_on.add(addon+","+this.add_on_amount.get(i).getText());
				this.cost += amount*findCost(addon);
				this.add_on_cost.get(i).setText(Integer.toString(amount*findCost(addon)));
				this.add_on_upkeep.get(i).setText(Integer.toString((int) Math.round(amount*findCost(addon)*0.2)));
			} else {
				this.add_on_cost.get(i).setText("0");
				this.add_on_upkeep.get(i).setText("0");
			}
		}
	}
	
	public void initialize() {
		//System.out.println("WALLADDON! initialize");	
	    this.setFrame();
	}
	
	public void setAddOn(String[] s,int[] amounts) {//walls load takes input into code and visual layers (should only be called when loading/opening pop-up!)
		//System.out.println("WALLADDON! setAddOn");
		this.built_add_on.clear();
		for (int i=0;i<s.length;i++) {
			this.built_add_on.add(s[i]+","+Integer.toString(amounts[i]));
			this.add_on.get(i).setSelectedItem(s[i]);
			this.add_on_amount.get(i).setText(Integer.toString(amounts[i]));
			this.add_on_cost.get(i).setText(Integer.toString(findCost(s[i])));
			this.add_on_cost.get(i).setToolTipText(findOfficialCost(findCost(s[i])));
			this.add_on_upkeep.get(i).setText(Integer.toString((int) Math.round(findCost(s[i])*0.2)));
		}
	}
	
	public void start(int hex_index, String fort_name, double fort_reducer) {
		//System.out.println("WALLADDON! start");
		this.hex_index = hex_index;
		this.wall_mod = fort_reducer;
		for (int i=0;i<this.add_on.size();i++) {
			try {
				this.built_add_on.get(i);
			} catch (IndexOutOfBoundsException e) {
				this.add_on.get(i).setSelectedItem("");
			}
		}
	    if (fort_name.equals(""))
	    	JOptionPane.showMessageDialog(null,"There will be no gates without walls!","Wall Error",JOptionPane.INFORMATION_MESSAGE);
	    else {
	    	this.setVisible(true);
	    }
	}
	
	public int findCost(String s) {// helper function
		//System.out.println("WALLADDON! findCost");
		int build_cost = 0;
		for (int i=0;i<wall_add_on_costs.length;i++){
			if (s.equals(walladdonlist[i+1]))
				build_cost = (int) Math.round(wall_add_on_costs[i]*this.wall_mod);
		}
		return build_cost;
	}
	
	public void stop() {
		//System.out.println("WALLADDON! stop");
		this.setVisible(false);
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