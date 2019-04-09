package NewGUITest;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

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
	public String[] unit_feat = new String[2];
	public Weapon[] weapons = new Weapon[3];
	public Armour armour = new Armour();
	public Armour shield = new Armour();
	public Mount mount = new Mount();
	
	//Output variables

	//unit-specific variables
	public String[] available_feats = {};
	public String[] available_unit_feats= {};
	public int training_cost = 0;
	public int equipment_cost = 0;
	public int mount_cost = 0;
	public int total_cost = 0;
	public int T = 0;
	public int W = 0;
	public int M = 0;
	public int C = 0;
	public int speed = 0;
	public int AC = 10;
	public int[] saves = new int[3];
	
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
	
	
	public Unit() {
		for (int i=0;i<this.weapons.length;i++) {
			this.weapons[i] = new Weapon();
			this.saves[i] = 0;
		}
		this.type = "";
		this.subtype = "";
		this.training = "";
		this.training_type = "";
	}
	
	public void setUnit(String[] s) {//set unit from string
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
		for (int i=0;i<feat.length;i++) 
			this.feat[i] = s[12+i];
		for (int i=0;i<unit_feat.length;i++)
			this.unit_feat[i] = s[15+i];
		String[] weaponstring = Arrays.copyOfRange(s,17,21);
		this.weapons[0].setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,22,26);
		this.weapons[1].setWeapon(weaponstring);
		weaponstring = Arrays.copyOfRange(s,27,31);
		this.weapons[2].setWeapon(weaponstring);
		String[] armourstring = Arrays.copyOfRange(s,32,37);
		this.armour.setArmour(armourstring);
		armourstring = Arrays.copyOfRange(s,38,43);
		this.shield.setArmour(armourstring);
		if (!s[44].equals("no_mount")) {
			String[] mountstring = Arrays.copyOfRange(s,44,72);
			mount.setMount(mountstring);
		}
		this.unit_lord = s[73];
		this.number_of_units = Integer.parseInt(s[74]);
		this.updateUnit();
	}
	
	public String[] getUnit() {
		String[] unitstring = new String[76];
		
		return unitstring;
	}
	
	//weapon class
	
	public class Weapon {
		
		public String name;
		public int cost;
		public int damage_dice;
		public String type;
		public int weight;
		public int AB;
		public int AP;
		
		public String[] types = {"","Ranged","One-Handed","Two-Handed","Light"};
		
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
		
		public String[] armourtypes = {"","Light","Medium","Heavy"};
		public String[] shieldtypes = {"","Light","Heavy","Tower Shield"};
		
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
		public int[] stats = new int[3];
		public boolean undead = false;
		public String size;
		public int natural_armour;
		public int number_of_hd;
		public int damage_dice;
		public int number_of_attacks;
		public int cost_of_one_mount;
		public String footing;
		public int base_speed;
		public Armour armour = new Armour();
		
		public Mount() {
			
		}
		
		public void setMount(String[] s) {
			System.out.println("UNIT! Mount:setMount");
			this.name = s[0];
			this.type = s[1];
			this.hd_type = s[2];
			for (int i=0;i<stats.length;i++) {
				this.stats[i] = Integer.parseInt(s[3+i]);
			}
			this.undead = Boolean.parseBoolean(s[7]);
			this.size = s[8];
			this.natural_armour = Integer.parseInt(s[9]);
			this.number_of_hd = Integer.parseInt(s[10]);
			this.damage_dice = Integer.parseInt(s[11]);
			this.number_of_attacks = Integer.parseInt(s[12]);
			this.cost_of_one_mount = Integer.parseInt(s[13]);
			this.footing = s[14];
			this.base_speed = Integer.parseInt(s[15]);
			if (!s[16].equals("no_mount")) {
				String[] mountarmourstring = Arrays.copyOfRange(s,16,s.length);
				this.armour.setArmour(mountarmourstring);
			}
		}
	}
	
	public void getUnitCost() {
		Lord lord = NationHandler.listoflords.get(Utility.findLord(unit_lord));
		double training_mod = lord.modifiers[21];
		double eq_mod = lord.modifiers[22];
		double slavery = 0.75;
		for (int i=0;i<lord.institutes.active_institutions.length;i++) {
			if (lord.institutes.active_institutions[i].equals("Slavery"))
				slavery = 0.5;
		}
		boolean mount_attack = false;
		//training and type
		if (this.training.equals(trainings[0])) {//irregular
			this.training_cost = 50;
			if (this.type.contains("Aerial"))
				this.training_cost += 50;
		} else if (this.training.equals(trainings[1])) {//regular
			this.training_cost = 155;
			if (this.type.contains("Aerial"))
				this.training_cost += 60;
		} else if (this.training.equals(trainings[2])) {//elite
			this.training_cost = 245;
			if (this.type.contains("Aerial"))
				this.training_cost += 70;
		} else 
			this.training_cost = 0;
		//stats&speed
		this.training_cost += this.stat_mods[0]*20;
		this.training_cost += this.stat_mods[1]*15;
		this.training_cost += this.stat_mods[2]*25;
		for (int i=3;i<6;i++)
			this.training_cost += this.stat_mods[i]*5;
		this.training_cost += this.speed;//minus if dash or fast
		//racethings
		this.training_cost += (this.race.natac+this.race.drmm)*15+(this.race.miscac+this.race.drbps+this.race.natattackdiceint+
				this.race.natattacks-1)*10;
		if (this.race.size.equals(Race.sizes[0]))
			this.training_cost -= 80;
		else if (this.race.size.equals(Race.sizes[1]))
			this.training_cost -= 60;
		else if (this.race.size.equals(Race.sizes[2]))
			this.training_cost -= 40;
		else if (this.race.size.equals(Race.sizes[3]))
			this.training_cost -= 20;
		else if (this.race.size.equals(Race.sizes[5]))
			this.training_cost += 20;
		else if (this.race.size.equals(Race.sizes[6]))
			this.training_cost += 40;
		else if (this.race.size.equals(Race.sizes[7]))
			this.training_cost += 60;
		else if (this.race.size.equals(Race.sizes[8]))
			this.training_cost += 80;
		//subtype
		if (this.subtype.equals("Archer")||this.subtype.equals("Scout"))
			this.training_cost += 100;
		else if (this.subtype.equals("Combat Engineer")||this.subtype.equals("Crusading")||this.subtype.equals("Garrison"))
			this.training_cost += 200;
		else if (this.subtype.equals("Slayer"))
			this.training_cost += 75;
		else if (this.subtype.equals("Worker"))
			this.training_cost -= 30;
		else if (this.subtype.equals("Mercenary"))
			training_mod = training_mod*1.5;
		else if (this.subtype.equals("Personal"))
			training_mod = training_mod*1.25;
		else if (this.subtype.equals("Penal"))
			training_mod = training_mod*slavery;
		//unit feats
		for (int i=0;i<this.unit_feat.length;i++) {
			if (this.unit_feat[i].equals("Brave"))
				this.training_cost += 20;
			else if (this.unit_feat[i].equals("Disciplined"))
				this.training_cost += 30;
			else if (this.unit_feat[i].equals("Fast"))
				this.training_cost += 50;
			else if (this.unit_feat[i].equals("Mage-Trained"))
				this.training_cost += 50;
			else if (this.unit_feat[i].equals("Mount Attack"))
				mount_attack = true;
			else if (this.unit_feat[i].equals("Precision Drill"))
				this.training_cost += 10;
			else if (this.unit_feat[i].equals("Skilled Defenders"))
				this.training_cost += 20;
			else if (this.unit_feat[i].contains("Terrain"))
				this.training_cost += 50;
			else if (this.unit_feat[i].equals("Unbreakable"))
				this.training_cost += 30;
		}
		//mount
		if (this.type.contains("Cavalry")) {
			//mount racials
			/*For Mounted units, pay for the following
Mounts Speed
Mounts Str, Dex
Mounts Con at 10 per
Mounts Natural Armour
Natural Weapons (Power of Primary Natural weapon +1 per additional weapon e.g. a D6 bite costs 30, 2 claws would cost 20 extra)
Size of the Mount
For animals with more than 1HD
HD	Animals		Magical Beasts
2nd	40		50
3rd	60		80
4th	90		120
5th	130		160

Mounts above 5HD are probably not common enough to serve as mounts (Aside from larger creatures, which work differently)

For Mounted Unit Stats
Use the Mounts Speed and Reflex Save
Grant the unit a second attack using the mounts MaB and Mpow
Rider gets +1 MaB and +1 AC
Take the Average of the Mount and Riders AC (Including the above bonus) and Toughness (rounding up)
Add +1 Wound

Special note: The mounted combat feat allows the unit to use the riders AC and Toughness instead of averaging (Great for light horsemen)
			 * 
			 */
			//mount eq
		}
		this.training_cost = (int) Math.round(this.training_cost*training_mod);
		//equipment
		this.equipment_cost = this.armour.cost;
		for (int i=0;i<this.weapons.length;i++) {
			this.equipment_cost += this.weapons[i].cost;
		}
		this.equipment_cost = (int) Math.round(this.equipment_cost*eq_mod);
		this.total_cost = this.equipment_cost + this.training_cost + this.mount_cost;
	}
	
	public void updateUnit() {
		
		//available feats
		List<String> av_feats = new ArrayList<>(Arrays.asList(feats));
		boolean crs = false;
		boolean mntd = false;
		for (int i=0;i<this.feat.length;i++) {
			if (this.feat[i].equals("Weapon Focus (W1)")&&this.weapons[0].type.equals("Ranged")&&this.weapons[0].name.contains("Cross")||
					this.feat[i].equals("Weapon Focus (W2)")&&this.weapons[1].type.equals("Ranged")&&this.weapons[1].name.contains("Cross")||
					this.feat[i].equals("Weapon Focus (W3)")&&this.weapons[2].type.equals("Ranged")&&this.weapons[2].name.contains("Cross"))
				crs = true;
			if (this.feat[i].equals("Mounted Combat"))
				mntd = true;
		}
		if (!(crs))
			av_feats.remove("Crossbow Sniper");
		if (!(mntd))
			av_feats.remove("Mounted Archery");
		if (this.stats[0]<13)
			av_feats.remove("Power Attack");
		if (this.stats[1]<13) {
			av_feats.remove("Deadly Aim");
			av_feats.remove("Dodge");
		}
		this.available_feats = av_feats.toArray(new String[0]);
		
		//available unit feats
		List<String> unitf = new ArrayList<>(Arrays.asList(unit_feats));
		if (!(this.type.contains("Cavalry")))
			unitf.remove("Mount Attack");
		if (!(this.training.equals("Elite")||this.training.equals("Regular")))
			unitf.remove("Skilled Defenders");
		if (!(this.training.equals("Elite")))
			unitf.remove("Unbreakable");
		this.available_unit_feats = unitf.toArray(new String[0]);
		
		//calculate stats
		int mab = 0;
		int map = 0;
		int rab = 0;
		int rap = 0;
		for (int i=0;i<6;i++)
			this.stat_mods[i] = (int) Math.floor((this.stats[i]-10)/2);
		this.W = 2;
		if (this.type.contains("Cavalry"))
			this.W = 3;
			//training mods
		if (this.training.equals("Irregular")) {
			this.saves[2] = 0;
			this.T = 1;
			this.M = 0;
			this.C = 0;
			map = 0;
			rap = 0;
			if (this.training_type.equals("Light")) {
				mab = 1;
				rab = 2;
				this.saves[0] = 0;
				this.saves[1] = 2;
				this.saves[2] = 0;
			}else if (this.training_type.equals("Medium")) {
				mab = 2;
				rab = 1;
				this.saves[0] = 1;
				this.saves[1] = 1;
			}else if (this.training_type.equals("Heavy")) {
				mab = 2;
				rab = 1;
				this.saves[0] = 2;
				this.saves[1] = 0;
			}
		}else if (this.training.equals("Regular")) {
			map = 1;
			rap = 1;
			this.M = 1;
			this.C = 2;
			this.saves[2] = 1;
			if (this.training_type.equals("Light")) {
				mab = 2;
				rab = 3;
				this.T = 1;
				this.saves[0] = 1;
				this.saves[1] = 3;
			}else if (this.training_type.equals("Medium")) {
				mab = 2;
				rab = 2;
				this.T = 2;
				this.saves[0] = 2;
				this.saves[1] = 2;
			}else if (this.training_type.equals("Heavy")) {
				mab = 3;
				rab = 2;
				this.T = 2;
				this.saves[0] = 3;
				this.saves[1] = 0;
			}
		}else if (this.training.equals("Elite")) {
			map = 2;
			rap = 2;
			this.T = 2;
			this.M = 2;
			this.C = 5;
			this.saves[2] = 1;
			if (this.training_type.equals("Light")) {
				mab = 3;
				rab = 5;
				this.saves[0] = 1;
				this.saves[1] = 3;
			}else if (this.training_type.equals("Medium")) {
				mab = 4;
				rab = 4;
				this.saves[0] = 2;
				this.saves[1] = 2;
			}else if (this.training_type.equals("Heavy")) {
				mab = 5;
				rab = 3;
				this.saves[0] = 3;
				this.saves[1] = 1;
			}
		}
			//racials
		if (this.race.isundead) {
			
		} //else if (this.race.statsinuse[1]&&)
		this.getUnitCost();
	}
}