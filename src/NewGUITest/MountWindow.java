package NewGUITest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MountWindow {
	
	static JFrame frame = new JFrame();
	static JPanel basepanel = new JPanel(new GridLayout(2,1));
	
	static JPanel statspanel = new JPanel(new GridLayout(0,4));
	static JLabel[] lbl = new JLabel[13];
	static JComboBox[] cmb = new JComboBox[5];
	static JTextField[] fields = new JTextField[8];
	static JRadioButton undead = new JRadioButton();
	
	static JPanel armourpanel = new JPanel(new GridLayout(0,6));
	static JComboBox<String> type = new JComboBox<String>();
	static JTextField[] armourfields = new JTextField[5];
	static JButton save = new JButton("Save");
	
	static String[] lbltags = {"Type","Leggedness","Size","HD","Natural Armour","Number of HD","Natural Attack Dice","Strength","Number of "
			+ "Natural Attacks","Dexterity","Speed","Constitution","Price of One Mount (GP)"};
	static String[] armourlbltags = {"Armour","Cost (GP)","Max Dex","AC","Type","Weight"};
	static String[] typealt = {"Animal","Magical Beast"};
	static String[] typealt = {"Magical Beast","Animal"};
	static String[] HDalt = {"d4","d6","d8","d10","d12"};
	static String[] damage = {"1","1d2","1d3","1d4","1d6","1d8","1d10","2d6","2d8","3d6","3d8","4d6"};
		
	public MountWindow() {
		
	}
	
	public static void initialize() {
		frame.setTitle("Mount Designer");
		frame.setSize(500,400);
		frame.setLocationRelativeTo(null);
		
		cmb[0] = new JComboBox<String>(typealt);
		cmb[1] = new JComboBox<String>(Race.footing);
		cmb[2] = new JComboBox<String>(Race.sizes);
		cmb[2].setSelectedItem("Large");
		cmb[3] = new JComboBox<String>(HDalt);
		cmb[4] = new JComboBox<String>(damage);
		for (int i=0;i<4;i++) {
			lbl[i] = new JLabel(lbltags[i]);
			statspanel.add(lbl[i]);
			statspanel.add(cmb[i]);
		}
		int k = 0;
		for (int i=4;i<lbltags.length;i++) {
			lbl[i] = new JLabel(lbltags[i]);
			statspanel.add(lbl[i]);
			if (i==6) {
				statspanel.add(cmb[4]);
			}else {
				fields[k] = new JTextField("");
				statspanel.add(fields[k]);
				k++;
			}
		}
		lbl[12].setToolTipText("The cost of one mount, excluding armour and other training, e.g. a combat-trained heavy horse would cost 300 gp");
		statspanel.add(new JLabel("Undead"));
		statspanel.add(undead);
		basepanel.add(statspanel);
		
		type = new JComboBox<String>(UnitWindow.current_unit.armour.armourtypes);
		for (int i=0;i<armourlbltags.length;i++)
			armourpanel.add(new JLabel(armourlbltags[i]));
		for (int i=0;i<4;i++) {
			armourfields[i] = new JTextField("");
			armourpanel.add(armourfields[i]);
		}
		armourpanel.add(type);
		armourfields[4] = new JTextField("");
		armourpanel.add(armourfields[4]);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ){
				saveWindow();
			}
		});
		armourpanel.add(save);
		basepanel.add(armourpanel);
		
		frame.add(basepanel);
	}
	
	public static void setWindow(Unit unit) {
		cmb[0].setSelectedItem(unit.mount.type);
		cmb[1].setSelectedItem(unit.mount.footing);
		cmb[2].setSelectedItem(unit.mount.size);
		cmb[3].setSelectedItem(unit.mount.hd_type);
		cmb[4].setSelectedIndex(unit.mount.damage_dice);
		fields[0].setText(Integer.toString(unit.mount.natural_armour));
		fields[1].setText(Integer.toString(unit.mount.number_of_hd));
		fields[2].setText(Integer.toString(unit.mount.stats[0]));
		fields[3].setText(Integer.toString(unit.mount.number_of_attacks));
		fields[4].setText(Integer.toString(unit.mount.stats[1]));
		fields[5].setText(Integer.toString(unit.mount.base_speed));
		fields[6].setText(Integer.toString(unit.mount.stats[2]));
		fields[7].setText(Integer.toString(unit.mount.cost_of_one_mount));
		undead.setSelected(unit.mount.undead);
		
		armourfields[0].setText(unit.mount.armour.name);
		armourfields[1].setText(Integer.toString(unit.mount.armour.cost));
		armourfields[2].setText(Integer.toString(unit.mount.armour.max_dex));
		armourfields[3].setText(Integer.toString(unit.mount.armour.ac));
		armourfields[4].setText(Integer.toString(unit.mount.armour.weight));
		type.setSelectedItem(unit.mount.armour.type);
	}
	
	public static void saveWindow() {
		if (undead.isSelected())
			lbl[11].setText("Charisma");
		else
			lbl[11].setText("Constitution");
		frame.revalidate();
		Unit unit = UnitWindow.current_unit;
		unit.mount.type = (String) cmb[0].getSelectedItem();
		unit.mount.footing = (String) cmb[1].getSelectedItem();
		unit.mount.size = (String) cmb[2].getSelectedItem();
		unit.mount.hd_type = (String) cmb[3].getSelectedItem();
		unit.mount.damage_dice = cmb[4].getSelectedIndex();
		unit.mount.natural_armour = Integer.parseInt(fields[0].getText());
		unit.mount.number_of_hd = Integer.parseInt(fields[1].getText());
		unit.mount.stats[0] = Integer.parseInt(fields[2].getText());
		unit.mount.number_of_attacks = Integer.parseInt(fields[3].getText());
		unit.mount.stats[1] = Integer.parseInt(fields[4].getText());
		unit.mount.base_speed = Integer.parseInt(fields[5].getText());
		unit.mount.stats[2] = Integer.parseInt(fields[6].getText());
		unit.mount.cost_of_one_mount = Integer.parseInt(fields[7].getText());
		unit.mount.undead = undead.isSelected();
		
		unit.mount.armour.name = armourfields[0].getText();
		unit.mount.armour.cost = Integer.parseInt(armourfields[1].getText());
		unit.mount.armour.max_dex = Integer.parseInt(armourfields[2].getText());
		unit.mount.armour.ac = Integer.parseInt(armourfields[3].getText());
		unit.mount.armour.weight = Integer.parseInt(armourfields[4].getText());
		unit.mount.armour.type = (String) type.getSelectedItem();
		NationHandler.saveUnit(UnitWindow.current_unit_number,unit);
		UnitWindow.loadActiveUnit();
	}
	
	public static void stop() {
		frame.setVisible(false);
	}
	
	public static void start() {
		frame.setVisible(true);
	}
}