package NewGUITest;


public class Governments {
	public String[] systems = {"Democratic","Autocratic","Meritocratic","Theocratic","Histocratic","Plutocratic","Hive","Federation"};
	public String[] strucs = {"Classisist","Individualistic"};
	public String[] rule = {"Monarchy","Oligarchy","Polyarchy"};
	public String[] life = {"Settled","Tribalistic","Nomadic"};
	public String[] centralisation = {"Highly","Moderately","Decentralised"};
	//If you ever want to add anything above, just add it at the end and look in the appropriate section below to add the method for it!
	public String sys = "";
	public String struc = "";
	public String ruler = "";
	public String style = "";
	public String cent = "";
	double total_val = 0;
	double settlement_cost_mod = 1;
	double unit_training_cost_mod = 1;
	double tax_eff = 0;
	double prod_eff = 0;
	double trade_eff = 0;
	double vassal_inc_eff = 0;
	double bank_inc_eff = 0;
	double bank_prod_eff = 0;
	double plunder_eff = 0;
	double max_pop_size = 0;
	int move_pop_mod = 1;
	double unit_cap_mod = 1;
	double max_bank_prod_eff = 0;
	int min_unrest = 0;
	double max_tax_rate = 0;
	
	public Governments() {
		
	}
	
	public void setNull() {//reset everything!
		sys = "";
		struc = "";
		ruler = "";
		style = "";
		cent = "";
		total_val = 0;
		settlement_cost_mod = 1;
		unit_training_cost_mod = 1;
		tax_eff = 0;
		prod_eff = 0;
		trade_eff = 0;
		vassal_inc_eff = 0;
		bank_inc_eff = 0;
		bank_prod_eff = 0;
		plunder_eff = 0;
		max_pop_size = 0;
		move_pop_mod = 1;
		unit_cap_mod = 1;
		max_bank_prod_eff = 0;
		min_unrest = 0;
		max_tax_rate = 0;
	}
		//START SYSTEMS!!
	public void setSystem(String s) {//a method which sets the system of governance
		if (s==systems[0]){
			setDemocratic();
		}else if (s==systems[1]){
			setAutocratic();
		}else if (s==systems[2]){
			setMeritocratic();
		}else if (s==systems[3]){
			setTheocratic();
		}else if (s==systems[4]){
			setHistocratic();
		}else if (s==systems[5]){
			setPlutocratic();
		}else if (s==systems[6]){
			setHive();
		}else if (s==systems[7]){
			setFederation();
		}
	}
	public void setDemocratic() {//modifiers for the democratic system
		sys = systems[0];
		settlement_cost_mod *= 0.9;
		tax_eff += 0.1;
		prod_eff += 0.1;
		bank_inc_eff += 0.2;
		bank_prod_eff += 0.2;
		trade_eff += 0.2;
	}
	public void setAutocratic() {//modifiers for the autocratic system
		sys = systems[1];
		unit_training_cost_mod *= 0.95;
		tax_eff += 0.1;
		bank_inc_eff += 0.2;
		vassal_inc_eff += 0.2;
		trade_eff += 0.2;
	}
	public void setMeritocratic() {//modifiers for the meritocratic system
		sys = systems[2];
		tax_eff += 0.1;
		prod_eff += 0.3;
		bank_prod_eff += 0.2;
	}
	public void setTheocratic() {//modifiers for the theocratic system
		sys = systems[3];
		tax_eff += 0.2;
		prod_eff += 0.1;
		bank_inc_eff += 0.2;
		vassal_inc_eff += 0.1;
	}
	public void setHistocratic() {//modifiers for the histocratic system
		sys = systems[4];
		total_val = 0.4;
	}
	public void setPlutocratic() {//modifiers for the plutocratic system
		sys = systems[5];
		tax_eff += 0.1;
		prod_eff += 0.1;
		bank_prod_eff += 0.2;
		trade_eff += 0.4;
	}
	public void setHive() {//modifiers for the hive system
		sys = systems[6];
		max_tax_rate = 1;
	}
	public void setFederation() {//modifiers for the federation system
		sys = systems[7];
		bank_inc_eff += 0.2;
		vassal_inc_eff += 0.3;
		trade_eff += 0.2;
	}
	//STOP SYSTEMS!!
	//START SOC STRUC!!
	public void setStruc(String s) {
		if (s==strucs[0]){
			setClassisist();
		}else if(s==strucs[1]){
			setIndividualistic();
		}
	}
	public void setClassisist() {
		struc = strucs[0];
	}
	public void setIndividualistic() {
		struc = strucs[1];
		min_unrest = -1;
	}
	//STOP SOC STRUC!!
	//START RULER!!
	public void setRuler(String s) {
		if (s==rule[0]){
			setMonarchy();
		}else if(s==rule[1]){
			setOligarchy();
		}else if(s==rule[2]){
			setPolyarchy();
		}
	}
	public void setMonarchy() {
		ruler = rule[0];
	}
	public void setOligarchy() {
		ruler = rule[1];
	}
	public void setPolyarchy() {
		ruler = rule[2];
	}
	//STOP RULER!!
	//START LIFE STYLE!!
	public void setLifeStyle(String s) {
		if (s==life[0]){
			setSettled();
		}else if(s==life[1]){
			setTribalistic();
		}else if(s==life[2]){
			setNomadic();
		}
	}
	public void setSettled() {
		style = life[0];
		max_bank_prod_eff = 10;
		bank_prod_eff += 0.1;
		max_pop_size = 10;
		move_pop_mod *= 16;
		plunder_eff = 0.25;
	}
	public void setTribalistic() {
		style = life[1];
		max_bank_prod_eff = 0.5;
		max_pop_size = 6;
		move_pop_mod *= 4;
		plunder_eff += 0.5;
	}
	public void setNomadic() {
		style = life[2];
		max_bank_prod_eff = 0.3;
		unit_training_cost_mod *= 0.9;
		unit_cap_mod *= 0.9;
		max_pop_size = 4;
		move_pop_mod *= 1;
		plunder_eff += 0.75;
	}
	//STOP LIFE STYLE!!
	//START CENTRALISATION!!
	public void setCentralisation(String s) {
		if (s==centralisation[0]){
			setHighly();
		}else if(s==centralisation[1]){
			setModerately();
		}else if(s==centralisation[2]){
			setDecentralised();
		}
	}
	public void setHighly() {
		cent = centralisation[0];
		prod_eff += -0.1;
		max_tax_rate = Math.max(max_tax_rate,0.7);
	}
	public void setModerately() {
		cent = centralisation[1];
		prod_eff += -0.5;
		max_tax_rate = Math.max(max_tax_rate,0.5);
	}
	public void setDecentralised() {
		cent = centralisation[2];
		max_tax_rate = Math.max(max_tax_rate,0.3);
	}
	//STOP CENTRALISATION!!
}