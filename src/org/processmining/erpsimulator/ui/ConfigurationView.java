package org.processmining.erpsimulator.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.erpsimulator.constants.GlobalConstants;
import org.processmining.erpsimulator.models.SapAutomation;
import org.processmining.erpsimulator.panels.MyListPanel;
import org.processmining.erpsimulator.panels.MyTableModel;
import org.processmining.erpsimulator.panels.MyTablePanel;

public class ConfigurationView extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    PluginContext context;
    private SapAutomationView parent;
    private SapAutomation sapAutomation;
    private ParamMappingPanel paramMappingPanel;
    private MyTablePanel paramPanel;
  //record the attributes of each activity
    private MyListPanel activityListPanel = new MyListPanel(null, "activities");
    private MyListPanel transactionListPanel = new MyListPanel(null, "corresponding SAP transactions");//show the transactions corresponding one activity
    private List<String> tableFieldRow = new ArrayList<String>();

    public ConfigurationView(PluginContext context, SapAutomationView parent, SapAutomation sapAutomation) {
    	this.context=context;
    	this.parent = parent;
    	this.sapAutomation=sapAutomation;
    	RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
    	rl.setFill(true);
		setLayout(rl);
		
		paramMappingPanel = new ParamMappingPanel(context);
		
		add(paramMappingPanel,new Float(100));
		
    	//update the transaction panel when the activity changes
    	activityListPanel.getJList().addListSelectionListener(new ListSelectionListener() {			
			public void valueChanged(ListSelectionEvent e) {
//				System.out.println("enter activityListPanel value changed");
				String selectedItem = activityListPanel.getJList().getSelectedValue();
				if(selectedItem != null){
					String activityName = selectedItem.split(":")[0].trim();
					//update the transaction panel

				    if(sapAutomation.getTransactionMap().containsKey(activityName)){
//				    	System.out.println("contains: ");
						List<String> activityList = new ArrayList<String>();
						activityList.add(sapAutomation.getTransactionMap().get(activityName));
				    	transactionListPanel.updateListPanel(activityList);
				    }
				    else{//clear the transaction list panel
//				    	System.out.println("not contains: ");
				    	transactionListPanel.updateListPanel(new ArrayList<String>());
				    }
				}
			}
		});
    	 
    	//update the table panel when the transaction changes
    	transactionListPanel.getJList().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedItem = activityListPanel.getJList().getSelectedValue();
				if(selectedItem != null){
					String activityName = selectedItem.split(":")[0].trim();	    
				    String selectedTransactionName = transactionListPanel.getSelectedElement();
				    
				    updateTable(activityName, selectedTransactionName);
				}				
			}
		});
        update();
    }
    
    class ParamMappingPanel extends JPanel {
		PluginContext context;
		public ParamMappingPanel(PluginContext context) {
			this.context=context;
			RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
			rl.setFill(true);
			setLayout(rl);
			
			paramPanel = new MyTablePanel("");
	        tableFieldRow.add("index");
//	        tableFieldRow.add("type");
	        tableFieldRow.add(GlobalConstants.ElementName);
	        tableFieldRow.add(GlobalConstants.AttributeName);
	        
	        paramPanel.setFieldRow(tableFieldRow);
	        paramPanel.updateTable();

	        MyTableModel model = (MyTableModel) paramPanel.getTable().getModel();
	        
	        model.addTableModelListener(new TableModelListener()  
	        {  
	            @Override  
	            public void tableChanged(TableModelEvent e)  
	            {                                 	
	                int col = e.getColumn();               
	                int row = e.getFirstRow();  
//	                System.out.println("col: " + col);
	                String selectedItem = activityListPanel.getJList().getSelectedValue();
	                String selectedActivity = selectedItem.split(":")[0].trim();
	                String selectedTransaction = transactionListPanel.getSelectedElement();
	                
	                if(col >= tableFieldRow.indexOf(GlobalConstants.AttributeName)){
	                	MyTableModel model = (MyTableModel) paramPanel.getTable().getModel();
	                    String columnName = model.getColumnName(col);
	                    String elementName = (String) model.getValueAt(row, tableFieldRow.indexOf(GlobalConstants.ElementName));
	                    String newAttributeName = (String) model.getValueAt(row, col);
	                    //if the default value column changed
	                } 
	            }  
	        }); 
	        
			JSplitPane transPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			transPanel.setLeftComponent(activityListPanel);
			transPanel.setRightComponent(transactionListPanel);
			
			add(transPanel,new Float(50));
	    	add(new JScrollPane(paramPanel),new Float(50));
		}
	}
 
    /*
     * update the table activity impacting panel for the input table
     */
    public void update(){
//    	System.out.println("update activity impacting panel");
    	
    	List<String> elementList = new ArrayList<String>(); 
    	
        updateTransactionNameBox(null);
        

    }
    

    
    public void updateTransactionNameBox(List<String> elementList){
    }
    
    //update the attribute matching panel
    public void updateTable(String activityName, String transactionName){
	    paramPanel.clearAllTableRows();

		Map<String, List<String>> activityInfoList = this.sapAutomation.getActivtyInfoList();
		Map<String, String> parameterMap = this.sapAutomation.getParameterMap();
		for(String info : activityInfoList.get(activityName)){
			if(info.equals("activity")){
				continue;
			}
			List<Object> valueList = new ArrayList<Object>();
			valueList.add(info);
			
			//if the value is assigned, show what is assigned; otherwise show "null" 
			String value = "null";							
			if(parameterMap.containsKey(info)){
				value = parameterMap.get(info);
			}
			valueList.add(value);
			paramPanel.addRecordRow(valueList);				
		}		
		paramPanel.updateTable();			

	    TableColumn col = paramPanel.getTable().getColumnModel().getColumn(tableFieldRow.indexOf(GlobalConstants.AttributeName));
	    paramPanel.getTable().setRowHeight(20);
    }

    public void updateList(List<String> valueList){
    	activityListPanel.updateListPanel(valueList);
    }

    
    public static void main(String[] args) {
    	Map<String, String> test = new HashMap<String, String>();
    	test.put("", "a");
    }
}

