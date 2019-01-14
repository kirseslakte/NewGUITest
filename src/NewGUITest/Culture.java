package NewGUITest;

import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Culture {

	public static double[] culture_bonuses;//culture bonuses
	public static double[] used_bonus;
	public static String[] culture_names =  {"Tax Efficiency (%)","Production Efficiency (%)","Trade Efficiency (%)","Vassal Tax Income Efficiency (%)","Plunder Efficinecy (%)",
			"Bank Efficiency (%)","Base Production (%)","Unit Cap (%)","Unit Speed","Unit Damage","Hit","AC","Morale","Command","Ranged Hit","Legitimacy Checks","Unrest Checks",
			"RGO/Road Cost (%)","Building Cost (%)","Settlement Upkeep Cost (%)","Settlement Upgrade Cost (%)","Unit Training Cost (%)","Unit Equipment Cost (%)",
			"Fortification Cost (%)","Mage Guild Cost (%)","Druid Guild Cost (%)","Spy Guild Cost (%)","Tinker Guild Cost (%)","All Guilds Cost (%)"};
			
			
			
			//reducers are (-20), boni are (20)

	public static int k;
	//culture variables
	public static List<JTextField> culturefields = new ArrayList<JTextField>();
	
	public Culture() {
		
	}
	public static Panel culturePane() {//culture panel
		System.out.println("LORDPANE! culturePane");
		Panel culture_panel = new Panel(new GridLayout(0,4));
		for (int i=0;i<culture_names.length;i++){
			culturefields.add(new JTextField("0"));
			culture_panel.add(new JLabel(culture_names[i]));
			culture_panel.add(culturefields.get(i));
		}
		return culture_panel;
	}
	
	public static void setCulturePane(double[] s) {//setting culture pane
		System.out.println("LORDPANE! setCulturePane");
		for (int i=0;i<culture_names.length;i++)//only ever called from lord.setCulture()
			culturefields.get(i).setText(Integer.toString((int) (s[i]*100)));
	}
	
	public static void getCulture() {//extracting all the culture modifiers
		System.out.println("CULTURE! getCulture");
		for (int i=0;i<culture_names.length;i++) {//from visual layer	
			culture_bonuses[i] = Double.parseDouble(culturefields.get(i).getText())*0.01;
			if (i<8)
				used_bonus[i] = culture_bonuses[i];
			else if (i>16)
				used_bonus[i] = 1+culture_bonuses[i];
			else
				used_bonus[i] = culture_bonuses[i]*100;
		}
	}
	
	public static String[] readCulture() {//just print out the fields to the save file
		System.out.println("CULTURE! readCulture");
		String[] reads = new String[culture_names.length];
		for (int i=0;i<culture_names.length;i++)
			reads[i] = culturefields.get(i).getText();
		return reads;
	}
	
	public static void loadCulture(String[] boni) {//only ever called when loading, nothing to do with visual layer
		System.out.println("CULTURE! loadCulture");
		createCulture();
		for (int i=0;i<NationHandler.listoflords.size();i++){
			if (NationHandler.listoflords.get(i).title.equals("overlord"))
				k = i;
		}
		for (int i=0;i<culture_names.length;i++)
			culture_bonuses[i] = Integer.parseInt(boni[i])*0.01;
	}
	
	public static void setCulture() {	
		System.out.println("CULTURE! setCulture");
		setCulturePane(culture_bonuses);
	}
	
	public static void createCulture() {
		System.out.println("CULTURE! createCulture");
		culture_bonuses = new double[culture_names.length];
		used_bonus = new double[culture_names.length];
	}
}