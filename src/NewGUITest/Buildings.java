package NewGUITest;



public class Buildings{
	
	public String[] available_buildings = {"RGO","Road","Highway","Armoury","Signal Towers","Hospital","Supply Cache","Bureau",
			"Center of Trade","Palace","Public Order","Port"};
	public String[] available_fortifications = {"Hill Fort","Pallisade","Castle","Fortress","Citadel","Aerial Defenses"};
	public String[] available_add_ons = {"Moat","Motte","Glacis","Curtain Wall","Keep"};
	public String[] available_walls = {"Primitive","Basic","Advanced"};
	public String[] available_wall_add_ons = {"Bastion","Standard Wood Gate","Heavy Wood Gate","Reinforced Wood Gate","Standard Iron Gate",
			"Heavy Iron Gate","Reinforced Iron Gate"};
	public int[] building_costs = {1000,1500,0,0,250,50,500,200,100,400,5000,7500,10000,200,400,500};//rgo1,2,rd,hw,ar,st,h,sc,b,cot,p1,2,3,po1,2,port
	public int[] fortifications_costs = {100,125,200,400,800,50};//hill,pall,cast,fort,cita,aeri def
	public int[] add_ons_costs = {10,10,10,20,20};//(%) moat,motte,glacis,curtain,keep
	public int[] walls_costs = {100,125,175};//prim,bas,adv
	public int[] walls_add_costs = {200,100,200,300,400,500,600};//bas,std w,h w,rf w,std i,h i,rf i
	
	
	
	public Buildings() {
		
	}
}