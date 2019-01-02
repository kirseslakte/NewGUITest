package NewGUITest;


public class Governments {
	public String[] systems = {"Democratic","Autocratic","Meritocratic","Theocratic","Histocratic","Plutocratic","Hive"
			,"Federation"};
	public String[] strucs = {"Classisist","Individualistic"};
	public String[] rule = {"Monarchy","Oligarchy","Polyarchy"};
	public String[] life = {"Settled","Tribalistic","Nomadic"};
	public String[] centralisation = {"Highly","Moderately","Decentralised"};
	public String[] alignments = {"LG","NG","CG","LN","NN","CN","LE","NE","CE"};
	public String[] histocratic_options = {"Tax Efficiency","Production Efficiency","Trade Efficiency","Vassal Income Efficiency",
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
	public double[] hist_val = {0,0,0,0};
	boolean histocracy_fault;
	double total_val = 0.4;
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
	int move_pop_mod = 1;
	double unit_cap_mod = 1;
	double max_bank_dev_eff = 0;
	int min_unrest = 0;
	double max_tax_rate = 0;
	double[] eco = {0,0,0,0};
	
	public Governments() {
		
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
		this.max_bank_dev_eff = 0;
		this.min_unrest = 0;
		this.max_tax_rate = 0;
		this.histocracy_fault = false;
	}
		//START SYSTEMS!!
	public void setSystem(String s) {//a method which sets the system of governance
		if (s==systems[0]){
			this.setDemocratic();
		}else if (s==systems[1]){
			this.setAutocratic();
		}else if (s==systems[2]){
			this.setMeritocratic();
		}else if (s==systems[3]){
			this.setTheocratic();
		}else if (s==systems[4]){
			this.setHistocratic();
		}else if (s==systems[5]){
			this.setPlutocratic();
		}else if (s==systems[6]){
			this.setHive();
		}else if (s==systems[7]){
			this.setFederation();
		}
	}
	
	public void setDemocratic() {//modifiers for the democratic system
		this.sys = systems[0];
		this.settlement_cost_mod *= 0.9;
		this.tax_eff += 0.1;
		this.prod_eff += 0.1;
		this.bank_inc_eff += 0.2;
		this.bank_dev_eff += 0.2;
		this.trade_eff += 0.2;
	}
	public void setAutocratic() {//modifiers for the autocratic system
		this.sys = systems[1];
		this.unit_training_cost_mod *= 0.95;
		this.tax_eff += 0.1;
		this.bank_inc_eff += 0.2;
		this.vassal_inc_eff += 0.2;
		this.trade_eff += 0.2;
	}
	public void setMeritocratic() {//modifiers for the meritocratic system
		this.sys = systems[2];
		this.tax_eff += 0.1;
		this.prod_eff += 0.3;
		this.bank_dev_eff += 0.2;
	}
	public void setTheocratic() {//modifiers for the theocratic system
		this.sys = systems[3];
		this.tax_eff += 0.2;
		this.prod_eff += 0.1;
		this.bank_inc_eff += 0.2;
		this.vassal_inc_eff += 0.1;
	}
	public void setHistocratic(){//modifiers for the histocratic system
		this.sys = systems[4];
		double value_checker = 0;
		for (int i=0;i<4;i++) {
			if (this.histocratic_choices[i].equals(histocratic_options[0])) {
				this.tax_eff += this.hist_val[i];
				value_checker += this.hist_val[i];
			}else if(this.histocratic_choices[i].equals(histocratic_options[1])) {
				this.prod_eff += this.hist_val[i];
				value_checker += this.hist_val[i];
			}else if(this.histocratic_choices[i].equals(histocratic_options[2])) {
				this.trade_eff += this.hist_val[i];
				value_checker += this.hist_val[i];
			}else if(this.histocratic_choices[i].equals(histocratic_options[3])) {
				this.vassal_inc_eff += this.hist_val[i];
				value_checker += this.hist_val[i];
			}else if(this.histocratic_choices[i].equals(histocratic_options[4])) {
				this.bank_inc_eff += this.hist_val[i];
				value_checker += this.hist_val[i]/2;
			}else if(this.histocratic_choices[i].equals(histocratic_options[5])) {
				this.bank_dev_eff += this.hist_val[i];
				value_checker += this.hist_val[i]/2;
			}
		}
		if (value_checker==total_val)
			this.histocracy_fault = false;
		else
			this.histocracy_fault = true;
	}
	public void setPlutocratic() {//modifiers for the plutocratic system
		this.sys = systems[5];
		this.tax_eff += 0.1;
		this.prod_eff += 0.1;
		this.bank_dev_eff += 0.2;
		this.trade_eff += 0.4;
	}
	public void setHive() {//modifiers for the hive system
		this.sys = systems[6];
		this.max_tax_rate = 1;
	}
	public void setFederation() {//modifiers for the federation system
		this.sys = systems[7];
		this.bank_inc_eff += 0.2;
		this.vassal_inc_eff += 0.3;
		this.trade_eff += 0.2;
	}
	//STOP SYSTEMS!!
	//START SOC STRUC!!
	public void setStruc(String s) {
		if (s==strucs[0]){
			this.setClassisist();
		}else if(s==strucs[1]){
			this.setIndividualistic();
		}
	}
	public void setClassisist() {
		this.struc = strucs[0];
	}
	public void setIndividualistic() {
		this.struc = strucs[1];
		this.min_unrest = -1;
	}
	//STOP SOC STRUC!!
	//START RULER!!
	public void setRuler(String s) {
		if (s==rule[0]){
			this.setMonarchy();
		}else if(s==rule[1]){
			this.setOligarchy();
		}else if(s==rule[2]){
			this.setPolyarchy();
		}
	}
	public void setMonarchy() {
		this.ruler = rule[0];
	}
	public void setOligarchy() {
		this.ruler = rule[1];
	}
	public void setPolyarchy() {
		this.ruler = rule[2];
	}
	//STOP RULER!!
	//START LIFE STYLE!!
	public void setLifeStyle(String s) {
		if (s==life[0]){
			this.setSettled();
		}else if(s==life[1]){
			this.setTribalistic();
		}else if(s==life[2]){
			this.setNomadic();
		}
	}
	public void setSettled() {
		this.style = life[0];
		this.max_bank_dev_eff = 10;
		this.bank_dev_eff += 0.1;
		this.max_pop_size = 10;
		this.move_pop_mod *= 16;
		this.plunder_eff = 0.25;
	}
	public void setTribalistic() {
		this.style = life[1];
		this.max_bank_dev_eff = 0.5;
		this.max_pop_size = 6;
		this.move_pop_mod *= 4;
		this.plunder_eff += 0.5;
	}
	public void setNomadic() {
		this.style = life[2];
		this.max_bank_dev_eff = 0.3;
		this.unit_training_cost_mod *= 0.9;
		this.unit_cap_mod *= 0.9;
		this.max_pop_size = 4;
		this.move_pop_mod *= 1;
		this.plunder_eff += 0.75;
	}
	//STOP LIFE STYLE!!
	//START CENTRALISATION!!
	public void setCentralisation(String s) {
		if (s==centralisation[0]){
			this.setHighly();
		}else if(s==centralisation[1]){
			this.setModerately();
		}else if(s==centralisation[2]){
			this.setDecentralised();
		}
	}
	public void setHighly() {
		this.cent = centralisation[0];
		this.prod_eff += -0.1;
		this.max_tax_rate = Math.max(max_tax_rate,0.7);
	}
	public void setModerately() {
		this.cent = centralisation[1];
		this.prod_eff += -0.5;
		this.max_tax_rate = Math.max(max_tax_rate,0.5);
	}
	public void setDecentralised() {
		this.cent = centralisation[2];
		this.max_tax_rate = Math.max(max_tax_rate,0.3);
	}
	//STOP CENTRALISATION!!
}