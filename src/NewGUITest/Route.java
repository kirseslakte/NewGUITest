package NewGUITest;

public class Route {
	
	public String name;//trade route object
	public String lord;
	public boolean active;
	public int lord_TAR;
	public int partner_TAR;
	public int partner_BP;
	
	public Route(String[] s) {
		this.name = s[0];//the name of the trade route
		this.lord = s[1];//the owner of it (title)
		this.active = Boolean.parseBoolean(s[2]);//if it is active or passive
		this.lord_TAR = Integer.parseInt(s[3]);//the trade agreement roll of lord
		this.partner_TAR = Integer.parseInt(s[4]);//the trade agreement roll of partner
		this.partner_BP = Integer.parseInt(s[5]);//bp of partner
	}
	
	
}