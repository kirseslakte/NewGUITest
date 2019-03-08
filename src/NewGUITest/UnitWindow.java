package NewGUITest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class UnitWindow {
	static JFrame unitwindow = new JFrame();
	static Panel mainpanel = new Panel(new GridLayout(2,2));
	
	static int current_unit_number;
	static Unit current_unit;

	static GridBagConstraints c = new GridBagConstraints();
	
	static Panel racepanel = new Panel(new GridBagLayout());
	static JComboBox<String> race;
	static Button[] racebtn = new Button[2];
	static JTextField[] pointbuy = new JTextField[6];
	static JLabel[] statmod = new JLabel[6];
	
	static Panel typepanel = new Panel(new GridLayout(0,4));
	static JComboBox<String>[] typeboxes = new JComboBox[4];
	static JLabel[] featlbl = new JLabel[3];
	static JComboBox<String>[] feats = new JComboBox[3];
	static JLabel[] ufeatlbl = new JLabel[2];
	static JComboBox<String>[] ufeats = new JComboBox[2];
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
	static Button mountbtn = new Button("Mount");
	
	static Panel outputpanel = new Panel(new GridLayout(0,4));
	static JLabel[] oplbl = new JLabel[13];
	static JLabel[] op = new JLabel[13];
	static String[] outputtags = {"Toughness","Wounds","Morale","Command","Speed","AC","Fort","Ref","Will","Training Cost","Equipment Cost",
			"Mount Cost","Total Cost"};
	
	public UnitWindow() {
		
	}
	
	public static void racePanelSetup() {
		c.gridy = 0;
		c.gridx = 0;
		race = new JComboBox<String>();
		for (Race r:UnitTab.listofraces)
			race.addItem(r.name);
		racepanel.add(race,c);
		c.gridx = 1;
		racebtn[0] = new Button("Add Race");
		racebtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceWindow.start();
			}
		});
		racepanel.add(racebtn[0],c);
		c.gridx = 2;
		racebtn[1] = new Button("Race List");
		racebtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceWindow.loadStart();
			}
		});
		racepanel.add(racebtn[1],c);
		c.gridx = 0;
		c.gridy = 1;
		racepanel.add(new JLabel("Ability"),c);
		c.gridy = 2;
		racepanel.add(new JLabel("Str"),c);
		c.gridy = 3;
		racepanel.add(new JLabel("Dex"),c);
		c.gridy = 4;
		racepanel.add(new JLabel("Con"),c);
		c.gridy = 5;
		racepanel.add(new JLabel("Int"),c);
		c.gridy = 6;
		racepanel.add(new JLabel("Wis"),c);
		c.gridy = 7;
		racepanel.add(new JLabel("Cha"),c);
		c.gridy = 1;
		c.gridx = 1;
		racepanel.add(new JLabel("Point-buy Score"),c);
		for (int i=0;i<pointbuy.length;i++) {
			c.gridx = 1;
			c.gridy ++;
			pointbuy[i] = new JTextField("0");
			racepanel.add(pointbuy[i],c);
			c.gridx = 2;
			statmod[i] = new JLabel("-5");
			racepanel.add(statmod[i],c);
		}
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
		mountbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get to the mountbit
			}
		});
		eqpanel.add(mountbtn);
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
				update();
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
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;

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
		
		typeboxes[0] = new JComboBox<String>();
		for (String s:Unit.types)
			typeboxes[0].addItem(s);
		typeboxes[1] = new JComboBox<String>();
		for (String s:Unit.subtypes)
			typeboxes[1].addItem(s);
		typeboxes[2] = new JComboBox<String>();
		for (String s:Unit.trainings)
			typeboxes[2].addItem(s);
		typeboxes[3] = new JComboBox<String>();
		for (String s:Unit.training_types)
			typeboxes[3].addItem(s);
		
		for (int i=0;i<3;i++) {
			eqtype[i] = new JComboBox<String>();
			for (String s:current_unit.armour.armourtypes)
				eqtype[i].addItem(s);
		}
		for (int i=3;i<5;i++) {
			eqtype[i] = new JComboBox<String>();
			for (String s:current_unit.armour.shieldtypes)
				eqtype[i].addItem(s);
		}
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
	
	public static void updateTypePanel() {
		/*
		if (current_unit.race.feat) {
			if (!(feats[2].getParent() == typepanel)) {
				typepanel.add(featlbl[2]);
				typepanel.add(feats[2]);
			}
		} else {
			if (feats[2].getParent() == typepanel) {
				typepanel.remove(featlbl[2]);
				typepanel.remove(feats[2]);
			}
		}
		if (current_unit.training.equals(Unit.trainings[0])) {//irregulars
			for (int i=0;i<ufeats.length;i++) {
				if (ufeats[i].getParent() == typepanel) {
					typepanel.remove(ufeats[i]);
					typepanel.remove(ufeatlbl[i]);
				}
			}
		} else if(current_unit.training.equals(Unit.trainings[1])) {//regulars
			if (!(ufeats[0].getParent() == typepanel)) {
				typepanel.remove(ufeats[0]);
				typepanel.remove(ufeatlbl[0]);
			}			
			if (ufeats[1].getParent() == typepanel) {
				typepanel.remove(ufeats[1]);
				typepanel.remove(ufeatlbl[1]);
			}
		} else {//elites
			for (int i=0;i<ufeats.length;i++) {
				if (!(ufeats[i].getParent() == typepanel)) {
					typepanel.add(ufeats[i]);
					typepanel.add(ufeatlbl[i]);
				}
			}
		}*/
		for (int i=0;i<feats.length;i++) {
			
		}
	}
	
	public static void calculateOutputs() {//calculates all the calculated values of the unit and 
										   //displays it in the appropriate visual context
	}
	
	public static void update() {
		//update unit from visuals
		
		//update visuals from unit
		//current_unit.updateUnit();
		updateComboBoxes();
	}
	
	public static void saveCurrentUnit() {
		//get all data from visual layer into current_unit
		String[] unitstr = new String[75];
		//variables from unit tab
		unitstr[0] = current_unit.name;
		unitstr[73] = current_unit.unit_lord;
		unitstr[74] = Integer.toString(current_unit.number_of_units);
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
		for (int i=0;i<pointbuy.length;i++) {
			pointbuy[i].setText(Integer.toString(current_unit.stats[i]));
			statmod[i].setText(Integer.toString(current_unit.stat_mods[i]));
		}
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
	}
	
	public static void clean() {
		current_unit = new Unit();
		loadUnitToVisuals();
	}
	
	public static void start(int unit) {
		current_unit_number = unit;
		loadActiveUnit();
		unitwindow.setVisible(true);
	}
	
	public static void stop() {
		unitwindow.setVisible(false);
	}
}