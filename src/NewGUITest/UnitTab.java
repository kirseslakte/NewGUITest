package NewGUITest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class UnitTab {
	
	static Panel unitpanel = new Panel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	static int[] widths = {4,1,1,3,1,2};
	public static List<JTextField> names = new ArrayList<JTextField>();
	public static List<JLabel> costs = new ArrayList<JLabel>();
	public static List<JLabel> upkeeps = new ArrayList<JLabel>();
	public static List<JComboBox> lords = new ArrayList<JComboBox>();
	public static List<JTextField> amounts = new ArrayList<JTextField>();
	public static List<Button> designbuttons = new ArrayList<Button>();
	public static UnitWindow unitwindow = new UnitWindow();
	public static List<Race> listofraces = new ArrayList<Race>();
	static String[] listoflords;
	static Button addrow;
	static Button copyrow;
	
	public UnitTab() {
		
	}
	
	public static Panel unitTab() {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridy = 0;
		c.gridwidth = widths[0];
		c.gridx = 0;
		unitpanel.add(new JLabel("Unit Name"),c);
		c.gridwidth = widths[1];
		c.gridx = widths[0];
		unitpanel.add(new JLabel("Cost of one Unit"),c);
		c.gridwidth = widths[2];
		c.gridx += widths[1];
		unitpanel.add(new JLabel("Upkeep of Units"),c);
		c.gridwidth = widths[3];
		c.gridx += widths[2];
		unitpanel.add(new JLabel("Owner"),c);
		c.gridwidth = widths[4];
		c.gridx += widths[3];
		unitpanel.add(new JLabel("Number"),c);
		c.gridwidth = widths[5];
		c.gridx += widths[4];
		unitpanel.add(new JLabel("Unit Design Window"),c);
		addRow();
		return unitpanel;
	}
	
	public static void addRow() {
		listoflords = new String[NationHandler.listoflords.size()];//updating the lord drop down list
		for (int i=0;i<listoflords.length;i++)
			listoflords[i] = NationHandler.listoflords.get(i).name;
		for (int i=0;i<lords.size();i++) {
			lords.get(i).removeAllItems();
			for (String s:listoflords)
				lords.get(i).addItem(s);
		}
		if (names.size()==0||!(names.get(names.size()-1).getText().equals(""))) {
			try {
				unitpanel.remove(addrow);
				unitpanel.remove(copyrow);
			} catch (NullPointerException e) {
				
			}
			NationHandler.listofunits.add(new Unit());
			c.gridy = names.size()+1;
			names.add(new JTextField(""));
			c.gridx = 0;
			c.gridwidth = widths[0];
			unitpanel.add(names.get(names.size()-1),c);
			costs.add(new JLabel(""));
			c.gridwidth = widths[1];
			c.gridx = widths[0];
			unitpanel.add(costs.get(costs.size()-1),c);
			upkeeps.add(new JLabel(""));
			c.gridwidth = widths[2];
			c.gridx += widths[1];
			unitpanel.add(upkeeps.get(upkeeps.size()-1),c);
			lords.add(new JComboBox<>(listoflords));
			c.gridwidth = widths[3];
			c.gridx += widths[2];
			unitpanel.add(lords.get(lords.size()-1),c);
			amounts.add(new JTextField(""));
			c.gridwidth = widths[4];
			c.gridx += widths[3];
			unitpanel.add(amounts.get(amounts.size()-1),c);
			designbuttons.add(new Button("Unit Design"));
			int k = designbuttons.size()-1;
			designbuttons.get(k).addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					setUnits();
					if (!unitwindow.isVisible())
						unitwindow.start(k);
				}
			});
			c.gridwidth = widths[5];
			c.gridx += widths[4];
			unitpanel.add(designbuttons.get(designbuttons.size()-1),c);
			c.gridy = names.size()+1;
			c.gridwidth = 1;
			c.gridx = 0;
			addrow = new Button("Add Row/Update");
			addrow.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					addRow();
				}
			});
			unitpanel.add(addrow,c);
			c.gridx = 1;
			copyrow = new Button("Copy Existing Unit");
			copyrow.addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					copyRow();
				}
			});
			unitpanel.add(copyrow,c);
			unitpanel.revalidate();
		}else {
			for (int i=1;i<names.size();i++) {
				if ((names.get(i-1).getText().equals("")&&names.get(i).getText().equals(""))||
						(i!=names.size()-1&&names.get(i).getText().equals(""))) {
					unitpanel.remove(names.get(i));
					unitpanel.remove(costs.get(i));
					unitpanel.remove(upkeeps.get(i));
					unitpanel.remove(lords.get(i));
					unitpanel.remove(amounts.get(i));
					unitpanel.remove(designbuttons.get(i));
					names.remove(i);
					costs.remove(i);
					upkeeps.remove(i);
					lords.remove(i);
					amounts.remove(i);
					designbuttons.remove(i);
					NationHandler.listofunits.remove(i);
					i--;
				}
			}
		}
		
	}
	
	public static void copyRow() {
		String[] unitalternatives = new String[names.size()];
		for (int i=0;i<names.size();i++)
			unitalternatives[i] = names.get(i).getText();
		String s = (String) JOptionPane.showInputDialog(unitpanel,"Choose which unit to copy","Copy Unit",JOptionPane.PLAIN_MESSAGE
				,null,unitalternatives,unitalternatives[0]);
		if (s != null&& (s.length()>0)) {
			addRow();
			names.get(names.size()-1).setText(s);
			NationHandler.listofunits.get(NationHandler.listofunits.size()-1).setUnit(
					NationHandler.listofunits.get(Utility.findUnit(s)).getUnit());
		}
	}
	
	public static void loadUnits() {//loads units from the listofunits in the nationhandler and makes neat rows for all of them
		for (int i=0;i<NationHandler.listofunits.size();i++) {
			names.get(i).setText(NationHandler.listofunits.get(i).name);
			lords.get(i).setSelectedItem(NationHandler.listofunits.get(i).unit_lord);
			amounts.get(i).setText(Integer.toString(NationHandler.listofunits.get(i).number_of_units));
			addRow();
		}
	}
	
	public static void setUnits() {//set name/lord/amount of units
		for (int i=0;i<NationHandler.listofunits.size();i++) {
			NationHandler.listofunits.get(i).name = (String) names.get(i).getText();
			NationHandler.listofunits.get(i).unit_lord = (String) lords.get(i).getSelectedItem();
			NationHandler.listofunits.get(i).number_of_units = (int) Integer.parseInt(amounts.get(i).getText());
		}
	}
	
	public static void save() {
		
	}
	
	public static void getUnits() {
		
	}
}