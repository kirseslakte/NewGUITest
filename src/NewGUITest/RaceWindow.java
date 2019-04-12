package NewGUITest;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class RaceWindow{
	
	static JFrame raceswindow = new JFrame();
	static Panel racespanel = new Panel(new GridLayout(0,3));
	static List<Button> racebuttons = new ArrayList<Button>();
	static List<Button> rmvracebuttons = new ArrayList<Button>();
	
	static JFrame racewindow = new JFrame();
	static int active_race_number;
	static Race active_race = new Race();
	static Panel racepanel = new Panel(new GridLayout(0,4));
	static JTextField[] stats = new JTextField[6];
	static JRadioButton feat = new JRadioButton();//feat
	static JRadioButton undead = new JRadioButton();
	static JRadioButton varmod = new JRadioButton();
	static JComboBox<String>[] combos = new JComboBox[2];//size,bipedal/quadropedal
	static JTextField[] rest = new JTextField[8];//name,speed,nac,mac,dr/bps,dr/mm,natattdice,#ofnatatt
	
	public RaceWindow() {
		
	}
	
	public static void init() {
		for (int i=0;i<rest.length;i++)
			rest[i] = new JTextField("0");
		for (int i=0;i<combos.length;i++)
			combos[i] = new JComboBox<String>();
		for (String size:Race.sizes)
			combos[0].addItem(size);
		for (String footing:Race.footing)
			combos[1].addItem(footing);
		for (int i=0;i<stats.length;i++) 
			stats[i] = new JTextField("0");
		racepanel.add(new JLabel("Name of Race"));
		racepanel.add(rest[0]);
		racepanel.add(new JLabel("Size"));
		racepanel.add(combos[0]);
		racepanel.add(new JLabel("Undead"));
		racepanel.add(undead);
		racepanel.add(new JLabel("Leggedness"));
		racepanel.add(combos[1]);
		racepanel.add(new JLabel("Base Speed"));
		racepanel.add(rest[1]);
		racepanel.add(new JLabel("+2 Varied Ability Mod"));
		racepanel.add(varmod);
		racepanel.add(new JLabel("Natural Armour Bonus"));
		racepanel.add(rest[2]);
		racepanel.add(new JLabel("Ability"));
		racepanel.add(new JLabel("Racial Modifier"));
		racepanel.add(new JLabel("Misc Armour Bonus"));
		racepanel.add(rest[3]);
		racepanel.add(new JLabel("Strength"));
		racepanel.add(stats[0]);
		racepanel.add(new JLabel("DR/B,P,S"));
		racepanel.add(rest[4]);
		racepanel.add(new JLabel("Dexterity"));
		racepanel.add(stats[1]);
		racepanel.add(new JLabel("DR/Magic,Material"));
		racepanel.add(rest[5]);
		racepanel.add(new JLabel("Constitution"));
		racepanel.add(stats[2]);
		racepanel.add(new JLabel("Largest Natural Weapons Dice"));
		racepanel.add(rest[6]);
		racepanel.add(new JLabel("Intelligence"));
		racepanel.add(stats[3]);
		racepanel.add(new JLabel("Number of Natural Attacks"));
		racepanel.add(rest[7]);
		racepanel.add(new JLabel("Wisdom"));
		racepanel.add(stats[4]);
		racepanel.add(new JLabel("Gains Extra Feat"));
		racepanel.add(feat);
		racepanel.add(new JLabel("Charisma"));
		racepanel.add(stats[5]);
		Button save = new Button("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				saveActiveRace();
			}
		});
		Button close = new Button("Save & Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				saveActiveRace();
				stop();
			}
		});
		racepanel.add(save);
		racepanel.add(close);
		racewindow.setSize(1000,300);
		racewindow.setLocationRelativeTo(null);
		racewindow.setTitle(active_race.name);
		racewindow.add(racepanel);
		racepanel.revalidate();
		
		raceswindow.setSize(500,300);
		raceswindow.setLocationRelativeTo(null);
		raceswindow.setTitle("Modify Races");
		raceswindow.add(new JScrollPane(racespanel));
	}
	
	public static void loadActiveRace() {
		for (int i=0;i<stats.length;i++)
			stats[i].setText(Integer.toString(active_race.stats[i]));
		feat.setSelected(active_race.feat);
		undead.setSelected(active_race.isundead);
		varmod.setSelected(!active_race.hasfixedabilities);
		combos[0].setSelectedItem(active_race.size);
		int k = 0;
		if (!active_race.bipedal)
			k = 1;
		combos[1].setSelectedIndex(k);
		rest[0].setText(active_race.name);
		rest[1].setText(Integer.toString(active_race.basespeed));
		rest[2].setText(Integer.toString(active_race.natac));
		rest[3].setText(Integer.toString(active_race.miscac));
		rest[4].setText(Integer.toString(active_race.drbps));
		rest[5].setText(Integer.toString(active_race.drmm));
		rest[6].setText(active_race.natattackdice);
		rest[7].setText(Integer.toString(active_race.natattacks));
		racepanel.revalidate();
	}
	
	public static void saveActiveRace() {
		for (int i=0;i<stats.length;i++)
			active_race.stats[i] = (int) Integer.parseInt(stats[i].getText());
		active_race.feat = feat.isSelected();
		active_race.size = (String) combos[0].getSelectedItem();
		active_race.isundead = undead.isSelected();
		active_race.bipedal = combos[1].getSelectedItem().equals(Race.footing[0]);
		active_race.hasfixedabilities = !varmod.isSelected();
		active_race.name = rest[0].getText();
		active_race.basespeed = Integer.parseInt(rest[1].getText());
		active_race.natac = Integer.parseInt(rest[2].getText());
		active_race.miscac = Integer.parseInt(rest[3].getText());
		active_race.drbps = Integer.parseInt(rest[4].getText());
		active_race.drmm = Integer.parseInt(rest[5].getText());
		if (rest[5].getText()!=null) {
			active_race.natattackdice = rest[6].getText();
			active_race.natattacks = Integer.parseInt(rest[7].getText());
		} else {
			active_race.natattackdice = "0";
			active_race.natattacks = 0;
		}
		UnitTab.getRace(active_race_number, active_race);
		UnitTab.saveRaces();
		active_race_number = UnitTab.listofraces.size()-1;
	}
	
	public static void start(int race) {
		active_race = UnitTab.listofraces.get(race);
		active_race_number = race;
		loadActiveRace();
		racewindow.setTitle(active_race.name);
		racewindow.setVisible(true);
	}
	
	public static void start() {
		active_race = new Race();
		UnitTab.listofraces.add(active_race);
		active_race_number = UnitTab.listofraces.size()-1;
		//System.out.println("Added race "+active_race.name);
		loadActiveRace();
		racewindow.setTitle("New Race");
		racewindow.setVisible(true);
	}
	
	public static void stop() {
		UnitTab.cleanRaces();
		UnitWindow.update();
		racewindow.setVisible(false);
	}
	
	public static void loadSetup() {
		racespanel.removeAll();
		racebuttons.clear();
		rmvracebuttons.clear();
		for (int i=0;i<UnitTab.listofraces.size();i++) {
			racespanel.add(new JLabel(UnitTab.listofraces.get(i).name));
			racebuttons.add(new Button("Modify "+UnitTab.listofraces.get(i).name));
			rmvracebuttons.add(new Button("Remove "+UnitTab.listofraces.get(i).name));
			int k = i;
			racebuttons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					start(k);
					loadStop();
				}
			});
			rmvracebuttons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UnitTab.listofraces.remove(k);
					loadStop();
					loadStart();
					UnitTab.saveRaces();
				}
			});
			racespanel.add(racebuttons.get(i));
			racespanel.add(rmvracebuttons.get(i));
		}
		racespanel.revalidate();
		racewindow.revalidate();
	}
	
	public static void loadStart() {
		UnitTab.cleanRaces();
		loadSetup();
		raceswindow.setVisible(true);
	}
	
	public static void loadStop() {
		raceswindow.setVisible(false);
		UnitWindow.update();
	}
}