package com.dkglabs.loan_application.staticModels;

import com.dkglabs.loan_application.R;

import java.util.HashMap;

public class PostStaticModelList {
    HashMap<String, String> postDocumentMap = new HashMap<String, String>() {{
        put("A601", "Upload Final RC");
        put("A606", "Additional Document"); //other documents
    }};
    HashMap<String, Integer> postNumberType = new HashMap<String, Integer>() {{
        put("A601", 601);
        put("A606", 606); //other documents
    }};

    public HashMap<String, Integer> getPostNumberType() {
        return postNumberType;
    }

    public void setPostNumberType(HashMap<String, Integer> postNumberType) {
        this.postNumberType = postNumberType;
    }

    HashMap<String, Integer> postIconMap = new HashMap<String, Integer>() {{
        put("A601", R.drawable.ic_motor);
        put("A606", R.drawable.ic_doc); //other
    }};
    HashMap<String, Boolean> postVrfsCodeMap = new HashMap<String, Boolean>() {{
        put("A601", false);
        put("A606",false);//other documents
    }};

    public HashMap<String, String> getPostDocumentMap() {
        return postDocumentMap;
    }

    public void setPostDocumentMap(HashMap<String, String> postDocumentMap) {
        this.postDocumentMap = postDocumentMap;
    }

    public HashMap<String, Integer> getPostIconMap() {
        return postIconMap;
    }

    public void setPostIconMap(HashMap<String, Integer> postIconMap) {
        this.postIconMap = postIconMap;
    }

    public HashMap<String, Boolean> getPostVrfsCodeMap() {
        return postVrfsCodeMap;
    }

    public void setPostVrfsCodeMap(HashMap<String, Boolean> postVrfsCodeMap) {
        this.postVrfsCodeMap = postVrfsCodeMap;
    }
}
