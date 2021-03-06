package org.processmining.erpsimulator.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

 
 
public class ParseAutomationLog {
 
	private List<String> eventList = new ArrayList<String>();
	private Map<String, Map<String, Object>> eventVSAttributeNameVSAttributeContent = new LinkedHashMap<String, Map<String, Object>>();
	private boolean parsedFlag = false;//indicate if the log is already parsed (i.e., if the parseLog method is called)

	public List<String> getEventList(){
		return eventList;
	}

	public Map<String, Map<String, Object>> getEventVSAttributeNameVSAttributeContentMap(){
		return eventVSAttributeNameVSAttributeContent;
	}

	public void parseLog(String filePath) throws IOException, ParseException, ParserConfigurationException, SAXException{
		System.out.println("start parsing log!");
		parsedFlag = true;
        File file = new File(filePath);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            NodeList root = document.getChildNodes();

            for (int i = 0; i < root.getLength(); i++) {
                Node logNode = root.item(i);
                NodeList eventNodeList = logNode.getChildNodes();
                int eventCounter = 1;
                for (int j = 0; j < eventNodeList.getLength(); j++) {
                    Node eventNode = eventNodeList.item(j);
                    if(eventNode.getNodeName() != "#text"){
                    	Map<String, Object> attributes = new HashMap<String, Object>();
                        NodeList attributeNodeList = eventNode.getChildNodes();
                        String activityName = null;
                        for (int k = 0; k < attributeNodeList.getLength(); k++) {
                        	Node attribute = attributeNodeList.item(k);
                            if(attribute.getNodeName() != "#text"){
//                            	System.out.println("attribute.getChildNodes().getLength(): " + attribute.getChildNodes().getLength());
                            	//if the attribute does not have sub-attributes
                            	if(attribute.getChildNodes().getLength() <= 1){
//                            		System.out.println("has no sub atrributes-attribute.getNodeName(): " + attribute.getNodeName());
                            		//(SAP property) orderId and deliveryId has assigned range
//                            		if(attribute.getNodeName().contains("rderId")) {
//                            			int orderId = Integer.parseInt(attribute.getTextContent());
//										orderId+=5004947;
//                                		attribute.setTextContent("000"+String.valueOf(orderId));
//                                	}else if(attribute.getNodeName().contains("eliveryId")) {
//                                		int deliveryId = Integer.parseInt(attribute.getTextContent());
//                                		deliveryId+=87001088;
//                                		attribute.setTextContent("00"+String.valueOf(deliveryId));
//                                	}else if(attribute.getNodeName().contains("uotationId")) {
//										int quotationId = Integer.parseInt(attribute.getTextContent());
//										quotationId+=5001466;
//										attribute.setTextContent("000"+String.valueOf(quotationId));
//									}else if(attribute.getNodeName().contains("nquiryId")) {
//										int inquiryId = Integer.parseInt(attribute.getTextContent());
//										inquiryId+=15000358;
//										attribute.setTextContent("00"+String.valueOf(inquiryId));
//									}else if(attribute.getNodeName().equals("itemNumList")) {
//                                		String[] tempItemNumList = attribute.getTextContent().split(",");
//                                		String[] itemNumList = new String[tempItemNumList.length];
//                                		for(int p=0;p<tempItemNumList.length;p++) {
//                                			itemNumList[p] = "0000" + tempItemNumList[p];
//                                		}
//                                		attribute.setTextContent(String.join(",", itemNumList));
//                                	}else if(attribute.getNodeName().equals("scheduleLineList")) {
//                                		String[] tempScheduleLineList = attribute.getTextContent().split(",");
//                                		String[] scheduleLineList = new String[tempScheduleLineList.length];
//                                		for(int p=0;p<tempScheduleLineList.length;p++) {
//                                			scheduleLineList[p] = "000" + tempScheduleLineList[p];
//                                		}
//                                		attribute.setTextContent(String.join(",", scheduleLineList));
//                                	}else if(attribute.getNodeName().equals("customer")) {
//                                		String customer = attribute.getTextContent();
//                                		attribute.setTextContent("000000"+customer);
//                                	}
                            		attributes.put(attribute.getNodeName(), attribute.getTextContent());
                                	if(attribute.getNodeName().equals("activity")){
                                		activityName = attribute.getTextContent();
                                	}
                            	}

                            	else{//e.g., attribute represent order lines
                            		NodeList subAttributeUnitNodeList = attribute.getChildNodes();
//                            		System.out.println("has sub atrributes-attribute.getNodeName(): " + attribute.getNodeName());
                            		List<Map<String, Object>> subAttributeUnitList = new ArrayList<Map<String, Object>>();
                            		attributes.put(attribute.getNodeName(), subAttributeUnitList);
                            		for (int n = 0; n < subAttributeUnitNodeList.getLength(); n++) {
                                    	Node subAttributeUnitNode = subAttributeUnitNodeList.item(n); //e.g., a unit represent a order line
                                        if(subAttributeUnitNode.getNodeName() != "#text"){
                                        	Map<String, Object> subAttributeUnit = new HashMap<String, Object>();
//                                        	System.out.println("subAttributeUnitNode.getNodeName(): " + subAttributeUnitNode.getNodeName());
                                        	NodeList subAttributeNodeList = subAttributeUnitNode.getChildNodes();
                                        	for (int m = 0; m < subAttributeNodeList.getLength(); m++) {
                                        		Node subAttributeNode = subAttributeNodeList.item(m); //e.g., an attribute of order line
                                        		if(subAttributeNode.getNodeName() != "#text"){
                                        			subAttributeUnit.put(subAttributeNode.getNodeName(), subAttributeNode.getTextContent());
//                                        			System.out.println("subAttributeNode.getNodeName(): " + subAttributeNode.getNodeName());
//                                        			System.out.println("subAttributeNode.getTextContent(): " + subAttributeNode.getTextContent());
                                        		}
                                        	}
                                        	subAttributeUnitList.add(subAttributeUnit);
                                        }
                            		}
                            	}
                            }
                        }
                        String eventId = "(" + eventCounter + ")" + activityName;
                       	eventList.add(eventId);
                       	eventVSAttributeNameVSAttributeContent.put(eventId, attributes);

                        eventCounter ++;
//                        System.out.println("eventId:" + eventId);
                    }
                }
            }

            List<String> originalList = eventList;
    		List<String> rankedList = new ArrayList<String>();
    		//get source tables
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		while(originalList.size()>0){
    			Date earlistTime= sdf.parse("2020-01-01 01:00:00");
    			int  earlistTimeIndex = 0;
    			for(int i=0; i<originalList.size(); i++){
    				//every event in the simulation log should have a "transactionTime" attribute, otherwise, it goes wrong

    				String time = (String) eventVSAttributeNameVSAttributeContent.get(originalList.get(i)).get("timestamp");
    				if(time == null){
    					JOptionPane.showMessageDialog(null, "ERROR! Some events do not have timestamp");
    				}
    				else{
    					Date timestamp = sdf.parse(time);
        				if(timestamp.before(earlistTime)){
        					earlistTimeIndex = i;
        					earlistTime = timestamp;
        				}
    				}
    			}
    			rankedList.add(originalList.get(earlistTimeIndex));
    			originalList.remove(earlistTimeIndex);

    		}
    		eventList = rankedList;
    		System.out.println("LOG PARSING SUCCESSFUL");
    		JOptionPane.showMessageDialog(null, "LOG PARSING SUCCESSFUL");

        } catch (NullPointerException|SAXParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR! The imported log is not of correct format!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR! The system cannot find the file specified!");
        }
    }

	public static void main(String[] args) throws Exception {
		ParseAutomationLog parser = new ParseAutomationLog();
		String filePath = "/Users/GYUNAM/examples/automation/low-conversion-rate-log.xml";
		parser.parseLog(filePath);
		Map<String, Map<String, Object>> events = parser.getEventVSAttributeNameVSAttributeContentMap();
		System.out.println(events);
	}
 
}