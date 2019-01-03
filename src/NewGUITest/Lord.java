package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lord extends NationHandler {
	
	public final int max_number_of_institutions = 4;//rule variables
	public final int number_of_culture_bonuses = 22;
	
	public String name;//government variables
	public String sys;
	public String society;
	public String rule;
	public String life;
	public String cent;
	public String alignment;
	public String religion;
	public String ruler_name;
	public int legitimacy;
	public double population_value;
	public int total_bpm;
	public int tax_income;
	public int total_trade_value;
	public double overlord_tax_rate;
	public String master_title;
	public String title;
	public Institutions institutes = new Institutions();
	public Governments government = new Governments();
	//TradeWindow trade = new TradeWindow();
	
	public int[] culture_bonuses = new int[22];//culture bonuses (22 of them)
	/*													MAYBE ROLL ALL THESE OFFICALS INTO SEPARATE CLASS
	 * bank income roll								4	OFFICIAL
	 * tax collect roll								5	OFFICIAL
	 * muster/train troops roll						6	OFFICIAL
	 * build guild/building/settlement roll			7	OFFICIAL
	 * command army roll							8	OFFICIAL
	 * trade negotiator skill						9	OFFICIAL
	 * 													AT LEAST THESE ARE THE NESSESARY OFFICIAL ACTIONS
	 *													THEY WILL BE ROLLED INTO THE OFFICIAL CLASS
	 */
	
	//// THESE ARE THE variables FOR THE GUI ////
	ReadNWrite write = new ReadNWrite();
	public boolean request_flag = false;
	public boolean save_request = false;
	public boolean generate_request = false;
	public boolean new_request = false;
	public LordPanes panes = new LordPanes();
	public HexPane hexpanel = new HexPane();
	//public TradeWindow trade = new TradeWindow(this);//a trade window for each lord
	
	//// START OF METHODS ////
	
	public Lord(String s, String master) {
		this.name = s;
		this.master_title = master;
	}
	
	public Panel setPanel(boolean master) {
		int layers = 1;
		if (master)
			layers = 2;
		Panel mainPnl = new Panel(new GridLayout(layers,2));//set up panel
		mainPnl.add(panes.nationPanel(government,!master_title.equals("")));
		mainPnl.add(panes.governmentPane(government, institutes));
		if (master) {
			mainPnl.add(panes.culturePane());
			Panel pnl4 = new Panel(new GridLayout(1,1));//setting up dummybuttons on the dummypane
			Button newVassalBtn = new Button("New Vassal");//adding buttons
			Button saveBtn = new Button("Save Nation");
			pnl4.add(saveBtn);
			mainPnl.add(pnl4);
			////BUTTON FUNCTIONALITIES////
			newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
				public void actionPerformed(ActionEvent e){
					request_flag = true;
					new_request = true;
				}
			});
			saveBtn.addActionListener(new ActionListener() {//add action event to save button
				public void actionPerformed(ActionEvent e){
					request_flag = true;
					save_request = true;
				}
			});
		}
		return mainPnl;
	}
	
	public void getGovernment() {//getting the government tab things
		this.government.eco[0] = (int) Double.parseDouble(this.panes.bank_rp.getText());	
		this.government.eco[1] = (int) Double.parseDouble(this.panes.bank_dev.getText());
		this.government.eco[2] = (int) Double.parseDouble(this.panes.tax_rate.getText());
		if (!(this.master_title.equals("")))
			this.government.eco[3] = (int) Double.parseDouble(this.panes.lord_tax_rate.getText());
		this.government.setSystem((String) this.panes.system.getSelectedItem());
		this.government.setStruc((String) this.panes.soc_structure.getSelectedItem());
		this.government.setRuler((String) this.panes.rule.getSelectedItem());
		this.government.setLifeStyle((String) this.panes.life_style.getSelectedItem());
		this.government.setCentralisation((String) this.panes.centralisation.getSelectedItem());
		this.government.culture = ((String) this.panes.culture.getSelectedItem());
		this.government.religion = ((String) this.panes.religion.getSelectedItem());
		this.government.legitimacy = (int) Double.parseDouble(this.panes.legitimacy.getText());
		for (int i=0;i<4;i++) {
			this.institutes.setInstitution((String) this.panes.institutions[i].getSelectedItem(),i);
		}
		if (this.government.sys.equals("Histocratic")){
			for (int i=0;i<4;i++) {
				this.government.histocratic_choices[i] = (String) this.panes.histocracy_choices[i].getSelectedItem();
				this.government.hist_val[i] = Integer.parseInt(this.panes.histocracy_values[i].getText());
			}
		}
		this.panes.updateGovernmentPane(government);
		//setGovernment();
	}
	
	public void loadGovernment(String[] s) {//only ever called when loading//has nothing to do with visual layer
		this.government.setSystem(s[1]);
		this.government.setStruc(s[2]);
		this.government.setRuler(s[3]);
		this.government.setLifeStyle(s[4]);
		this.government.setCentralisation(s[5]);
		this.government.culture = s[6];
		this.government.religion = s[7];
		this.government.legitimacy = (int) Double.parseDouble(s[8]);
		for (int i=0;i<4;i++) {
			this.institutes.setInstitution(s[10+i],i);
		}
		this.government.eco[0] = (int) Double.parseDouble(s[14]);
		this.government.eco[1] = (int) Double.parseDouble(s[15]);
		this.government.eco[2] = (int) Double.parseDouble(s[16]);
		if (!(this.master_title.equals("")))
			this.government.eco[3] = (int) Double.parseDouble(s[17]);
		if (this.government.sys.equals("Histocratic")){
			for (int i=0;i<4;i++) {
				this.government.histocratic_choices[i] = s[18+2*i];
				this.government.hist_val[i] = Integer.parseInt(s[19+2*i]);
			}
		}
	}
	
	public void setGovernment() {//visual update of government pane
		this.panes.setGovernmentPane(government, institutes);
	}
	
	public void getCulture() {//extracting all the culture modifiers
		for (int i=0;i<22;i++) {//from visual layer
			this.culture_bonuses[i] = Integer.parseInt(this.panes.culturefields.get(i).getText());
		}
	}
	
	public void loadCulture(String[] s) {//only ever called when loading, nothing to do with visual layer
		for (int i=0;i<22;i++) {
			this.culture_bonuses[i] = Integer.parseInt(s[i]);
		}
	}
	
	public void setCulture() {//visual update of culture pane
		this.panes.setCulturePane(this.culture_bonuses);
	}
	
	public void resetRequest() {
		request_flag = false;
		new_request = false;
		save_request = false;
		generate_request = false;
	}
}