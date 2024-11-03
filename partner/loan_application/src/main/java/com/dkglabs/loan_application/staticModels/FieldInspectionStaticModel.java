package com.dkglabs.loan_application.staticModels;

import java.util.ArrayList;
import java.util.List;

public class FieldInspectionStaticModel {
    static List<String> refOne = new ArrayList<String>() {{
        add("A506");
        add("A525");
        add("A526");
    }};
    public static String refOnePic="A529";

    static List<String> refTwo = new ArrayList<String>() {{
        add("A507");
        add("A527");
        add("A528");
    }};
    public static String refTwoPic="A530";

    public static List<String> getRefOne() {
        return refOne;
    }

    public static void setRefOne(List<String> refOne) {
        FieldInspectionStaticModel.refOne = refOne;
    }

    public static String getRefOnePic() {
        return refOnePic;
    }

    public static void setRefOnePic(String refOnePic) {
        FieldInspectionStaticModel.refOnePic = refOnePic;
    }

    public static List<String> getRefTwo() {
        return refTwo;
    }

    public static void setRefTwo(List<String> refTwo) {
        FieldInspectionStaticModel.refTwo = refTwo;
    }

    public static String getRefTwoPic() {
        return refTwoPic;
    }

    public static void setRefTwoPic(String refTwoPic) {
        FieldInspectionStaticModel.refTwoPic = refTwoPic;
    }
}
