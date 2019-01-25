package NewGUITest;

public class Route{//trade route object
	
	public String name;//input variables
	public String lord;
	public boolean active;
	public int lord_TAR;
	public int partner_TAR;
	public int partner_BP;
	
	public double trade_value;//output variables
	public int rounded_tv;
	public static int max_roll_diff = 40,
			passive_min = 0,
			passive_max = 12,
			active_min = 8,
			active_max = 22;
	
	public Route(String[] s) {
		//System.out.println("ROUTE! Route");
		this.name = s[0];//the name of the trade route
		this.lord = s[1];//the owner of it name
		this.active = Boolean.parseBoolean(s[2]);//if it is active or passive
		this.lord_TAR = Integer.parseInt(s[3]);//the trade agreement roll of lord
		this.partner_TAR = Integer.parseInt(s[4]);//the trade agreement roll of partner
		this.partner_BP = Integer.parseInt(s[5]);//bp of partner
		double split = Math.max(Math.min(((this.lord_TAR-this.partner_TAR)*50/max_roll_diff)+50,100),0);
		double gain = 0;
		if (this.active)
			gain = (split-50)/50*(active_max-active_min)/2+(active_max+active_min)/2;
		else 
			gain = (split-50)/50*(passive_max-passive_min)/2+(passive_max+passive_min)/2;
		this.trade_value = this.partner_BP*gain/100;
		this.rounded_tv = (int) Math.round(this.trade_value);
	}
}