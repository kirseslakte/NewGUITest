package NewGUITest;

import java.util.Arrays;

public class Unit {
	//Inputs handled in tab
	public String name;
	public String unit_lord;
	public int number_of_units;
	//Input variables
	public Race race = new Race();
	public int[] stats = new int[6];
	public int[] stat_mods = new int[6];
	public String type;
	public String subtype;
	public String training;
	public String training_type;
	public String[] feat = new String[3];
	public String[] unit_feat = new String[3];
	public Weapon weapon1 = new Weapon();
	public Weapon weapon2 = new Weapon();
	public Weapon weapon3 = new Weapon();
	public Armour armour = new Armour();
	public Armour shield = new Armour();
	public Mount mount = new Mount();
	
	//Output variables
	
	
	//static variables
	static public String[] types = {"","Infantry","Cavalry","Aerial Infantry","Aerial Cavalry"};
	static public String[] subtypes = {"","Archer","Combat Engineer","Crusading","Garrison","Mercenary","Penal","Personal","Scout",
			"Slayer","Worker"};
	static public String[] trainings = {"","Irregular","Regular","Elite"};
	static public String[] training_types = {"","Light","Medium","Heavy"};
	static public String[] feats = {"","Crossbow Sniper","Dash","Deadly Aim","Dodge","Exotic Weapon Proficiency","Mounted Archery","Mounted Combat",
			"Power Attack","Rapid Reload","Shield Focus","Toughness","Weapon Finesse","Weapon Focus (W1)","Weapon Focus (W2)","Weapon Focus (W3)"};
	static public String[] unit_feats = {"","Brave","Disciplined","Fast","Mage-Trained","Mount Attack","Precision Drill","Skilled Defenders",
			"Terrain-Trained (Light Forest)","Terrain-Trained (Dense Forest)","Terrain-Trained (Marsh)","Terrain-Trained (Rocky)"
			,"Terrain-Trained (Glacier)","Unbreakable"};
	public static int[] stat_costs = {20,15,25,5,5,5};
	public static int[] training_costs = {60,160,260};
	public static int aerial_training_cost = 50;
	public static int[] subtype_costs = {100,200,200,200,150,50,125,100,75,-30};//150,50,125 are percentages (+50%,-50%,+25%)
	public static int[] unit_feat_costs = {20,30,50,50,150,10,20,50,30};//mount-attack(index 4) is in percentage of mount cost
	
	//unit-specific variables
	public String[] available_feats = {};
	public String[] available_unit_feats= {};
	
	public Unit() {
		
	}
	
	public void setUnit(String[] s) {
		System.out.println("UNIT! setUnit");
		this.name = s[0];
		this.race = UnitTab.listofraces.get(Utility.findRace(s[1]));
		for (int i=0;i<stats.length;i++) {
			this.stats[i] = Integer.parseInt(s[2+i]);
		}
		this.type = s[8];
		this.subtype = s[9];
		this.training = s[10];
		this.training_type = s[11];
		for (int i=0;i<feat.length;i++) {
			this.feat[i] = s[12+i];
			this.unit_feat[i] = s[15+i];
		}
		String[] weaponstring = Arrays.copyOfRange(s,18,22);
		this.weapon1.setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,23,27);
		this.weapon2.setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,28,32);
		this.weapon3.setWeapon(weaponstring);
		String[] armourstring = Arrays.copyOfRange(s,33,38);
		this.armour.setArmour(armourstring);
		armourstring = Arrays.copyOfRange(s,39,44);
		this.shield.setArmour(armourstring);
		if (!s[65].equals("no_mount")) {
			String[] mountstring = Arrays.copyOfRange(s,45,73);
			mount.setMount(mountstring);
		}
		this.unit_lord = s[74];
		this.number_of_units = Integer.parseInt(s[75]);
	}
	
	public String[] getUnit() {
		String[] unitstring = new String[76];
		//put unit into the output string
		return unitstring;
	}
	
	public void updateUnit() {
		for (int i=0;i<this.stats.length;i++)
			stat_mods[i] = (int) Math.floor((this.stats[i]-10)/2);
		
	}
	
	//weapon class
	
	public class Weapon {
		
		public String name;
		public int cost;
		public int damage_dice;
		public String type;
		public int weight;
		
		public String[] types = {"Ranged","One-Handed","Two-Handed","Light"};
		
		public Weapon() {
			
		}
		
		public void setWeapon(String[] s){
			System.out.println("UNIT! Weapon:setWeapon");
			this.name = s[0];
			this.cost = Integer.parseInt(s[1]);
			this.damage_dice = Integer.parseInt(s[2]);
			this.type = s[3];
			this.weight = Integer.parseInt(s[4]);
		}
	}
	
	//armour class
	
	public class Armour {
		
		public String name;
		public int cost;
		public int max_dex;
		public int ac;
		public String type;
		public int weight;
		
		public String[] armourtypes = {"Light","Medium","Heavy"};
		public String[] shieldtypes = {"Light","Heavy","Tower Shield"};
		
		public Armour() {
			
		}
		
		public void setArmour(String[] s) {
			System.out.println("UNIT! Armour:setArmour");
			this.name = s[0];
			this.cost = Integer.parseInt(s[1]);
			this.max_dex = Integer.parseInt(s[2]);
			this.ac = Integer.parseInt(s[3]);
			this.type = s[4];
			this.weight = Integer.parseInt(s[5]);
		}
	}
	
	//mount class
	
	public class Mount {
		
		public String name;
		public String type;
		public String hd_type;
		public int[] stats = new int[6];
		public boolean[] stats_used = new boolean[6];
		public String size;
		public int natural_armour;
		public int number_of_hd;
		public int damage_dice;
		public int number_of_attacks;
		public int cost_of_one_mount;
		public String footing;
		public int base_speed;
		public Armour mount_armour = new Armour();
		
		public Mount() {
			
		}
		
		public void setMount(String[] s) {
			System.out.println("UNIT! Mount:setMount");
			this.name = s[0];
			this.type = s[1];
			this.hd_type = s[2];
			for (int i=0;i<stats.length;i++) {
				this.stats[i] = Integer.parseInt(s[3+i]);
				this.stats_used[i] = Boolean.parseBoolean(s[9+i]);
			}
			this.size = s[15];
			this.natural_armour = Integer.parseInt(s[16]);
			this.number_of_hd = Integer.parseInt(s[17]);
			this.damage_dice = Integer.parseInt(s[18]);
			this.number_of_attacks = Integer.parseInt(s[19]);
			this.cost_of_one_mount = Integer.parseInt(s[20]);
			this.footing = s[21];
			this.base_speed = Integer.parseInt(s[22]);
			if (!s[23].equals("")) {
				String[] mountarmourstring = Arrays.copyOfRange(s,23,s.length);
				this.mount_armour.setArmour(mountarmourstring);
			}
		}
	}
	
}