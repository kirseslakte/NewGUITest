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
	static int active_race_number;
	static Race active_race = new Race();
	static Panel racepanel = new Panel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	static JTextField[] stats = new JTextField[6];
	static JRadioButton[] used = new JRadioButton[7];//stats+feat
	static JComboBox<String>[] combos = new JComboBox[2];//size,bipedal/quadropedal
	static JTextField[] rest = new JTextField[8];//speed,natAC,miscAC,DRbps,DRmm,natattackdice,natattacks,name
	
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
		c.gridx = 4;
		c.gridy = 1;
		racepanel.add(new Label("Name of Race"),c);
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
		racepanel.add(new Label("Speed"),c);
		c.gridy ++;
		racepanel.add(new Label("Gains extra feat"),c);
		c.gridwidth = 1;
		c.gridy = 0;
		c.gridx = 1;
		racepanel.add(new Label("Racial Ability Mod"));
		c.gridx = 2;
		racepanel.add(new Label("Used?"));
		for (int i=0;i<stats.length;i++) {
			c.gridy = 1+i;
			c.gridx = 2;
			stats[i] = new JTextField("0");
			racepanel.add(stats[i],c);
			c.gridx = 3;
			used[i] = new JRadioButton();
			used[i].setSelected(true);
			racepanel.add(used[i],c);
		}
		c.gridx = 6;
		c.gridy = 1;
		rest[0] = new JTextField("Race");
		racepanel.add(rest[0],c);
		for (int i=1;i<rest.length;i++) {
			c.gridy ++;
			rest[i] = new JTextField("0");
			racepanel.add(rest[i],c);
		}
		c.gridy ++;
		used[6] = new JRadioButton();
		racepanel.add(used[6],c);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 7;
		combos[0] = new JComboBox<String>(Race.footing);
		racepanel.add(combos[0],c);
		combos[1] = new JComboBox<String>(Race.sizes);
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
		c.gridy ++;
		c.gridwidth = 2;
		c.gridx = 0;
		racepanel.add(save,c);
		c.gridx = 2;
		racepanel.add(close,c);
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
		rest[0].setText(active_race.name);
		rest[1].setText(Integer.toString(active_race.natac));
		rest[2].setText(Integer.toString(active_race.miscac));
		rest[3].setText(Integer.toString(active_race.drbps));
		rest[4].setText(Integer.toString(active_race.drmm));
		rest[5].setText(Integer.toString(active_race.natattackdice));
		rest[6].setText(Integer.toString(active_race.natattacks));
		rest[7].setText(Integer.toString(active_race.basespeed));
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
		active_race.name = rest[0].getText();
		active_race.basespeed = Integer.parseInt(rest[7].getText());
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
		UnitTab.getRace(active_race_number, active_race);
		UnitTab.saveRaces();
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
		loadActiveRace();
		racewindow.setTitle("New Race");
		racewindow.setVisible(true);
	}
	
	public static void stop() {
		saveActiveRace();
		racewindow.setVisible(false);
	}
	
	public static void loadSetup() {
		racespanel.removeAll();
		racespanel.add(new JLabel("Race"));//the loadmenu
		racespanel.add(new JLabel("Modify"));
		racebuttons.clear();
		for (int i=0;i<UnitTab.listofraces.size();i++) {
			racespanel.add(new JLabel(UnitTab.listofraces.get(i).name));
			racebuttons.add(new Button("Modify "+UnitTab.listofraces.get(i).name));
			int k = i;
			racebuttons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					start(k);
					loadStop();
				}
			});
			racespanel.add(racebuttons.get(i));
		}
		racespanel.revalidate();
		racewindow.revalidate();
	}
	
	public static void loadStart() {
		loadSetup();
		raceswindow.setVisible(true);
	}
	
	public static void loadStop() {
		raceswindow.setVisible(false);
	}
}