package org.processmining.erpsimulator.Test;

public class TestMain {
	public static void main(String[] arg) throws Exception{
		
//		SapAutomation sapAutomation = new SapAutomation();
//		ConfigurationParameters configuration = new ConfigurationParameters();
//    	configuration.getNameVSMapMapMapParameterMap().put(GlobalConstants.activityVSTransactionVSElementVSAttribute, null);
//			configuration.getNameVSMapListParameterMap().put(GlobalConstants.activityVSTransactionList, null);
//			
//      	ReadConfigurations parser = new ReadConfigurations(configuration);
//      	String filePath = "/Users/GYUNAM/ERPDataGenerator/PluginConfigurationFile/OTCTransMapping.xml";
//      	parser.parserXml(filePath, null);
//		
//		if(configuration.getNameVSMapListParameterMap().get(GlobalConstants.activityVSTransactionList) == null){
//			JOptionPane.showMessageDialog(null, "Wrong! The imported file is not of correct format!");   
//		}
//		else{
//			JOptionPane.showMessageDialog(null, "Succeed importing a file!"); 
//			sapAutomation.updateActivityVSTransactionList(configuration.getNameVSMapListParameterMap().get(GlobalConstants.activityVSTransactionList));
//    		sapAutomation.updateActivityVSTransactionVSElementVSAttribute(configuration.getNameVSMapMapMapParameterMap().get(GlobalConstants.activityVSTransactionVSElementVSAttribute));
//    		
//		}
//		
//		ParseAutomationLog logParser = new ParseAutomationLog();
//		String logfilePath = "/Users/GYUNAM/ERPDataGenerator/CPNModel&SimulationLog/CPNSimulationLogs/SAPlog.xml";
//		logParser.parseLog(logfilePath);
//		Map<String, Map<String, Object>> events = logParser.getEventVSAttributeNameVSAttributeContentMap();
//		sapAutomation.updateEvents(events);
//		System.out.println(sapAutomation.getActivityVSTransactionList());
//		System.out.println(sapAutomation.getActivityVSTransactionVSElementVSAttribute());
//		System.out.println(sapAutomation.getEvents());
//		for(String eventName:sapAutomation.getEvents().keySet()) {
//			Map<String, Object> event = sapAutomation.getEvents().get(eventName);
//			Transaction trans = sapAutomation.transformToTrans(event);
//			trans.execute();
//		}
		
		String date = java.time.LocalDate.now().plusDays(1).toString();
		System.out.println(String.join("", date.split("-")));
	
	}
}
