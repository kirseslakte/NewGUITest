package NewGUITest;

import java.util.List;
import java.util.ArrayList;

public class Official {//initializing an 'official' object where you can create 'characters'
	
	public String name;//these are the input variables:
	public String job;
	public int roll;
	public String lord;
	public boolean free;
	public String texteffect;
	public int effect;
	int rollhelper;//-1:nothing,0: something,1: 1st eff,2:2nd effect,3:3rd effect
	
	public Official(){
		
	}
	
	public Official(String[] s){				//requires input:
		//System.out.println("OFFICIAL! Official");
		this.name = s[0];						//name
		this.job = s[1];						//official action
		if (!(s[2].equals("")))
			this.roll = Integer.parseInt(s[2]);	//the roll/skill/whatever value is applicable
		this.lord = s[3];						//which lord the official belongs to
		this.free = Boolean.parseBoolean(s[4]);
		this.rollhelper = -1;
		if (this.job.equals("Cast Realm Spell"))//set the effect text
			this.texteffect = "DC 10+2*spell level";
		else if (this.job.equals("Annex Land")){
			this.texteffect = "Unrest reduction:";
			this.rollhelper = 0;
			this.effect = (int) Math.floor((this.roll-25)/5);
		} else if (this.job.equals("Collect Taxes")) {
			this.texteffect = "Tax eff. bonus:";
			this.rollhelper = 1;
		} else if (this.job.equals("Muster/Disband Troops")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 1;
		} else if (this.job.equals("Name Formation")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 1;
		} else if (this.job.equals("Train Troops")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 1;
		} else if (this.job.equals("Build Building")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 1;
		} else if (this.job.equals("Upgrade Population Center")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 1;
		}else if (this.job.equals("Build Fortification")){
			this.texteffect = "Cost reduction & build speed:";
			this.rollhelper = 1;
		}else if (this.job.equals("Command Army")) {
			this.texteffect = "Cost reduction:";
			this.rollhelper = 2;
		} else if (this.job.equals("Bank Income")) {
			this.texteffect = "Bank inc eff. bonus:";
			this.rollhelper = 2;
		} else if (this.job.equals("Move Population Center")){
			this.texteffect = "Cost reduction:";
			this.rollhelper = 2;
		}else if (this.job.equals("Maintain Trade Route")) {
			this.texteffect = "Number of routes:";
			this.rollhelper = 3;
		}else if (this.job.equals("Proselytize Culture")) {
			this.texteffect = "Chance to convert:";
			this.rollhelper = 4;
		}else if (this.job.equals("Lower Unrest")) {
			this.texteffect = "Chance to reduce unrest:";
			this.rollhelper = 4;
		}else if (this.job.equals("Proselytize Religion")) {
			this.texteffect = "Chance to convert:";
			this.rollhelper = 5;
		}else if (this.job.equals("Boost Legitimacy")) {
			this.texteffect = "Chance to increase legitimacy:";
			this.rollhelper = 5;
		}
		//then we set the effect of the roll
		if (this.rollhelper==1) {//build/muster/train/fort/tax
			if (this.roll<5)
				this.effect = -70;
			else if (this.roll<10)
				this.effect = -40;
			else if (this.roll<15)
				this.effect = -20;
			else if (this.roll<20)
				this.effect = -10;
			else if (this.roll<25)
				this.effect = 0;
			else if (this.roll<30)
				this.effect = 3;
			else if (this.roll<35)
				this.effect = 5;
			else
				this.effect = 5+ (int) Math.floor((this.roll-30)/5);
		} else if (this.rollhelper==2) {//bank/move pop/troops
			if (this.roll<5)
				this.effect = -80;
			else if (this.roll<10)
				this.effect = -40;
			else if (this.roll<15)
				this.effect = -20;
			else if (this.roll<20)
				this.effect = -10;
			else if (this.roll<25)
				this.effect = 0;
			else if (this.roll<30)
				this.effect = 6;
			else if (this.roll<35)
				this.effect = 10;
			else if (this.roll<40)
				this.effect = 12;
			else
				this.effect = 12+ (int) Math.floor((this.roll-35)/5);
		} else if (this.rollhelper==3) {//routes
			if (this.roll<5)
				this.effect = 0;
			else if (this.roll<10)
				this.effect = 1;
			else if (this.roll<20)
				this.effect = 2;
			else
				this.effect = 2+(int) Math.floor((this.roll-15)/5);
		} else if (this.rollhelper==4) {//culture/unrest
			if (this.roll<10)
				this.effect = 0;
			else if (this.roll<15)
				this.effect = 10;
			else if (this.roll<25)
				this.effect = 10+10 * (int) Math.floor((this.roll-10)/5);
			else 
				this.effect = 30+5 * (int) Math.floor((this.roll-20)/5);
		} else if (this.rollhelper==5) {//religion/legitimacy
			if (this.roll<10)
				this.effect = 0;
			else 
				this.effect = 5+5 * (int) Math.floor((this.roll-10)/5);
		}
	}//this should be the only required class to call??
}