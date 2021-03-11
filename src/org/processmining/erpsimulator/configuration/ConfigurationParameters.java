package org.processmining.erpsimulator.configuration;

import java.util.HashMap;
import java.util.Map;
 

/*
 * write and read map data structure into/from xml file
 */
public class ConfigurationParameters {
    
    //keep the parameters (i.e., maps) and their names
	private Map<String, Map<String, String>> transactionMap = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, String>> parameterMap = new HashMap<String, Map<String, String>>();
	private Map<String, Map<String, String>> sapCredential = new HashMap<String, Map<String, String>>();
    
    public ConfigurationParameters(){
    }

	public Map<String, Map<String, String>> getTransactionMap(){
		return transactionMap;
	}

	public Map<String, Map<String, String>> getParameterMap(){
		return parameterMap;
	}

	public Map<String, Map<String, String>> getSapCredential(){
		return sapCredential;
	}
}