package NewGUITest;

import java.util.Arrays;

public class Unit {
	public String name;//input variables
	public int[] stats = new int[6];
	public int[] stats_racial_mods = new int[6];
	public boolean[] stats_used = new boolean[6];
	public String type;
	public String subtype;
	public String training;
	public String training_type;
	public String size;
	public String footing;
	public int natural_armour;
	public int misc_armour_bonus;
	public int base_speed;
	public int dr_bps;
	public int dr_mm;
	public int natural_weapons_dice;
	public int number_of_attacks;
	public String[] feats = new String[3];
	public String[] unit_feats = new String[3];
	public Weapon weapon1 = new Weapon();
	public Weapon weapon2 = new Weapon();
	public Weapon weapon3 = new Weapon();
	public Armour armour = new Armour();
	public Armour shield = new Armour();
	public Mount mount = new Mount();
	public String unit_lord;
	public int number_of_units;
	
	public Unit() {
		
	}
	
	public void setUnit(String[] s) {
		System.out.println("UNIT! setUnit");
		this.name = s[0];
		for (int i=0;i<stats.length;i++) {
			this.stats[i] = Integer.parseInt(s[i+1]);
			this.stats_racial_mods[i] = Integer.parseInt(s[i+7]);
			this.stats_used[i] = Boolean.parseBoolean(s[i+13]);
		}
		this.type = s[19];
		this.subtype = s[20];
		this.training = s[21];
		this.training_type = s[22];
		this.size = s[23];
		this.footing = s[24];
		this.natural_armour = Integer.parseInt(s[25]);
		this.misc_armour_bonus = Integer.parseInt(s[26]);
		this.base_speed = Integer.parseInt(s[27]);
		this.dr_bps = Integer.parseInt(s[28]);
		this.dr_mm = Integer.parseInt(s[29]);
		this.natural_weapons_dice = Integer.parseInt(s[30]);
		this.number_of_attacks = Integer.parseInt(s[31]);
		for (int i=0;i<feats.length;i++) {
			this.feats[i] = s[32+i];
			this.unit_feats[i] = s[35+i];
		}
		String[] weaponstring = Arrays.copyOfRange(s,38,42);
		this.weapon1.setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,43,47);
		this.weapon2.setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,48,52);
		this.weapon3.setWeapon(weaponstring);
		String[] armourstring = Arrays.copyOfRange(s,53,58);
		this.armour.setArmour(armourstring);
		armourstring = Arrays.copyOfRange(s,59,64);
		this.shield.setArmour(armourstring);
		if (!s[65].equals("no_mount")) {
			String[] mountstring = Arrays.copyOfRange(s,65,93);
			mount.setMount(mountstring);
		}
		this.unit_lord = s[94];
		this.number_of_units = Integer.parseInt(s[95]);
	}
	
	//weapon class
	
	public class Weapon {
		
		public String name;
		public int cost;
		public int damage_dice;
		public String type;
		public int weight;
		
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