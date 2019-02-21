package NewGUITest;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class RaceWindow{
	
	static JFrame raceswindow = new JFrame();
	static Panel racespanel = new Panel(new GridLayout(0,2));
	static List<Button> racebuttons = new ArrayList<Button>();
	
	static JFrame racewindow = new JFrame();
	static Race active_race = new Race();
	static Panel racepanel = new Panel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	static JTextField[] stats = new JTextField[6];
	static JRadioButton[] used = new JRadioButton[7];//stats+feat
	static JComboBox[] combos = new JComboBox[2];//size,bipedal/quadropedal
	static JTextField[] rest = new JTextField[7];//speed,natAC,miscAC,DRbps,DRmm,natattackdice,natattacks
	
	public RaceWindow() {
		
	}
	
	public static void init() {
		c.fill = GridBagConstraints.HORIZONTAL;//the window for the individual races
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		racepanel.add(new Label("Racial Ability Mods"),c);
		c.gridy = 1;
		racepanel.add(new Label("Str"),c);
		c.gridy = 2;
		racepanel.add(new Label("Dex"),c);
		c.gridy = 3;
		racepanel.add(new Label("Con"),c);
		c.gridy = 4;
		racepanel.add(new Label("Int"),c);
		c.gridy = 5;
		racepanel.add(new Label("Wis"),c);
		c.gridy = 6;
		racepanel.add(new Label("Cha"),c);
		c.gridy = 0;
		c.gridx = 4;
		racepanel.add(new Label("Ability"),c);
		c.gridy = 1;
		racepanel.add(new Label("Speed"),c);
		c.gridy = 2;
		racepanel.add(new Label("Natural AC"),c);
		c.gridy = 3;
		racepanel.add(new Label("Misc AC"),c);
		c.gridy = 4;
		racepanel.add(new Label("DR/b,p,s"),c);
		c.gridy = 5;
		racepanel.add(new Label("DR/magic,material"),c);
		c.gridy = 6;
		racepanel.add(new Label("Natural attack dice (if any)"),c);
		c.gridy = 7;
		racepanel.add(new Label("Number of natural attacks"),c);
		c.gridy = 8;
		racepanel.add(new Label("Gains extra feat"),c);
		c.gridwidth = 1;
		for (int i=0;i<stats.length;i++) {
			c.gridy = 1+i;
			c.gridx = 2;
			racepanel.add(stats[i],c);
			c.gridx = 3;
			racepanel.add(used[i],c);
		}
		c.gridx = 6;
		for (int i=0;i<rest.length;i++) {
			c.gridy = 1+i;
			racepanel.add(rest[i],c);
		}
		c.gridy ++;
		racepanel.add(used[6],c);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 7;
		combos[0] = new JComboBox(Race.footing);
		racepanel.add(combos[0],c);
		combos[1] = new JComboBox(Race.sizes);
		c.gridy ++;
		racepanel.add(combos[1],c);
		c.gridy ++;
		Button save = new Button("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				saveActiveRace();
			}
		});
		Button close = new Button("Save & Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				stop();
			}
		});
		racewindow.setSize(1000,300);
		racewindow.setLocationRelativeTo(null);
		racewindow.setTitle(active_race.name);
		racewindow.add(racepanel);
		racepanel.revalidate();
		
		loadSetup();
	}
	
	public static void loadActiveRace() {
		for (int i=0;i<stats.length;i++) {
			stats[i].setText(Integer.toString(active_race.stats[i]));
			used[i].setSelected(active_race.statsinuse[i]);
		}
		used[6].setSelected(active_race.feat);
		int k = 0;
		if (!active_race.bipedal)
			k = 1;
		combos[0].setSelectedIndex(k);
		combos[1].setSelectedItem(active_race.size);
		rest[0].setText(Integer.toString(active_race.basespeed));
		rest[1].setText(Integer.toString(active_race.natac));
		rest[2].setText(Integer.toString(active_race.miscac));
		rest[3].setText(Integer.toString(active_race.drbps));
		rest[4].setText(Integer.toString(active_race.drmm));
		rest[5].setText(Integer.toString(active_race.natattackdice));
		rest[6].setText(Integer.toString(active_race.natattacks));
		racepanel.revalidate();
	}
	
	public static void saveActiveRace() {
		for (int i=0;i<stats.length;i++) {
			active_race.stats[i] = (int) Integer.parseInt(stats[i].getText());
			active_race.statsinuse[i] = used[i].isSelected();
		}
		active_race.feat = used[6].isSelected();
		active_race.bipedal = combos[0].getSelectedItem().equals(Race.footing[0]);
		active_race.size = (String) combos[1].getSelectedItem();
		active_race.basespeed = Integer.parseInt(rest[0].getText());
		active_race.natac = Integer.parseInt(rest[1].getText());
		active_race.miscac = Integer.parseInt(rest[2].getText());
		active_race.drbps = Integer.parseInt(rest[3].getText());
		active_race.drmm = Integer.parseInt(rest[4].getText());
		if (rest[5].getText()!=null) {
			active_race.natattackdice = Integer.parseInt(rest[5].getText());
			active_race.natattacks = Integer.parseInt(rest[6].getText());
		} else {
			active_race.natattackdice = 0;
			active_race.natattacks = 0;
		}
		
	}
	
	public static void start(int race) {
		active_race = UnitTab.listofraces.get(race);
		loadActiveRace();
		racewindow.setVisible(true);
	}
	
	public static void stop() {
		saveActiveRace();
		racewindow.setVisible(false);
	}
	
	public static void loadSetup() {
		racespanel.removeAll();
		racespanel.add(new JLabel("Race"),0,0);//the loadmenu
		racespanel.add(new JLabel("Modify"),0,1);
		for (int i=0;i<UnitTab.listofraces.size();i++) {
			racespanel.add(new JLabel(UnitTab.listofraces.get(i).name),1+i,0);
			racebuttons.add(new Button("Modify "+UnitTab.listofraces.get(i).name));
			int k = i;
			racebuttons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					start(k);
					loadStop();
				}
			});
			racespanel.add(racebuttons.get(i),1+i,1);
		}

		raceswindow.setSize(1500,300);
		raceswindow.setLocationRelativeTo(null);
		raceswindow.setTitle("Modify Races");
		raceswindow.add(new JScrollPane(racespanel));
		racespanel.revalidate();
	}
	
	public static void loadStart() {
		loadSetup();
		raceswindow.setVisible(true);
	}
	
	public static void loadStop() {
		raceswindow.setVisible(false);
	}
}