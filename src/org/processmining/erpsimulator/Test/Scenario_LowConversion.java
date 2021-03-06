package org.processmining.erpsimulator.Test;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import org.processmining.erpsimulator.sap.SapConnector;
import org.processmining.erpsimulator.sap.SapTransRepo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scenario_LowConversion {

    public static void normalProcess(SapTransRepo transRepo, JCoDestination dest, String inquiryNumber, String quotationNumber, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010";
        String materialArray = "P-101";
        String quantityArray = "2";
        String scheduleLineArray = "0001";

        //create inquiry
        Map<String,String> paramMap_inquiry = new LinkedHashMap<String,String>();
        paramMap_inquiry.put("INQUIRY_NUMBER", inquiryNumber);
        paramMap_inquiry.put("DOC_TYPE", "ZPIN");
        paramMap_inquiry.put("SALES_ORG", "1000");
        paramMap_inquiry.put("DISTR_CHAN", "10");
        paramMap_inquiry.put("DIVISION", "00");
        paramMap_inquiry.put("PARTN_NUMB", "0000001032");

        //create quotation with reference
        Map<String,String> paramMap_cq_ref_iq = new LinkedHashMap<String,String>();
        paramMap_cq_ref_iq.put("QUOTATION_NUMBER", quotationNumber);
        paramMap_cq_ref_iq.put("DOC_TYPE", "AG");
        paramMap_cq_ref_iq.put("SALES_ORG", "1000");
        paramMap_cq_ref_iq.put("DISTR_CHAN", "10");
        paramMap_cq_ref_iq.put("DIVISION", "00");
        paramMap_cq_ref_iq.put("PARTN_NUMB", "0000001032");
        paramMap_cq_ref_iq.put("REF_DOC", inquiryNumber);

        //create order with reference to quotation
        Map<String,String> paramMap_co_ref_quo = new LinkedHashMap<String,String>();
        paramMap_co_ref_quo.put("REF_DOC", quotationNumber);
        paramMap_co_ref_quo.put("DOC_TYPE", "TA");
        paramMap_co_ref_quo.put("SALES_ORG", "1000");
        paramMap_co_ref_quo.put("DISTR_CHAN", "10");
        paramMap_co_ref_quo.put("DIVISION", "00");
        paramMap_co_ref_quo.put("PARTN_NUMB", "0000001032");
        paramMap_co_ref_quo.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //create delivery
        Map<String,String> paramMap_CD = new LinkedHashMap<String,String>();
        paramMap_CD.put("REF_DOC", orderNumber);
        paramMap_CD.put("DELIV_NUMB", deliveryNumber);

        //pick items
        Map<String,String> paramMap_cto = new LinkedHashMap<String,String>();
        paramMap_cto.put("WAREHOUSE_NUM", "010");
        paramMap_cto.put("DELIVERY_NUM", deliveryNumber);

        //post goods issue
        Map<String,String> paramMap_pgi = new LinkedHashMap<String,String>();
        paramMap_pgi.put("DELIVERY", deliveryNumber);
        paramMap_pgi.put("VBELN_VL", deliveryNumber);

        //cancel order
        Map<String,String> paramMap_cfo3 = new LinkedHashMap<String,String>();
        paramMap_cfo3.put("SALESDOCUMENT", orderNumber);

        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                double delayTime = 7.0;
                transRepo.createInquiry(dest,paramMap_inquiry);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                transRepo.createQuotationWithRef(dest,paramMap_cq_ref_iq);
                transRepo.commitTrans(dest);
                try {
                    double processingTime = fRandom.nextGaussian()*6+12;
                    TimeUnit.SECONDS.sleep((long) processingTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//				createQuotation(dest,paramMap_quotation);
//				createOrder(dest,paramMap_co);
                transRepo.createOrderWithRef(dest,paramMap_co_ref_quo);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                transRepo.confirmOrder(dest,paramMap_cfo);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//				changeOrder(dest,paramMap_change_qua,"quantity");
//				changeOrder(dest,paramMap_change_price,"price");
//				changeOrder(dest,paramMap_change_mat,"material");
//				changeOrder(dest,paramMap5,"delete");
//				changeOrder(dest,paramMap6,"create_delivery_date");
//				changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
//				changeDelivery(dest,paramMap8,"net_weight");
//				changeDelivery(dest,paramMap_cfo0,"delivery_quantity");
//				sendErrorMessage(dest,paramMap);
//				createShipment(dest,paramMap_cs);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);

//				cancelOrder(dest,paramMap_cfo3);
                transRepo.createInvoice(dest,paramMap_ci);
                transRepo.commitTrans(dest);
            }
            finally {
                // clean ups
                JCoContext.end(dest);
            }
        }
        catch(JCoException e) {
            e.printStackTrace();
        }
    }

    public static void actionProcess(SapTransRepo transRepo, JCoDestination dest, String inquiryNumber, String quotationNumber, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010";
        String materialArray = "P-101";
        String quantityArray = "2";
        String scheduleLineArray = "0001";

        //create inquiry
        Map<String,String> paramMap_inquiry = new LinkedHashMap<String,String>();
        paramMap_inquiry.put("INQUIRY_NUMBER", inquiryNumber);
        paramMap_inquiry.put("DOC_TYPE", "ZPIN");
        paramMap_inquiry.put("SALES_ORG", "1000");
        paramMap_inquiry.put("DISTR_CHAN", "10");
        paramMap_inquiry.put("DIVISION", "00");
        paramMap_inquiry.put("PARTN_NUMB", "0000001032");

        //create quotation with reference
        Map<String,String> paramMap_cq_ref_iq = new LinkedHashMap<String,String>();
        paramMap_cq_ref_iq.put("QUOTATION_NUMBER", quotationNumber);
        paramMap_cq_ref_iq.put("DOC_TYPE", "AG");
        paramMap_cq_ref_iq.put("SALES_ORG", "1000");
        paramMap_cq_ref_iq.put("DISTR_CHAN", "10");
        paramMap_cq_ref_iq.put("DIVISION", "00");
        paramMap_cq_ref_iq.put("PARTN_NUMB", "0000001032");
        paramMap_cq_ref_iq.put("REF_DOC", inquiryNumber);

        //create order with reference to quotation
        Map<String,String> paramMap_co_ref_quo = new LinkedHashMap<String,String>();
        paramMap_co_ref_quo.put("REF_DOC", quotationNumber);
        paramMap_co_ref_quo.put("DOC_TYPE", "TA");
        paramMap_co_ref_quo.put("SALES_ORG", "1000");
        paramMap_co_ref_quo.put("DISTR_CHAN", "10");
        paramMap_co_ref_quo.put("DIVISION", "00");
        paramMap_co_ref_quo.put("PARTN_NUMB", "0000001032");
        paramMap_co_ref_quo.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //create delivery
        Map<String,String> paramMap_CD = new LinkedHashMap<String,String>();
        paramMap_CD.put("REF_DOC", orderNumber);
        paramMap_CD.put("DELIV_NUMB", deliveryNumber);
        paramMap_CD.put("NET_WEIGHT_LIST", "250");

        //pick items
        Map<String,String> paramMap_cto = new LinkedHashMap<String,String>();
        paramMap_cto.put("WAREHOUSE_NUM", "010");
        paramMap_cto.put("DELIVERY_NUM", deliveryNumber);

        //post goods issue
        Map<String,String> paramMap_pgi = new LinkedHashMap<String,String>();
        paramMap_pgi.put("DELIVERY", deliveryNumber);
        paramMap_pgi.put("VBELN_VL", deliveryNumber);

        //cancel order
        Map<String,String> paramMap_cfo3 = new LinkedHashMap<String,String>();
        paramMap_cfo3.put("SALESDOCUMENT", orderNumber);

        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                double delayTime = 7.0;
                transRepo.createInquiry(dest,paramMap_inquiry);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                transRepo.createQuotationWithRef(dest,paramMap_cq_ref_iq);
                transRepo.commitTrans(dest);
                try {
                    double processingTime = fRandom.nextGaussian()*6+36;
                    TimeUnit.SECONDS.sleep((long) processingTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//				createQuotation(dest,paramMap_quotation);
//				createOrder(dest,paramMap_co);
                transRepo.createOrderWithRef(dest,paramMap_co_ref_quo);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                transRepo.confirmOrder(dest,paramMap_cfo);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//				changeOrder(dest,paramMap_change_qua,"quantity");
//				changeOrder(dest,paramMap_change_price,"price");
//				changeOrder(dest,paramMap_change_mat,"material");
//				changeOrder(dest,paramMap5,"delete");
//				changeOrder(dest,paramMap6,"create_delivery_date");
//				changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
//				changeDelivery(dest,paramMap8,"net_weight");
//				changeDelivery(dest,paramMap_cfo0,"delivery_quantity");
//				sendErrorMessage(dest,paramMap);
//				createShipment(dest,paramMap_cs);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);

//				cancelOrder(dest,paramMap_cfo3);
                transRepo.createInvoice(dest,paramMap_ci);
                transRepo.commitTrans(dest);
            }
            finally {
                // clean ups
                JCoContext.end(dest);
            }
        }
        catch(JCoException e) {
            e.printStackTrace();
        }
    }

    public static void abnormalProcess(SapTransRepo transRepo, JCoDestination dest, String inquiryNumber, String quotationNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010";
        String materialArray = "P-101";
        String quantityArray = "2";
        String scheduleLineArray = "0001";

        //create inquiry
        Map<String,String> paramMap_inquiry = new LinkedHashMap<String,String>();
        paramMap_inquiry.put("INQUIRY_NUMBER", inquiryNumber);
        paramMap_inquiry.put("DOC_TYPE", "ZPIN");
        paramMap_inquiry.put("SALES_ORG", "1000");
        paramMap_inquiry.put("DISTR_CHAN", "10");
        paramMap_inquiry.put("DIVISION", "00");
        paramMap_inquiry.put("PARTN_NUMB", "0000001032");

        //create quotation with reference
        Map<String,String> paramMap_cq_ref_iq = new LinkedHashMap<String,String>();
        paramMap_cq_ref_iq.put("QUOTATION_NUMBER", quotationNumber);
        paramMap_cq_ref_iq.put("DOC_TYPE", "AG");
        paramMap_cq_ref_iq.put("SALES_ORG", "1000");
        paramMap_cq_ref_iq.put("DISTR_CHAN", "10");
        paramMap_cq_ref_iq.put("DIVISION", "00");
        paramMap_cq_ref_iq.put("PARTN_NUMB", "0000001032");
        paramMap_cq_ref_iq.put("REF_DOC", inquiryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                double delayTime = 7.0;
                transRepo.createInquiry(dest,paramMap_inquiry);
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                transRepo.createQuotationWithRef(dest,paramMap_cq_ref_iq);
                transRepo.commitTrans(dest);
                try {
                    double processingTime = fRandom.nextGaussian()*6+60;
                    TimeUnit.SECONDS.sleep((long) processingTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            finally {
                // clean ups
                JCoContext.end(dest);
            }
        }
        catch(JCoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) throws Exception{
        Map<String,String> sapCredential = new HashMap<>();
        sapCredential.put("host","185.208.175.208");
        sapCredential.put("client","800");
        sapCredential.put("user","m4jid1");
        sapCredential.put("passwd","m4jidabap");
        sapCredential.put("sysnr","0");
        sapCredential.put("lang","en");
        SapConnector sapConnector = new SapConnector(sapCredential);
        JCoDestination dest = sapConnector.getDestination();
        SapTransRepo transRepo = new SapTransRepo();

        int temp_inquiryNumber = 15000258;
        int temp_quotationNumber = 5001366;
        int temp_orderNumber = 5004534;
        int temp_deliveryNumber = 87000675;

        for(int i=0;i<100;i++){
            if(i%2==0){
                if(i%4==0){ //failed
                    temp_inquiryNumber+=1;
                    temp_quotationNumber+=1;
                    String inquiryNumber = "00" + Integer.toString(temp_inquiryNumber);
                    String quotationNumber = "000" + Integer.toString(temp_quotationNumber);
                    abnormalProcess(transRepo,dest,inquiryNumber,quotationNumber);
                }else { //successful (among long-taking)
                    temp_inquiryNumber+=1;
                    temp_quotationNumber+=1;
                    temp_orderNumber+=1;
                    temp_deliveryNumber+=1;
                    String inquiryNumber = "00" + Integer.toString(temp_inquiryNumber);
                    String quotationNumber = "000" + Integer.toString(temp_quotationNumber);
                    String orderNumber = "000" + Integer.toString(temp_orderNumber);
                    String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
                    actionProcess(transRepo,dest,inquiryNumber,quotationNumber,orderNumber,deliveryNumber);
                }
            } else{ //successful
                temp_inquiryNumber+=1;
                temp_quotationNumber+=1;
                temp_orderNumber+=1;
                temp_deliveryNumber+=1;
                String inquiryNumber = "00" + Integer.toString(temp_inquiryNumber);
                String quotationNumber = "000" + Integer.toString(temp_quotationNumber);
                String orderNumber = "000" + Integer.toString(temp_orderNumber);
                String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
                normalProcess(transRepo,dest,inquiryNumber,quotationNumber,orderNumber,deliveryNumber);
            }
        }



    }
}
