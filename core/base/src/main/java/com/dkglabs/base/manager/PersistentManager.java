package com.dkglabs.base.manager;

import com.dkglabs.base.utils.DataUtil;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.UserResponse;

public class PersistentManager extends BasePersistentManager {

    static boolean DealerReq=false;
    public static boolean isDealerReq() {
        return DealerReq;
    }

    public static void setDealerReq(boolean dealerReq) {
        DealerReq = dealerReq;
    }

    public static LoginResponse getLoginResponse() {
        String respString = (String) readFromPreference("login_response", String.class, null);
        LoginResponse response = DataUtil.getObjectFromJson(respString, LoginResponse.class);
        return response;
    }

    public static void setLoginResponse(LoginResponse loginResponse) {
        String respString = null;
        if (loginResponse != null) {
            respString = DataUtil.getStringFromObj(loginResponse);
        }
        writeToPreference("login_response", respString);
    }

    public static UserResponse getUserResponse() {
        String respString = (String) readFromPreference("user_response", String.class, null);
        UserResponse response = DataUtil.getObjectFromJson(respString, UserResponse.class);
        return response;
    }

    public static void setUserResponse(UserResponse userResponse) {
        String respString = null;
        if (userResponse != null) {
            respString = DataUtil.getStringFromObj(userResponse);
        }
        writeToPreference("user_response", respString);
    }

    public static void setUserAlreadyLogin(Boolean alreadyLogin) {
        writeToPreference("already_login", alreadyLogin);
    }

    public static Boolean isUserAlreadyLogin() {
        return (Boolean) readFromPreference("already_login", Boolean.class, false);
    }

    public static void setNewUser(Boolean newUser) {
        writeToPreference("new_user", newUser);
    }

    public static Boolean isNewUser() {
        return (Boolean) readFromPreference("new_user", Boolean.class, true);
    }

    public static String getPushMsgToken() {
        return (String) readFromPreference("push_msg", String.class, null);
    }

    public static void setPushMsgToken(String pushMsgToken) {
        writeToPreference("push_msg", pushMsgToken);
    }


    public static String getAuthToken() {
        return (String) readFromPreference("auth_token", String.class, null);
    }

    public static void setAuthToken(String pushMsgToken) {
        writeToPreference("auth_token", pushMsgToken);
    }

    public static String getImageSignature() {
        return (String) readFromPreference("image_signature", String.class, "000");
    }

    public static void setImageSignature(String pushMsgToken) {
        writeToPreference("image_signature", pushMsgToken);
    }


}
