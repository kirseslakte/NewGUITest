package NewGUITest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LordPanes {
	//nationPanel variables
	public JLabel[] nationlabels = new JLabel[21];
	public String[] nationinfo = {"Tax Efficiency","Production of Own Hexes","Production Efficiency","Province Income","Trade Efficiency",
			"Province Upkeep","Vassal Income Efficiency","Development","Bank Income Efficiency","Population (%)","Bank Development Efficiency",
			"Vassal Tax Income","Plunder Efficiency","Trade Income","Guild Upkeep","Army Upkeep","Government Upkeep","Trade Value",
			"Total Income","Total Upkeep","Total Production","Banked Development","Banked RP","Taxation Level","Lord Tax Rate"};//25 long
	/*tax_eff,hex_prod,prod_eff,prov_inc,trade_eff,vassal_inc_eff,plunder_eff,dev,bank_inc_eff,bank_dev_eff,
	trade_value,prov_upk,ppop,vassal_tax_inc,guild_upk,trade_inc,army_upk,gov_upk,tot_inc,tot_upk,tot_prod;*/
	public JTextField[] inputs = new JTextField[4];//bank_dev,bank_rp,tax_rate,lord_tax_rate;
	//governmentPanel variables
	public Panel government_panel = new Panel(new GridLayout(0,4));
	public JLabel legitimacy_label;
	public JComboBox culture,religion,system,soc_structure,life_style,centralisation,rule;
	public JComboBox[] institutions = new JComboBox[4];
	public JComboBox[] histocracy_choices = new JComboBox[4];
	public JTextField[] histocracy_values = new JTextField[4];
	public JTextField legitimacy;
	public boolean histocheck;
	
	//TODO:
	//make sure, when loading that the correct values are displayed,
	//and make it updateable
	
	public LordPanes() {
		
	}
	
	public Panel nationPanel(Governments government,boolean b) {//creates the nation panel 
		System.out.println("LORDPANE! nationPanel");
		Panel nation_panel = new Panel(new GridLayout(0,4));
		for (int i=0;i<nationlabels.length;i++) {
			nationlabels[i] = new JLabel("0");
			nation_panel.add(new JLabel(nationinfo[i]));
			nation_panel.add(nationlabels[i]);
		}
		for (int i=0;i<inputs.length;i++){
			if (b||i<3) {
				inputs[i] = new JTextField("0");
				nation_panel.add(new JLabel(nationinfo[nationlabels.length+i]));
				nation_panel.add(inputs[i]);
			}
		}
		return nation_panel;
	}
	
	public Panel governmentPane(Governments government, Institutions institutes) {//government panel
		System.out.println("LORDPANE! governmentPane");
		this.government_panel.add(new JLabel("Culture"));
		this.government_panel.add(culture = new JComboBox<>(government.alignments));
		this.government_panel.add(legitimacy_label = new JLabel("Legitimacy"));
		this.government_panel.add(legitimacy = new JTextField("10"));
		this.government_panel.add(new JLabel("Religion"));
		this.government_panel.add(religion = new JComboBox<>(government.alignments));
		this.government_panel.add(new JLabel("System"));
		this.government_panel.add(system = new JComboBox<>(government.systems));
		this.government_panel.add(new JLabel("Social Structure"));
		this.government_panel.add(soc_structure = new JComboBox<>(government.strucs));
		this.government_panel.add(new JLabel("Rule"));
		this.government_panel.add(rule = new JComboBox<>(government.rule));
		this.government_panel.add(new JLabel("Life Style"));
		this.government_panel.add(life_style = new JComboBox<>(government.life));
		this.government_panel.add(new JLabel("Centralisation"));
		this.government_panel.add(centralisation = new JComboBox<>(government.centralisation));
		for (int i=0;i<4;i++){
			this.government_panel.add(new JLabel("Institution"));
			this.government_panel.add(institutions[i] = new JComboBox<>(institutes.institution_names));
			this.histocracy_values[i] = new JTextField("0");
			this.histocracy_choices[i] = new JComboBox<>(government.histocratic_options);
		}
		return government_panel;
	}
	
	public void setGovernmentPane(Governments government, Institutions institutes) {//load visuals for government pane
		System.out.println("LORDPANE! setGovernmentPane");
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
		if (histocheck) {
			for (int i=0;i<4;i++) {
				this.government_panel.add(histocracy_choices[i]);
				this.government_panel.add(histocracy_values[i]);
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
			}
		}
	}
	
	public void updateGovernmentPane(Governments government) {//update if hive/hist are selected
		System.out.println("LORDPANE! updateGovernmentPane");
		if (government.sys.equals("Hive"))  //set legitimacy text for hive or not hive
			this.legitimacy_label.setText("Control");
		else if (!government.sys.equals("Hive")&&legitimacy_label.getText().equals("Control"))
			this.legitimacy_label.setText("Legitimacy");
		if ((government.sys.equals("Histocratic")&&government_panel.getComponentCount()<25)) {
			for (int i=0;i<4;i++) {
				this.government_panel.add(histocracy_choices[i] = new JComboBox<>(government.histocratic_options));
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);
				this.government_panel.add(histocracy_values[i] = new JTextField(""));
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
			}
		}else if ((government.sys.equals("Histocratic")&&government_panel.getComponentCount()>25)) {
			for (int i=0;i<4;i++) {
				this.histocracy_values[i].setText(Integer.toString(government.hist_val[i]));
				this.histocracy_choices[i].setSelectedItem(government.histocratic_choices[i]);	
			}	
		}else if ((!government.sys.equals("Histocratic"))&&government_panel.getComponentCount()>25) {
			for (int i=0;i<4;i++) {
				this.government_panel.remove(histocracy_choices[i]);
				this.government_panel.remove(histocracy_values[i]);
			}
		}
		this.government_panel.revalidate();		
	}
	
}