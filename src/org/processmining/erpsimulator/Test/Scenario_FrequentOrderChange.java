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

public class Scenario_FrequentOrderChange {
    static double delayTime = 10.0;

    public static void normalProcess(SapTransRepo transRepo, JCoDestination dest, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010,000020";
        String materialArray = "P-101,P-100";
        String quantityArray = "1,2";
        String scheduleLineArray = "0001,0002";

        //create order without reference to quotation
        Map<String,String> paramMap_co = new LinkedHashMap<String,String>();
        paramMap_co.put("DOC_TYPE", "TA");
        paramMap_co.put("SALES_ORG", "1000");
        paramMap_co.put("DISTR_CHAN", "10");
        paramMap_co.put("DIVISION", "00");
        paramMap_co.put("PARTN_NUMB", "0000001032");
        paramMap_co.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //change quantity
        Map<String,String> paramMap_change_qua = new LinkedHashMap<String,String>();
        paramMap_change_qua.put("SALESDOCUMENT", orderNumber);
        paramMap_change_qua.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_qua.put("TARGET_QTY", "2");
        paramMap_change_qua.put("REQ_QTY", "8");

        //change price
        Map<String,String> paramMap_change_price = new LinkedHashMap<String,String>();
        paramMap_change_price.put("SALESDOCUMENT", orderNumber);
        paramMap_change_price.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_price.put("COND_VAL", "10");
        paramMap_change_price.put("COND_TYPE", "K007");
        paramMap_change_price.put("CURRENCY", "EUR");

        //change material
        Map<String,String> paramMap_change_mat = new LinkedHashMap<String,String>();
        paramMap_change_mat.put("SALESDOCUMENT", orderNumber);
        paramMap_change_mat.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_mat.put("MATERIAL", "P-102");
        paramMap_change_mat.put("TARGET_QTY", "2");

        //extend delivery date
        Map<String,String> paramMap_change_delidate = new LinkedHashMap<String,String>();
        paramMap_change_delidate.put("SALESDOCUMENT", orderNumber);
        paramMap_change_delidate.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_delidate.put("SCHED_LINE", "1");
        paramMap_change_delidate.put("REQ_DATE", "20201123");

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

        //send invoice
        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
				transRepo.createOrder(dest,paramMap_co);
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
//                transRepo.changeOrder(dest,paramMap_change_qua,"quantity");
//                transRepo.changeOrder(dest,paramMap_change_price,"price");
//                transRepo.changeOrder(dest,paramMap_change_mat,"material");
//                transRepo.changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);
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

    public static void actionProcess(SapTransRepo transRepo, JCoDestination dest, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010,000020";
        String materialArray = "P-101,P-100";
        String quantityArray = "1,2";
        String scheduleLineArray = "0001,0002";

        //create order without reference to quotation
        Map<String,String> paramMap_co = new LinkedHashMap<String,String>();
        paramMap_co.put("DOC_TYPE", "TA");
        paramMap_co.put("SALES_ORG", "1000");
        paramMap_co.put("DISTR_CHAN", "10");
        paramMap_co.put("DIVISION", "00");
        paramMap_co.put("PARTN_NUMB", "0000001032");
        paramMap_co.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //change quantity
        Map<String,String> paramMap_change_qua = new LinkedHashMap<String,String>();
        paramMap_change_qua.put("SALESDOCUMENT", orderNumber);
        paramMap_change_qua.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_qua.put("TARGET_QTY", "2");
        paramMap_change_qua.put("REQ_QTY", "8");

        //change price
        Map<String,String> paramMap_change_price = new LinkedHashMap<String,String>();
        paramMap_change_price.put("SALESDOCUMENT", orderNumber);
        paramMap_change_price.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_price.put("COND_VAL", "10");
        paramMap_change_price.put("COND_TYPE", "K007");
        paramMap_change_price.put("CURRENCY", "EUR");

        //change material
        Map<String,String> paramMap_change_mat = new LinkedHashMap<String,String>();
        paramMap_change_mat.put("SALESDOCUMENT", orderNumber);
        paramMap_change_mat.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_mat.put("MATERIAL", "P-101");
        paramMap_change_mat.put("TARGET_QTY", "2");

        //extend delivery date
        Map<String,String> paramMap_change_delidate = new LinkedHashMap<String,String>();
        paramMap_change_delidate.put("SALESDOCUMENT", orderNumber);
        paramMap_change_delidate.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_delidate.put("SCHED_LINE", "1");
        paramMap_change_delidate.put("REQ_DATE", "20201123");

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

        //send invoice
        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                transRepo.createOrder(dest,paramMap_co);
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
                Random rand = new Random();
                int upperbound = 100;
                int intRandom = rand.nextInt(upperbound);
                int numChangeType = 4;
                while(intRandom>50){
                    if(intRandom%numChangeType==0){
                        transRepo.changeOrder(dest,paramMap_change_qua,"quantity");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if(intRandom%numChangeType==1){
                        transRepo.changeOrder(dest,paramMap_change_price,"price");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if(intRandom%numChangeType==2){
                        transRepo.changeOrder(dest,paramMap_change_mat,"material");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if(intRandom%numChangeType==3){
                        transRepo.changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    intRandom = rand.nextInt(upperbound);
                }
                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);
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

    public static void abnormalProcess(SapTransRepo transRepo, JCoDestination dest, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010,000020";
        String materialArray = "P-101,P-100";
        String quantityArray = "1,2";
        String scheduleLineArray = "0001,0002";

        //create order without reference to quotation
        Map<String,String> paramMap_co = new LinkedHashMap<String,String>();
        paramMap_co.put("DOC_TYPE", "TA");
        paramMap_co.put("SALES_ORG", "1000");
        paramMap_co.put("DISTR_CHAN", "10");
        paramMap_co.put("DIVISION", "00");
        paramMap_co.put("PARTN_NUMB", "0000001032");
        paramMap_co.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //change quantity
        Map<String,String> paramMap_change_qua = new LinkedHashMap<String,String>();
        paramMap_change_qua.put("SALESDOCUMENT", orderNumber);
        paramMap_change_qua.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_qua.put("TARGET_QTY", "2");
        paramMap_change_qua.put("REQ_QTY", "8");

        //change price
        Map<String,String> paramMap_change_price = new LinkedHashMap<String,String>();
        paramMap_change_price.put("SALESDOCUMENT", orderNumber);
        paramMap_change_price.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_price.put("COND_VAL", "10");
        paramMap_change_price.put("COND_TYPE", "K007");
        paramMap_change_price.put("CURRENCY", "EUR");

        //change material
        Map<String,String> paramMap_change_mat = new LinkedHashMap<String,String>();
        paramMap_change_mat.put("SALESDOCUMENT", orderNumber);
        paramMap_change_mat.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_mat.put("MATERIAL", "P-101");
        paramMap_change_mat.put("TARGET_QTY", "2");

        //extend delivery date
        Map<String,String> paramMap_change_delidate = new LinkedHashMap<String,String>();
        paramMap_change_delidate.put("SALESDOCUMENT", orderNumber);
        paramMap_change_delidate.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_delidate.put("SCHED_LINE", "1");
        paramMap_change_delidate.put("REQ_DATE", "20201123");

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

        //send invoice
        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                transRepo.createOrder(dest,paramMap_co);
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
                Random rand = new Random();
                int upperbound = 100;
                int intRandom = rand.nextInt(upperbound);
                int numChangeType = 4;
                while(intRandom>25){
                    if(intRandom%numChangeType==0){
                        transRepo.changeOrder(dest,paramMap_change_qua,"quantity");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("quantity changes");
                    }else if(intRandom%numChangeType==1){
                        transRepo.changeOrder(dest,paramMap_change_price,"price");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    System.out.println("price changes");
                    }else if(intRandom%numChangeType==2){
                        transRepo.changeOrder(dest,paramMap_change_mat,"material");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("material changes");
                    }else if(intRandom%numChangeType==3){
                        transRepo.changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                        transRepo.commitTrans(dest);
                        try {
                            TimeUnit.SECONDS.sleep((long) delayTime);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("date changes");
                    }
                    intRandom = rand.nextInt(upperbound);
                }
                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);
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

    public static void tempProcess(SapTransRepo transRepo, JCoDestination dest, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010,000020";
        String materialArray = "P-101,P-100";
        String quantityArray = "1,2";
        String scheduleLineArray = "0001,0002";

        //create order without reference to quotation
        Map<String,String> paramMap_co = new LinkedHashMap<String,String>();
        paramMap_co.put("DOC_TYPE", "TA");
        paramMap_co.put("SALES_ORG", "1000");
        paramMap_co.put("DISTR_CHAN", "10");
        paramMap_co.put("DIVISION", "00");
        paramMap_co.put("PARTN_NUMB", "0000001032");
        paramMap_co.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        //change quantity
        Map<String,String> paramMap_change_qua = new LinkedHashMap<String,String>();
        paramMap_change_qua.put("SALESDOCUMENT", orderNumber);
        paramMap_change_qua.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_qua.put("TARGET_QTY", "3");
        paramMap_change_qua.put("REQ_QTY", "8");

        //change price
        Map<String,String> paramMap_change_price = new LinkedHashMap<String,String>();
        paramMap_change_price.put("SALESDOCUMENT", orderNumber);
        paramMap_change_price.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_price.put("COND_VAL", "10");
        paramMap_change_price.put("COND_TYPE", "K007");
        paramMap_change_price.put("CURRENCY", "EUR");

        //change material
        Map<String,String> paramMap_change_mat = new LinkedHashMap<String,String>();
        paramMap_change_mat.put("SALESDOCUMENT", orderNumber);
        paramMap_change_mat.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_mat.put("MATERIAL", "P-102");
        paramMap_change_mat.put("TARGET_QTY", "2");

        //extend delivery date
        Map<String,String> paramMap_change_delidate = new LinkedHashMap<String,String>();
        paramMap_change_delidate.put("SALESDOCUMENT", orderNumber);
        paramMap_change_delidate.put("ITM_NUMBER_LIST", "000010");
        paramMap_change_delidate.put("SCHED_LINE", "1");
        paramMap_change_delidate.put("REQ_DATE", "20210228");

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

        //send invoice
        Map<String,String> paramMap_ci = new LinkedHashMap<String,String>();
        paramMap_ci.put("REF_DOC", deliveryNumber);

        try {
            try {
                // start the session
                JCoContext.begin(dest);
                transRepo.createOrder(dest,paramMap_co);
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
                transRepo.changeOrder(dest,paramMap_change_qua,"quantity");
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("quantity changes");
                transRepo.changeOrder(dest,paramMap_change_mat,"material");
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("material changes");
                transRepo.changeOrder(dest,paramMap_change_delidate,"create_delivery_date");
                transRepo.commitTrans(dest);
                try {
                    TimeUnit.SECONDS.sleep((long) delayTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("date changes");

                transRepo.createDelivery(dest,paramMap_CD);
                transRepo.commitTrans(dest);
                transRepo.createTransferOrderforDelivery(dest,paramMap_cto);
                transRepo.commitTrans(dest);
                transRepo.createPGI(dest,paramMap_pgi);
                transRepo.commitTrans(dest);
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

    public static void tempProcess2(SapTransRepo transRepo, JCoDestination dest, String orderNumber, String deliveryNumber){
        Random fRandom = new Random();
        String itemNumArray = "000010,000020";
        String materialArray = "P-101,P-100";
        String quantityArray = "1,2";
        String scheduleLineArray = "0001,0002";

        //create order without reference to quotation
        Map<String,String> paramMap_co = new LinkedHashMap<String,String>();
        paramMap_co.put("DOC_TYPE", "TA");
        paramMap_co.put("SALES_ORG", "1000");
        paramMap_co.put("DISTR_CHAN", "10");
        paramMap_co.put("DIVISION", "00");
        paramMap_co.put("PARTN_NUMB", "0000001032");
        paramMap_co.put("SALESDOCUMENTIN", orderNumber);

        //confirm order
        Map<String,String> paramMap_cfo = new LinkedHashMap<String,String>();
        paramMap_cfo.put("SALESDOCUMENT", orderNumber);
        paramMap_cfo.put("ITM_NUMBER_LIST", itemNumArray);
        paramMap_cfo.put("MATERIAL_LIST", materialArray);
        paramMap_cfo.put("TARGET_QTY_LIST", quantityArray);
        paramMap_cfo.put("SCHED_LINE_LIST", scheduleLineArray);

        Map<String,String> paramMap_cano = new LinkedHashMap<String,String>();
        paramMap_cano.put("SALESDOCUMENT", orderNumber);



        try {
            try {
                // start the session
                JCoContext.begin(dest);
                transRepo.createOrder(dest,paramMap_co);
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
                transRepo.cancelOrder(dest,paramMap_cano);
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

        int temp_orderNumber = 5004947;
        int temp_deliveryNumber = 87001088;

        for(int i=0;i<2;i++){
            //action
//            temp_orderNumber+=1;
//            temp_deliveryNumber+=1;
//            String orderNumber = "000" + Integer.toString(temp_orderNumber);
//            String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
//            actionProcess(transRepo,dest,orderNumber,deliveryNumber);



//            if(i%2==0){ //normal
//                temp_orderNumber+=1;
//                temp_deliveryNumber+=1;
//                String orderNumber = "000" + Integer.toString(temp_orderNumber);
//                String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
//                normalProcess(transRepo,dest,orderNumber,deliveryNumber);
//            } else{ //abnormal
//                temp_orderNumber+=1;
//                temp_deliveryNumber+=1;
//                String orderNumber = "000" + Integer.toString(temp_orderNumber);
//                String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
//                abnormalProcess(transRepo,dest,orderNumber,deliveryNumber);
//            }
            temp_orderNumber+=1;
            temp_deliveryNumber+=1;
            String orderNumber = "000" + Integer.toString(temp_orderNumber);
            String deliveryNumber = "00" + Integer.toString(temp_deliveryNumber);
            tempProcess2(transRepo,dest,orderNumber,deliveryNumber);
        }



    }
}
