package org.processmining.erpsimulator.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.erpsimulator.models.SapAutomation;
import org.processmining.erpsimulator.panels.MyListPanel;

public class AutomationLogView extends JPanel{
	private SapAutomationView parent;
	private SapAutomation sapAutomation;
    private MyListPanel commandsPanel;
    private MyListPanel commandAttributesPanel;
    
    PluginContext context;
    public AutomationLogView(PluginContext context,SapAutomationView parent,SapAutomation sapAutomation){
		this.context = context;
		this.parent=parent;
		this.sapAutomation=sapAutomation;
		RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
		rl.setFill(true);
		setLayout(rl);
		AutomationLogPanel automationLogPanel = new AutomationLogPanel(context);
    	add(automationLogPanel, new Float(100));
    }
    
    class AutomationLogPanel extends JPanel{
        PluginContext context;
        
        public AutomationLogPanel(PluginContext context) {
        	this.context = context;
    		RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
    		rl.setFill(true);
    		setLayout(rl);
    		
        	commandsPanel = new MyListPanel(null, "commands");
        	commandAttributesPanel = new MyListPanel(null, "information");
    		
        	commandsPanel.getJList().addListSelectionListener(new ListSelectionListener() {
        		public void valueChanged(ListSelectionEvent e) {
        			System.out.println("\n selected value: " + commandsPanel.getJList().getSelectedValue());
        			String selectedEventTransactionListValue = commandsPanel.getJList().getSelectedValue();
        			if(selectedEventTransactionListValue != null){
        				Map<String, Object> attributes = sapAutomation.getCommands().get(selectedEventTransactionListValue);
        				List<String> valueList = new ArrayList<String>();
        				if(attributes != null){

        					for(String key : attributes.keySet()){
        						if(attributes.get(key) instanceof String){
        							String value = (String) attributes.get(key);
        							valueList.add(key + ": " + value);
        						}
        						else if(attributes.get(key) instanceof List){
        							System.out.println("attribute of list type");
        							List<Map<String, String>> subAttributeUnitList = (List<Map<String, String>>) attributes.get(key);
        							valueList.add(key + ": ");
        							for(Map<String, String> subAttributeUnit : subAttributeUnitList){
        								valueList.add("-(" + (subAttributeUnitList.indexOf(subAttributeUnit) + 1) + "): ");
        								for(String subAttributeKey : subAttributeUnit.keySet()){
        									valueList.add("--" + subAttributeKey + ": " + subAttributeUnit.get(subAttributeKey));
        								}
        							}
        						}
        					}
        					commandAttributesPanel.updateListPanel(valueList);
        				}
        				System.out.print("\n sourceList.getSelectedValue(): " + selectedEventTransactionListValue);
        				String transactionName = selectedEventTransactionListValue.split("\\)")[1];;
        				System.out.print("\n transactionName: " + transactionName);
        			}		
        		}
    		});
        	JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        	splitPanel.setLeftComponent(commandsPanel);
        	splitPanel.setRightComponent(commandAttributesPanel);
        	splitPanel.setResizeWeight(0.01);   
        	splitPanel.setBorder(BorderFactory.createEmptyBorder());
        	
        	add(splitPanel, new Float(100));
        }
    }
    
    public MyListPanel getCommandsPanel() {
    	return this.commandsPanel;
    }
}
