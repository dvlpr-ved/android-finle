package com.dkglabs.loan_application.staticModels;

import com.dkglabs.loan_application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PreStaticModelList {

    HashMap<String, String> preDocumentMap = new HashMap<String, String>() {{
        put("A401", "Upload Motor Number");
        put("A406", "Upload Battery's Photo");
        put("A411", "Upload Chassis");
        put("A416", "Upload Passbook");
        put("A421", "Upload Invoice");
        put("A426", "Upload Insurance");
        put("A431", "Upload RC Receipt"); //registration
        put("A436", "Upload Additional Document"); //other documents
    }};
    HashMap<String, Integer> preNumberMap = new HashMap<String, Integer>() {{
        put("A401", 401);
        put("A406", 406);
        put("A411", 411);
        put("A416", 416);
        put("A421", 421);
        put("A426", 426);
        put("A431", 431); //registration
        put("A436", 436); //other documents
    }};

    public HashMap<String, Integer> getPreNumberMap() {
        return preNumberMap;
    }

    public void setPreNumberMap(HashMap<String, Integer> preNumberMap) {
        this.preNumberMap = preNumberMap;
    }

    HashMap<String, Integer> preIconMap = new HashMap<String, Integer>() {{
        put("A401", R.drawable.ic_motor);
        put("A406", R.drawable.ic_battery);
        put("A411", R.drawable.ic_chassis);
        put("A416", R.drawable.ic_passbook);
        put("A421", R.drawable.ic_invoice);
        put("A426", R.drawable.ic_insurance);
        put("A431", R.drawable.ic_receipt); //registration
        put("A436", R.drawable.ic_doc); //other
    }};

    HashMap<String, Boolean> preVrfsCodeMap = new HashMap<String, Boolean>() {{
        put("A401", false);
        put("A406", false);
        put("A411", false);
        put("A416", false);
        put("A421", false);
        put("A426", false);
        put("A431", false);//registration
        put("A436",false);//other documents
    }};
    static HashMap<String, List<String>> preOptional=new HashMap<String, List<String>>(){{
        //Pre Request.
        put("A401",new ArrayList<>(Arrays.asList("A401","A402", "A403", "A404","A404")));
        put("A406",new ArrayList<>(Arrays.asList("A406","A407", "A408", "A409","A410")));
        put("A411",new ArrayList<>(Arrays.asList("A411","A412", "A413", "A414","A415")));
        put("A416",new ArrayList<>(Arrays.asList("A416","A417", "A418", "A419","A420")));
        put("A421",new ArrayList<>(Arrays.asList("A421","A422", "A423", "A424","A425")));
        put("A426",new ArrayList<>(Arrays.asList("A426","A427", "A428", "A429","A430")));
        put("A431",new ArrayList<>(Arrays.asList("A431","A432", "A433", "A434","A435")));
        put("A436",new ArrayList<>(Arrays.asList("A436","A437", "A438", "A439","A440")));

        //this is for loan fulfilment Model
        put("A501",new ArrayList<>(Arrays.asList("A501","A502","A503","A504","A505")));
        put("A508",new ArrayList<>(Arrays.asList("A508","A509","A510","A511","A512")));//,"A513" -> ignoring this one for now
        put("A514",new ArrayList<>(Arrays.asList("A514","A515","A516","A517","A518")));// ,"A519" -> ignoring this one for now
        put("A520",new ArrayList<>(Arrays.asList("A520","A521","A522","A523","A524"))); //For IMEI Number

        //this is for post Model
        put("A601",new ArrayList<>((Arrays.asList("A601","A602","A603","A604","A605"))));
        put("A606",new ArrayList<>(Arrays.asList("A606","A607","A608","A609","A610")));

    }};

    public static HashMap<String, List<String>> getPreOptional() {
        return preOptional;
    }

    public void setPreOptional(HashMap<String, List<String>> preOptional) {
        this.preOptional = preOptional;
    }

    public HashMap<String, String> getPreDocumentMap() {
        return preDocumentMap;
    }

    public void setPreDocumentMap(HashMap<String, String> preDocumentMap) {
        this.preDocumentMap = preDocumentMap;
    }

    public HashMap<String, Integer> getPreIconMap() {
        return preIconMap;
    }

    public void setPreIconMap(HashMap<String, Integer> preIconMap) {
        this.preIconMap = preIconMap;
    }

    public HashMap<String, Boolean> getPreVrfsCodeMap() {
        return preVrfsCodeMap;
    }

    public void setPreVrfsCodeMap(HashMap<String, Boolean> preVrfsCodeMap) {
        this.preVrfsCodeMap = preVrfsCodeMap;
    }

}
