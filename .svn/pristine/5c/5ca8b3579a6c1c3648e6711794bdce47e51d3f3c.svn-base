package org.processmining.erpsimulator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.processmining.erpsimulator.parser.Preprocessor;
import org.processmining.erpsimulator.sap.SapConnector;
import org.processmining.erpsimulator.sap.Transaction;

public class SapAutomation {
	private SapConnector sapConnector;
	private List<String> commandList = new ArrayList<String>();
	private Map<String, Map<String, Object>> commands;
	private Map<String, List<String>> activtyInfoList;
	private Map<String, String> transactionMap = new HashMap<String, String>();
	private Map<String, String> parameterMap = new HashMap<String, String>();
	private int executionCount=0;
	private boolean commit = true;
	private boolean preprocess = false;
	
	public SapAutomation() {
		transactionMap = new HashMap<String,String>();
		parameterMap = new HashMap<String,String>();
		commands = new HashMap<String, Map<String, Object>>();
	}

	public void establishSAPConnection(Map<String,String> sapCredential) {
		sapConnector = new SapConnector(sapCredential);
	}

	public void setCommit(boolean commit) {
		this.commit=commit;
	}

	public void preprocessCommands(boolean preprocess) {
		this.preprocess=preprocess;
		if(this.preprocess==true){
			Preprocessor preprocessor = new Preprocessor();
			this.commands = preprocessor.padDocumentId(commands,"rderId","000",5004948);
			this.commands = preprocessor.padDocumentId(commands,"eliveryId","00",87001089);
			this.commands = preprocessor.padDocumentId(commands,"uotationId","000",5001467);
			this.commands = preprocessor.padDocumentId(commands,"nquiryId","00",15000359);
			System.out.println("Preprocessing successfully done!");
		}
	}
	
	public void updateCommandList(List<String> commandList) {
		this.commandList=commandList;
	}

	public void updateTransactionMap(Map<String,String> transactionMap) {
		this.transactionMap = transactionMap;
	}

	public void updateParameterMap(Map<String,String> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	public void updateCommands(Map<String, Map<String, Object>> commands) {
		this.commands = commands;
		Map<String, List<String>> tempActivityInfoList = new HashMap<>();
		for(String eid: commands.keySet()){
			String activityName = (String) commands.get(eid).get("activity");

			if(!tempActivityInfoList.containsKey(activityName)){
				List<String> infoList = new ArrayList<>();
				for(String info: commands.get(eid).keySet()){
					infoList.add(info);
				}
				tempActivityInfoList.put(activityName,infoList);
			}
		this.activtyInfoList=tempActivityInfoList;
		}
	}
	
	public List<String> getCommandList() {
		return this.commandList;
	}

	
	public Map<String,String> getTransactionMap() {
		return this.transactionMap;
	}

	public Map<String, List<String>> getActivtyInfoList() {
		return this.activtyInfoList;
	}

	public Map<String,String> getParameterMap() {
		return this.parameterMap;
	}
	
	public Map<String, Map<String, Object>> getCommands() {
		return this.commands;
	}
	
	public int getExecutionCount() {
		return this.executionCount;
	}
	
	public Transaction transformToTrans(Map<String, Object> command) throws Exception {
		String activityName = (String) command.get("activity");
		Map<String, String> paramMap = new HashMap<String,String>();
		String transName = this.transactionMap.get(activityName);
		for(String info:command.keySet()){
			if(!info.equals("activity")){
				if(parameterMap.get(info)!=null){
					paramMap.put(parameterMap.get(info), (String) command.get(info));
				}
				else{
					throw new Exception(String.format("Information '%s' is not specified in the parameter mapping. " +
							"Please check the parameter mapping.",info));
				}
			}

		}
		Transaction trans = new Transaction(activityName,transName,paramMap,this.sapConnector.getMyJcoDestination(),this.commit);
		System.out.println(trans);
		return trans;
	}
	
	public void executeTrans(Transaction trans) {
		trans.execute();
	}
	
	public String executeOneTrans() throws Exception {
		List<Map<String, Object>> commandList = new ArrayList<Map<String, Object>>(this.commands.values());
		Map<String, Object> command = commandList.get(executionCount);
		Transaction trans = transformToTrans(command);
		executeTrans(trans);
		this.executionCount+=1;
		return trans.getRecord();
	}
	
	public String executeAllTrans() throws Exception {
		String recordList = "";
		List<Map<String, Object>> commandList = new ArrayList<Map<String, Object>>(this.commands.values());
		for(int i=this.executionCount;i<commandList.size();i++) {
			Map<String, Object> command = commandList.get(i);
			Transaction trans = transformToTrans(command);
			executeTrans(trans);
			this.executionCount+=1;
			recordList += trans.getRecord();
		}
		return recordList;
	}
}
