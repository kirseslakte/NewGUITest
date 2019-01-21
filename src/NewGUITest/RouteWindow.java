package NewGUITest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class RouteWindow extends JFrame{
	
	public List<Route> listofroutes = new ArrayList<Route>();
	public List<JTextField> names = new ArrayList<JTextField>();
	public List<Boolean> active = new ArrayList<Boolean>();
	public List<JTextField> lord_tar = new ArrayList<JTextField>();
	public List<JTextField> partner_tar = new ArrayList<JTextField>();
	public List<JTextField> partner_bp = new ArrayList<JTextField>();
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
				readRoutes();
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
	    		readRoutes();
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
		this.names.clear();
		this.active.clear();
		this.lord_tar.clear();
		this.partner_tar.clear();
		this.partner_bp.clear();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    for (int i=0;i<this.lord.official_jobs[0];i++) {
	    	this.active.add(true);
			Panel routepanel = new Panel(new GridBagLayout());
		    constraints.gridy = 0;
		    constraints.gridx = 0;
		    constraints.gridwidth = 2;
		    this.names.add(new JTextField("Name of Trade Route"));
		    routepanel.add(names.get(i), constraints);
		    constraints.gridx = 2;
		    routepanel.add(new JLabel("Partner"), constraints);
		    constraints.gridwidth = 1;
		    constraints.gridy = 1;
		    constraints.gridx = 0;
		    constraints.gridwidth = 2;
		    routepanel.add(new JLabel("TAR"), constraints);
		    constraints.gridwidth = 1;
		    constraints.gridx = 2;
		    routepanel.add(new JLabel("BP"), constraints);
		    constraints.gridx = 3;
		    routepanel.add(new JLabel("TAR"), constraints);
		    constraints.gridy = 2;
		    constraints.gridx = 0;
		    constraints.gridwidth = 2;
		    this.lord_tar.add(new JTextField("0"));
		    routepanel.add(this.lord_tar.get(i), constraints);
		    constraints.gridwidth = 1;
		    constraints.gridx = 2;
		    this.partner_bp.add(new JTextField("0"));
		    routepanel.add(this.partner_bp.get(i), constraints);
		    constraints.gridx = 3;
		    this.partner_tar.add(new JTextField("0"));
		    routepanel.add(this.partner_tar.get(i), constraints);
	    	this.activemain.add(routepanel);
	    }
	    constraints.gridy = 2;
	    constraints.gridx = 0;
	    constraints.weighty = 1;
	    this.main.add(this.activemain);
	    this.main.add(this.passivemain);
	    this.basepanel.add(this.main,constraints);
	    this.setSize(this.getWidth(), 70+30*this.names.size());
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
		addPassiveRoute();
	}
	
	public void addPassiveRoute() {
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
    	this.active.add(false);
		Panel routepanel = new Panel(new GridBagLayout());
	    constraints.gridy = 0;
	    constraints.gridx = 0;
	    constraints.gridwidth = 2;
	    this.names.add(new JTextField("Name of Trade Route"));
	    routepanel.add(names.get(this.names.size()-1), constraints);
	    constraints.gridx = 2;
	    routepanel.add(new JLabel("Partner"), constraints);
	    constraints.gridwidth = 1;
	    constraints.gridy = 1;
	    constraints.gridx = 0;
	    constraints.gridwidth = 2;
	    routepanel.add(new JLabel("TAR"), constraints);
	    constraints.gridwidth = 1;
	    constraints.gridx = 2;
	    routepanel.add(new JLabel("BP"), constraints);
	    constraints.gridx = 3;
	    routepanel.add(new JLabel("TAR"), constraints);
	    constraints.gridy = 2;
	    constraints.gridx = 0;
	    constraints.gridwidth = 2;
	    this.lord_tar.add(new JTextField("0"));
	    routepanel.add(this.lord_tar.get(this.lord_tar.size()-1), constraints);
	    constraints.gridwidth = 1;
	    constraints.gridx = 2;
	    this.partner_bp.add(new JTextField("0"));
	    routepanel.add(this.partner_bp.get(this.partner_bp.size()-1), constraints);
	    constraints.gridx = 3;
	    this.partner_tar.add(new JTextField("0"));
	    routepanel.add(this.partner_tar.get(this.partner_tar.size()-1), constraints);
    	this.passivemain.add(routepanel);
    	this.revalidate();
	}
	
	public void readRoutes() {
		
	}
	
	public void loadRoute() {
		
	}
	
	public void start(Lord lord) {
		this.lord = lord;
		if (lord.official_jobs[0]<1)
			JOptionPane.showMessageDialog(null,"This lord has no trading officials!","Trade Error",JOptionPane.INFORMATION_MESSAGE);
		else {
			this.setVisible(true);
			this.setActiveRoutes();
		}
	}
	
	public void stop() {
		this.setVisible(false);
	}
}