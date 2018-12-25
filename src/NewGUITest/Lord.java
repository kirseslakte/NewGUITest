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
	public boolean is_vassal;
	public String title;
	private Institutions institutes = new Institutions();
	private Governments government = new Governments();
	//TradeWindow trade = new TradeWindow();
	
	public String[] institutions;//institutions
	
	public double[] culture_bonuses;//culture bonuses (22 of them)
	public String[] culture_bonus_names = {"Unit Training Cost","Unit Equipment Cost","Unit Cap",
			"Undead Unit Cap","Hit Modifier","AC Modifier","Morale Modifier","Command Modifier",
			"Ranged Hit Modifier","Fortification Cost","Settlement Cost","Settlement Upgrade",
			"Base Production Modifier","Production Efficiency","Tax Efficiency","Banking Efficiency",
			"Trade Efficiency","Vassal Income Efficiency","Magic Guild Cost","Tinker Guild Cost",
			"Spy Guild Cost","All Guild Cost"};

	public double[] eco;//economy
	/* key for eco:
	 * extra officials								0
	 * tax rate										1
	 * banked rp									2
	 * banked dev									3	
	 * tax rate overlord (=0 if overlord)			4
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
	//public TradeWindow trade = new TradeWindow(this);//a trade window for each lord
	
	//// START OF METHODS ////
	
	public Lord(String s) {
		this.name = s;
		this.setFrame();
	}
	
	public void setGovernment(String[] s) {			//requires following input
		this.sys = s[0];									//system
		this.society = s[1];								//societal structure
		this.rule = s[2];								//ruler
		this.life = s[3];								//life style
		this.cent = s[4];								//centralisation level
		this.alignment = s[5];							//alignment
		this.religion = s[6];							//religion
		this.ruler_name = s[7];							//ruler name
		this.legitimacy = Integer.parseInt(s[8]);		//legitimacy
		this.population_value = Double.parseDouble(s[9]);//population value
		this.total_bpm = Integer.parseInt(s[10]);		//total base production modifier
		this.tax_income = Integer.parseInt(s[11]);		//total tax income
		this.total_trade_value = Integer.parseInt(s[12]);//total trade value
		this.overlord_tax_rate = Double.parseDouble(s[13]);//the overlord tax rate of the current vassal
		this.title = s[14];								//codename of the lord (eg. 'overlord' 'vassal1' 'vassal2_3'
		if (title.contains("lord"))
			this.is_vassal = false;
		else{
			this.is_vassal = true;
		}
		government.setNull();
		government.setSystem(this.sys);
		government.setStruc(this.society);
		government.setRuler(this.rule);
		government.setLifeStyle(this.life);
		government.setCentralisation(this.cent);
	}
	
	public void setInstitutions(String[] in_institutions) {//set the institutions of the nation
		this.institutions = in_institutions;
		for (String s: in_institutions) {
			//check which institutions player wants
			this.institutes.findInstitution(s);
		}
	}
	
	public void setCultureBonuses(double[] input_bonuses) {//set the culture bonuses fed from front panel
		this.culture_bonuses = input_bonuses;
	}
	
	public void setEconomyAndOfficials(double[] e) {//set the economy settings fed from the front panel
		this.eco = e;
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
	    
		////SETTING UP THE MENU BAR////
	    
		JMenuBar menuBar = new JMenuBar();
		JMenu filemenu = new JMenu("File");		//file menu		
		JMenuItem menuSave = new JMenuItem("Save");	//save button
		Action saveAction = new AbstractAction("Save") {
			public void actionPerformed(ActionEvent e) {
				request_flag = true;
				save_request = true;
			}
		};
		saveAction.putValue(Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menuSave.setAction(saveAction);//done setting the save button
		
		JMenuItem menuQuit = new JMenuItem("Quit");	//quit button
		Action quitAction = new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		menuQuit.setAction(quitAction);//done setting the quit button
		filemenu.add(menuSave);
		filemenu.add(menuQuit);
		menuBar.add(filemenu);
		this.setJMenuBar(menuBar);
		
	    ////DONE WITH THE MENU BAR////
		////SETTING UP MAIN PANEL////
		
		Panel mainPnl = new Panel(new GridLayout(2,2));//set out panel
		//nation stats panel
		Panel nation_panel = new Panel(new GridLayout(0,4));//will use a lot of JComboBox! will be cool
		nation_panel.add(new JLabel("Tax Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.tax_eff))));
		nation_panel.add(new JLabel("Production of Hexes"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Production Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.prod_eff))));
		nation_panel.add(new JLabel("Province Income"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Trade Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.trade_eff))));
		nation_panel.add(new JLabel("Province Upkeep"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Vassal Income Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.vassal_inc_eff))));
		nation_panel.add(new JLabel("Development"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Bank Income Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.bank_inc_eff))));
		nation_panel.add(new JLabel("Population (%)"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Bank Development Efficiency"));
		nation_panel.add(new JLabel(Integer.toString((int)Math.rint(government.bank_dev_eff))));
		nation_panel.add(new JLabel("Vassal Tax Income"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Guild Upkeep"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Trade Income"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Army Upkeep"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Government Upkeep"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Trade Value"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Total Income"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Total Upkeep"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Total Production"));
		nation_panel.add(new JLabel(""));
		nation_panel.add(new JLabel("Banked Development"));
		nation_panel.add(new JTextField(""));
		nation_panel.add(new JLabel("Banked RP"));
		nation_panel.add(new JTextField(""));
		nation_panel.add(new JLabel("Taxation Level"));
		nation_panel.add(new JTextField(""));
		mainPnl.add(nation_panel);
		//government panel
		Panel government_panel = new Panel(new GridLayout(0,4));
		government_panel.add(new JLabel("Culture"));
		government_panel.add(new JComboBox(government.alignments));
		government_panel.add(new JLabel("Legitimacy"));
		government_panel.add(new JTextField(""));
		government_panel.add(new JLabel("Religion"));
		government_panel.add(new JComboBox(government.alignments));
		government_panel.add(new JLabel("System"));
		government_panel.add(new JComboBox(government.systems));
		government_panel.add(new JLabel("Social Structure"));
		government_panel.add(new JComboBox(government.strucs));
		government_panel.add(new JLabel("Rule"));
		government_panel.add(new JComboBox(government.rule));
		government_panel.add(new JLabel("Life Style"));
		government_panel.add(new JComboBox(government.life));
		government_panel.add(new JLabel("Centralisation"));
		government_panel.add(new JComboBox(government.centralisation));
		for (int i=0;i<4;i++){
			government_panel.add(new JLabel("Institution"));
			government_panel.add(new JComboBox(institutes.institution_names));
		}
		mainPnl.add(government_panel);
		//anotherpanel
		Panel pnl3 = new Panel(new GridLayout(1,1));
		mainPnl.add(pnl3);
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
		//mainPane.addTab("TradeMap");
		//mainPane.addTab("VassalMap");
		//mainPane.addTab("Notes");
		
		////DONE SETTING UP MAIN PANEL////
		////SETTING UP THE TABBED PANES////
		
		this.add(mainPane);
		
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
		quitBtn.addActionListener(new ActionListener() {//add action event to quit button
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		dummy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				request_flag = true;
				generate_request = true;
			}
		});
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