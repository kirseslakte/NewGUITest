package NewGUITest;


public class Institutions {
	public int number_of_institutions;
	public String[] institution_names = { "Arts","Church","Courts","Druids","Education","Foreign Office","Interior","Mages","Merchants",
			"Military","Monstrologist","Parliament","Psions","Secret Service","Slavery","Tinkers","Treasury","Welfare"};
	public Institutions() {//whenever an object is created from this class we reset all the modifiers from them
		resetModifiers();
	}
	
	public void findInstitution(String s) {//input is institute and it executes that institue method
		if (s==institution_names[0])
			arts();
		else if (s==institution_names[1])
			church();
		else if (s==institution_names[2])
			courts();
		else if (s==institution_names[3])
			druids();
		else if (s==institution_names[4])
			education();
		else if (s==institution_names[5])
			foreignOffice();
		else if (s==institution_names[6])
			interior();
		else if (s==institution_names[7])
			mages();
		else if (s==institution_names[8])
			merchants();
		else if (s==institution_names[9])
			military();
		else if (s==institution_names[10])
			monstrologist();
		else if (s==institution_names[11])
			parliament();
		else if (s==institution_names[12])
			psions();
		else if (s==institution_names[13])
			secretService();
		else if (s==institution_names[14])
			slavery();
		else if (s==institution_names[15])
			tinkers();
		else if (s==institution_names[16])
			treasury();
		else if (s==institution_names[17])
			welfare();
		else
			//nothing
		
		resetModifiers();
	}	
	public void resetModifiers() {//resets all the modifiers from insitutions
		
	}
	public void arts() {
		//bard lvl 1 in all possible hexes (not paying for that tho)
		String name = institution_names[0];
	}
	public void church() {
		//church lvl 1 in all possible hexes (not paying for that tho)
	}
	public void courts() {
		//1 free unrest-lowering official, can generate lawyer officials, -3 DC unrest checks
	}
	public void druids() {
		//druid lvl 1 in all possible hexes (not paying for that tho)
	}
	public void education() {
		//education lvl 1 in all possible hexes (not paying for that tho)
	}
	public void foreignOffice() {
		//1 free diplomatic related official, can generate diplomatic officials, +4 on relevant diplomatic official skill
	}
	public void interior() {
		//requires official
		//0.5 mod on troop movement in own territory, 0.9 rgo/roads cost mod, +15% trade eff, +0.025 base production mod
	}
	public void mages() {
		//arecane lvl 1 in all possible hexes (not paying for that tho)
	}
	public void merchants() {
		//1 free trade-related official, can generate trade officials, +20% trade eff
	}
	public void military() {
		//martial lvl 1 in all possible hexes (not paying for that tho)
	}
	public void monstrologist() {
		//monstrologist lvl 1 in all possible hexes (not paying for that tho)
	}
	public void parliament() {
		//parliament can generate officials, grant 1 extra official slot for specific task (Parliament Action)
		//can decrease impact on unrest from diplomatic or political actions
	}
	public void psions() {
		//psion lvl 1 in all possible hexes (not paying for that tho)
	}
	public void secretService() {
		//rogue lvl 1 in all possible hexes (not paying for that tho)
	}
	public void slavery() {
		//+10% plunder eff, -2 on unrest checks, 0.95 mod on cost of rgo/road/settlement/buildings, slave units have a cost mod of
		//0.5 instead of 0.75, slave units have +1 Command and Morale
	}
	public void tinkers() {
		//tinker lvl 1 in all possible hexes (not paying for that tho)
	}
	public void treasury() {
		//1 free banking official, can generate tax/bank officials, +20% bank income eff
	}
	public void welfare() {
		//requires official
		//+3 on unrest checks, 0.95 settlement cost mod, +0.05 base production mod
	}
}