package NewGUITest;

public class Culture {

	public static double[] culture_bonuses;//culture bonuses
	public static double[] used_bonus;
	public static String[] culture_names =  {"Tax Efficiency (%)","Production Efficiency (%)","Trade Efficiency (%)","Vassal Tax Income Efficiency (%)","Plunder Efficinecy (%)",
			"Bank Efficiency (%)","Base Production (%)","Unit Cap (%)","Unit Speed","Unit Damage","Hit","AC","Morale","Command","Ranged Hit","Legitimacy Checks","Unrest Checks",
			"RGO/Road Cost (%)","Building Cost (%)","Settlement Upkeep Cost (%)","Settlement Upgrade Cost (%)","Unit Training Cost (%)","Unit Equipment Cost (%)",
			"Fortification Cost (%)","Mage Guild Cost (%)","Druid Guild Cost (%)","Spy Guild Cost (%)","Tinker Guild Cost (%)","All Guilds Cost (%)"};
			
			
			
			//reducers are (-20), boni are (20)

	int k;
	
	public Culture() {
		
	}
	
	public void getCulture() {//extracting all the culture modifiers
		for (int i=0;i<culture_names.length;i++) {//from visual layer	
			NationHandler getter = new NationHandler();
			culture_bonuses[i] = Double.parseDouble(getter.listoflords.get(k).panes.culturefields.get(i).getText())*0.01;
			if (i<8)
				used_bonus[i] = culture_bonuses[i];
			else if (i>16)
				used_bonus[i] = 1+culture_bonuses[i];
			else
				used_bonus[i] = culture_bonuses[i]*100;
		}
	}
	
	public String[] readCulture() {//just print out the fields to the save file
		NationHandler getter = new NationHandler();
		String[] reads = new String[culture_names.length];
		for (int i=0;i<culture_names.length;i++)
			reads[i] = getter.listoflords.get(k).panes.culturefields.get(i).getText();
		return reads;
	}
	
	public void loadCulture(String[] boni) {//only ever called when loading, nothing to do with visual layer
		NationHandler getter = new NationHandler();//we get where the overlord is first!
		createCulture();
		for (int i=0;i<getter.listoflords.size();i++){
			if (getter.listoflords.get(i).title.equals("overlord"))
				k = i;
		}
		for (int i=0;i<culture_names.length;i++)
			culture_bonuses[i] = Integer.parseInt(boni[i])*0.01;
	}
	
	public void setCulture() {	
		NationHandler getter = new NationHandler();
		getter.listoflords.get(k).panes.setCulturePane(culture_bonuses);
	}
	
	public void createCulture() {
		culture_bonuses = new double[culture_names.length];
		used_bonus = new double[culture_names.length];
	}
}