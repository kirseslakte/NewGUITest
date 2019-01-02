package NewGUITest;


public class Institutions {
	public int number_of_institutions;
	public String[] active_institutions = new String[4];
	public String[] institution_names = { "","Arts","Church","Courts","Druids","Education","Foreign Office","Interior","Mages","Merchants",
			"Military","Monstrologist","Parliament","Psions","Secret Service","Slavery","Tinkers","Treasury","Welfare"};
	public Institutions() {//whenever an object is created from this class we reset all the modifiers from them
		resetModifiers();
	}
	
	public void setInstitution(String s,int i) {//input is institute and it executes that institue method
		if (s.equals(institution_names[1]))
			arts(i);
		else if (s.equals(institution_names[2]))
			church(i);
		else if (s.equals(institution_names[3]))
			courts(i);
		else if (s.equals(institution_names[4]))
			druids(i);
		else if (s.equals(institution_names[5]))
			education(i);
		else if (s.equals(institution_names[6]))
			foreignOffice(i);
		else if (s.equals(institution_names[7]))
			interior(i);
		else if (s.equals(institution_names[8]))
			mages(i);
		else if (s.equals(institution_names[9]))
			merchants(i);
		else if (s.equals(institution_names[10]))
			military(i);
		else if (s.equals(institution_names[11]))
			monstrologist(i);
		else if (s.equals(institution_names[12]))
			parliament(i);
		else if (s.equals(institution_names[13]))
			psions(i);
		else if (s.equals(institution_names[14]))
			secretService(i);
		else if (s.equals(institution_names[15]))
			slavery(i);
		else if (s.equals(institution_names[16]))
			tinkers(i);
		else if (s.equals(institution_names[17]))
			treasury(i);
		else if (s.equals(institution_names[18]))
			welfare(i);
	}	
	public void resetModifiers() {//resets all the modifiers from insitutions
		for (int i=0;i<active_institutions.length;i++)
			active_institutions[i] = "";
	}
	public void arts(int i) {
		//bard lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[1];
	}
	public void church(int i) {
		//church lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[2];
	}
	public void courts(int i) {
		//1 free unrest-lowering official, can generate lawyer officials, -3 DC unrest checks
		this.active_institutions[i] = institution_names[3];
	}
	public void druids(int i) {
		//druid lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[4];
	}
	public void education(int i) {
		//education lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[5];
	}
	public void foreignOffice(int i) {
		//1 free diplomatic related official, can generate diplomatic officials, +4 on relevant diplomatic official skill
		this.active_institutions[i] = institution_names[6];
	}
	public void interior(int i) {
		//requires official
		//0.5 mod on troop movement in own territory, 0.9 rgo/roads cost mod, +15% trade eff, +0.025 base production mod
		this.active_institutions[i] = institution_names[7];
	}
	public void mages(int i) {
		//arecane lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[8];
	}
	public void merchants(int i) {
		//1 free trade-related official, can generate trade officials, +20% trade eff
		this.active_institutions[i] = institution_names[9];
	}
	public void military(int i) {
		//martial lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[10];
	}
	public void monstrologist(int i) {
		//monstrologist lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[11];
	}
	public void parliament(int i) {
		//parliament can generate officials, grant 1 extra official slot for specific task (Parliament Action)
		//can decrease impact on unrest from diplomatic or political actions
		this.active_institutions[i] = institution_names[12];
	}
	public void psions(int i) {
		//psion lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[13];
	}
	public void secretService(int i) {
		//rogue lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[14];
	}
	public void slavery(int i) {
		//+10% plunder eff, -2 on unrest checks, 0.95 mod on cost of rgo/road/settlement/buildings, slave units have a cost mod of
		//0.5 instead of 0.75, slave units have +1 Command and Morale
		this.active_institutions[i] = institution_names[15];
	}
	public void tinkers(int i) {
		//tinker lvl 1 in all possible hexes (not paying for that tho)
		this.active_institutions[i] = institution_names[16];
	}
	public void treasury(int i) {
		//1 free banking official, can generate tax/bank officials, +20% bank income eff
		this.active_institutions[i] = institution_names[17];
	}
	public void welfare(int i) {
		//requires official
		//+3 on unrest checks, 0.95 settlement cost mod, +0.05 base production mod
		this.active_institutions[i] = institution_names[18];
	}
}