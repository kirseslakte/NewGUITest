package NewGUITest;

public class Official {//initializing an 'official' object where you can create 'characters'
	public String name;//these are the input variables:
	public String type;
	public int roll;
	public String hex;
	public String lord;

	public boolean inhex;//non-input variables
	
	public Official(String[] s){				//requires input:
		this.name = s[0];						//name
		this.type = s[1];						//official action
		this.roll = Integer.parseInt(s[2]);		//the roll/skill/whatever value is applicable
		this.hex = s[3];						//which hex the official is in
		this.lord = s[4];						//which lord the official belongs to (title)
		this.inhex = !s[3].equals("not_in_hex");//if the bonus is hex-specific
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
	public void changeHex(String s) {
		this.hex = s;
	}
}