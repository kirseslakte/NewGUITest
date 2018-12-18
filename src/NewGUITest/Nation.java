package NewGUITest;


public class Nation {
	
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
	public boolean is_vassal;
	public int vassalage_level;
	
	public String[] institutions;//institutions
	
	public double[] culture_bonuses;//culture bonuses
	public String[] culture_bonus_names = {"Unit Training Cost","Unit Equipment Cost","Unit Cap",
			"Undead Unit Cap","Hit Modifier","AC Modifier","Morale Modifier","Command Modifier",
			"Ranged Hit Modifier","Fortification Cost","Settlement Cost","Settlement Upgrade",
			"Base Production Modifier","Production Efficiency","Tax Efficiency","Banking Efficiency",
			"Trade Efficiency","Vassal Income Efficiency","Magic Guild Cost","Tinker Guild Cost",
			"Spy Guild Cost","All Guild Cost"};

	public double[] eco;//economy
	/* key for eco:
	 * extra officials								0
	 * taxation level								1
	 * banked rp									2
	 * banked dev									3
	 * bank income roll								4	OFFICIAL
	 * tax collect roll								5	OFFICIAL
	 * muster/train troops roll						6	OFFICIAL
	 * build guild/building/settlement roll			7	OFFICIAL
	 * command army roll							8	OFFICIAL
	 * trade negotiator skill						9	OFFICIAL
	 * #if (is_vassal)->tax from overlord			10
	 */
	
	public Nation(String s) {
		name = s;
	}
	
	public void setGovernment(String[] s) {			//requires following input
		Governments govn = new Governments();
		sys = s[0];									//system
		society = s[1];								//societal structure
		rule = s[2];								//ruler
		life = s[3];								//life style
		cent = s[4];								//centralisation level
		alignment = s[5];							//alignment
		religion = s[6];							//religion
		ruler_name = s[7];							//ruler name
		legitimacy = Integer.parseInt(s[8]);		//legitimacy
		vassalage_level = Integer.parseInt(s[9]);	//level of vassalage
		if (vassalage_level == 0)
			is_vassal = false;
		else
			is_vassal = true;
		govn.setNull();
		govn.setSystem(sys);
		govn.setStruc(society);
		govn.setRuler(rule);
		govn.setLifeStyle(life);
		govn.setCentralisation(cent);
	}
	
	public void setInstitutions(String[] in_institutions) {
		for (String s: in_institutions) {
			//check which institutions player wants
		}
	}
	
	public class official {
		public String name;
		public String type;
		public int roll;
		
		
	}
	
	public void setCultureBonuses(double[] input_bonuses) {
		culture_bonuses = input_bonuses;
	}
	
	public void setEconomyAndOfficials(double[] e) {
		eco = e;
	}
}