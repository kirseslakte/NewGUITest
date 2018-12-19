package NewGUITest;

public class Lord {
	
	public int max_number_of_institutions = 4;//rule variables
	public int number_of_culture_bonuses = 22;
	
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
	public boolean is_vassal;
	public String title;
	Institutions institutes = new Institutions();
	
	public String[] institutions;//institutions
	
	public double[] culture_bonuses;//culture bonuses (22 of them)
	public String[] culture_bonus_names = {"Unit Training Cost","Unit Equipment Cost","Unit Cap",
			"Undead Unit Cap","Hit Modifier","AC Modifier","Morale Modifier","Command Modifier",
			"Ranged Hit Modifier","Fortification Cost","Settlement Cost","Settlement Upgrade",
			"Base Production Modifier","Production Efficiency","Tax Efficiency","Banking Efficiency",
			"Trade Efficiency","Vassal Income Efficiency","Magic Guild Cost","Tinker Guild Cost",
			"Spy Guild Cost","All Guild Cost"};

	public double[] eco;//economy
	/* key for eco:
	 * extra officials								0
	 * tax rate										1
	 * banked rp									2
	 * banked dev									3	
	 * tax rate overlord (=0 if overlord)			4
	 */
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
	public Lord(String s) {
		this.name = s;
	}
	
	public void setGovernment(String[] s) {			//requires following input
		Governments govn = new Governments();
		this.sys = s[0];									//system
		this.society = s[1];								//societal structure
		this.rule = s[2];								//ruler
		this.life = s[3];								//life style
		this.cent = s[4];								//centralisation level
		this.alignment = s[5];							//alignment
		this.religion = s[6];							//religion
		this.ruler_name = s[7];							//ruler name
		this.legitimacy = Integer.parseInt(s[8]);		//legitimacy
		this.population_value = Double.parseDouble(s[9]);//population value
		this.total_bpm = Integer.parseInt(s[10]);		//total base production modifier
		this.tax_income = Integer.parseInt(s[11]);		//total tax income
		this.total_trade_value = Integer.parseInt(s[12]);//total trade value
		this.overlord_tax_rate = Double.parseDouble(s[13]);//the overlord tax rate of the current vassal
		this.title = s[14];								//codename of the lord (eg. 'overlord' 'vassal1' 'vassal2_3'
		if (title.contains("lord"))
			this.is_vassal = false;
		else{
			this.is_vassal = true;
		}
		govn.setNull();
		govn.setSystem(this.sys);
		govn.setStruc(this.society);
		govn.setRuler(this.rule);
		govn.setLifeStyle(this.life);
		govn.setCentralisation(this.cent);
	}
	
	public void setInstitutions(String[] in_institutions) {//set the institutions of the nation
		institutions = in_institutions;
		for (String s: in_institutions) {
			//check which institutions player wants
			this.institutes.findInstitution(s);
		}
	}
	
	public void setCultureBonuses(double[] input_bonuses) {//set the culture bonuses fed from front panel
		this.culture_bonuses = input_bonuses;
	}
	
	public void setEconomyAndOfficials(double[] e) {//set the economy settings fed from the front panel
		this.eco = e;
	}
	
	public Official assignOfficial(String[] s) {//create a new official object
		Official official = new Official(s);
		return official;
	}
	
	//////HERE IS THE OFFICIAL CLASS///////
	public class Official {//initializing an 'official' object where you can create 'characters'
		public String name;
		public String type;
		public int roll;
		public boolean inhex;
		public String hex;
		
		public Official(String[] s){				//requires input:
			this.name = s[0];						//name
			this.type = s[1];						//official action
			this.roll = Integer.parseInt(s[2]);		//the roll/skill/whatever value is applicable
			this.inhex = Boolean.parseBoolean(s[3]);//if the bonus is hex-specific
			if (inhex)
				this.hex = s[4];					//which hex the official is in
			else
				this.hex = "";
		}
		
		public void changeName(String s) {//methods for changing the official attributes (some might be redundant)
			this.name = s;
		}
		public void changeType(String s) {
			this.type = s;
		}
		public void changeRoll(int i) {
			this.roll = i;
		}
		public void changeInHex(boolean b) {
			this.inhex = b;
		}
		public void changeHex(String s) {
			this.hex = s;
		}
	}

}