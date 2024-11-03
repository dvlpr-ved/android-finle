package com.dkglabs.remote.utils;

public class AppUrlConstants {
    public static final String SERVER_URL = "https://finle-api-gateway.azurewebsites.net";
    public static final String POSTAL_SERVER_URL = "https://api.postalpincode.in";
    public static final String PIN_CODE = "/pincode";

    // SERVICES
    public static final String USER_SERVICE = "/user-service";
    public static final String AUTH_SERVICE = "/auth-service";
    public static final String ADMIN_SERVICE = "/admin-service";
    public static final String COLLECTION_SERVICE = "/collection-service";

    public static final String PRIVACY_POLICY = "https://www.finle.in/privacy-policy";
    public static final String TERMS_CONDITION = "https://www.finle.in/privacy-policy";

    public static final String LOGIN = AUTH_SERVICE + "/login";
    public static final String REGISTER = AUTH_SERVICE + "/register";

    //psy test
    public static final String GET_PSY_TEST = USER_SERVICE + "/psyTest";
    public static final String SAVE_PSY_TEST = USER_SERVICE + "/savePsyTest";
    public static final String GET_PSY_TEST_RESULT = USER_SERVICE + "/getPsyTestResult";


    //user
    public static final String USER_DETAILS = USER_SERVICE + "/userDetails";
    public static final String UPDATE_USER = USER_SERVICE + "/updateUser";
    public static final String UPDATE_PASSWORD = USER_SERVICE + "/updatePassword";
    public static final String DOWNLOAD_PROFILE_IMAGE = USER_SERVICE + "/downloadProfileImage";
    public static final String REMOVE_PROFILE_IMAGE = USER_SERVICE + "/removeProfileImage";
    public static final String UPLOAD_PROFILE_IMAGE = USER_SERVICE + "/uploadProfileImage";

    //loan
    public static final String SAVE_PERSONAL_DETAILS = USER_SERVICE + "/savePersonalDetails";
    public static final String SAVE_LOAN_DETAIL = USER_SERVICE + "/saveLoanDetail";
    public static final String SAVE_INCOME_DETAILS = USER_SERVICE + "/saveIncomeDetails";
    public static final String SAVE_DOCUMENT_DETAILS = USER_SERVICE + "/saveDocumentDetails";
    public static final String SAVE_COLLATERAL_DETAILS = USER_SERVICE + "/saveCollateralDetails";
    public static final String SAVE_BANK_DETAILS = USER_SERVICE + "/saveBankDetails";
    public static final String SAVE_ADDRESS_DETAILS = USER_SERVICE + "/saveAddressDetails";
    public static final String SAVE_CONTACT_DETAILS = USER_SERVICE + "/saveContactDetails";
    public static final String LOAN_DETAILS = USER_SERVICE + "/loanDetails";
    public static final String APPLY_LOAN = USER_SERVICE + "/applyLoan";
    public static final String LOAN_DOCUMENTS = USER_SERVICE + "/loanDocuments";
    public static final String DEALER_DOCUMENTS = USER_SERVICE + "/dealerDocuments";
    public static final String DEALER_LONE_DOCUMENTS = USER_SERVICE + "/dealerLoanDocuments";
    public static final String ALL_LOAN_APPLICANTS = USER_SERVICE + "/allLoanApplicants";
    public static final String ALL_NBFC_APPROVED_APPLICANTS = USER_SERVICE + "/allNbfcApprovedLoanApplicants";
    public static final String GET_LOAN_PROCESSING_DOCUMENTS = USER_SERVICE + "/getLoanProcessingDocuments";
    public static final String GET_ESIGNED_LOAN_DOCUMENT = USER_SERVICE + "/getEsignedLoanDocument";
    public static final String E_SIGN_LOAN_DOCUMENT = USER_SERVICE + "/esignLoanDocument";


    //loan doc verification
    public static final String VALIDATE_LICENCE = USER_SERVICE + "/validateLicence";
    public static final String VALIDATE_AADHAAR = USER_SERVICE + "/validateAadhaar";
    public static final String VALIDATE_PAN = USER_SERVICE + "/validatePAN";
    public static final String UPLOAD_DOCUMENT = USER_SERVICE + "/uploadDocument";
    public static final String DOWNLOAD_DOCUMENT = USER_SERVICE + "/downloadDocument";
    public static final String GET_AADHAAR_OKYC_OTP = USER_SERVICE + "/getAadhaarOkycOtp";

    //EV Score
    public static final String CALCULATE_EV_SCORE = USER_SERVICE + "/calculateEVScore";
    public static final String GET_EV_SCORE = USER_SERVICE + "/getEVScore";
    public static final String SAVE_EV_SCORE_PARAMS = USER_SERVICE + "/saveEVScoreParams";

    //collection-emi
    public static final String GET_COLLECTION_DETAILS = COLLECTION_SERVICE + "/getCollectionDetails";
    public static final String GET_LOAN_PAYMENT_DETAILS = COLLECTION_SERVICE + "/getLoanPaymentDetails";
    public static final String GET_LOAN_EMI_DETAIL = COLLECTION_SERVICE + "/getLoanEmiDetail";
    public static final String GET_LOAN_EMI_DETAILS = COLLECTION_SERVICE + "/getLoanEmiDetails";

    //payment
    public static final String PAY_CREATE_ORDER = USER_SERVICE + "/pay/cashfree/createOrder";
    public static final String PAY_STATUS = USER_SERVICE + "/pay/cashfree/status";

    public static final String PRE_SAVE_DISBURSEMENT = USER_SERVICE + "/saveLoanPreDisbursement";

    public static final String POST_SAVE_DISBURSEMENT = USER_SERVICE + "/saveLoanPostDisbursement";
    public static final String SAVE_LOAN_FULFILMENT = USER_SERVICE + "/saveLoanFullfilment";

    public static final String SAVE_LOAN_PAYMENT_DETAIL = COLLECTION_SERVICE + "/saveLoanPaymentDetails";

    public static final String GET_DEALER_DETAIL = ADMIN_SERVICE + "/getDealerDetail";
    public static final String GET_NBFC_DETAILS = ADMIN_SERVICE + "/getNBFCDetails";

}
