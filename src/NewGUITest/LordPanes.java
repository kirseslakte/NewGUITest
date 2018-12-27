package NewGUITest;

import javax.swing.*;
import java.awt.*;

public class LordPanes {
	//nationPanel variables
	public JLabel tax_eff,hex_prod,prod_eff,prov_inc,trade_eff,vassal_inc_eff,dev,bank_inc_eff,bank_dev_eff,
	trade_value,prov_upk,ppop,vassal_tax_inc,guild_upk,trade_inc,army_upk,gov_upk,tot_inc,tot_upk,tot_prod;
	public JTextField bank_dev,bank_rp,tax_rate,lord_tax_rate;
	//governmentPanel variables
	
	
	public LordPanes() {
		
	}
	
	public Panel nationPanel(Governments government,boolean b) {//creates the nation panel 
		Panel nation_panel = new Panel(new GridLayout(0,4));
		nation_panel.add(new JLabel("Tax Efficiency"));
		nation_panel.add(tax_eff = new JLabel(Integer.toString((int)Math.rint(government.tax_eff))));
		nation_panel.add(new JLabel("Production of Directly Controlled Hexes"));
		nation_panel.add(hex_prod = new JLabel(""));
		nation_panel.add(new JLabel("Production Efficiency"));
		nation_panel.add(prod_eff = new JLabel(Integer.toString((int)Math.rint(government.prod_eff))));
		nation_panel.add(new JLabel("Province Income"));
		nation_panel.add(prov_inc = new JLabel(""));
		nation_panel.add(new JLabel("Trade Efficiency"));
		nation_panel.add(trade_eff = new JLabel(Integer.toString((int)Math.rint(government.trade_eff))));
		nation_panel.add(new JLabel("Province Upkeep"));
		nation_panel.add(prov_upk = new JLabel(""));
		nation_panel.add(new JLabel("Vassal Income Efficiency"));
		nation_panel.add(vassal_inc_eff = new JLabel(Integer.toString((int)Math.rint(government.vassal_inc_eff))));
		nation_panel.add(new JLabel("Development"));
		nation_panel.add(dev = new JLabel(""));
		nation_panel.add(new JLabel("Bank Income Efficiency"));
		nation_panel.add(bank_inc_eff = new JLabel(Integer.toString((int)Math.rint(government.bank_inc_eff))));
		nation_panel.add(new JLabel("Population (%)"));
		nation_panel.add(ppop = new JLabel(""));
		nation_panel.add(new JLabel("Bank Development Efficiency"));
		nation_panel.add(bank_dev_eff = new JLabel(Integer.toString((int)Math.rint(government.bank_dev_eff))));
		nation_panel.add(new JLabel("Vassal Tax Income"));
		nation_panel.add(vassal_tax_inc = new JLabel(""));
		nation_panel.add(new JLabel("Guild Upkeep"));
		nation_panel.add(guild_upk = new JLabel(""));
		nation_panel.add(new JLabel("Trade Income"));
		nation_panel.add(trade_inc = new JLabel(""));
		nation_panel.add(new JLabel("Army Upkeep"));
		nation_panel.add(army_upk = new JLabel(""));
		nation_panel.add(new JLabel("Government Upkeep"));
		nation_panel.add(gov_upk = new JLabel(""));
		nation_panel.add(new JLabel("Trade Value"));
		nation_panel.add(trade_value = new JLabel(""));
		nation_panel.add(new JLabel("Total Income"));
		nation_panel.add(tot_inc = new JLabel(""));
		nation_panel.add(new JLabel("Total Upkeep"));
		nation_panel.add(tot_upk = new JLabel(""));
		nation_panel.add(new JLabel("Total Production"));
		nation_panel.add(tot_prod = new JLabel(""));
		nation_panel.add(new JLabel("Banked Development"));
		nation_panel.add(bank_dev = new JTextField(""));
		nation_panel.add(new JLabel("Banked RP"));
		nation_panel.add(bank_rp = new JTextField(""));
		nation_panel.add(new JLabel("Taxation Level"));
		nation_panel.add(tax_rate = new JTextField(""));
		if (b) {
			nation_panel.add(new JLabel("Lord Tax Rate"));
			nation_panel.add(lord_tax_rate = new JTextField(""));
		}
		return nation_panel;
	}
	
	public Panel governmentPane(Governments government, Institutions institutes) {
		Panel government_panel = new Panel(new GridLayout(0,4));
		government_panel.add(new JLabel("Culture"));
		government_panel.add(new JComboBox(government.alignments));
		government_panel.add(new JLabel("Legitimacy"));
		government_panel.add(new JTextField(""));
		government_panel.add(new JLabel("Religion"));
		government_panel.add(new JComboBox(government.alignments));
		government_panel.add(new JLabel("System"));
		government_panel.add(new JComboBox(government.systems));
		government_panel.add(new JLabel("Social Structure"));
		government_panel.add(new JComboBox(government.strucs));
		government_panel.add(new JLabel("Rule"));
		government_panel.add(new JComboBox(government.rule));
		government_panel.add(new JLabel("Life Style"));
		government_panel.add(new JComboBox(government.life));
		government_panel.add(new JLabel("Centralisation"));
		government_panel.add(new JComboBox(government.centralisation));
		for (int i=0;i<4;i++){
			government_panel.add(new JLabel("Institution"));
			government_panel.add(new JComboBox(institutes.institution_names));
		}
		return government_panel;
	}
}