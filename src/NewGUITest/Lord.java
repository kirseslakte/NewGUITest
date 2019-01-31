package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lord {
	
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
	public String master_title;
	public String title;
	public boolean[] accessed_resources = new boolean[Hex.passive_resources.length];//darkwood->Drugs
	public Institutions institutes = new Institutions();
	public Governments government = new Governments();
	public OfficialWindow official = new OfficialWindow();
	public RouteWindow route = new RouteWindow();
	public double[] modifiers = new double[33];
	public static String[] mod_names =  {"Tax Efficiency","Production Efficiency","Trade Efficiency","Vassal Tax Income Efficiency","Plunder Efficinecy","Bank Income Efficiency",
			"Bank Development Efficiency","Rare Resource Income","Unit Speed","Unit Damage","Hit","AC","Morale","Command","Ranged Hit","Unit Cap","Legitimacy Checks","Unrest Checks",
			"Trade Rolls","Base Production","RGO/Road Cost","Building Cost","Settlement Upkeep Cost","Settlement Upgrade Cost","Unit Training Cost","Unit Equipment Cost",
			"Fortification Cost","Palace Build Cost","Mage Guild Cost","Druid Guild Cost","Spy Guild Cost","Tinker Guild Cost","All Guilds Cost"};
	public static double[] base_eff = {0.20,0.20,0.20,0.20,0.20,0.20};//tax,prod,trade,bank inc,bank dev,vassal inc
	public static double[] gov_base_eff = {0.70,0.40,0.70,0.70,0.20,0.70};//tax,prod,trade,bank inc,bank dev,vassal inc
	public int number_officials = 0;//purely used to determine the government upkeep of a lord
	public int[] official_jobs = new int[5];//trade,bank,tax,welfare,interior
	public int own_bp=0,total_bp=0,total_trade_value=0,overlord_tax=0,population=0,province_inc=0,province_upk=0,development=0,
			guild_upk=0,government_upk=0,army_upk=0,total_inc=0,total_upk=0,vassal_inc=0;
	public double[] govnm_upk_mod = new double[2];//static+scaling upk mod for gov
	
	//// THESE ARE THE variables FOR THE GUI ////
	public LordPanes panes = new LordPanes();
	public Panel mainPnl;
	
	//// START OF METHODS ////
	
	public Lord(String s, String master) {
		//System.out.println("creating lord "+s);
		this.name = s;
		this.master_title = master;
		for (int i=0;i<this.accessed_resources.length;i++)
			this.accessed_resources[i] = false;
		this.official.initialize(this);
		this.route.initialize(this);
	}
	
	public Panel setPanel(boolean master) {
		//System.out.println("LORD! setPanel");
		mainPnl = new Panel(new GridBagLayout());//set up panel
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridy = 0;
		c.gridx = 0;
		mainPnl.add(this.panes.nationPane(this.government,!this.master_title.equals("")),c);
		c.gridx = 1;
		mainPnl.add(this.panes.governmentPane(this.government,this.institutes),c);
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.05;
		mainPnl.add(this.panes.buttonPane(this.name),c);
		return mainPnl;
	}
	
	public void getGovernment() {//getting the government tab things
		//System.out.println("LORD! getGovernment");
		//for (int i=0;i<this.panes.inputs.length;i++)
			//this.government.eco[i] = (int) Integer.parseInt(this.panes.inputs[0].getText());
		//this.army_upk = this.government.eco[0];
		//this.guild_upk = this.government.eco[1];
		//this.government.eco[0] = (int) Double.parseDouble(this.panes.inputs[0].getText());	
		//this.government.eco[1] = (int) Double.parseDouble(this.panes.inputs[1].getText());
		//this.government.eco[2] = (int) Double.parseDouble(this.panes.inputs[2].getText());
		//if (!(this.master_title.equals("")))
			//this.government.eco[3] = (int) Double.parseDouble(this.panes.inputs[3].getText());
		String[] gov = new String[5];
		//this.alignment = (String) this.panes.culture.getSelectedItem();
		//this.religion = (String) this.panes.religion.getSelectedItem();
		gov[0] = (String) this.panes.system.getSelectedItem();
		gov[1] = (String) this.panes.soc_structure.getSelectedItem();
		gov[2] = (String) this.panes.rule.getSelectedItem();
		gov[3] = (String) this.panes.life_style.getSelectedItem();
		gov[4] = (String) this.panes.centralisation.getSelectedItem();
		this.government.setGovernment(gov);
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
		this.panes.updateGovernmentPane(this.government);
		//setGovernment();
	}
	
	public void loadGovernment(String[] s) {//only ever called when loading//has nothing to do with visual layer
		//System.out.println("LORD! loadGovernment");
		String[] gov = new String[5];
		for (int i=0;i<gov.length;i++)
			gov[i] = s[1+i];
		this.government.setGovernment(gov);
		this.government.culture = s[6];
		this.government.religion = s[7];
		this.government.legitimacy = (int) Double.parseDouble(s[8]);
		for (int i=0;i<4;i++) {
			this.institutes.setInstitution(s[10+i],i);
		}
		for (int i=0;i<this.government.eco.length;i++)
			this.government.eco[i] = (int) Double.parseDouble(s[14+i]);
		if (this.government.sys.equals("Histocratic")){
			for (int i=0;i<4;i++) {
				this.government.histocratic_choices[i] = s[20+2*i];
				this.government.hist_val[i] = Integer.parseInt(s[21+2*i]);
			}
		}
	}
	
	public void setGovernment() {//visual update of government pane
		//System.out.println("LORD! setGovernment");
		this.panes.setGovernmentPane(this.government, this.institutes);
	}
	
	public void updateLord() {
		//System.out.println("LORD! updateLord");
		if (this.title.equals("overlord"))
			Culture.getCulture();
		this.getOfficials();
		this.institutes.updateInstitutions(this.official_jobs[3]==1,this.official_jobs[4]==1);
		this.getGovernment();//resets the government modifiers so that new can be applied without click-stacking
		this.panes.updateNationPane(Utility.findLord(this.name));
		this.army_upk = this.government.eco[0];
		this.guild_upk = this.government.eco[1];
		this.government.importModifiers(this.institutes.outputs);//get institution bonuses to government modifiers
		this.loadModifiers();
		this.getRoutes();
		//this.army_upk = 
		this.getHexes();
		this.total_inc = (int) Math.round(this.total_trade_value*this.modifiers[2]+this.province_inc*(1-this.government.eco[5]*0.01)+this.vassal_inc);
		this.panes.updateNationPane(Utility.findLord(this.name));
	}
	
	public void getOfficials() {
		this.number_officials = this.official.listofofficials.size();
		for (int i=0;i<this.official_jobs.length;i++)
			this.official_jobs[i] = 0;
		for (Official o:this.official.listofofficials) {
			if (o.free)
				this.number_officials--;//reduce the number of officials with the number of free officials
			if (o.job.equals("Maintain Trade Route"))
				this.official_jobs[0] += o.effect;
			else if (o.job.equals("Bank Income"))
				this.official_jobs[1] = o.effect;
			else if (o.job.equals("Collect Taxes"))
				this.official_jobs[2] = o.effect;
			else if (o.job.equals("Interior"))
				this.official_jobs[3] = 1;
			else if (o.job.equals("Welfare"))
				this.official_jobs[4] = 1;
		}
		this.govnm_upk_mod[0] = 10*Math.pow(1.5,(this.number_officials));
		this.govnm_upk_mod[1] = 2*Math.pow((this.number_officials),1.5)*0.01;
	}
	
	public void getHexes() {
		this.total_bp = 0;
		this.own_bp = 0;
		this.development = 0;
		this.population = 0;
		this.government_upk = 0;
		this.province_inc = 0;
		this.province_upk = 0;
		this.total_upk = 0;
		this.vassal_inc = 0;
		this.total_inc = 0;
		int vassal_bp = 0;
		double vassal_gov = 0;
		double vassal_income = 0;
		try {
			for (Hex hex:NationHandler.listofhexes) {
				if (hex.owner.equals(this.name)) {
					this.own_bp += hex.base_production;
					this.population += hex.population_value;
					this.government_upk += hex.govnm_upkeep;
					this.province_upk += hex.upkeep;
				}
			}
			this.province_inc = (int) Math.round(this.government.eco[4]*0.01*this.own_bp*this.modifiers[0]);
			this.development = (int) Math.round((1-this.government.eco[4]*0.01)*this.own_bp*this.modifiers[1]);
			if (this.title.equals("overlord")) {
				for (Lord lord:NationHandler.listoflords) {
					if (!(lord.title.equals("overlord"))) {
						vassal_bp += lord.total_bp;
						vassal_gov += lord.government_upk*lord.government.eco[5]*0.01;
						vassal_income += lord.province_inc*lord.government.eco[5]*0.01*this.modifiers[3];
					}
				}
			}
			this.vassal_inc = (int) Math.round(vassal_income);
			this.government_upk += vassal_gov;
			this.government_upk = (int) Math.round(this.government_upk*this.govnm_upk_mod[1]+this.govnm_upk_mod[0]);
			this.total_bp = this.own_bp+vassal_bp;
			this.total_upk = this.government_upk+this.province_upk+this.guild_upk+this.army_upk;
			this.total_inc = (int) Math.round(this.total_trade_value*this.modifiers[2]+
					this.vassal_inc+this.province_inc*(1-this.government.eco[5]*0.01));
		} catch (NullPointerException e) {
			//this is just initializing
			System.out.println("Hex-fetching for lord "+this.name+" failed");
		}
	}
	
	public void getRoutes() {
		this.total_trade_value = 0;
		double holder = 0;
		for (Route r:this.route.listofroutes)
			holder += r.trade_value;
		this.total_trade_value = (int) Math.round(holder);
	}
	public void loadModifiers() {//getting all modifiers from code layers
		//System.out.println("LORD! loadModifiers");
		int traderolls = 0;
		double darkwood = 1;
		double metals = 0;
		double marble = 1;
		double iron = 1;
		double stone = 1;
		for (int i=0;i<this.accessed_resources.length;i++) {
			if (this.accessed_resources[i]) {
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
		}//NEED TO ADD A BUNCH OF THINGS FROM GOVERNMENT THAT ISN'T IN HERE!!
		this.modifiers[0] = Culture.used_bonus[0]+base_eff[0]+(gov_base_eff[0]+this.government.tax_eff+this.official_jobs[2]*0.01)*this.government.legitimacy*0.1;//taxeff
		this.modifiers[1] = Culture.used_bonus[1]+base_eff[1]+(gov_base_eff[1]+this.government.prod_eff)*this.government.legitimacy*0.1;//prodeff
		this.modifiers[2] = Culture.used_bonus[2]+base_eff[2]+(gov_base_eff[2]+this.government.trade_eff)*this.government.legitimacy*0.1+metals;//tradeeff
		this.modifiers[3] = Culture.used_bonus[3]+base_eff[5]+(gov_base_eff[5]+this.government.vassal_inc_eff)*this.government.legitimacy*0.1;//vassaleff
		this.modifiers[4] = Culture.used_bonus[4]+this.government.plunder_eff*this.government.legitimacy*0.1;//plundereff
		this.modifiers[5] = Culture.used_bonus[5]+base_eff[3]+(gov_base_eff[3]+this.government.bank_inc_eff+this.official_jobs[1]*0.01)*this.government.legitimacy*0.1;//bank inc eff
		this.modifiers[6] = Math.min(Culture.used_bonus[5]+base_eff[4]+(gov_base_eff[4]+this.government.bank_dev_eff)*this.government.legitimacy*0.1,
				this.government.max_bank_dev_eff);//bank dev eff
		this.modifiers[7] = 0;//material inc eff
		this.modifiers[8] = Culture.used_bonus[6]+this.institutes.outputs[3]*this.government.legitimacy*0.1;//hex prod mod
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
		this.modifiers[20] = Culture.used_bonus[17]*this.institutes.outputs[5]*this.government.legitimacy*0.1;//rgo/road
		this.modifiers[21] = Culture.used_bonus[18]*darkwood;//building
		this.modifiers[22] = Culture.used_bonus[19];//settlement upkeep
		this.modifiers[23] = Culture.used_bonus[20];//settlement cost
		this.modifiers[24] = Culture.used_bonus[21];//unit training
		this.modifiers[25] = Culture.used_bonus[22]*iron;//unit eq
		this.modifiers[26] = Culture.used_bonus[23]*stone;//fortification
		this.modifiers[27] = marble;//palace
		this.modifiers[28] = Culture.used_bonus[24];//mage
		this.modifiers[29] = Culture.used_bonus[25];//druid
		this.modifiers[30] = Culture.used_bonus[26];//spy
		this.modifiers[31] = Culture.used_bonus[27];//tinker
		this.modifiers[32] = Culture.used_bonus[28];//guild
	}
}