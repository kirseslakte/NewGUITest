package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lord extends NationHandler {
	
	public final int max_number_of_institutions = 4;//rule variables
	
	public String name;//government variables
	public String sys;
	public String society;
	public String rule;
	public String life;
	public String cent;
	public String alignment;
	public String religion;
	public String ruler_name;
	public int legitimacy;
	public double population_value;
	public int total_bpm;
	public int tax_income;
	public int total_trade_value;
	public double overlord_tax_rate;
	public String master_title;
	public String title;
	public boolean[] accessed_resources = new boolean[17];//darkwood->Drugs
	public Institutions institutes = new Institutions();
	public Governments government = new Governments();
	public double[] modifiers = new double[33];
	public static String[] mod_names =  {"Tax Efficiency","Production Efficiency","Trade Efficiency","Vassal Tax Income Efficiency","Plunder Efficinecy","Bank Income Efficiency",
			"Bank Development Efficiency","Rare Resource Income","Unit Speed","Unit Damage","Hit","AC","Morale","Command","Ranged Hit","Unit Cap","Legitimacy Checks","Unrest Checks",
			"Trade Rolls","Base Production","RGO/Road Cost","Building Cost","Settlement Upkeep Cost","Settlement Upgrade Cost","Unit Training Cost","Unit Equipment Cost",
			"Fortification Cost","Palace Build Cost","Mage Guild Cost","Druid Guild Cost","Spy Guild Cost","Tinker Guild Cost","All Guilds Cost"};
	
	//TradeWindow trade = new TradeWindow();
	
	static NationHandler getter = new NationHandler();
	/*													MAYBE ROLL ALL THESE OFFICALS INTO SEPARATE CLASS
	 * bank income roll								4	OFFICIAL
	 * tax collect roll								5	OFFICIAL
	 * muster/train troops roll						6	OFFICIAL
	 * build guild/building/settlement roll			7	OFFICIAL
	 * command army roll							8	OFFICIAL
	 * trade negotiator skill						9	OFFICIAL
	 * 													AT LEAST THESE ARE THE NESSESARY OFFICIAL ACTIONS
	 *													THEY WILL BE ROLLED INTO THE OFFICIAL CLASS
	 */
	
	//// THESE ARE THE variables FOR THE GUI ////
	ReadNWrite write = new ReadNWrite();
	public LordPanes panes = new LordPanes();
	//public TradeWindow trade = new TradeWindow(this);//a trade window for each lord
	
	//// START OF METHODS ////
	
	public Lord(String s, String master) {
		this.name = s;
		this.master_title = master;
		for (int i=0;i<accessed_resources.length;i++)
			accessed_resources[i] = false;
	}
	
	public Panel setPanel(boolean master) {
		System.out.println("LORD! setPanel");
		int layers = 1;
		if (master)
			layers = 2;
		Panel mainPnl = new Panel(new GridLayout(layers,2));//set up panel
		mainPnl.add(panes.nationPanel(government,!master_title.equals("")));
		mainPnl.add(panes.governmentPane(government, institutes));
		if (master) {/*
			mainPnl.add(Culture.culturePane());*/
			Panel pnl4 = new Panel(new GridLayout(1,1));//setting up dummybuttons on the dummypane
			Button newVassalBtn = new Button("New Vassal");//adding buttons
			Button saveBtn = new Button("Save Nation");
			pnl4.add(saveBtn);
			mainPnl.add(pnl4);
			////BUTTON FUNCTIONALITIES////
			newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
				public void actionPerformed(ActionEvent e){
					//getter.newLord(new_lord, master);
				}
			});
			saveBtn.addActionListener(new ActionListener() {//add action event to save button
				public void actionPerformed(ActionEvent e){
					getter.saveNation();
				}
			});
		}
		return mainPnl;
	}
	
	public void getGovernment() {//getting the government tab things
		System.out.println("LORD! getGovernment");
		this.government.eco[0] = (int) Double.parseDouble(this.panes.inputs[0].getText());	
		this.government.eco[1] = (int) Double.parseDouble(this.panes.inputs[1].getText());
		this.government.eco[2] = (int) Double.parseDouble(this.panes.inputs[2].getText());
		if (!(this.master_title.equals("")))
			this.government.eco[3] = (int) Double.parseDouble(this.panes.inputs[3].getText());
		this.government.setSystem((String) this.panes.system.getSelectedItem());
		this.government.setStruc((String) this.panes.soc_structure.getSelectedItem());
		this.government.setRuler((String) this.panes.rule.getSelectedItem());
		this.government.setLifeStyle((String) this.panes.life_style.getSelectedItem());
		this.government.setCentralisation((String) this.panes.centralisation.getSelectedItem());
		this.government.culture = ((String) this.panes.culture.getSelectedItem());
		this.government.religion = ((String) this.panes.religion.getSelectedItem());
		this.government.legitimacy = (int) Double.parseDouble(this.panes.legitimacy.getText());
		for (int i=0;i<4;i++) {
			this.institutes.setInstitution((String) this.panes.institutions[i].getSelectedItem(),i);
		}
		if (this.government.sys.equals("Histocratic")){
			for (int i=0;i<4;i++) {
				this.government.histocratic_choices[i] = (String) this.panes.histocracy_choices[i].getSelectedItem();
				this.government.hist_val[i] = Integer.parseInt(this.panes.histocracy_values[i].getText());
			}
		}
		this.panes.updateGovernmentPane(government);
		//setGovernment();
	}
	
	public void loadGovernment(String[] s) {//only ever called when loading//has nothing to do with visual layer
		System.out.println("LORD! loadGovernment");
		this.government.setSystem(s[1]);
		this.government.setStruc(s[2]);
		this.government.setRuler(s[3]);
		this.government.setLifeStyle(s[4]);
		this.government.setCentralisation(s[5]);
		this.government.culture = s[6];
		this.government.religion = s[7];
		this.government.legitimacy = (int) Double.parseDouble(s[8]);
		for (int i=0;i<4;i++) {
			this.institutes.setInstitution(s[10+i],i);
		}
		this.government.eco[0] = (int) Double.parseDouble(s[14]);
		this.government.eco[1] = (int) Double.parseDouble(s[15]);
		this.government.eco[2] = (int) Double.parseDouble(s[16]);
		if (!(this.master_title.equals("")))
			this.government.eco[3] = (int) Double.parseDouble(s[17]);
		if (this.government.sys.equals("Histocratic")){
			for (int i=0;i<4;i++) {
				this.government.histocratic_choices[i] = s[18+2*i];
				this.government.hist_val[i] = Integer.parseInt(s[19+2*i]);
			}
		}
	}
	
	public void setGovernment() {//visual update of government pane
		System.out.println("LORD! setGovernment");
		this.panes.setGovernmentPane(government, institutes);
	}
	
	public void getModifiers() {//getting all modifiers from visual layers
		System.out.println("LORD! getModifiers");
		//NO!! getter layer should not communicate with
	}
	
	public void loadModifiers() {//getting all modifiers from code layers
		System.out.println("LORD! loadModifiers");
		int traderolls = 0;
		double darkwood = 1;
		double metals = 0;
		double marble = 1;
		double iron = 1;
		double stone = 1;
		for (int i=0;i<accessed_resources.length;i++) {
			if (accessed_resources[i]) {
				traderolls += 1;
				if (i==0)
					darkwood = 0.95;
				else if (i==1)
					metals = 0.02;
				else if (i==7)
					marble = 0.95;
				else if (i==11)
					iron = 0.95;
				else if (i==12)
					stone = 0.95;
				else
					traderolls += 2;
			}
		}
		this.modifiers[0] = Culture.used_bonus[0]+this.government.tax_eff*this.government.legitimacy*0.1;//taxeff
		this.modifiers[1] = Culture.used_bonus[1]+this.government.prod_eff*this.government.legitimacy*0.1;//prodeff
		this.modifiers[2] = Culture.used_bonus[2]+this.government.trade_eff*this.government.legitimacy*0.1+metals;//tradeeff
		this.modifiers[3] = Culture.used_bonus[3]+this.government.vassal_inc_eff*this.government.legitimacy*0.1;//vassaleff
		this.modifiers[4] = Culture.used_bonus[4]+this.government.plunder_eff*this.government.legitimacy*0.1;//plundereff
		this.modifiers[5] = Culture.used_bonus[5]+this.government.bank_inc_eff*this.government.legitimacy*0.1;//bank inc eff
		this.modifiers[6] = Math.min(Culture.used_bonus[5]+this.government.bank_dev_eff*this.government.legitimacy*0.1,this.government.max_bank_dev_eff);//bank dev eff
		this.modifiers[7] = 0;//material inc eff
		this.modifiers[8] = Culture.used_bonus[6];//hex prod mod
		this.modifiers[9] = Culture.used_bonus[7];//unit cap
		this.modifiers[10] = Culture.used_bonus[8];//speed
		this.modifiers[11] = Culture.used_bonus[9];//dmg
		this.modifiers[12] = Culture.used_bonus[10];//hit
		this.modifiers[13] = Culture.used_bonus[11];//ac
		this.modifiers[14] = Culture.used_bonus[12];//morale
		this.modifiers[15] = Culture.used_bonus[13];//command
		this.modifiers[16] = Culture.used_bonus[14];//ranged hit
		this.modifiers[17] = Culture.used_bonus[15];//legitimacy
		this.modifiers[18] = Culture.used_bonus[16];//unrest
		this.modifiers[19] = traderolls;//trade rolls
		this.modifiers[20] = Culture.used_bonus[17];//rgo/road
		this.modifiers[21] = Culture.used_bonus[18]*darkwood;//building
		this.modifiers[22] = Culture.used_bonus[19];//settlement upkeep
		this.modifiers[23] = Culture.used_bonus[20];//settlement cost
		this.modifiers[24] = Culture.used_bonus[21];//unit training
		this.modifiers[25] = Culture.used_bonus[22];//unit eq
		this.modifiers[26] = Culture.used_bonus[23]*stone;//fortification
		this.modifiers[27] = marble;//palace
		this.modifiers[28] = Culture.used_bonus[24];//mage
		this.modifiers[29] = Culture.used_bonus[25];//druid
		this.modifiers[30] = Culture.used_bonus[26];//spy
		this.modifiers[31] = Culture.used_bonus[27];//tinker
		this.modifiers[32] = Culture.used_bonus[28];//guild
	}
}