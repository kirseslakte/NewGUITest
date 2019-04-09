package NewGUITest;

public class Race {
	
	public String name;
	public int[] stats = new int[6];
	public String size = "Medium";
	public boolean bipedal = true;
	public int natac = 0;
	public int miscac = 0;
	public int drbps = 0;
	public int drmm = 0;
	public String natattackdice ;
	public int natattacks = 0;
	public int natattackdiceint = 0;
	public int basespeed = 0;
	public boolean feat = false;
	public boolean isundead = false;
	public boolean hasfixedabilities = true;

	static String[] footing = {"Bipedal","Quadrupedal"};
	static String[] sizes = {"Fine","Diminutive","Tiny","Small","Medium","Large","Huge","Gargantuan","Colossal"};
	
	public Race() {
		
	}
	
	public Race(String[] s) {
		try {
			this.name = s[0];
			for (int i=0;i<this.stats.length;i++)
				this.stats[i] = (int) Integer.parseInt(s[1+i]);
			this.size = s[7];
			this.bipedal = Boolean.parseBoolean(s[8]);
			this.natac = Integer.parseInt(s[9]);
			this.miscac = Integer.parseInt(s[10]);
			this.drbps = Integer.parseInt(s[11]);
			this.drmm = Integer.parseInt(s[12]);
			this.natattackdice = s[13];
			this.natattacks = Integer.parseInt(s[14]);
			this.basespeed = Integer.parseInt(s[15]);
			this.feat = Boolean.parseBoolean(s[16]);
			this.isundead = Boolean.parseBoolean(s[17]);
			this.hasfixedabilities = Boolean.parseBoolean(s[18]);
		} catch (NumberFormatException e) {
			System.out.println("That race be wierd, yo");
			for (String k:s)
				System.out.println(k);
		}
	}
}