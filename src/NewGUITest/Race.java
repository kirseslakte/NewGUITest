package NewGUITest;

public class Race {
	
	public String name;
	public int[] stats = new int[6];
	public boolean[] statsinuse = new boolean[6];
	public String size;
	public boolean bipedal;
	public int natac = 0;
	public int miscac = 0;
	public int drbps = 0;
	public int drmm = 0;
	public int natattackdice = 0;
	public int natattacks = 0;
	public int basespeed = 0;
	public boolean feat = false;

	static String[] footing = {"Bipedal","Quadrupedal"};
	static String[] sizes = {"Fine","Diminutive","Tiny","Small","Medium","Large","Huge","Gargantuan","Colossal"};
	
	public Race() {
		
	}
	
	public Race(String[] s) {
		this.name = s[0];
		for (int i=0;i<this.stats.length;i++) {
			this.stats[i] = (int) Integer.parseInt(s[1+2*i]);
			this.statsinuse[i] = Boolean.parseBoolean(s[2+i*2]);
		}
		this.size = s[13];
		this.bipedal = Boolean.parseBoolean(s[14]);
		this.natac = Integer.parseInt(s[15]);
		this.miscac = Integer.parseInt(s[16]);
		this.drbps = Integer.parseInt(s[17]);
		this.drmm = Integer.parseInt(s[18]);
		this.natattackdice = Integer.parseInt(s[19]);
		this.natattacks = Integer.parseInt(s[20]);
		this.basespeed = Integer.parseInt(s[21]);
		this.feat = Boolean.parseBoolean(s[22]);
	}
}