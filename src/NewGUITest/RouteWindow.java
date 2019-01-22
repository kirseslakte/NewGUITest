package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class RouteWindow extends JFrame{
	
	public List<Route> listofroutes = new ArrayList<Route>();
	
	public List<JTextField> anames = new ArrayList<JTextField>();
	public List<JTextField> alord_tar = new ArrayList<JTextField>();
	public List<JTextField> apartner_tar = new ArrayList<JTextField>();
	public List<JTextField> apartner_bp = new ArrayList<JTextField>();
	public List<JLabel> atrade_value = new ArrayList<JLabel>();
	
	public List<JTextField> pnames = new ArrayList<JTextField>();
	public List<JTextField> plord_tar = new ArrayList<JTextField>();
	public List<JTextField> ppartner_tar = new ArrayList<JTextField>();
	public List<JTextField> ppartner_bp = new ArrayList<JTextField>();
	public List<JLabel> ptrade_value = new ArrayList<JLabel>();
	public List<Panel> passiveroute = new ArrayList<Panel>();
	public Lord lord;
	
	Panel basepanel = new Panel(new GridBagLayout());
	Panel main = new Panel(new GridLayout(1,2));//panel with all routes
	Panel activemain = new Panel(new GridLayout(0,2));//panel with all active routes
	Panel passivemain = new Panel(new GridLayout(0,2));//panel with all passive routes
	
	public RouteWindow() {
		
	}
	
	public void initialize(Lord lord) {
		System.out.println("ROUTEWINDOW! initialize");
		this.lord = lord;
		//setJobsList();
		this.setFrame();
	}
	
	public void setFrame() {
		System.out.println("ROUTEWINDOW! setFrame");
		this.setSize(1000, 100);
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
	    constraints.gridwidth = 2;
	    Button update = new Button("Save & Update");
	    update.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		updateRoutes();
	    	}
	    });
	    this.basepanel.add(update,constraints);
	    JPanel labels = new JPanel(new GridLayout(1,2));
	    constraints.gridy = 1;
	    labels.add(new JLabel("Active Trade Routes"));
	    labels.add(new JLabel("Passive Trade Routes"));
	    this.basepanel.add(labels,constraints);
	    this.setActiveRoutes();
	    this.passivePanel();
	    this.add(this.basepanel);
	}
	
	public void setActiveRoutes() {//removes all routes from local memory
		this.activemain.removeAll();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    for (int i=0;i<this.lord.official_jobs[0];i++) {
			Panel routepanel = new Panel(new GridBagLayout());
		    constraints.gridy = 0;
		    constraints.gridx = 0;
		    constraints.gridwidth = 2;
		    this.anames.add(new JTextField("Name of Trade Route"));
		    routepanel.add(anames.get(i), constraints);
		    constraints.gridx = 2;
		    routepanel.add(new JLabel("Partner"), constraints);
		    constraints.gridwidth = 1;
		    constraints.gridy = 1;
		    constraints.gridx = 0;
		    routepanel.add(new JLabel("Trade Value"), constraints);
		    constraints.gridx = 1;
		    routepanel.add(new JLabel("TAR"), constraints);
		    constraints.gridx = 2;
		    routepanel.add(new JLabel("BP"), constraints);
		    constraints.gridx = 3;
		    routepanel.add(new JLabel("TAR"), constraints);
		    constraints.gridy = 2;
		    constraints.gridx = 0;
		    this.atrade_value.add(new JLabel("0"));
		    routepanel.add(this.atrade_value.get(this.atrade_value.size()-1), constraints);
		    constraints.gridx = 1;
		    this.alord_tar.add(new JTextField("0"));
		    routepanel.add(this.alord_tar.get(i), constraints);
		    constraints.gridx = 2;
		    this.apartner_bp.add(new JTextField("0"));
		    routepanel.add(this.apartner_bp.get(i), constraints);
		    constraints.gridx = 3;
		    this.apartner_tar.add(new JTextField("0"));
		    routepanel.add(this.apartner_tar.get(i), constraints);
	    	this.activemain.add(routepanel);
	    }
	    constraints.gridy = 2;
	    constraints.gridx = 0;
	    constraints.weighty = 1;
	    this.main.add(this.activemain);
	    this.main.add(this.passivemain);
	    this.basepanel.add(this.main,constraints);
	    this.setSize(this.getWidth(), 200+30*(this.anames.size()+this.pnames.size()));
	    this.revalidate();
	}
	
	public void passivePanel() {
		Button addRoute = new Button("Add Passive Trade Route");
		addRoute.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				addPassiveRoute();
			}
		});	
	    this.passivemain.add(addRoute);
		this.addPassiveRoute();
	}
	
	public void addPassiveRoute() {
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
		this.passiveroute.add(new Panel(new GridBagLayout()));
	    constraints.gridy = 0;
	    constraints.gridx = 0;
	    constraints.gridwidth = 2;
	    this.pnames.add(new JTextField("Name of Trade Route"));
	    this.passiveroute.get(this.passiveroute.size()-1).add(pnames.get(this.pnames.size()-1), constraints);
	    constraints.gridx = 2;
	    this.passiveroute.get(this.passiveroute.size()-1).add(new JLabel("Partner"), constraints);
	    constraints.gridwidth = 1;
	    constraints.gridy = 1;
	    constraints.gridx = 0;
	    this.passiveroute.get(this.passiveroute.size()-1).add(new JLabel("Trade Value"), constraints);
	    constraints.gridx = 1;
	    this.passiveroute.get(this.passiveroute.size()-1).add(new JLabel("TAR"), constraints);
	    constraints.gridx = 2;
	    this.passiveroute.get(this.passiveroute.size()-1).add(new JLabel("BP"), constraints);
	    constraints.gridx = 3;
	    this.passiveroute.get(this.passiveroute.size()-1).add(new JLabel("TAR"), constraints);
	    constraints.gridy = 2;
	    constraints.gridx = 0;
	    this.ptrade_value.add(new JLabel("0"));
	    this.passiveroute.get(this.passiveroute.size()-1).add(this.ptrade_value.get(this.ptrade_value.size()-1), constraints);
	    constraints.gridx = 1;
	    this.plord_tar.add(new JTextField("0"));
	    this.passiveroute.get(this.passiveroute.size()-1).add(this.plord_tar.get(this.plord_tar.size()-1), constraints);
	    constraints.gridx = 2;
	    this.ppartner_bp.add(new JTextField("0"));
	    this.passiveroute.get(this.passiveroute.size()-1).add(this.ppartner_bp.get(this.ppartner_bp.size()-1), constraints);
	    constraints.gridx = 3;
	    this.ppartner_tar.add(new JTextField("0"));
	    this.passiveroute.get(this.passiveroute.size()-1).add(this.ppartner_tar.get(this.ppartner_tar.size()-1), constraints);
    	this.passivemain.add(this.passiveroute.get(this.passiveroute.size()-1));
    	this.revalidate();
	}
	
	public void updateRoutes() {
		System.out.println("Updating things");
		this.saveRoutes();
		for (int i=1;i<this.pnames.size();i++) {
			System.out.println(i);
			if (this.ppartner_bp.get(i).getText().equals("")||this.ppartner_bp.get(i).getText().equals("0")) {
				System.out.println("Empty passive detected at "+i+" in passive list");
				this.passivemain.remove(this.passiveroute.get(i));
				this.passiveroute.remove(i);
				this.pnames.remove(i);
				this.ptrade_value.remove(i);
				this.plord_tar.remove(i);
				this.ppartner_bp.remove(i);
				this.ppartner_tar.remove(i);
				i--;
			}
		}
		for (int i=0;i<this.anames.size();i++)
			this.atrade_value.get(i).setText(Integer.toString((int) Math.round(this.listofroutes.get(i).trade_value)));
		for (int i=0;i<this.pnames.size();i++)
			this.ptrade_value.get(i).setText(Integer.toString((int) Math.round(this.listofroutes.get(i).trade_value)));
		this.revalidate();
	}
	
	public void clearRoutes() {
		this.anames.clear();
		this.alord_tar.clear();
		this.apartner_tar.clear();
		this.apartner_bp.clear();
		this.pnames.clear();
		this.plord_tar.clear();
		this.ppartner_tar.clear();
		this.ppartner_bp.clear();
	}
	
	public void saveRoutes() {//reads routes from visual layer and puts into codelayer
		this.listofroutes.clear();
		String[] route = new String[6];
		for (int i=0;i<this.anames.size();i++) {
			for (int j=0;j<route.length;j++)
				route[j] = "";
			route[0] = this.anames.get(i).getText();
			route[1] = this.lord.name;
			route[2] = "true";
			route[3] = this.alord_tar.get(i).getText();
			route[4] = this.apartner_tar.get(i).getText();
			route[5] = this.apartner_bp.get(i).getText();
			this.listofroutes.add(new Route(route));
		}
		
		for (int i=0;i<this.pnames.size();i++) {
			for (int j=0;j<route.length;j++)
				route[j] = "";
			route[0] = this.pnames.get(i).getText();
			route[1] = this.lord.name;
			route[2] = "false";
			route[3] = this.plord_tar.get(i).getText();
			route[4] = this.ppartner_tar.get(i).getText();
			route[5] = this.ppartner_bp.get(i).getText();
			this.listofroutes.add(new Route(route));
		}
	}
	
	public void loadRoute() {//loads directly from saved layer and puts into code and visual layers
		
	}
	
	public void start() {
		this.setVisible(true);
		this.clearRoutes();
		this.setActiveRoutes();
	}
	
	public void stop() {
		this.setVisible(false);
	}
}