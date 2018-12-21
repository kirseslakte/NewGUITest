package NewGUITest;

public class Official {//initializing an 'official' object where you can create 'characters'
	public String name;
	public String type;
	public int roll;
	public boolean inhex;
	public String hex;
	
	public Official(String[] s){				//requires input:
		this.name = s[0];						//name
		this.type = s[1];						//official action
		this.roll = Integer.parseInt(s[2]);		//the roll/skill/whatever value is applicable
		this.inhex = Boolean.parseBoolean(s[3]);//if the bonus is hex-specific
		if (inhex)
			this.hex = s[4];					//which hex the official is in
		else
			this.hex = "";
	}
	
	public void changeName(String s) {//methods for changing the official attributes (some might be redundant)
		this.name = s;
	}
	public void changeType(String s) {
		this.type = s;
	}
	public void changeRoll(int i) {
		this.roll = i;
	}
	public void changeInHex(boolean b) {
		this.inhex = b;
	}
	public void changeHex(String s) {
		this.hex = s;
	}
}