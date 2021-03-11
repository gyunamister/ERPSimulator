package org.processmining.erpsimulator.ui;

import javax.swing.JPanel;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.erpsimulator.models.SapAutomation;

public class SapAutomationView extends JPanel{
	PluginContext context;
	private SapAutomation sapAutomation;
	private RightPanel rightPanel;
	private LeftPanel leftPanel;
	
	public SapAutomationView(PluginContext context, SapAutomation sapAutomation) {
		this.context = context;
		this.sapAutomation=sapAutomation;
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
		rl.setFill(true);
		setLayout(rl);

		leftPanel = new LeftPanel(context, this,sapAutomation);
		rightPanel = new RightPanel(context, this,sapAutomation);
		
		add(leftPanel, new Float(50));
		add(rightPanel, new Float(50));
	}
	
	class RightPanel extends JPanel {
		PluginContext context;
		private ConfigurationView configurationView;
		public RightPanel(PluginContext context, SapAutomationView parent,SapAutomation sapAutomation) {
			this.context=context;
			RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
			rl.setFill(true);
			setLayout(rl);
			configurationView = new ConfigurationView(context, parent, sapAutomation);
			add(configurationView, new Float(100));
		}
		
		public ConfigurationView getConfigurationView() {
			return this.configurationView;
		}
	}
	
	class LeftPanel extends JPanel {
		PluginContext context;
		private AutomationLogView autoLogView;
		public LeftPanel(PluginContext context, SapAutomationView parent,SapAutomation sapAutomation) {
			this.context=context;
			RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
			rl.setFill(true);
			setLayout(rl);
			autoLogView = new AutomationLogView(context, parent,sapAutomation);
			add(autoLogView, new Float(100));
		}
		
		public AutomationLogView getAutomationLogView() {
			return this.autoLogView;
		}
	}
	
	public LeftPanel getLeftPanel() {
		return this.leftPanel;
	}
	
	public RightPanel getRightPanel() {
		return this.rightPanel;
	}
	
}
