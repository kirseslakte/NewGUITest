package NewGUITest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class UnitWindow {
	static JFrame unitwindow = new JFrame();
	static Panel mainpanel = new Panel(new GridLayout(2,2));
	
	static int current_unit_number;
	static Unit current_unit;
	
	static Panel racepanel = new Panel(new GridLayout(0,3));
	static JComboBox<String> race = new JComboBox<String>();
	static Button[] racebtn = new Button[2];
	static JTextField[] pointbuy = new JTextField[6];
	static JLabel[] totstat = new JLabel[6];
	static Button mountbtn = new Button("Mount");
	static JComboBox<String> stat = new JComboBox<String>();
	static JLabel statlabel = new JLabel("Racial Point-up Ability");
	static JLabel raceplaceholder = new JLabel("");
	
	static Panel typepanel = new Panel(new GridLayout(0,4));
	static JComboBox<String>[] typeboxes = new JComboBox[4];
	static JLabel[] featlbl = new JLabel[3];
	static JComboBox<String>[] feats = new JComboBox[3];
	static JLabel[] ufeatlbl = new JLabel[2];
	static JComboBox<String>[] ufeats = new JComboBox[2];
	static JLabel[] typeplaceholder = new JLabel[4];
	static Button savebtn = new Button("Save & Update");
	static Button clrbtn = new Button("Clear Unit");
	
	static Panel eqpanel = new Panel(new GridLayout(0,7));
	static JTextField[] eqname = new JTextField[5];
	static JTextField[] eqcost = new JTextField[5];
	static JTextField[] wpdice = new JTextField[3];
	static JTextField[] mxdex = new JTextField[2];
	static JTextField[] ac = new JTextField[2];
	static JComboBox<String>[] eqtype = new JComboBox[5];
	static JTextField[] eqwgt = new JTextField[5];
	static JLabel[] wpatk = new JLabel[3];
	static JLabel[] wppwr = new JLabel[3];
	
	static Panel outputpanel = new Panel(new GridLayout(0,4));
	static JLabel[] oplbl = new JLabel[13];
	static JLabel[] op = new JLabel[13];
	static String[] outputtags = {"Toughness","Wounds","Morale","Command","Speed","AC","Fort","Ref","Will","Training Cost","Equipment Cost",
			"Mount Cost","Total Cost"};
	static String[] abilities = {"Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"};
	
	
	public UnitWindow() {
		
	}
	
	public static void racePanelSetup() {
		racepanel.add(new JLabel("Race"));
		racepanel.add(race);
		racepanel.add(new JLabel(""));
		racebtn[0] = new Button("Add Race");
		racebtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceWindow.start();
			}
		});
		racepanel.add(racebtn[0]);
		racebtn[1] = new Button("Race List");
		racebtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceWindow.loadStart();
			}
		});
		racepanel.add(racebtn[1]);
		racepanel.add(new JLabel(""));
		racepanel.add(new JLabel("Ability"));
		racepanel.add(new JLabel("Point-buy Score"));
		racepanel.add(new JLabel("Total Ability Score"));
		for (int i=0;i<pointbuy.length;i++) {
			pointbuy[i] = new JTextField("10");
			totstat[i] = new JLabel("10");
			racepanel.add(new JLabel(abilities[i]));
			racepanel.add(pointbuy[i]);
			racepanel.add(totstat[i]);
		}
		mountbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MountWindow.start();
			}
		});
		racepanel.add(mountbtn);
		for (String ab:abilities)
			stat.addItem(ab);
	}
	
	public static void equipmentPanelSetup() {
		eqpanel.add(new JLabel("Name"));
		eqpanel.add(new JLabel("Cost"));
		eqpanel.add(new JLabel("Damage Dice"));
		eqpanel.add(new JLabel("Type"));
		eqpanel.add(new JLabel("Weigth"));
		eqpanel.add(new JLabel("Attack"));
		eqpanel.add(new JLabel("Power"));
		for (int i=0;i<3;i++) {
			eqname[i] = new JTextField("Weapon "+(i+1));
			eqpanel.add(eqname[i]);
			eqcost[i] = new JTextField("0");
			eqpanel.add(eqcost[i]);
			wpdice[i] = new JTextField("0");
			eqpanel.add(wpdice[i]);
			eqtype[i] = new JComboBox<String>(current_unit.weapons[0].types);
			eqpanel.add(eqtype[i]);
			eqwgt[i] = new JTextField("0");
			eqpanel.add(eqwgt[i]);
			wpatk[i] = new JLabel("0");
			eqpanel.add(wpatk[i]);
			wppwr[i] = new JLabel("0");
			eqpanel.add(wppwr[i]);
		}
		eqpanel.add(new JLabel("Name"));
		eqpanel.add(new JLabel("Cost"));
		eqpanel.add(new JLabel("Max Dex"));
		eqpanel.add(new JLabel("AC"));
		eqpanel.add(new JLabel("Type"));
		eqpanel.add(new JLabel("Weight"));
		eqpanel.add(new Panel());
		for (int i=3;i<5;i++) {
			if (i==3)
				eqname[i] = new JTextField("Armour");
			else
				eqname[i] = new JTextField("Shield");
			eqpanel.add(eqname[i]);
			eqcost[i] = new JTextField("0");
			eqpanel.add(eqcost[i]);
			mxdex[i-3] = new JTextField("0");
			eqpanel.add(mxdex[i-3]);
			ac[i-3] = new JTextField("0");	
			eqpanel.add(ac[i-3]);
			if (i==3)
				eqtype[i] = new JComboBox<String>(current_unit.armour.armourtypes);
			else
				eqtype[i] = new JComboBox<String>(current_unit.armour.shieldtypes);
			eqpanel.add(eqtype[i]);
			eqwgt[i] = new JTextField("0");
			eqpanel.add(eqwgt[i]);
			eqpanel.add(new Panel());
		}
		//eqpanel.add(mountbtn);
	}
	
	public static void typePanelSetup() {
		typepanel.add(new JLabel("Type"));
		typeboxes[0] = new JComboBox<String>(Unit.types);
		typepanel.add(typeboxes[0]);
		typepanel.add(new JLabel("Subtype"));
		typeboxes[1] = new JComboBox<String>(Unit.subtypes);
		typepanel.add(typeboxes[1]);
		typepanel.add(new JLabel("Training"));
		typeboxes[2] = new JComboBox<String>(Unit.trainings);
		typepanel.add(typeboxes[2]);
		typepanel.add(new JLabel("Training Type"));
		typeboxes[3] = new JComboBox<String>(Unit.training_types);
		typepanel.add(typeboxes[3]);
		
		featlbl[0] = new JLabel("Feat 1");
		typepanel.add(featlbl[0]);
		feats[0] = new JComboBox<String>(current_unit.available_feats);
		typepanel.add(feats[0]);
		ufeatlbl[0] = new JLabel("Unit Feat 1");
		typepanel.add(ufeatlbl[0]);
		ufeats[0] = new JComboBox<String>(current_unit.available_unit_feats);
		typepanel.add(ufeats[0]);
		featlbl[1] = new JLabel("Feat 2");
		typepanel.add(featlbl[1]);
		feats[1] = new JComboBox<String>(current_unit.available_feats);
		typepanel.add(feats[1]);
		ufeatlbl[1] = new JLabel("Unit Feat 2");
		typepanel.add(ufeatlbl[1]);
		ufeats[1] = new JComboBox<String>(current_unit.available_unit_feats);
		typepanel.add(ufeats[1]);
		featlbl[2] = new JLabel("Feat 3");	
		typepanel.add(featlbl[2]);
		feats[2] = new JComboBox<String>(current_unit.available_feats);
		typepanel.add(feats[2]);
		for (int i=0;i<typeplaceholder.length;i++)
			typeplaceholder[i] = new JLabel("");
	}
	
	public static void outputPanelSetup() {
		for (int i=0;i<oplbl.length;i++) {
			oplbl[i] = new JLabel(outputtags[i]);
			outputpanel.add(oplbl[i]);
			op[i] = new JLabel("0");
			outputpanel.add(op[i]);
		}

		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCurrentUnit();
			}
		});
		outputpanel.add(savebtn);
		clrbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clean();
			}
		});
		outputpanel.add(clrbtn);
	}
	
	public static void init() {
		unitwindow.setSize(1200,500);
		unitwindow.setLocationRelativeTo(null);
		unitwindow.addWindowListener(new WindowAdapter() {//close program on closing window
			public void windowClosing(WindowEvent windowEvent){
				stop();
			}
		});

		current_unit = new Unit();
		racePanelSetup();
		mainpanel.add(racepanel);
		equipmentPanelSetup();
		mainpanel.add(eqpanel);
		typePanelSetup();
		mainpanel.add(typepanel);
		outputPanelSetup();
		mainpanel.add(outputpanel);
		
		unitwindow.add(mainpanel);
		/*
		for (int i=0;i<3;i++) {
			eqtype[i].removeAll();
			for (String s:current_unit.weapons[i].types)
				eqtype[i].addItem(s);
		}
		eqtype[3].removeAll();
		for (String s:current_unit.armour.armourtypes)
			eqtype[3].addItem(s);
		eqtype[4].removeAll();
		for (String s:current_unit.armour.shieldtypes)
			eqtype[4].addItem(s);*/
		update();
		MountWindow.initialize();
	}
	
	public static void updateComboBoxes() {
		String[] holder = new String[feats.length+ufeats.length];
		
		String tmprace = (String) race.getSelectedItem();//remove all items and save placeholders
		race.removeAllItems();
		for (int i=0;i<feats.length;i++){
			holder[i] = (String) feats[i].getSelectedItem();
			feats[i].removeAllItems();
		}
		for (int i=0;i<ufeats.length;i++) {
			holder[feats.length+i] = (String) ufeats[i].getSelectedItem();
			ufeats[i].removeAllItems();
		}
		
		for (Race r:UnitTab.listofraces)//add all available items
			race.addItem(r.name);
		for (int i=0;i<feats.length;i++) {
			for (String s:current_unit.available_feats)
				feats[i].addItem(s);
		}
		for (int i=0;i<ufeats.length;i++){
			for (String s:current_unit.available_unit_feats)
				ufeats[i].addItem(s);
		}
		
		race.setSelectedItem(tmprace);//set the previously selected item
		for (int i=0;i<feats.length;i++)
			feats[i].setSelectedItem(holder[i]);
		for (int i=0;i<ufeats.length;i++)
			ufeats[i].setSelectedItem(holder[feats.length+i]);
	}
	
	public static void updateRacePanel() {
		try {//update the racepanel
			if (current_unit.race.hasfixedabilities) {
				racepanel.remove(statlabel);
				racepanel.remove(stat);
				racepanel.remove(raceplaceholder);
			}else {
				racepanel.remove(mountbtn);
				racepanel.add(statlabel);
				racepanel.add(stat);
				racepanel.add(raceplaceholder);
				racepanel.add(mountbtn);
			}
			int k;
			for (int i=0;i<totstat.length;i++) {
				totstat[i].setText(Integer.toString(current_unit.stats[i]));
			}
			racepanel.revalidate();
		} catch (IndexOutOfBoundsException e) {
			//this should only be thrown on startup
		} catch (NullPointerException ex) {
			//this should only be thrown opening the unit window without any race selected (like first time opening that units window)
		}
	}
	
	public static void updateEquipmentPanel() {
		for (int i=0;i<current_unit.weapons.length;i++) {
			wpatk[i].setText(Integer.toString(current_unit.weapons[i].AB));
			wppwr[i].setText(Integer.toString(current_unit.weapons[i].AP));
		}
	}
	
	public static void updateTypePanel() {
		try {
			for (int i=0;i<2;i++) {
				typepanel.remove(feats[i]);
				typepanel.remove(featlbl[i]);
				typepanel.remove(ufeats[i]);
				typepanel.remove(ufeatlbl[i]);
				typepanel.remove(typeplaceholder[i]);
				typepanel.remove(typeplaceholder[2+i]);
			}
			typepanel.remove(feats[2]);
			typepanel.remove(featlbl[2]);
			int k = 0;
			if (current_unit.training.equals("Regular"))
				k = 1;
			else if (current_unit.training.equals("Elite"))
				k = 2;
			for (int i=0;i<k;i++) {
				typepanel.add(featlbl[i]);
				typepanel.add(feats[i]);
				typepanel.add(ufeatlbl[i]);
				typepanel.add(ufeats[i]);
			}
			if (k==1||k==0) {
				for (int i=k;i<feats.length-1;i++) {
					typepanel.add(featlbl[i]);
					typepanel.add(feats[i]);
					typepanel.add(typeplaceholder[2*i]);
					typepanel.add(typeplaceholder[2*i+1]);
				}
			}
			if (current_unit.race.feat) {
				typepanel.add(featlbl[2]);
				typepanel.add(feats[2]);
			} else
				feats[2].setSelectedItem("");
		} catch (NullPointerException e) {
		}
	}
	
	public static void updateOutputPanel() {
		op[0].setText(Integer.toString(current_unit.T));
		op[1].setText(Integer.toString(current_unit.W));
		op[2].setText(Integer.toString(current_unit.M));
		op[3].setText(Integer.toString(current_unit.C));
		op[4].setText(Integer.toString(current_unit.speed));
		op[5].setText(Integer.toString(current_unit.AC));
		op[6].setText(Integer.toString(current_unit.saves[0]));
		op[7].setText(Integer.toString(current_unit.saves[1]));
		op[8].setText(Integer.toString(current_unit.saves[2]));
		op[9].setText(Integer.toString(current_unit.training_cost));
		op[10].setText(Integer.toString(current_unit.equipment_cost));
		op[11].setText(Integer.toString(current_unit.mount_cost));
		op[12].setText(Integer.toString(current_unit.total_cost));
	}
	
	public static void visualsToUnit() {//visual layer->current_unit
		try {
			if (!(race.getSelectedItem()==null))
				current_unit.race = UnitTab.listofraces.get(Utility.findRace((String) race.getSelectedItem()));
			for (int i=0;i<6;i++) {
				current_unit.stats[i] = Integer.parseInt(pointbuy[i].getText());
				if (!(current_unit.race.hasfixedabilities)&&stat.getSelectedItem().equals(abilities[i]))
					current_unit.stats[i] += 2;
				if (current_unit.race.hasfixedabilities)
					current_unit.stats[i] += current_unit.race.stats[i];
			}
			current_unit.type = (String) typeboxes[0].getSelectedItem();
			current_unit.subtype = (String) typeboxes[1].getSelectedItem();
			current_unit.training = (String) typeboxes[2].getSelectedItem();
			current_unit.training_type = (String) typeboxes[3].getSelectedItem();
			for (int i=0;i<3;i++)
				current_unit.feat[i] = (String) feats[i].getSelectedItem();
			if (!current_unit.race.feat)
				current_unit.feat[2] = "";
			for (int i=0;i<2;i++)
				current_unit.unit_feat[i] = (String) ufeats[i].getSelectedItem();
			if (!current_unit.training.equals("Elite")) {
				current_unit.unit_feat[1] = "";
				if (!current_unit.training.equals("Regular"))
					current_unit.unit_feat[0] = "";
			}
			for (int i=0;i<3;i++) {
				current_unit.weapons[i].name = eqname[i].getText();
				current_unit.weapons[i].cost = Integer.parseInt(eqcost[i].getText());
				current_unit.weapons[i].damage_dice = Integer.parseInt(wpdice[i].getText());
				current_unit.weapons[i].type = (String) eqtype[i].getSelectedItem();
				current_unit.weapons[i].weight = Integer.parseInt(eqwgt[i].getText());
			}
			current_unit.armour.name = eqname[3].getText();
			current_unit.armour.cost = Integer.parseInt(eqcost[3].getText());
			current_unit.armour.max_dex = Integer.parseInt(mxdex[0].getText());
			current_unit.armour.ac = Integer.parseInt(ac[0].getText());
			current_unit.armour.type = (String) eqtype[3].getSelectedItem();
			current_unit.armour.weight = Integer.parseInt(eqwgt[3].getText());
			
			current_unit.shield.name = eqname[4].getText();
			current_unit.shield.cost = Integer.parseInt(eqcost[4].getText());
			current_unit.shield.max_dex = Integer.parseInt(mxdex[1].getText());
			current_unit.shield.ac = Integer.parseInt(ac[1].getText());
			current_unit.shield.type = (String) eqtype[4].getSelectedItem();
			current_unit.shield.weight = Integer.parseInt(eqwgt[4].getText());
		} catch (IndexOutOfBoundsException e) {
			//this should only be thrown on startup
			System.out.println("could not update visuals to unit: INDEXERROR");
		} catch (NullPointerException ex) {
			//this should only be thrown opening the unit window without any race selected (like first time opening that units window)
			System.out.println("could not update visuals to unit: NULLERROR");
		}
	}
	
	public static void update() {
		//update unit from visuals
		visualsToUnit();
		current_unit.updateUnit();
		//update visuals from unit
		updateRacePanel();
		updateEquipmentPanel();
		updateTypePanel();
		updateOutputPanel();
		updateComboBoxes();
		
	}
	
	public static void saveCurrentUnit() {//visual->current_unit//listofunits(NH)//savelayer
		//visual->current_unit
		update();
		//current_unit->listofunits
		
		
		//save listofunits
		
		String[] unitstr = new String[67];
		//variables from unit tab
		unitstr[0] = current_unit.name;
		unitstr[65] = current_unit.unit_lord;
		unitstr[66] = Integer.toString(current_unit.number_of_units);
		//variables from racepanel
		unitstr[1] = (String) race.getSelectedItem();
		for (int i=0;i<pointbuy.length;i++)
			unitstr[2+i] = pointbuy[i].getText();
		for (int i=0;i<typeboxes.length;i++)
			unitstr[8+i] = (String) typeboxes[i].getSelectedItem();
		for (int i=0;i<feats.length;i++)
			unitstr[12+i] = (String) feats[i].getSelectedItem();
		for (int i=0;i<ufeats.length;i++)
			unitstr[15+i] = (String) ufeats[i].getSelectedItem();
		for (int i=0;i<wpatk.length;i++) {
			unitstr[17+5*i] = eqname[i].getText();
			unitstr[18+5*i] = eqcost[i].getText();
			unitstr[19+5*i] = wpdice[i].getText();
			unitstr[20+5*i] = (String) eqtype[i].getSelectedItem();
			unitstr[21+5*i] = eqwgt[i].getText();
		}
		for (int i=0;i<ac.length;i++) {
			unitstr[32+6*i] = eqname[3+i].getText();
			unitstr[33+6*i] = eqcost[3+i].getText();
			unitstr[34+6*i] = mxdex[i].getText();
			unitstr[35+6*i] = ac[i].getText();
			unitstr[36+6*i] = (String) eqtype[3+i].getSelectedItem();
			unitstr[37+6*i] = eqwgt[3+i].getText();
		}
		unitstr[44] = "no_mount";//get mount to here later
		NationHandler.saveUnit(current_unit_number,current_unit);
	}
	
	public static void loadActiveUnit() {
		current_unit = NationHandler.listofunits.get(current_unit_number);
		unitwindow.setTitle(current_unit.name);
		loadUnitToVisuals();
	}
	
	public static void loadUnitToVisuals() {//takes whatever is in current_unit and puts into the visuals
		unitwindow.setTitle(current_unit.name);
		//racepanel
		race.setSelectedItem(current_unit.race.name);
		for (int i=0;i<pointbuy.length;i++) 
			pointbuy[i].setText(Integer.toString(current_unit.stats[i]));
		//typepanel
		typeboxes[0].setSelectedItem(current_unit.type);
		typeboxes[1].setSelectedItem(current_unit.subtype);
		typeboxes[2].setSelectedItem(current_unit.training);
		typeboxes[3].setSelectedItem(current_unit.training_type);
		for (int i=0;i<feats.length;i++)
			feats[i].setSelectedItem(current_unit.feat[i]);
		for (int i=0;i<ufeats.length;i++)
			ufeats[i].setSelectedItem(current_unit.unit_feat[i]);
		//equipmentpanel
		for (int i=0;i<current_unit.weapons.length;i++) {
			eqname[i].setText(current_unit.weapons[i].name);
			eqcost[i].setText(Integer.toString(current_unit.weapons[i].cost));
			wpdice[i].setText(Integer.toString(current_unit.weapons[i].damage_dice));
			eqtype[i].setSelectedItem(current_unit.weapons[i].type);
			eqwgt[i].setText(Integer.toString(current_unit.weapons[i].weight));
			wpatk[i].setText(Integer.toString(current_unit.weapons[i].AB));
			wppwr[i].setText(Integer.toString(current_unit.weapons[i].AP));
		}
		eqname[3].setText(current_unit.armour.name);
		eqcost[3].setText(Integer.toString(current_unit.armour.cost));
		mxdex[0].setText(Integer.toString(current_unit.armour.max_dex));
		ac[0].setText(Integer.toString(current_unit.armour.ac));
		eqtype[3].setSelectedItem(current_unit.armour.type);
		eqwgt[3].setText(Integer.toString(current_unit.armour.weight));
		eqname[4].setText(current_unit.shield.name);
		eqcost[4].setText(Integer.toString(current_unit.shield.cost));
		mxdex[1].setText(Integer.toString(current_unit.shield.max_dex));
		ac[1].setText(Integer.toString(current_unit.shield.ac));
		eqtype[4].setSelectedItem(current_unit.shield.type);
		eqwgt[4].setText(Integer.toString(current_unit.shield.weight));
		//outputpanel
		op[0].setText(Integer.toString(current_unit.T));
		op[1].setText(Integer.toString(current_unit.W));
		op[2].setText(Integer.toString(current_unit.M));
		op[3].setText(Integer.toString(current_unit.C));
		op[4].setText(Integer.toString(current_unit.speed));
		op[5].setText(Integer.toString(current_unit.AC));
		op[6].setText(Integer.toString(current_unit.saves[0]));
		op[7].setText(Integer.toString(current_unit.saves[1]));
		op[8].setText(Integer.toString(current_unit.saves[2]));
		op[9].setText(Integer.toString(current_unit.training_cost));
		op[10].setText(Integer.toString(current_unit.equipment_cost));
		op[11].setText(Integer.toString(current_unit.mount_cost));
		op[12].setText(Integer.toString(current_unit.total_cost));
		MountWindow.setWindow(current_unit);
	}
	
	public static void clean() {
		current_unit = new Unit();
		loadUnitToVisuals();
	}
	
	public static void start(int unit) {
		current_unit_number = unit;
		loadActiveUnit();
		update();
		unitwindow.setVisible(true);
	}
	
	public static void stop() {
		unitwindow.setVisible(false);
		MountWindow.stop();
		RaceWindow.stop();
	}	
}