package org.processmining.erpsimulator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.erpsimulator.configuration.ConfigurationParameters;
import org.processmining.erpsimulator.configuration.ReadConfigurations;
import org.processmining.erpsimulator.constants.GlobalConstants;
import org.processmining.erpsimulator.dialogue.IOFileDialogue;
import org.processmining.erpsimulator.models.SapAutomation;
import org.processmining.erpsimulator.parser.ParseAutomationLog;
import org.xml.sax.SAXException;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class HomeView  extends JPanel{
	PluginContext context;
	private SapAutomationView sapAutomationView;
	private InputPanel inputPanel;
	private SapAutomation sapAutomation;
	private ResultPanel resultPanel;
	
	public HomeView(PluginContext context, SapAutomation sapAutomation) {
		this.context = context;
		this.sapAutomation=sapAutomation;
		RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
		rl.setFill(true);
		setLayout(rl);

		sapAutomationView = new SapAutomationView(context, this.sapAutomation);
		inputPanel= new InputPanel(context);
		
		resultPanel = new ResultPanel(context);
		
		add(inputPanel, new Float(10));
		add(sapAutomationView, new Float(100));
		add(resultPanel, new Float(20));
	}
	
	class InputPanel extends JPanel{
		PluginContext context;
		private ParseAutomationLog ParseAutomationLog = new ParseAutomationLog();
		private File lastFile;
		public InputPanel(PluginContext context) {
			this.context = context;
			RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
			rl.setFill(true);
			setLayout(rl);

			JButton sapCredentialButton = SlickerFactory.instance().createButton("Import SAP Credential");
			JButton autoLogInputButton = SlickerFactory.instance().createButton("Import Commands");
			JButton transMappingInputButton = SlickerFactory.instance().createButton("Import Transaction Mapping");
		    JButton transParamInputButton = SlickerFactory.instance().createButton("Import Parameter Mapping");
		    JButton transOneExecutionButton = SlickerFactory.instance().createButton("Execute one transaction");
		    JButton transAllExecutionButton = SlickerFactory.instance().createButton("Execute all transactions");
		    JCheckBox commitCheckBox = SlickerFactory.instance().createCheckBox("Commit", true);
			JCheckBox preprocessCheckBox = SlickerFactory.instance().createCheckBox("Preprocess", false);

			add(sapCredentialButton, new Float(5));
		    add(autoLogInputButton, new Float(5));
		    add(transMappingInputButton, new Float(5));
		    add(transParamInputButton, new Float(5));
		    add(transOneExecutionButton, new Float(5));
		    add(transAllExecutionButton, new Float(5));
		    add(commitCheckBox, new Float(3));
			add(preprocessCheckBox, new Float(3));

			sapCredentialButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					IOFileDialogue fileImporter = new IOFileDialogue();
					fileImporter.show("Mapping Log (.xml)", "xml", JFileChooser.FILES_ONLY, "Select", lastFile);
					File file = fileImporter.getSelectedFile();
					if(file != null){
						System.out.println("selected file is not empty!");
						lastFile = file.getParentFile();
						ConfigurationParameters configuration = new ConfigurationParameters();
						configuration.getSapCredential().put(GlobalConstants.sapCredential, null);

						ReadConfigurations parser = new ReadConfigurations(configuration);
						parser.parserXml(file.getAbsolutePath(), null);

						if(configuration.getSapCredential().get(GlobalConstants.sapCredential) == null){
							JOptionPane.showMessageDialog(null, "ERROR! The imported file is not of correct format!");
						}
						else{
							JOptionPane.showMessageDialog(null, "FILE IMPORTED");
							sapAutomation.establishSAPConnection(configuration.getSapCredential().get(GlobalConstants.sapCredential));
						}
					}
				}
			});

			transOneExecutionButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String record = null;
					try {
						record = sapAutomation.executeOneTrans();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					Document doc = resultPanel.textPane.getStyledDocument();
					SimpleAttributeSet attributeSet = new SimpleAttributeSet();
					try {
						record = "(" + sapAutomation.getExecutionCount() + ")" + record;
						doc.insertString(doc.getLength(), record, attributeSet);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		    });
		    
		    transAllExecutionButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					sapAutomation.executeAllTrans();
					for(int i=sapAutomation.getExecutionCount();i<sapAutomation.getCommandList().size();i++) {
						transOneExecutionButton.doClick();
						long bufferTime = (long) 3.0;
						try {
							TimeUnit.SECONDS.sleep(bufferTime);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
		    });
		    
		    commitCheckBox.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent event) {
		            JCheckBox cb = (JCheckBox) event.getSource();
		            if (cb.isSelected()) {
		                sapAutomation.setCommit(true);
		            } else {
		            	sapAutomation.setCommit(false);
		            }
		        }
		    });

			preprocessCheckBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					JCheckBox cb = (JCheckBox) event.getSource();
					if (cb.isSelected()) {
						sapAutomation.preprocessCommands(true);
					} else {
						sapAutomation.preprocessCommands(false);
					}
				}
			});
			
			autoLogInputButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("import file button is transactioned!");
	            	IOFileDialogue fileImporter = new IOFileDialogue();
	            	fileImporter.show("Simulation Log (.xml)", "xml", JFileChooser.FILES_ONLY, "Select", lastFile);
	            	File file = fileImporter.getSelectedFile();
					if(file != null){
						System.out.println("selected file is not empty!");
	    				String path = file.getAbsolutePath();
	    				lastFile = file.getParentFile();

	            		try {
							ParseAutomationLog.parseLog(path);
						} catch (IOException | ParseException | ParserConfigurationException | SAXException error) {
							// TODO Auto-generated catch block
							error.printStackTrace();
						}
	            		sapAutomation.updateCommands(ParseAutomationLog.getEventVSAttributeNameVSAttributeContentMap());
	            		sapAutomation.updateCommandList(ParseAutomationLog.getEventList());
					}

	            	//update the transaction panels
					sapAutomationView.getLeftPanel().getAutomationLogView().getCommandsPanel().updateListPanel(sapAutomation.getCommandList());
	            	
	                List<String> eventKindList = new ArrayList<String>();
	                Map<String, Integer> kindVSCountMap = new HashMap<String, Integer>();
	                for(String eventId : sapAutomation.getCommandList()){
	                	String eventKind = eventId.split("\\)")[1];
	                	if(!eventKindList.contains(eventKind)){
	                		eventKindList.add(eventKind);
	                	}
	                	if(!kindVSCountMap.containsKey(eventKind)){
	                		kindVSCountMap.put(eventKind, 1);
	                	}
	                	else{
	                		int currentCount = kindVSCountMap.get(eventKind);
	                		kindVSCountMap.put(eventKind, currentCount+1);
	                	}               		
	                }
	                
	                List<String> eventKindCountList = new ArrayList<String>();
	                for(String eventKind : eventKindList){
	                	String eventKindCount = eventKind + " :" + kindVSCountMap.get(eventKind);
	                	eventKindCountList.add(eventKindCount);
	                }
	                
	                //update the other panels
//	                sapAutomationView.getRightPanel().getConfigurationView().setActivityVSAttributeList(sapAutomation.getT);
	                sapAutomationView.getRightPanel().getConfigurationView().updateList(eventKindCountList);
				}
			});
			
			transMappingInputButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					IOFileDialogue fileImporter = new IOFileDialogue();
	            	fileImporter.show("Mapping Log (.xml)", "xml", JFileChooser.FILES_ONLY, "Select", lastFile);
	            	File file = fileImporter.getSelectedFile();
					if(file != null){
						System.out.println("selected file is not empty!");
						lastFile = file.getParentFile();
				    	ConfigurationParameters configuration = new ConfigurationParameters();
						configuration.getTransactionMap().put(GlobalConstants.transactionMap, null);

			          	ReadConfigurations parser = new ReadConfigurations(configuration);
			          	parser.parserXml(file.getAbsolutePath(), null);
			    		
			    		if(configuration.getTransactionMap().get(GlobalConstants.transactionMap) == null){
			    			JOptionPane.showMessageDialog(null, "ERROR! The imported file is not of correct format!");   
			    		}
			    		else{
			    			JOptionPane.showMessageDialog(null, "FILE IMPORTED");
			    			sapAutomation.updateTransactionMap(configuration.getTransactionMap().get(GlobalConstants.transactionMap));
			    		}
					}				
				}
			});  
			
			transParamInputButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					IOFileDialogue fileImporter = new IOFileDialogue();
	            	fileImporter.show("Mapping Log (.xml)", "xml", JFileChooser.FILES_ONLY, "Select", lastFile);
	            	File file = fileImporter.getSelectedFile();
					if(file != null){
						System.out.println("FILE IMPORTED");
						lastFile = file.getParentFile();
				    	ConfigurationParameters configuration = new ConfigurationParameters();
						configuration.getParameterMap().put(GlobalConstants.parameterMap, null);
		 				
			          	ReadConfigurations parser = new ReadConfigurations(configuration);
			          	parser.parserXml(file.getAbsolutePath(), null);
			    		
			    		if(configuration.getParameterMap().get(GlobalConstants.parameterMap) == null){
			    			JOptionPane.showMessageDialog(null, "ERROR! The imported file is not of correct format!");   
			    		}
			    		else{
			    			JOptionPane.showMessageDialog(null, "FILE IMPORTED");
							sapAutomation.updateParameterMap(configuration.getParameterMap().get(GlobalConstants.parameterMap));
			    		}
					}
				}
			});  
		}
	}
	
	class ResultPanel extends JPanel{
		PluginContext context;
		JTextPane textPane = new JTextPane();
		JScrollPane resultPanel = new JScrollPane(textPane);
		
		public ResultPanel(PluginContext context) {
			this.context = context;
			SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
	        StyleConstants.setBold(attributeSet, true);  
	  
	        // Set the attributes before adding text  
	        textPane.setCharacterAttributes(attributeSet, true);  
//	        pane.setText(simulator.ae.messages);
	        textPane.setEditable(true);
	  
	        attributeSet = new SimpleAttributeSet();  
	        StyleConstants.setItalic(attributeSet, true);  
	  
	        Document doc = textPane.getStyledDocument();  
	        try {
	        	doc.insertString(doc.getLength(), "Transaction Log \n", attributeSet);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
			
			RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
			rl.setFill(true);
			setLayout(rl);
			add(resultPanel,new Float(100));
		}
	}
}
