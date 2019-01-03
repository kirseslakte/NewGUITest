package NewGUITest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LordPanes {
	//nationPanel variables
	public JLabel tax_eff,hex_prod,prod_eff,prov_inc,trade_eff,vassal_inc_eff,dev,bank_inc_eff,bank_dev_eff,
	trade_value,prov_upk,ppop,vassal_tax_inc,guild_upk,trade_inc,army_upk,gov_upk,tot_inc,tot_upk,tot_prod;
	public JTextField bank_dev,bank_rp,tax_rate,lord_tax_rate;
	//governmentPanel variables
	public Panel government_panel = new Panel(new GridLayout(0,4));
	public JLabel legitimacy_label;
	public JComboBox culture,religion,system,soc_structure,life_style,centralisation,rule;
	public JComboBox[] institutions = new JComboBox[4];
	public JComboBox[] histocracy_choices = new JComboBox[4];
	public JTextField[] histocracy_values = new JTextField[4];
	public JTextField legitimacy;
	public boolean histocheck;
	//culture variables
	public List<JTextField> culturefields = new ArrayList<JTextField>();
	public String[] culture_names =  {"Unit Training Cost (%)","Undead Unit Cap (%)","Unit Cap (%)","Unit Equipment Cost (%)","Hit","AC",
			"Morale","Command","Ranged Hit","Settlement Upkeep Cost (%)","Fortification Cost (%)","Settlement Upgrade Cost (%)","Base Production (%)",
			"Production Efficiency (%)","Tax Efficiency (%)","Bank Efficiency (%)","Trade Efficiency (%)","Vassal Tax Income Efficiency (%)",
			"Mage Guild Cost (%)","Tinker Guild Cost (%)","Spy Guild Cost (%)","All Guilds Cost (%)"};
	
	//TODO:
	//make sure, when loading that the correct values are displayed,
	//and make it updateable
	
	public LordPanes() {
		
	}
	
	public Panel nationPanel(Governments government,boolean b) {//creates the nation panel 
		Panel nation_panel = new Panel(new GridLayout(0,4));
		nation_panel.add(new JLabel("Tax Efficiency"));
		nation_panel.add(tax_eff = new JLabel(Integer.toString((int)Math.rint(government.tax_eff))));
		nation_panel.add(new JLabel("Production of Own Hexes"));
		nation_panel.add(hex_prod = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Production Efficiency"));
		nation_panel.add(prod_eff = new JLabel(Integer.toString((int)Math.rint(government.prod_eff))));
		nation_panel.add(new JLabel("Province Income"));
		nation_panel.add(prov_inc = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Trade Efficiency"));
		nation_panel.add(trade_eff = new JLabel(Integer.toString((int)Math.rint(government.trade_eff))));
		nation_panel.add(new JLabel("Province Upkeep"));
		nation_panel.add(prov_upk = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Vassal Income Efficiency"));
		nation_panel.add(vassal_inc_eff = new JLabel(Integer.toString((int)Math.rint(government.vassal_inc_eff))));
		nation_panel.add(new JLabel("Development"));
		nation_panel.add(dev = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Bank Income Efficiency"));
		nation_panel.add(bank_inc_eff = new JLabel(Integer.toString((int)Math.rint(government.bank_inc_eff))));
		nation_panel.add(new JLabel("Population (%)"));
		nation_panel.add(ppop = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Bank Development Efficiency"));
		nation_panel.add(bank_dev_eff = new JLabel(Integer.toString((int)Math.rint(government.bank_dev_eff))));
		nation_panel.add(new JLabel("Vassal Tax Income"));
		nation_panel.add(vassal_tax_inc = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Guild Upkeep"));
		nation_panel.add(guild_upk = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Trade Income"));
		nation_panel.add(trade_inc = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Army Upkeep"));
		nation_panel.add(army_upk = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Government Upkeep"));
		nation_panel.add(gov_upk = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Trade Value"));
		nation_panel.add(trade_value = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Total Income"));
		nation_panel.add(tot_inc = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Total Upkeep"));
		nation_panel.add(tot_upk = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Total Production"));
		nation_panel.add(tot_prod = new JLabel("0"));//needs updating
		nation_panel.add(new JLabel("Banked Development"));
		nation_panel.add(bank_dev = new JTextField(Integer.toString(government.eco[1])));//needs updating
		nation_panel.add(new JLabel("Banked RP"));
		nation_panel.add(bank_rp = new JTextField(Integer.toString(government.eco[0])));//needs updating
		nation_panel.add(new JLabel("Taxation Level"));
		nation_panel.add(tax_rate = new JTextField(Integer.toString(government.eco[2])));//needs updating
		if (b) {
			nation_panel.add(new JLabel("Lord Tax Rate"));
			nation_panel.add(lord_tax_rate = new JTextField(Integer.toString(government.eco[3])));//needs updating
		}
		return nation_panel;
	}
	
	public Panel governmentPane(Governments government, Institutions institutes) {//government panel
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
	
	public Panel culturePane() {//culture panel
		Panel culture_panel = new Panel(new GridLayout(0,4));
		for (int i=0;i<22;i++){
			culturefields.add(new JTextField("0"));
			culture_panel.add(new JLabel(culture_names[i]));
			culture_panel.add(culturefields.get(i));
		}
		return culture_panel;
	}
	
	public void setCulturePane(int[] s) {//setting culture pane
		for (int i=0;i<22;i++)//only ever called from lord.setCulture()
			culturefields.get(i).setText(Integer.toString(s[i]));		
	}
}