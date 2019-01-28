package NewGUITest;


public class Governments {
	public static String[] systems = {"Democratic","Autocratic","Meritocratic","Theocratic","Histocratic","Plutocratic","Hive"
			,"Federation"};
	public static String[] strucs = {"Classisist","Individualistic"};
	public static String[] rule = {"Monarchy","Oligarchy","Polyarchy"};
	public static String[] life = {"Settled","Tribalistic","Nomadic"};
	public static String[] centralisation = {"Highly","Moderately","Decentralised"};
	public static String[] alignments = {"LG","NG","CG","LN","NN","CN","LE","NE","CE"};
	public static String[] histocratic_options = {"Tax Efficiency","Production Efficiency","Trade Efficiency","Vassal Income Efficiency","Plunder Efficiency",
			"Bank Income Efficiency","Bank Development Efficiency"};//6 options
	//If you ever want to add anything above, just add it at the end and 
	//look in the appropriate section below to add the method for it!
	public String sys = "";
	public String struc = "";
	public String ruler = "";
	public String style = "";
	public String cent = "";
	public String culture = "";
	public String religion = "";
	int legitimacy = 0;
	public String[] histocratic_choices = {"","","",""};
	public int[] hist_val = {0,0,0,0};
	int total_val = 40;
	double settlement_cost_mod = 1;
	double unit_training_cost_mod = 1;
	double tax_eff = 0;
	double prod_eff = 0;
	double trade_eff = 0;
	double vassal_inc_eff = 0;
	double bank_inc_eff = 0;
	double bank_dev_eff = 0;
	double plunder_eff = 0;
	double max_pop_size = 0;
	double build_cost = 1;
	int move_pop_mod = 1;
	double unit_cap_mod = 1;
	double max_bank_dev_eff = 10;
	int min_unrest = 0;
	double max_tax_rate = 0;
	int[] eco = {0,0,0,0,0,0};//army,guild,rp,dev,tax,overlord tax
	
	public Governments() {
		
	}
	
	public void setGovernment(String[] s) {
		this.setNull();
		this.setSystem(s[0]);
		this.setStruc(s[1]);
		this.setRuler(s[2]);
		this.setLifeStyle(s[3]);
		this.setCentralisation(s[4]);
	}
	
	public void importModifiers(double[] d) {//should be reset before this is called
		//institute modifiers
		//input is trade/bank inc/plunder/hpm/settlement cost/road,rgo/building cost
		this.trade_eff += d[0];
		this.bank_inc_eff += d[1];
		this.plunder_eff += d[2];
		this.settlement_cost_mod *= d[4];
		this.build_cost *= d[6];
	}
	
	public void setNull() {//reset everything!
		this.sys = "";
		this.struc = "";
		this.ruler = "";
		this.style = "";
		this.cent = "";
		this.culture = "";
		this.religion = "";
		this.settlement_cost_mod = 1;
		this.unit_training_cost_mod = 1;
		this.tax_eff = 0;
		this.prod_eff = 0;
		this.trade_eff = 0;
		this.vassal_inc_eff = 0;
		this.bank_inc_eff = 0;
		this.bank_dev_eff = 0;
		this.plunder_eff = 0;
		this.max_pop_size = 0;
		this.move_pop_mod = 1;
		this.unit_cap_mod = 1;
		this.build_cost = 1;
		this.max_bank_dev_eff = 10;
		this.min_unrest = 0;
		this.max_tax_rate = 0;
	}
		//START SYSTEMS!!
	public void setSystem(String s) {//a method which sets the system of governance
		if (s.equals(systems[0])){
			this.setDemocratic();
		}else if (s.equals(systems[1])){
			this.setAutocratic();
		}else if (s.equals(systems[2])){
			this.setMeritocratic();
		}else if (s.equals(systems[3])){
			this.setTheocratic();
		}else if (s.equals(systems[4])){
			this.setHistocratic();
		}else if (s.equals(systems[5])){
			this.setPlutocratic();
		}else if (s.equals(systems[6])){
			this.setHive();
		}else if (s.equals(systems[7])){
			this.setFederation();
		}
	}
	
	public void setDemocratic() {//modifiers for the democratic system
		this.sys = systems[0];
		this.settlement_cost_mod = 0.9;
		this.tax_eff = 0.1;
		this.prod_eff = 0.1;
		this.bank_inc_eff = 0.2;
		this.bank_dev_eff = 0.2;
		this.trade_eff = 0.2;
	}
	public void setAutocratic() {//modifiers for the autocratic system
		this.sys = systems[1];
		this.unit_training_cost_mod = 0.95;
		this.tax_eff = 0.1;
		this.bank_inc_eff = 0.2;
		this.vassal_inc_eff = 0.2;
		this.trade_eff = 0.2;
	}
	public void setMeritocratic() {//modifiers for the meritocratic system
		this.sys = systems[2];
		this.tax_eff = 0.1;
		this.prod_eff = 0.3;
		this.bank_dev_eff = 0.2;
	}
	public void setTheocratic() {//modifiers for the theocratic system
		this.sys = systems[3];
		this.tax_eff = 0.2;
		this.prod_eff = 0.1;
		this.bank_inc_eff = 0.2;
		this.vassal_inc_eff = 0.1;
	}
	public void setHistocratic(){//modifiers for the histocratic system
		this.sys = systems[4];
		for (int i=0;i<4;i++) {
			if (this.histocratic_choices[i].equals(histocratic_options[0])) {
				this.tax_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[1])) {
				this.prod_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[2])) {
				this.trade_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[3])) {
				this.vassal_inc_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[4])) {
				this.plunder_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[5])) {
				this.bank_inc_eff = this.hist_val[i]*0.01;
			}else if(this.histocratic_choices[i].equals(histocratic_options[6])) {
				this.bank_dev_eff = this.hist_val[i]*0.01;
			}
		}
	}
	public void setPlutocratic() {//modifiers for the plutocratic system
		this.sys = systems[5];
		this.tax_eff = 0.1;
		this.prod_eff = 0.1;
		this.bank_inc_eff = 0.2;
		this.trade_eff = 0.4;
	}
	public void setHive() {//modifiers for the hive system
		this.sys = systems[6];
		this.max_tax_rate = 1;
	}
	public void setFederation() {//modifiers for the federation system
		this.sys = systems[7];
		this.bank_inc_eff = 0.2;
		this.vassal_inc_eff = 0.3;
		this.trade_eff = 0.2;
	}
	//STOP SYSTEMS!!
	//START SOC STRUC!!
	public void setStruc(String s) {
		this.struc = s;
	}
	//STOP SOC STRUC!!
	//START RULER!!
	public void setRuler(String s) {
		this.ruler = s;
	}
	//STOP RULER!!
	//START LIFE STYLE!!
	public void setLifeStyle(String s) {
		if (s.equals(life[0])){
			this.setSettled();
		}else if(s.equals(life[1])){
			this.setTribalistic();
		}else if(s.equals(life[2])){
			this.setNomadic();
		}
	}
	public void setSettled() {
		this.style = life[0];
		this.max_bank_dev_eff = 10;
		this.bank_dev_eff += 0.1;
		this.max_pop_size = 10;
		this.move_pop_mod = 16;
		this.plunder_eff += 0.25;
	}
	public void setTribalistic() {
		this.style = life[1];
		this.max_bank_dev_eff = 0.5;
		this.max_pop_size = 6;
		this.move_pop_mod = 4;
		this.plunder_eff += 0.5;
	}
	public void setNomadic() {
		this.style = life[2];
		this.max_bank_dev_eff = 0.3;
		this.unit_training_cost_mod *= 0.9;
		this.unit_cap_mod = 1.5;
		this.max_pop_size = 4;
		this.move_pop_mod = 1;
		this.plunder_eff += 0.75;
	}
	//STOP LIFE STYLE!!
	//START CENTRALISATION!!
	public void setCentralisation(String s) {
		if (s.equals(centralisation[0])){
			this.setHighly();
		}else if(s.equals(centralisation[1])){
			this.setModerately();
		}else if(s.equals(centralisation[2])){
			this.setDecentralised();
		}
	}
	public void setHighly() {
		this.cent = centralisation[0];
		this.prod_eff += -0.1;
		this.max_tax_rate = Math.max(this.max_tax_rate,0.7);
	}
	public void setModerately() {
		this.cent = centralisation[1];
		this.prod_eff += -0.5;
		this.max_tax_rate = Math.max(this.max_tax_rate,0.5);
	}
	public void setDecentralised() {
		this.cent = centralisation[2];
		this.max_tax_rate = Math.max(this.max_tax_rate,0.3);
	}
	//STOP CENTRALISATION!!
}