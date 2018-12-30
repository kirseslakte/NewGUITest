package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lord extends JFrame{
	
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

	public double[] eco = new double[4];//economy
	/* key for eco:
	 * tax rate										0
	 * banked rp									1
	 * banked dev									2	
	 * tax rate overlord (=0 if overlord)			3
	 */
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
	//public TradeWindow trade = new TradeWindow(this);//a trade window for each lord
	
	//// START OF METHODS ////
	
	public Lord(String s, String master) {
		this.name = s;
		this.master_title = master;
		this.setFrame();
	}
	
	public void setFrame() {
		this.setSize(1500,1000);//x,y
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	    this.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		this.setTitle(name);
		////SETTING UP MAIN PANEL////
		
		Panel mainPnl = new Panel(new GridLayout(2,2));//set out panel
		//nation stats panel
		mainPnl.add(panes.nationPanel(government,!master_title.equals("")));
		//government panel
		mainPnl.add(panes.governmentPane(government, institutes));
		//culture
		mainPnl.add(panes.culturePane());
		//lastpanel
		Panel pnl4 = new Panel(new GridLayout(1,1));
		Button newVassalBtn = new Button("New Vassal");//adding buttons
		Button saveBtn = new Button("Save Nation");
		Button quitBtn = new Button("Quit");
		Button dummy = new Button("Dummy");
		pnl4.add(dummy);
		pnl4.add(saveBtn);
		pnl4.add(quitBtn);
		mainPnl.add(pnl4);
		JTabbedPane mainPane = new JTabbedPane();
		mainPane.addTab("Government",mainPnl);
		//mainPane.addTab("Officials");
		//mainPane.addTab("Units");
		//mainPane.addTab("TradeMap");
		//mainPane.addTab("VassalMap");
		//mainPane.addTab("Notes");
		this.add(mainPane);
		
		////DONE SETTING UP MAIN PANEL////
		
		////BUTTON FUNCTIONALITIES////
		newVassalBtn.addActionListener(new ActionListener() {//add action event to new button
			public void actionPerformed(ActionEvent e){
				request_flag = true;
				new_request = true;
			}
		});
		saveBtn.addActionListener(new ActionListener() {//add action event to save button
			public void actionPerformed(ActionEvent e){
				getGovernment();
				getCulture();
				request_flag = true;
				save_request = true;
			}
		});
		quitBtn.addActionListener(new ActionListener() {//add action event to quit button
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		dummy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {/*
				request_flag = true;
				generate_request = true;*/
				getGovernment();
				getCulture();
			}
		});
	}
	
	public void getGovernment() {//getting the government tab things
		this.eco[0] = (int) Double.parseDouble(this.panes.bank_rp.getText());	
		this.eco[1] = (int) Double.parseDouble(this.panes.bank_dev.getText());
		this.eco[2] = (int) Double.parseDouble(this.panes.tax_rate.getText());
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
		this.setFrame();
	}
	
	public void setGovernment(String[] s) {//setting the government tab things
		System.out.println("Setting government");
		this.panes.bank_rp.setText(s[0]);
		System.out.println(panes.bank_rp.getText());
		this.panes.bank_dev.setText(s[1]);
		this.panes.tax_rate.setText(s[2]);
		this.panes.system.setSelectedItem(s[3]);
		this.panes.soc_structure.setSelectedItem(s[4]);
		this.panes.rule.setSelectedItem(s[5]);
		this.panes.life_style.setSelectedItem(s[6]);
		this.panes.centralisation.setSelectedItem(s[7]);
		this.panes.culture.setSelectedItem(s[8]);
		this.panes.religion.setSelectedItem(s[9]);
		this.panes.legitimacy.setText(s[10]);
		for (int i=0;i<4;i++) {
			this.panes.institutions[i].setSelectedItem(s[11+i]);
		}
		this.getGovernment();//to set the newly imported values into more than the visuals
	}
	
	public void getCulture() {//extracting all the culture modifiers
		this.culture_bonuses[0] = Integer.parseInt(this.panes.unit_training_cost.getText());
		this.culture_bonuses[1] = Integer.parseInt(this.panes.undead_unit_cap.getText());
		this.culture_bonuses[2] = Integer.parseInt(this.panes.unit_cap.getText());
		this.culture_bonuses[3] = Integer.parseInt(this.panes.unit_equipment_cost.getText());
		this.culture_bonuses[4] = Integer.parseInt(this.panes.hit_mod.getText());
		this.culture_bonuses[5] = Integer.parseInt(this.panes.ac_mod.getText());
		this.culture_bonuses[6] = Integer.parseInt(this.panes.m_mod.getText());
		this.culture_bonuses[7] = Integer.parseInt(this.panes.c_mod.getText());
		this.culture_bonuses[8] = Integer.parseInt(this.panes.ranged_hit_mod.getText());
		this.culture_bonuses[9] = Integer.parseInt(this.panes.settlement_upk.getText());
		this.culture_bonuses[10] = Integer.parseInt(this.panes.fortification_cost.getText());
		this.culture_bonuses[11] = Integer.parseInt(this.panes.settlement_upgrade.getText());
		this.culture_bonuses[12] = Integer.parseInt(this.panes.bp_mod.getText());
		this.culture_bonuses[13] = Integer.parseInt(this.panes.prod_mod.getText());
		this.culture_bonuses[14] = Integer.parseInt(this.panes.tax_mod.getText());
		this.culture_bonuses[15] = Integer.parseInt(this.panes.bank_mod.getText());
		this.culture_bonuses[16] = Integer.parseInt(this.panes.trade_mod.getText());
		this.culture_bonuses[17] = Integer.parseInt(this.panes.vassal_mod.getText());
		this.culture_bonuses[18] = Integer.parseInt(this.panes.magic_mod.getText());
		this.culture_bonuses[19] = Integer.parseInt(this.panes.tinker_mod.getText());
		this.culture_bonuses[20] = Integer.parseInt(this.panes.spy_mod.getText());
		this.culture_bonuses[21] = Integer.parseInt(this.panes.guild_mod.getText());
	}
	
	public void setCulture(String[] s) {//extracting all the culture modifiers
		if (this.title.equals("overlord")) {
			this.panes.unit_training_cost.setText(s[0]);
			this.panes.undead_unit_cap.setText(s[1]);
			this.panes.unit_cap.setText(s[2]);
			this.panes.unit_equipment_cost.setText(s[3]);
			this.panes.hit_mod.setText(s[4]);
			this.panes.ac_mod.setText(s[5]);
			this.panes.m_mod.setText(s[6]);
			this.panes.c_mod.setText(s[7]);
			this.panes.ranged_hit_mod.setText(s[8]);
			this.panes.settlement_upk.setText(s[9]);
			this.panes.fortification_cost.setText(s[10]);
			this.panes.settlement_upgrade.setText(s[11]);
			this.panes.bp_mod.setText(s[12]);
			this.panes.prod_mod.setText(s[13]);
			this.panes.tax_mod.setText(s[14]);
			this.panes.bank_mod.setText(s[15]);
			this.panes.trade_mod.setText(s[16]);
			this.panes.vassal_mod.setText(s[17]);
			this.panes.magic_mod.setText(s[18]);
			this.panes.tinker_mod.setText(s[19]);
			this.panes.spy_mod.setText(s[20]);
			this.panes.guild_mod.setText(s[21]);
			getCulture();
		} else {
			for (int i=0;i<22;i++) {
				this.culture_bonuses[i] = Integer.parseInt(s[i]);
			}
		}
	}
	
	public void resetRequest() {
		request_flag = false;
		new_request = false;
		save_request = false;
		generate_request = false;
	}
	
	public void start(){
		this.setVisible(true);
	}
	public void stop() {
		this.setVisible(false);
	}
	
	public void loadLord() {
		
	}	
}