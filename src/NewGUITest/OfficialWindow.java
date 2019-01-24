package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class OfficialWindow extends JFrame{
	
	public static String[] jobslist = {"Annex Land","Bank Income","Cast Realm Spell","Collect Taxes","Negotiate Peacedeal","Forge Alliance",
			"Muster/Disband Troops","Name Formation","Train Troops","Maintain Trade Route","Build Building","Build Fortification",
			"Upgrade Population Center","Command Army","Espionage","Sabotage",
			"Proselytize Religion","Proselytize Culture","Boost Legitimacy","Lower Unrest","Move Population Center","Welfare","Interior"};
	//list of all possible jobs
	public String[] available_jobs;		  //list of available jobs
	
	public List<Official> listofofficials = new ArrayList<Official>();
	public List<JTextField> names = new ArrayList<JTextField>();
	public List<JComboBox> jobs = new ArrayList<JComboBox>();
	public List<JTextField> rolls = new ArrayList<JTextField>();
	public List<JLabel> effects = new ArrayList<JLabel>();
	public List<JCheckBox> free = new ArrayList<JCheckBox>();
	public JButton add_button = new JButton("Add Official");
	Panel main = new Panel(new GridLayout(0,5));
	Lord lord;
	
	public OfficialWindow() {
		
	}
	
	public void initialize(Lord lord) {
		//System.out.println("OFFICIALWINDOW! initialize");
		this.lord = lord;
		setJobsList();
		setFrame();
	}
	
	public void setJobsList() {
		//System.out.println("OFFICIALWINDOW! setJobsList");
		try { 		this.lord = NationHandler.listoflords.get(Utility.findLord(this.lord.name));
		} catch (IndexOutOfBoundsException e) {
			//System.out.println(e);
		}
		List<String> temp = new ArrayList<String>();
		temp.add("");
		for (int i=0;i<jobslist.length-2;i++)
			temp.add(jobslist[i]);
		for (int i=0;i<this.lord.institutes.active_institutions.length;i++) {
			if (this.lord.institutes.active_institutions[i].equals("Welfare"))
				temp.add(jobslist[jobslist.length-2]);
			if (this.lord.institutes.active_institutions[i].equals("Interior"))
				System.out.println("INTERIOR ADDED");
		}
		this.available_jobs = new String[temp.size()];
		for (int i=0;i<temp.size();i++)
			this.available_jobs[i] = temp.get(i);
		for (int i=0;i<this.jobs.size();i++) {
			String setjob = (String) this.jobs.get(i).getSelectedItem();
			this.jobs.get(i).removeAllItems();
			for (String s:this.available_jobs)
				this.jobs.get(i).addItem(s);
			this.jobs.get(i).setSelectedItem(setjob);
		}
	}
	
	public void setFrame() {	
		//System.out.println("OFFICIALWINDOW! setFrame");
		this.setSize(800, 100);
		this.setLocationRelativeTo(null);
		this.setTitle("Officials for "+this.lord.name);
	    this.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				readOfficials();
				NationHandler.getOfficials(lord.name);
			}
		});
	    this.main.add(new JLabel("Name"));
	    this.main.add(new JLabel("Job"));
	    this.main.add(new JLabel("Roll"));
	    this.main.add(new JLabel("Effect"));
	    this.main.add(new JLabel("Free?"));
	    this.add_button.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		addOfficial();
	    		readOfficials();
	    	}
	    });
	    this.addOfficial();
	    this.add(this.main);
	}
	
	public void addOfficial() {
		//System.out.println("OFFICIALWINDOW! addOfficial");
		//System.out.println("THE NUMBER OF OFFICIALS IS "+this.names.size());
		this.setJobsList();
		try {
			if (!(this.names.get(this.names.size()-1).getText().equals(""))) {
				//System.out.println("ADDING ANOTHER LINE, PREVIOUS OFFICIAL NAME: ");
				this.remove(add_button);
				this.names.add(new JTextField(""));
				this.main.add(this.names.get(this.names.size()-1));
				this.jobs.add(new JComboBox<>(this.available_jobs));
				this.main.add(this.jobs.get(this.jobs.size()-1));
				this.rolls.add(new JTextField(""));
				this.main.add(this.rolls.get(this.rolls.size()-1));
				this.effects.add(new JLabel("0"));
				this.main.add(this.effects.get(this.effects.size()-1));
				this.free.add(new JCheckBox());
				this.main.add(this.free.get(this.free.size()-1));
				this.main.add(add_button);
			} else {
				for (int i=0;i<this.names.size()-1;i++){
					if (this.names.get(i).getText().equals("")) {//&&this.jobs.get(i).getSelectedItem().equals("")&&
							//this.rolls.get(i).getText().equals("")) {
						this.main.remove(this.names.get(i));
						this.main.remove(this.jobs.get(i));
						this.main.remove(this.rolls.get(i));
						this.main.remove(this.effects.get(i));
						this.main.remove(this.free.get(i));
						this.names.remove(i);
						this.jobs.remove(i);
						this.rolls.remove(i);
						this.effects.remove(i);
						this.free.remove(i);
						this.main.revalidate();
						i = 0;
					}
				}
			}
			this.main.revalidate();
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("OUT OF BOUNDS");
			this.remove(add_button);
			this.names.add(new JTextField(""));
			this.main.add(this.names.get(this.names.size()-1));
			this.jobs.add(new JComboBox<>(this.available_jobs));
			this.main.add(this.jobs.get(this.jobs.size()-1));
			this.rolls.add(new JTextField(""));
			this.main.add(this.rolls.get(this.rolls.size()-1));
			this.effects.add(new JLabel("0"));
			this.main.add(this.effects.get(this.effects.size()-1));
			this.free.add(new JCheckBox());
			this.main.add(this.free.get(this.free.size()-1));
			this.main.add(add_button);
			this.main.revalidate();
		}
		this.readOfficials();
		for (int i=0;i<this.listofofficials.size();i++) {
			if (!(this.listofofficials.get(i).rollhelper==-1))
				this.effects.get(i).setText(this.listofofficials.get(i).texteffect+" "+Integer.toString(this.listofofficials.get(i).effect));
			else
				this.effects.get(i).setText(this.listofofficials.get(i).texteffect);
		}
		this.setSize(this.getWidth(), 70+30*this.names.size());
		this.revalidate();
	}
	
	public void readOfficials() {
		//System.out.println("OFFICIALWINDOW! readOfficials");
		this.listofofficials.clear();
		for (int i=0;i<this.names.size()-1;i++) {
			String[] official = new String[5];
			official[0] = this.names.get(i).getText();
			official[1] = (String) this.jobs.get(i).getSelectedItem();
			official[2] = this.rolls.get(i).getText();
			official[3] = this.lord.name;
			official[4] = Boolean.toString(this.free.get(i).isSelected());
			this.listofofficials.add(new Official(official));
		}
	}
	
	public void loadOfficials() {
		//System.out.println("OFFICIALWINDOW! loadOfficials");
		int i = 0;
		for (Official official:NationHandler.listofofficials) {
			if (official.lord.equals(this.lord.name)){
				this.names.get(i).setText(official.name);
				this.jobs.get(i).setSelectedItem(official.job);
				this.rolls.get(i).setText(Integer.toString(official.roll));
				this.free.get(i).setSelected(official.free);
				this.addOfficial();
				i++;
			}
		}
		this.readOfficials();
	}
	
	public void start() {
		//System.out.println("OFFICIALWINDOW! start");
		this.setVisible(true);
		this.setJobsList();
	}
	public void stop() {
		//System.out.println("OFFICIALWINDOW! stop");
		this.setVisible(false);
	}
}