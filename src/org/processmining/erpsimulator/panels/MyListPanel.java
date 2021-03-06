package org.processmining.erpsimulator.panels;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

 
/**
 * @version 1.0
 * @author liangdong
 *
 */
public class MyListPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;   
    private JLabel listLabel = new JLabel("list values (number: 0)");
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList<String> list  = new JList<String>(listModel);//control the content of the list panel
    private JScrollPane listScroller;//control the size of the list panel    
    private List<String> valueList = new ArrayList<String>();//abstract the content by the form of a list of string
    private String name = "list values";
    public MyListPanel(List<String> valueList) {    	
    	this.valueList = valueList;
		if(valueList != null){
			for(String value : valueList){
				listModel.addElement(value);
			}
		}	
    	listLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		//add list listener to show the texts where the mouse cursor points to
		list.addMouseMotionListener(new MouseMotionAdapter() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	            JList l = (JList)e.getSource();
	            ListModel m = l.getModel();
	            int index = l.locationToIndex(e.getPoint());
	            if( index>-1 ) {
	                l.setToolTipText(m.getElementAt(index).toString());
	            }
	        }
	    });
		
		//scroll panel
		listScroller = new JScrollPane(list);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setAlignmentY(Component.TOP_ALIGNMENT);
		//head panel
		JPanel headPanel = new JPanel();
		headPanel.add(listLabel);
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
		
		this.add(headPanel);
		this.add(listScroller);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public MyListPanel(List<String> valueList, String name) {    	
    	this.valueList = valueList;
		if(valueList != null){
			for(String value : valueList){
				listModel.addElement(value);
			}
		}	
    	listLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		//scroll panel
		listScroller = new JScrollPane(list);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setAlignmentY(Component.TOP_ALIGNMENT);
		//head panel
		JPanel headPanel = new JPanel();
		headPanel.add(listLabel);
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
		
		//set the panel name
		listLabel.setText(name + " (number: 0)");
		this.name = name;
		this.add(headPanel);
		this.add(listScroller);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public List<String> getValueList(){
    	return valueList;
    }  
    
    public JScrollPane getPanel(){
    	return listScroller;
    }
    
    public JList<String> getJList(){
    	return list;
    } 
     
    public String getSelectedElement(){
    	return list.getSelectedValue();
    }
    
    public void setListPanelName(String name){
    	this.name = name;
    }
    
    public void addElement(String element){
    	List<String> elementList = new ArrayList<String>();
    	if(valueList != null){
    		elementList = valueList;
    	}
    	elementList.add(element);
    	updateListPanel(elementList);
    } 
    
    public void removeElement(String element){
    	if(valueList != null){
        	valueList.remove(element);
        	updateListPanel(valueList);
    	}
    } 
    
    /*
     * rank the element by the alphabet
     */
    public void rankElement(){
    	if(valueList != null){
    		Collections.sort(valueList);
    	}
    	
    	 
    } 
    
    public void updateListPanel(List<String> valueList){	
		if(valueList != null){
			this.valueList = valueList;
			listLabel.setText(name + " (number: " + valueList.size() + ")");
			listModel.removeAllElements();
			for(String tableName : valueList){
				listModel.addElement(tableName);
			}
			list.setModel(listModel);
		}	
    }  
    
    public static void main(String[] args) {
    	MyListPanel createTablePanel = new MyListPanel(null);
    	JFrame frame = new JFrame();
		frame.add(createTablePanel);
		frame.setVisible(true);
    }
}