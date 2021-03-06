package org.processmining.erpsimulator.configuration;

 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
 

/*
 * write and read map data structure into/from xml file
 */
public class WriteConfigurations {
    private Document document;
    
    private ConfigurationParameters configuration;
    
    public WriteConfigurations(ConfigurationParameters configuration){
    	this.configuration = configuration;
    	init();
    }
   		 
    public WriteConfigurations(){
    	
    }
    
    public void init() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }
 
	public void createXml(String fileName) {
        Element root = this.document.createElement("configurations"); 
        this.document.appendChild(root);    

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
        	System.out.println("Start writing XML file!");
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("Succeed creating XML file!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
 

    
    /**
     *
     * @param inputMap
     *
     */
    public void add_Map_String_List_Node(Element mapNode, Map<String, List<String>> inputMap){
        if(inputMap != null){
        	for(String stringName : inputMap.keySet()){
        		 if(stringName != null && stringName != ""){
        			 System.out.println("stringName: " + stringName);
                 	 Element stringNode = this.document.createElement(stringName); 
                 	 mapNode.appendChild(stringNode); 
                 	 for(String listElementName : inputMap.get(stringName)){
                 		System.out.println("listElementName: " + listElementName);
                 		Element listElementNode = this.document.createElement("element");       		
                 		listElementNode.appendChild(this.document.createTextNode(listElementName)); 
                 		stringNode.appendChild(listElementNode); 
                 	 }
        		 }      		
            }
        }      
    }
    
    public void add_Map_String_Map_Node(Element mapNode, Map<String, Map<String, String>> inputMap){
        if(inputMap != null){
        	 for(String stringName : inputMap.keySet()){
        		 if(stringName != null && stringName != ""){
//        			 stringName = stringName.split("\\)")[1] + "_1";
        			 System.out.println("add_Map_String_Map_Node-stringName: " + stringName);
                 	 Element stringNode = this.document.createElement(stringName); 
                 	 mapNode.appendChild(stringNode); 
                 	 for(String childMapKeyName : inputMap.get(stringName).keySet()){
                 		String childMapValueName = inputMap.get(stringName).get(childMapKeyName);				
         				Element childMapKeyNode = this.document.createElement(childMapKeyName);       		
         				childMapKeyNode.appendChild(this.document.createTextNode(childMapValueName)); 
         				stringNode.appendChild(childMapKeyNode); 
                 	 } 
        		 }       		 
             }
        }       
    }
    
    public void add_Map_Map_List_Node(Element mapNode, Map<String, Map<String, List<String>>> inputMap){
        if(inputMap != null){
            for(String mapKeyName : inputMap.keySet()){
            	Map<String, List<String>> childMap = inputMap.get(mapKeyName);
            	Element mapKeyNode = this.document.createElement(mapKeyName);       		
    			mapNode.appendChild(mapKeyNode);    			
            	add_Map_String_List_Node(mapKeyNode, childMap);
            }
        }
    }
    
    public void add_Map_Map_Map_Node(Element mapNode, Map<String, Map<String, Map<String, String>>> inputMap){
        if(inputMap != null){
            for(String mapKeyName : inputMap.keySet()){
            	Map<String, Map<String, String>> childMap = inputMap.get(mapKeyName);
            	Element mapKeyNode = this.document.createElement(mapKeyName);       		
    			mapNode.appendChild(mapKeyNode);    			
    			add_Map_String_Map_Node(mapKeyNode, childMap);
            }
        }
    }
    
    
    public void add_Map_Node(Element mapNode, Map<String, String> inputMap){
        if(inputMap != null){
            for(String mapKeyName : inputMap.keySet()){
            	if(mapKeyName != null && mapKeyName != ""){
            		String mapValueName = inputMap.get(mapKeyName);		
                	System.out.println("mapKeyName: "  + mapKeyName);
        			Element mapKeyNode = this.document.createElement(mapKeyName);     
 //       			Element mapKeyNode = this.document.createElement("A");  
        			mapKeyNode.appendChild(this.document.createTextNode(mapValueName)); 
        			mapNode.appendChild(mapKeyNode); 
            	}
            	
            }
        }
    }
    
    public void add_List_Node(Element listNode, List<String> inputList){
        if(inputList != null){
            for(String element : inputList){
            	System.out.println("element: "  + element);
    			Element elementNode = this.document.createElement(element);        			
    			listNode.appendChild(elementNode);           	
            }
        }
    }
    
    public static void main(String args[]){
    	WriteConfigurations dd=new WriteConfigurations();
        String str="D:/grade2.xml";
        dd.init();
//        dd.createXml(str);    //??????xml
//        dd.parserXml(str);    //??????xml
    }
}