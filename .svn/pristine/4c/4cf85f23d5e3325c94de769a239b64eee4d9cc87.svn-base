package org.processmining.erpsimulator.panels;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

 
/**
 * @version 1.0
 * @author guangming
 *
 */
public class MyTablePanel extends JPanel { 
//public class TablePanel extends JScrollPane { 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTable table;
    private MyTableModel model;

    
    private String tableName;
    private JScrollPane scrollpane;
    public MyTablePanel(String tableName) {   	
    	model = new MyTableModel();
	    table = new JTable(model);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 水平滚动条	      
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//	      table.setPreferredScrollableViewportSize(new Dimension(500, 300));
//	      table.setFillsViewportHeight(true);	      
	    scrollpane = new JScrollPane(table);
	    this.add(scrollpane);
    }

    public void updateTable(){
    	model.fireTableStructureChanged();
    }
    
    public JScrollPane getScrollPane(){
    	return scrollpane;
    }
    
    public MyTableModel getTableModel(){
    	return model;
    }
    
    public void setTableModel(MyTableModel dataModel){
    	table.setModel(dataModel);;
    }
    
    public String getTableName(){
    	return tableName;
    }
    
    public JTable getTable(){
    	return table;
    }
    public void setFieldRow(List<String> nameList) {
    	if(nameList != null){
            for(int i=0; i<nameList.size(); i++){
            	model.setName(nameList.get(i), i);
            }
            model.setC(nameList.size());
            model.fireTableStructureChanged();
    	}
    }
    
	public void addRecordRow(List<Object> valueList) {
		if(valueList != null){
			int currentRowCount = model.getRowCount();
			model.setRC(currentRowCount+1, currentRowCount, 0);
	        for(int i=0; i<valueList.size(); i++){
	        	model.setRC(valueList.get(i), currentRowCount, i+1);
	        }
	        model.setR(currentRowCount+1);
	        model.fireTableStructureChanged();
		}
	}

//	public void addRecordRow(List<Object> valueList) {
//		if(valueList != null){
//			int currentRowCount = model.getRowCount();
//			model.setRC(currentRowCount+1, currentRowCount, 0);
//	        for(int i=0; i<valueList.size(); i++){
//	        	model.setRC(valueList.get(i), currentRowCount, i+1);
//	        }
//	        model.setR(currentRowCount+1);
//	        model.fireTableStructureChanged();
//		}
//	}
	
	public List<Object> getRecordRow(int row){
		List<Object> valueList = new ArrayList<Object>();
		int columnCount = model.getColumnCount();
		for(int i=0; i<columnCount; i++){
			valueList.add(model.getValueAt(row, i));
		}		
		return valueList;
	}
	
	public int getRowCount(){	
		return model.getRowCount();
	}
	   
	public void clearAllTableRows(){
		model.setR(0);
		updateTable();
	}
	
    public static void main(String[] args) {
    	MyTablePanel TablePanel = new MyTablePanel("");
//    	TablePanel.getScrollPane().setSize(2000, 5000);
    	//
	    int C = 10;
    	MyTableModel model = (MyTableModel) TablePanel.getTable().getModel();
	      model.setC(C); 
	      model.setName("index", 0);
	      model.setName("age", 1);
	      model.setName("gender", 2);
	      model.setName("country", 3);
//	      model.setName("degree", 4);
	      model.setValueAt("0", 0, 0);
	      model.setValueAt("56", 0, 1);
	      model.setValueAt("male", 0, 2);
	      model.setValueAt("China", 0, 3);
	      model.setValueAt("1", 1, 0);
	      model.setValueAt("25", 1, 1);
	      model.setValueAt("female", 1, 2);
	      model.setValueAt("Japan", 1, 3);
	      model.setValueAt("2", 2, 0);
	      model.setValueAt("36", 2, 1);
	      model.setValueAt("male", 2, 2);
	      model.setValueAt("Russia", 2, 3);
	      model.setR(3); 
	      List<Object> recordValueList = model.getRecordRow(0);
	      for(Object value : recordValueList){
	    	  System.out.println("\n value: " + value);
//	    	  System.out.println("index: " + fieldRow.indexOf(field));
	      }
	      
	      
	      String columnValueComplex = model.getColumnValueComplexBasedOnColumnNameComplex("gender-country",0);
	      System.out.println("\n columnValueComplex: " + columnValueComplex);
	      
	      List<Object> derivedColumnValueComplex = model.getColumnValuesWithColumnConstraint("age-country", "gender", "male");
	      for(Object derivedColumnValu : derivedColumnValueComplex){
	    	  System.out.println("\n derivedColumnValu: " + derivedColumnValu);
//	    	  System.out.println("index: " + fieldRow.indexOf(field));
	      }
	      
	      
//	      model.setR(3);
//	      model.setR(0);
//	      model.setValueAt("0", 0, 0);
//	      model.setValueAt("name0", 0, 1);
//	      model.setR(1);
	      
	      List<String> fieldRow = model.getFieldRow();
	      for(String field : fieldRow){
	    	  System.out.println("field: " + field);
	    	  System.out.println("index: " + fieldRow.indexOf(field));
	      }
	      TablePanel.getTable().setModel(model);
	      TablePanel.updateTable();
    	TablePanel.getTable().setPreferredScrollableViewportSize(new Dimension(1000, 300));
    	JFrame frame = new JFrame();
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.add(TablePanel);
		frame.setVisible(true);
    }
}