package com.dkglabs.loan_application.staticModels;

import com.dkglabs.loan_application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FulfillStaticModelList {
    /*
    @PARAMETERS the 506 ,507 and 508 are under Field inspection
    * */
    static Map<String, Boolean>  fulFillVrfsCodeMap= new HashMap<String, Boolean>() {{
        put("A501", false);
//        put("A506", false);
//        put("A507", false);
        put("A508", false);
        put("A514", false);//Additional Documents
        put("A520",false); //For IMEI Number
    }};
    HashMap<String, Integer> preNumberMap = new HashMap<String, Integer>() {{
        put("A501", 501);
        put("A506", 506);
        put("A507", 507);
        put("A508", 508);
        put("A514", 514);
        put("A520", 520);
    }};

    Set<String> fieldInspection=new HashSet<String>() {{
        add("A506");
        add("A507");
        add("A508");
    }};

    static HashMap<String, Integer> fullFillIcon = new HashMap<String, Integer>() {{
        put("A501", R.drawable.ic_cheque);
        put("A502", R.drawable.ic_cheque);
        put("A503", R.drawable.ic_cheque);
        put("A504", R.drawable.ic_cheque);
        put("A505", R.drawable.ic_cheque);

        put("A506", R.drawable.ic_reference);
        put("A507", R.drawable.ic_reference);
        put("A508", R.drawable.ic_reference);

        put("A509", R.drawable.ic_cheque);
        put("A510", R.drawable.ic_cheque);
        put("A511", R.drawable.ic_cheque);
        put("A512", R.drawable.ic_cheque);
        put("A513", R.drawable.ic_cheque);

        put("A514",R.drawable.ic_doc);//Additional Documents
        put("A515", R.drawable.ic_cheque);
        put("A516", R.drawable.ic_cheque);
        put("A517", R.drawable.ic_cheque);
        put("A518", R.drawable.ic_cheque);
        put("A519", R.drawable.ic_cheque);

        put("A520",R.drawable.ic_imei);//For IMEI Number
        put("A521",R.drawable.ic_imei);
        put("A522",R.drawable.ic_imei);
        put("A523",R.drawable.ic_imei);
        put("A524",R.drawable.ic_imei);
    }};

    static HashMap<String, String> fullFillDocuments = new HashMap<String, String>() {{
        put("A501", "Upload Cheque");
        put("A506", "Upload First Reference  Inspection");
        put("A507", "Upload Second Reference Document");
        put("A508", "Field Inspection");
        put("A514", "Upload Additional Document");
        put("A520","IMEI Number");
    }};

    static HashMap<String, List<String>> fulFilVrfsList=new HashMap<String ,List<String>>(){{
        put("A501",new ArrayList<>(Arrays.asList("A501","A502","A503","A504","A505")));
//        put("A506",new ArrayList<>(Arrays.asList("A506")));
//        put("A507",new ArrayList<>(Arrays.asList("A507")));
//        put("A506",new ArrayList<>(Arrays.asList("A506","A507","A508","A509","A510","A511","A512","A513")));
        put("A508",new ArrayList<>(Arrays.asList("A506","A507","A508")));
        put("A514",new ArrayList<>(Arrays.asList("A514","A515","A516","A517","A518","A519")));
        put("A520",new ArrayList<>(Arrays.asList("A520","A521","A522","A523","A524")));

    }};

    public Set<String> getFieldInspection() {
        return fieldInspection;
    }

    public void setFieldInspection(Set<String> fieldInspection) {
        this.fieldInspection = fieldInspection;
    }

    public HashMap<String, Integer> getPreNumberMap() {
        return preNumberMap;
    }

    public void setPreNumberMap(HashMap<String, Integer> preNumberMap) {
        this.preNumberMap = preNumberMap;
    }
    public static HashMap<String, List<String>> getFulFilVrfsList() {
        return fulFilVrfsList;
    }

    public static void setFulFilVrfsList(HashMap<String, List<String>> fulFilVrfsList) {
        FulfillStaticModelList.fulFilVrfsList = fulFilVrfsList;
    }


    public Map<String, Boolean> getFulFillVrfsCodeMap() {
        return fulFillVrfsCodeMap;
    }

    public void setFulFillVrfsCodeMap(Map<String, Boolean> fulFillVrfsCodeMap) {
        this.fulFillVrfsCodeMap = fulFillVrfsCodeMap;
    }

    public HashMap<String, Integer> getFullFillIcon() {
        return fullFillIcon;
    }

    public void setFullFillIcon(HashMap<String, Integer> fullFillIcon) {
        this.fullFillIcon = fullFillIcon;
    }

    public HashMap<String, String> getFullFillDocuments() {
        return fullFillDocuments;
    }

    public void setFullFillDocuments(HashMap<String, String> fullFillDocuments) {
        this.fullFillDocuments = fullFillDocuments;
    }
}
