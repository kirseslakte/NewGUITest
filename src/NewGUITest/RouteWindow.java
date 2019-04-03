package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class RouteWindow extends JFrame{
	
	public List<Route> listofroutes = new ArrayList<Route>();
	
	public List<JButton> names = new ArrayList<JButton>();
	public List<JLabel> trade_value = new ArrayList<JLabel>();
	public List<JLabel> official = new ArrayList<JLabel>();
	public List<JButton> remove = new ArrayList<JButton>();

	public Lord lord;
	
	Panel basepanel = new Panel(new GridBagLayout());
    Button add_route = new Button("Add Trade Route");
    Button save = new Button("Save Trade Routes");
	//Panel main = new Panel(new GridLayout(1,2));//panel with all routes
	
	JFrame route = new JFrame();
	Panel routepanel = new Panel(new GridBagLayout());//panel with the currently displayed route
	JTextField route_name = new JTextField("Name of Route");
	JComboBox<String> route_official = new JComboBox<>();
	JTextField[] tar_pbp_ptar = new JTextField[3];
    JButton close = new JButton("Save&Close");
	int active_route = -1;
	
	public RouteWindow() {
		
	}
	
	public void initialize(Lord lord) {
		//System.out.println("ROUTEWINDOW! initialize");
		this.lord = lord;
		this.setFrame();
		this.initializeRoute();
	}
	
	public void setFrame() {
		//System.out.println("ROUTEWINDOW! setFrame");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("Trade routes of "+this.lord.name);
	    this.addWindowListener(new WindowAdapter() {//close frame on closing window
			public void windowClosing(WindowEvent windowEvent){
				saveRoutes();
				NationHandler.getRoutes(lord.name);
			}
		});
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    constraints.gridy = 0;
	    constraints.gridx = 0;
	    constraints.weighty = 0.05;
	    this.basepanel.add(new JLabel("Route Name"),constraints);
	    constraints.gridx = 1;
	    this.basepanel.add(new JLabel("Trade Value"),constraints);
	    constraints.gridx = 2;
	    this.basepanel.add(new JLabel("Supervising Official"),constraints);
	    this.addRoute(true);
	    this.add_route.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		addRoute(false);
	    		//System.out.println("Adding route");
	    	}
	    });
	    this.save.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		saveRoutes();
	    		//System.out.println("Saving route");
	    	}
	    });
	    this.add(this.basepanel);
	}
	
	public void setJobsList() {
		//System.out.println("ROUTEWINDOW! setActiveRoutes");
		this.route_official.removeAllItems();
		this.route_official.addItem("Passive");
		for (Official o:this.lord.official.listofofficials) {
			if (o.rollhelper==3) {
				String ok = o.name+" ("+o.effect+")";
				this.route_official.addItem(ok);
			}
		}
		this.routepanel.revalidate();
	}
	
	public void addRoute(boolean startup) {
		//System.out.println("ROUTEWINDOW! addRoute");
		String[] name = {"Trade Route "+(this.names.size()+1),this.lord.name," ","0","0","0"};
		this.listofroutes.add(new Route(name));
		this.names.add(new JButton(name[0]));
		int route_index = this.names.size()-1;
		this.names.get(this.names.size()-1).addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				startRoute(route_index);
			}
		});
	    this.trade_value.add(new JLabel("0"));
	    this.official.add(new JLabel(" "));
	    this.remove.add(new JButton("Remove Route"));
	    this.remove.get(this.remove.size()-1).addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		removeRoute(route_index);
	    	}
	    });
	    this.basepanel.remove(this.add_route);
	    this.basepanel.remove(this.save);
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    constraints.weighty = 1;
	    constraints.gridy = this.names.size();
	    constraints.gridx = 0;
	    this.basepanel.add(this.names.get(this.names.size()-1),constraints);
	    constraints.gridx++;
	    this.basepanel.add(this.trade_value.get(this.trade_value.size()-1),constraints);
	    constraints.gridx++;
	    this.basepanel.add(this.official.get(this.official.size()-1),constraints);
	    constraints.gridx++;
	    this.basepanel.add(this.remove.get(this.remove.size()-1),constraints);
	    constraints.gridx = 0;
	    constraints.gridy++;
	    constraints.weighty = 0.05;
	    this.basepanel.add(this.add_route,constraints);
	    constraints.gridx++;
	    this.basepanel.add(this.save,constraints);
	    if (!startup)
	    	this.updateRoutes();
    	this.revalidate();
    	//System.out.println("Added route index "+(this.names.size()-1));
	}
	
	public void removeRoute(int route_index) {
		//System.out.println("Removing route index "+route_index);
		List<Route> templist = new ArrayList<Route>();
		for (int i=this.listofroutes.size()-1;i>=route_index;i--) {
			if (i != route_index)
				templist.add(this.listofroutes.get(i));
			this.basepanel.remove(this.names.get(i));
			this.basepanel.remove(this.trade_value.get(i));
			this.basepanel.remove(this.official.get(i));
			this.basepanel.remove(this.remove.get(i));
			this.names.remove(i);
			this.trade_value.remove(i);
			this.official.remove(i);
			this.remove.remove(i);
			this.listofroutes.remove(i);
			//System.out.println("removed route index "+i);
		}
		for (int i=templist.size()-1;i>=0;i--) {
			this.addRoute(false);
			this.listofroutes.remove(this.listofroutes.size()-1);
			this.listofroutes.add(templist.get(i));
			//System.out.println("added route index "+(this.listofroutes.size()-1));
		}
		this.updateRoutes();
		this.basepanel.revalidate();
	}
	
	public void saveActiveRoute() {//visual->code/save
		this.listofroutes.get(this.active_route).name = this.route_name.getText();
		this.listofroutes.get(this.active_route).lord = this.lord.name;
		this.listofroutes.get(this.active_route).official = (String) this.route_official.getSelectedItem();
		this.listofroutes.get(this.active_route).lord_TAR = (int) Integer.parseInt(this.tar_pbp_ptar[0].getText());
		this.listofroutes.get(this.active_route).partner_TAR = (int) Integer.parseInt(this.tar_pbp_ptar[2].getText());
		this.listofroutes.get(this.active_route).partner_BP = (int) Integer.parseInt(this.tar_pbp_ptar[1].getText());
		NationHandler.getRoutes(this.lord.name);
		NationHandler.saveNation();
		this.updateRoutes();
		this.saveRoutes();
	}
	
	public void updateRoutes() {
		//System.out.println("ROUTEWINDOW! updateRoutes");
		this.lord = NationHandler.listoflords.get(Utility.findLord(this.lord.name));
		try {
			for (int i=0;i<this.names.size();i++) {
				this.names.get(i).setText(this.listofroutes.get(i).name);
				this.trade_value.get(i).setText(Integer.toString((int) this.listofroutes.get(i).trade_value));
				this.official.get(i).setText(this.listofroutes.get(i).official);
				this.listofroutes.get(i).calculateRoute();
			}
		} catch (IndexOutOfBoundsException e) {
			
		}
		//this.saveRoutes();
		this.revalidate();
	}
	
	public void clearRoutes() {
		//System.out.println("ROUTEWINDOW! clearRoutes");
		for (int i=this.listofroutes.size()-1;i>=0;i--)
			this.removeRoute(i);
	}
	
	public void saveRoutes() {//reads routes from visual layer and puts into codelayer
		//System.out.println("ROUTEWINDOW! saveRoutes");
		for (Route route:this.listofroutes)
			route.calculateRoute();
		NationHandler.getRoutes(this.lord.name);
		NationHandler.saveNation();
	}
	
	public void loadRoutes() {//loads directly from saved layer and puts into code and visual layers
		//System.out.println("ROUTEWINDOW! loadRoutes");
		this.clearRoutes();
		List<Route> temp = ReadNWrite.loadRoutes(this.lord.name);
		//System.out.println("loaded routes for "+this.lord.name);
		//for (Route r:temp)
			//System.out.println(r.name);
		for (int i=0;i<temp.size();i++) {
			this.addRoute(false);
			this.listofroutes.get(i).name = temp.get(i).name;
			this.listofroutes.get(i).lord = temp.get(i).lord;
			this.listofroutes.get(i).official = temp.get(i).official;
			this.listofroutes.get(i).lord_TAR = temp.get(i).lord_TAR;
			this.listofroutes.get(i).partner_TAR = temp.get(i).partner_TAR;
			this.listofroutes.get(i).partner_BP = temp.get(i).partner_BP;
			this.listofroutes.get(i).calculateRoute();
		}
		NationHandler.listoflords.get(Utility.findLord(this.lord.name)).getOfficials();
		NationHandler.getRoutes(this.lord.name);
		//System.out.println("there are "+this.listofroutes.size()+" routes after loading "+this.lord.name+" "+temp.size());
		this.updateRoutes();
	}
	
	public void loadActiveRoute() {
		this.route.setTitle("Trading with "+this.listofroutes.get(this.active_route).name);
		this.route_name.setText(this.listofroutes.get(this.active_route).name);
		this.route_official.setSelectedItem(this.listofroutes.get(this.active_route).official);
		this.tar_pbp_ptar[0].setText(Integer.toString(this.listofroutes.get(this.active_route).lord_TAR));
		this.tar_pbp_ptar[1].setText(Integer.toString(this.listofroutes.get(this.active_route).partner_BP));
		this.tar_pbp_ptar[2].setText(Integer.toString(this.listofroutes.get(this.active_route).partner_TAR));
		this.routepanel.revalidate();
	}
	
	public void start() {
		//System.out.println("ROUTEWINDOW! start");
		this.loadRoutes();
		this.updateRoutes();
		this.setVisible(true);
		this.setJobsList();
	}
	public void stop() {
		//System.out.println("ROUTEWINDOW! stop");
		this.setVisible(false);
		NationHandler.updateNation();
	}
	
	public void initializeRoute() {
		this.route.setSize(400, 122);
		this.route.setLocationRelativeTo(null);
		this.route.setResizable(false);
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    constraints.gridy = 0;
	    constraints.gridx = 0;
	    constraints.gridwidth = 2;
	    this.routepanel.add(this.route_name,constraints);
	    constraints.gridwidth = 1;
	    constraints.gridx = 2;
	    this.routepanel.add(this.route_official,constraints);
	    constraints.gridy = 1;
	    constraints.gridx = 0;
	    this.routepanel.add(new JLabel("Lord TAR"),constraints);
	    constraints.gridx = 1;
	    this.routepanel.add(new JLabel("Partner BP"),constraints);
	    constraints.gridx = 2;
	    this.routepanel.add(new JLabel("Partner TAR"),constraints);
	    constraints.gridy = 2;
	    constraints.gridx = 0;
	    for (int i=0;i<this.tar_pbp_ptar.length;i++) {
	    	this.tar_pbp_ptar[i] = new JTextField("0");
	    	this.routepanel.add(this.tar_pbp_ptar[i],constraints);
	    	constraints.gridx++;
	    }
	    constraints.gridy = 3;
	    constraints.gridx = 0;
	    this.close.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		saveActiveRoute();
	    		stopRoute();
	    	}
	    });
	    this.routepanel.add(this.close,constraints);
	    this.route.add(this.routepanel);
	}
	public void startRoute(int route_index) {
		//System.out.println("Starting up route index "+route_index);
		this.active_route = route_index;
		this.loadActiveRoute();
		this.route.setVisible(true);
	}
	public void stopRoute() {
		this.updateRoutes();
		this.route.setVisible(false);
	}
}