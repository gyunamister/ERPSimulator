package org.processmining.erpsimulator.sap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public class Transaction {
	private String activityName;
	private String transName;
	private Map<String,String> paramMap;
	private JCoDestination sapDest;
	private boolean commit;
	private boolean timePerspective;
	private String record;
	
	public Transaction(String activityName, String transName,Map<String,String> paramMap, JCoDestination dest, boolean commit) {
		this.activityName=activityName;
		this.transName=transName;
		this.paramMap=paramMap;
		this.sapDest=dest;
		this.commit=commit;
		this.timePerspective=false;
	}
	
	public void execute() {
		this.record = this.transName + " is executed with " + this.paramMap + "\n";
		try {
			try {
				// start the session
				JCoContext.begin(this.sapDest);
				this.applyTransaction(activityName, transName, paramMap, sapDest,commit,timePerspective);
				} 
			finally {
				// clean ups
				JCoContext.end(this.sapDest);
				}
			}
		catch(JCoException e) {
			e.printStackTrace();
		}
	}

	public static void applyTransaction(String activityName, String transName,Map<String,String> paramMap,JCoDestination sapDest, boolean commit, boolean timePerspective) throws JCoException {
		Random fRandom = new Random();
		SapTransRepo transRepo = new SapTransRepo();
		double delayTime = 0.0;
		if(activityName.equals("create_order")) {
			transRepo.createOrder(sapDest,paramMap);
			delayTime = 7.0;
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(activityName.equals("receive_inquiry")) {
			transRepo.createInquiry(sapDest,paramMap);
			delayTime = 7.0;
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(activityName.equals("send_quotation_with_inquiry")) {
			transRepo.createQuotationWithRef(sapDest,paramMap);
			delayTime = 7.0;
//			if(timePerspective) {
//				try {
//					double processing = fRandom.nextGaussian() * 1 + 10;
//					TimeUnit.SECONDS.sleep((long) processing);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		}else if(activityName.equals("send_quotation")) {
			transRepo.createQuotation(sapDest, paramMap);
			delayTime = 7.0;
			if (timePerspective) {
				try {
					double processing = fRandom.nextGaussian() * 1 + 10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else if(activityName.equals("create_order_with_quotation")) {
			transRepo.createOrderWithRef(sapDest,paramMap);
			delayTime = 7.0;
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("cancel_order")) {
			transRepo.cancelOrder(sapDest,paramMap);
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("confirm_order")) {
			transRepo.confirmOrder(sapDest,paramMap);
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("change_quantity")) {
			transRepo.changeOrder(sapDest,paramMap,"quantity");
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("change_price")) {
			transRepo.changeOrder(sapDest,paramMap,"price");
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("change_material")) {
			transRepo.changeOrder(sapDest,paramMap,"material");
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("extend_delivery_date")) {
			String extendedDay = paramMap.get("REQ_DATE");
			int tempExtendDay = Integer.parseInt(extendedDay);
			String tempExtendedDate = java.time.LocalDate.now().plusDays(tempExtendDay).toString();
			String extendedDate = String.join("", tempExtendedDate.split("-"));
			paramMap.replace("REQ_DATE", extendedDate);
			transRepo.changeOrder(sapDest,paramMap,"create_delivery_date");
			if(timePerspective==true) {
				try {
					double processing = fRandom.nextGaussian()*10+120;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("create_delivery")) {
			transRepo.createDelivery(sapDest,paramMap);
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("change_delivery_quantity")) {
			transRepo.changeDelivery(sapDest,paramMap,"delivery_quantity");
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*10+60;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("change_delivery_net_weight")) {
			transRepo.changeDelivery(sapDest,paramMap,"net_weight");
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*10+60;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("pick_item")) {
			transRepo.createTransferOrderforDelivery(sapDest,paramMap);
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("create_shipment")) {
			transRepo.createShipment(sapDest,paramMap);
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("post_goods_issue")) {
			transRepo.createPGI(sapDest,paramMap);
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if(activityName.equals("create_invoice")) {
			transRepo.createInvoice(sapDest,paramMap);
			if(timePerspective) {
				try {
					double processing = fRandom.nextGaussian()*1+10;
					TimeUnit.SECONDS.sleep((long) processing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if(commit==true) {
			transRepo.commitTrans(sapDest);
		}

		if(activityName.equals("send_quotation_with_inquiry")){
			double processing = fRandom.nextGaussian()*12+Integer.parseInt(paramMap.get("REL_TIMESTAMP"));
			System.out.println("sleep starts for " + processing + " seconds.");
			try {
				TimeUnit.SECONDS.sleep((long) processing);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("sleep ends");
		}

		if(delayTime!=0.0){
			try {
				TimeUnit.SECONDS.sleep((long) delayTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public String getRecord() {
		return this.record;
	}
}
