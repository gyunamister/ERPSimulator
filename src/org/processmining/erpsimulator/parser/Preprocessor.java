package org.processmining.erpsimulator.parser;

import java.util.LinkedHashMap;
import java.util.Map;

public class Preprocessor {
    private Map<String, Map<String, Object>> eventVSAttributeNameVSAttributeContent = new LinkedHashMap<String, Map<String, Object>>();
    public Preprocessor(){

    }

    public Map<String, Map<String, Object>> padDocumentId(Map<String, Map<String, Object>> commands, String documentType, String pad, int beginInt){
        for(String eid:commands.keySet()){
            Map<String,Object> infoMap = commands.get(eid);
            for(String info:infoMap.keySet()){
                String value = (String) infoMap.get(info);
                if(info.contains(documentType)) {
                    int documentId = Integer.parseInt(value);
                    documentId+=beginInt;
                    infoMap.put(info,pad+String.valueOf(documentId));
                }
            }

        }
        return commands;
    }



}
