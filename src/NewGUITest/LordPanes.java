package NewGUITest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LordPanes {
	//nationPanel variables
	public JLabel[] nationlabels = new JLabel[19];
	public String[] nationinfo = {"Tax Efficiency","Production of Own Hexes","Production Efficiency","Province Income","Trade Efficiency",
			"Province Upkeep","Vassal Income Efficiency","Development","Bank Income Efficiency","Population (%)","Bank Development Efficiency",
			"Vassal Tax Income","Plunder Efficiency","Trade Income","Government Upkeep","Trade Value",
			"Total Income","Total Upkeep","Total Production","Guild Upkeep","Army Upkeep","Banked Development","Banked RP","Taxation Level","Lord Tax Rate"};//25 long
	/*tax_eff,hex_prod,prod_eff,prov_inc,trade_eff,vassal_inc_eff,plunder_eff,dev,bank_inc_eff,bank_dev_eff,
	trade_value,prov_upk,ppop,vassal_tax_inc,guild_upk,trade_inc,army_upk,gov_upk,tot_inc,tot_upk,tot_prod;*/
	public JTextField[] inputs = new JTextField[6];//army_upk,guild_upk,bank_dev,bank_rp,tax_rate,lord_tax_rate;
	//governmentPanel variables
	public Panel government_panel = new Panel(new GridLayout(0,4));
	public Panel nation_panel = new Panel(new GridBagLayout());
	public JLabel legitimacy_label;
	public JComboBox culture,religion,system,soc_structure,life_style,centralisation,rule;
	public JComboBox[] institutions = new JComboBox[4];
	public JComboBox[] histocracy_choices = new JComboBox[4];
	public JTextField[] histocracy_values = new JTextField[4];
	public JTextField legitimacy;
	public boolean histocheck;
	static GridBagConstraints c = new GridBagConstraints();
	
	//TODO:
	//make sure, when loading that the correct values are displayed,
	//and make it updateable
	
	public LordPanes() {
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 1;
		c.ipady = 5;
	}
	
	public Panel buttonPane(String lord_name) {		
		Panel pane = new Panel(new GridLayout(0,4));
		Button saveBtn = new Button("Save & Update Nation");
		Button newVassalBtn = new Button("New Vassal");
		Button routes = new Button("Trade Routes");
		Button officials = new Button("Officials");
		pane.add(newVassalBtn);
		pane.add(saveBtn);
		pane.add(routes);
		pane.add(officials);
		int lordindex = Utility.findLord(lord_name);
		System.out.println(lordindex);
		if (NationHandler.listoflords.get(lordindex).title.equals("overlord")) {
			newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
				public void actionPerformed(ActionEvent e){
					String newlord = JOptionPane.showInputDialog(null,"Name the new vassal:","Vassal Creation",JOptionPane.QUESTION_MESSAGE);
					NationHandler.newLord(newlord,"overlord");
				}
			});
		} else {
			newVassalBtn.setLabel("Remove Vassal");
			newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
				public void actionPerformed(ActionEvent e){
					NationHandler.removeLord(lordindex);
				}
			});
		}
		saveBtn.addActionListener(new ActionListener() {//add action event to save button
			public void actionPerformed(ActionEvent e){
				NationHandler.saveNation();
			}
		});
		routes.addActionListener(new ActionListener() {//add action event to save button
			public void actionPerformed(ActionEvent e){
				if (!(NationHandler.listoflords.get(lordindex).route.isVisible()))
					NationHandler.listoflords.get(lordindex).route.start();
			}
		});
		officials.addActionListener(new ActionListener() {//add action event to save button
			public void actionPerformed(ActionEvent e){
				if (!(NationHandler.listoflords.get(lordindex).official.isVisible()))
					NationHandler.listoflords.get(lordindex).official.start();
			}
		});
		return pane;
	}
	
	public Panel nationPane(Governments government,boolean b) {//creates the nation panel 
		//System.out.println("LORDPANE! nationPanel");
		for (int i=0;i<this.nationlabels.length;i++) {
			c.gridy = (int) Math.floor(i/2);
			this.nationlabels[i] = new JLabel("0");
			if (i%2==0) {
				c.gridx = 0;
				this.nation_panel.add(new JLabel(this.nationinfo[i]),c);
				c.gridx = 1;
				this.nation_panel.add(nationlabels[i],c);
			} else {
				c.gridx = 2;
				this.nation_panel.add(new JLabel(this.nationinfo[i]),c);
				c.gridx = 3;
				this.nation_panel.add(this.nationlabels[i],c);
			}
		}
		for (int i=0;i<inputs.length;i++){
			inputs[i] = new JTextField("0");
			if (b||i<5) {
				if (i%2==0) {
					c.gridy++;
					c.gridx = 0;
					this.nation_panel.add(new JLabel(this.nationinfo[this.nationlabels.length+i]),c);
					c.gridx = 1;
					this.nation_panel.add(inputs[i],c);
				} else {
					c.gridx = 2;
					this.nation_panel.add(new JLabel(this.nationinfo[this.nationlabels.length+i]),c);
					c.gridx = 3;
					this.nation_panel.add(inputs[i],c);					
				}
			}
		}
		return nation_panel;
	}
	
	public void updateNationPane(int lordindex) {
		//System.out.println("LORDPANE! updateNationPane");
		Lord lord = NationHandler.listoflords.get(lordindex);
		double total_population = 0;
		for (Lord l:NationHandler.listoflords)
			total_population += l.population;
		this.nationlabels[0].setText(Integer.toString((int) Math.round(lord.modifiers[0]*100)));//tax
		this.nationlabels[1].setText(Integer.toString(lord.own_bp));//production of own hexes
		this.nationlabels[2].setText(Integer.toString((int) Math.round(lord.modifiers[1]*100)));//prod
		this.nationlabels[3].setText(Integer.toString(lord.province_inc));//province inc
		this.nationlabels[4].setText(Integer.toString((int) Math.round(lord.modifiers[2]*100)));//trade
		this.nationlabels[5].setText(Integer.toString(lord.province_upk));//province upk
		this.nationlabels[6].setText(Integer.toString((int) Math.round(lord.modifiers[3]*100)));//vassal
		this.nationlabels[7].setText(Integer.toString(lord.development));//development
		this.nationlabels[8].setText(Integer.toString((int) Math.round(lord.modifiers[5]*100)));//bank inc
		this.nationlabels[9].setText(Double.toString((double) Math.round(lord.population/Math.max(total_population,1)*1000d) /1000d));//population %
		this.nationlabels[10].setText(Integer.toString((int) Math.round(lord.modifiers[6]*100)));//bank dev
		this.nationlabels[11].setText(Integer.toString(lord.vassal_inc));//vassal tax income
		this.nationlabels[12].setText(Integer.toString((int) Math.round(lord.modifiers[4]*100)));//plunder
		this.nationlabels[13].setText(Integer.toString((int) Math.round(lord.total_trade_value*lord.modifiers[2])));//trade inc
		//this.nationlabels[14].setText(Integer.toString(lord.guild_upk));//guild upk
		//this.nationlabels[15].setText(Integer.toString(lord.army_upk));//army upk
		this.nationlabels[14].setText(Integer.toString(lord.government_upk));//gov upk
		this.nationlabels[15].setText(Integer.toString(lord.total_trade_value));//trade value
		this.nationlabels[16].setText(Integer.toString(lord.total_inc));//total inc
		this.nationlabels[17].setText(Integer.toString(lord.total_upk));//total upk
		this.nationlabels[18].setText(Integer.toString(lord.total_bp));//total production
		for (int i=0;i<this.inputs.length;i++) {
			if (this.inputs[i].getText().equals("0"))
				this.inputs[i].setText(Integer.toString(NationHandler.listoflords.get(lordindex).government.eco[i]));
			else
				NationHandler.listoflords.get(lordindex).government.eco[i] = Integer.parseInt(this.inputs[i].getText());
		}
	}
	
	public Panel governmentPane(Governments government, Institutions institutes) {//government panel
		//System.out.println("LORDPANE! governmentPane");
		this.government_panel.add(new JLabel("Culture"));
		this.government_panel.add(culture = new JComboBox<>(Governments.alignments));
		this.government_panel.add(legitimacy_label = new JLabel("Legitimacy"));
		this.government_panel.add(legitimacy = new JTextField("10"));
		this.government_panel.add(new JLabel("Religion"));
		this.government_panel.add(religion = new JComboBox<>(Governments.alignments));
		this.government_panel.add(new JLabel("System"));
		this.government_panel.add(system = new JComboBox<>(Governments.systems));
		this.government_panel.add(new JLabel("Social Structure"));
		this.government_panel.add(soc_structure = new JComboBox<>(Governments.strucs));
		this.government_panel.add(new JLabel("Rule"));
		this.government_panel.add(rule = new JComboBox<>(Governments.rule));
		this.government_panel.add(new JLabel("Life Style"));
		this.government_panel.add(life_style = new JComboBox<>(Governments.life));
		this.government_panel.add(new JLabel("Centralisation"));
		this.government_panel.add(centralisation = new JComboBox<>(Governments.centralisation));
		for (int i=0;i<4;i++){
			this.government_panel.add(new JLabel("Institution"));
			this.government_panel.add(institutions[i] = new JComboBox<>(Institutions.institution_names));
			this.histocracy_values[i] = new JTextField("0");
			this.histocracy_choices[i] = new JComboBox<>(Governments.histocratic_options);
		}
		return government_panel;
	}
	
	public void setGovernmentPane(Governments government, Institutions institutes) {//load visuals for government pane
		//System.out.println("LORDPANE! setGovernmentPane");
		this.culture.setSelectedItem(government.culture);
		this.legitimacy.setText(Integer.toString(government.legitimacy));
		this.religion.setSelectedItem(government.religion);
		this.system.setSelectedItem(government.sys);
		this.histocheck = system.getSelectedItem().equals("Histocratic");
		this.soc_structure.setSelectedItem(government.struc);
		this.rule.setSelectedItem(government.ruler);
		this.life_style.setSelectedItem(government.style);
		this.centralisation.setSelectedItem(government.cent);
		for (int i=0;i<4;i++) {
			this.institutions[i].setSelectedItem(institutes.active_institutions[i]);
		}
		if (this.histocheck) {
			for (int i=0;i<4;i++) {
				this.government_panel.add(this.histocracy_choices[i]);
				this.government_panel.add(this.histocracy_values[i]);
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
			}
		}
	}
	
	public void updateGovernmentPane(Governments government) {//update if hive/hist are selected
		//System.out.println("LORDPANE! updateGovernmentPane");
		if (government.sys.equals("Hive"))  //set legitimacy text for hive or not hive
			this.legitimacy_label.setText("Control");
		else if (!government.sys.equals("Hive")&&legitimacy_label.getText().equals("Control"))
			this.legitimacy_label.setText("Legitimacy");
		if ((government.sys.equals("Histocratic")&&this.government_panel.getComponentCount()<25)) {
			for (int i=0;i<4;i++) {
				this.government_panel.add(this.histocracy_choices[i] = new JComboBox<>(Governments.histocratic_options));
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);
				this.government_panel.add(this.histocracy_values[i] = new JTextField(""));
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
			}
		}else if ((government.sys.equals("Histocratic")&&this.government_panel.getComponentCount()>25)) {
			for (int i=0;i<4;i++) {
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);	
			}	
		}else if ((!government.sys.equals("Histocratic"))&&this.government_panel.getComponentCount()>25) {
			for (int i=0;i<4;i++) {
				this.government_panel.remove(this.histocracy_choices[i]);
				this.government_panel.remove(this.histocracy_values[i]);
			}
		}
		this.government_panel.revalidate();		
	}
	
}