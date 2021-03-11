package org.processmining.erpsimulator.configuration;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.processmining.erpsimulator.constants.GlobalConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * write and read map data structure into/from xml file
 */
public class ReadConfigurations {
    private ConfigurationParameters configuration;
    public ReadConfigurations(ConfigurationParameters configuration){
    	this.configuration = configuration;
    }
    
    public ReadConfigurations(){
    	
    }
       
	public void parserXml(String fileName, List<String> tableList) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);
             
            NodeList rootList = document.getChildNodes();
            Node root = rootList.item(0);
            NodeList configurations = root.getChildNodes();
            
            //build a map between the name and node
            Map<String, Node> nameVSNode = new HashMap<String, Node>();
            for (int i = 0; i < configurations.getLength(); i++) {
                Node configurationNode = configurations.item(i);
                if(configurationNode.getNodeName() != "#text"){
                    String configurationNodeName = configurationNode.getNodeName();
                    nameVSNode.put(configurationNodeName, configurationNode);
                }
            }
           
            //parse all elements related to the tableClassActivity list
            for (int i = 0; i < configurations.getLength(); i++) {
                Node configurationNode = configurations.item(i);
                if(configurationNode.getNodeName() != "#text"){
                    //System.out.println("**********configuration**************");
                    String configurationNodeName = configurationNode.getNodeName();
                    //System.out.println("configurationNodeName: " + configurationNodeName);
                    if(configuration.getTransactionMap().containsKey(configurationNodeName)){
                        Map<String, String> parameter = new HashMap<String, String>();
                        configuration.getTransactionMap().put(configurationNodeName, parameter);
                        parse_Key_Value_Node(configurationNode, parameter, null);
                    }
                    else if(configuration.getParameterMap().containsKey(configurationNodeName)){
                        Map<String, String> parameter = new HashMap<String, String>();
                        configuration.getParameterMap().put(configurationNodeName, parameter);
                        parse_Key_Value_Node(configurationNode, parameter, null);
                    }
                    else if(configuration.getSapCredential().containsKey(configurationNodeName)){
                        Map<String, String> parameter = new HashMap<String, String>();
                        configuration.getSapCredential().put(configurationNodeName, parameter);
                        parse_Key_Value_Node(configurationNode, parameter, null);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void parse_Map_String_List_Node(Node configurationNode, Map<String, List<String>> inputMap, List<String> constraints){
    	 NodeList mapKeyList = configurationNode.getChildNodes();
         for (int j = 0; j < mapKeyList.getLength(); j++) {
             Node mapKeyNode = mapKeyList.item(j);
             if(mapKeyNode.getNodeName() != "#text"){
                 String mapKeyName = mapKeyNode.getNodeName();
                 if(constraints == null || constraints.contains(mapKeyName) || constraints.contains(mapKeyName.split("-")[0])){
                	 NodeList mapValueElementList = mapKeyNode.getChildNodes();
                     List<String> mapValueElementNameList = new ArrayList<String>();
                     for (int k = 0; k < mapValueElementList.getLength(); k++) {
                    	 
                    	 String mapValueElementName = mapValueElementList.item(k).getTextContent();
                      	 if(mapValueElementList.item(k).getNodeName() != "#text"){ 		 
                      		 mapValueElementNameList.add(mapValueElementName);
                              
                      	 }                     
                     }
                     inputMap.put(mapKeyName, mapValueElementNameList);
                 }
                 
             }                       
         }   
         //System.out.println(inputMap);
    }
    
    public void parse_Map_String_Map_Node(Node configurationNode, Map<String, Map<String, String>> inputMap, List<String> constraints){
   	 NodeList mapKeyList = configurationNode.getChildNodes();
        for (int j = 0; j < mapKeyList.getLength(); j++) {
            Node mapKeyNode = mapKeyList.item(j);
            if(mapKeyNode.getNodeName() != "#text"){
                String mapKeyName = mapKeyNode.getNodeName();
                if(constraints == null || constraints.contains(mapKeyName)){
                	NodeList mapValueElementList = mapKeyNode.getChildNodes();
                	Map<String, String> childMap = new HashMap<String, String>();
                	for(int k = 0; k < mapValueElementList.getLength(); k++) {                	  
                		String mapValueElementName = mapValueElementList.item(k).getNodeName();
                   	  	if(mapValueElementList.item(k).getNodeName() != "#text"){              		                     		  
                   	  		String childMapValueName = mapValueElementList.item(k).getTextContent();
                   	  		childMap.put(mapValueElementName, childMapValueName);
                   	  	}                     
                	}
                	inputMap.put(mapKeyName, childMap);
                }               
            }                       
        }               
    }
    
    public void parse_List_Node(Node configurationNode, List<String> inputList, List<String> constraints){	
   	 	NodeList mapKeyList = configurationNode.getChildNodes();
   	 	//System.out.println("********new function List****************");
        for (int j = 0; j < mapKeyList.getLength(); j++) {
        	//System.out.println("********key list****************");
            Node mapKeyNode = mapKeyList.item(j);
            String mapKeyName = mapKeyNode.getNodeName();
            //System.out.println("mapKeyName: " + mapKeyName);
            if(constraints == null || constraints.contains(mapKeyName)){         	
                if(mapKeyNode.getNodeName() != "#text"){  
                	//System.out.println("********node name****************");      
                    inputList.add(mapKeyName);
                }     
            }
                              
        }               
    }
    
    public void parse_Map_Node(Node configurationNode, Map<String, String> inputMap, List<String> constraints){	
   	 	NodeList mapKeyList = configurationNode.getChildNodes();
   	 	//System.out.println("********map****************");
        for (int j = 0; j < mapKeyList.getLength(); j++) {
        	//System.out.println("********key list****************");
            Node mapKeyNode = mapKeyList.item(j);
            String mapKeyName = mapKeyNode.getNodeName();
            //System.out.println("mapKeyName: " + mapKeyName);
            if(constraints == null || constraints.contains(mapKeyName)){         	
                if(mapKeyNode.getNodeName() != "#text"){  
                	//System.out.println("********node name****************");
                	
                    String mapValueName = mapKeyNode.getTextContent();   
                    //System.out.println("mapValueName: " + mapValueName);
                    inputMap.put(mapKeyName, mapValueName);
                }     
            }
                              
        }               
    }
    
    public void parse_Map_Map_List_Node(Node configurationNode, Map<String, Map<String, List<String>>> inputMap, List<String> constraints){
   	 	NodeList mapKeyList = configurationNode.getChildNodes();
   	 	//System.out.println("********map****************");
        for (int j = 0; j < mapKeyList.getLength(); j++) {
        	//System.out.println("********key list****************");
            Node mapKeyNode = mapKeyList.item(j);
            String mapKeyName = mapKeyNode.getNodeName();
            if(constraints == null || constraints.contains(mapKeyName)){         	
                Map<String, List<String>> childMap = new HashMap<String, List<String>>();
                parse_Map_String_List_Node(mapKeyNode, childMap, null);

                if(mapKeyName != "#text"){  
                	//System.out.println("********node name****************");            
                    inputMap.put(mapKeyName, childMap);
                } 
            }                     
        }               
    }
    
    public void parse_Map_Map_Map_Node(Node configurationNode, Map<String, Map<String, Map<String, String>>> inputMap, List<String> constraints){
   	 	NodeList mapKeyList = configurationNode.getChildNodes();
   	 	//System.out.println("********map****************");
        for (int j = 0; j < mapKeyList.getLength(); j++) {
        	//System.out.println("********key list****************");
            Node mapKeyNode = mapKeyList.item(j);
            String mapKeyName = mapKeyNode.getNodeName();
            if(constraints == null || constraints.contains(mapKeyName)){         	
                Map<String, Map<String, String>> childMap = new HashMap<String, Map<String, String>>();
                parse_Map_String_Map_Node(mapKeyNode, childMap, null);

                if(mapKeyName != "#text"){  
                	//System.out.println("********node name****************");            
                    inputMap.put(mapKeyName, childMap);
                } 
            }                     
        }               
    }

    public void parse_Key_Value_Node(Node configurationNode, Map<String, String> inputMap, List<String> constraints){
        NodeList mapKeyList = configurationNode.getChildNodes();
        //System.out.println("********map****************");
        for (int j = 0; j < mapKeyList.getLength(); j++) {
            //System.out.println("********key list****************");
            Node mapKeyNode = mapKeyList.item(j);
            String mapKeyName = mapKeyNode.getNodeName();
            if(mapKeyName != "#text"){
                System.out.println(mapKeyName);
                String key = mapKeyNode.getAttributes().getNamedItem("key").getNodeValue();
                String value = mapKeyNode.getAttributes().getNamedItem("value").getNodeValue();
                inputMap.put(key, value);
            }

        }
    }
//    
    public static void main(String args[]){
    	ConfigurationParameters configuration = new ConfigurationParameters();
        configuration.getTransactionMap().put(GlobalConstants.transactionMap, null);
        configuration.getParameterMap().put(GlobalConstants.parameterMap, null);
    	ReadConfigurations conf = new ReadConfigurations(configuration);
    	List<String> tableList;
//    	conf.parserXml("/Users/GYUNAM/examples/automation/OTCTransMapping2.xml",null);
//        System.out.println(configuration.getNameVSMapListParameterMap());
//        System.out.println(configuration.getTransactionMap());
        conf.parserXml("/Users/GYUNAM/examples/automation/SAPTransParams2.xml",null);
        System.out.println(configuration.getParameterMap());
    }
}