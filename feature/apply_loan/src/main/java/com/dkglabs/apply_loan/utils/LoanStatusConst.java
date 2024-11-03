package com.dkglabs.apply_loan.utils;

public class LoanStatusConst {
    public static final String INCOMPLETE = "Incomplete"; // Loan init or NBFC rejected with comment
    public static final String AWAITING_APPROVAL = "Awaiting Approval"; // Loan applied
    public static final String APPROVED = "Approved"; //
    public static final String REJECTED = "Rejected"; // NBFC rejected without comment
    public static final String PENDING = "Pending"; // NBFC Approved
    public static final String ACTIVE_LOAN = "Active Loan";
    public static final String CLOSED_LOAN = "Closed Loan";

    public static final String ENACH_ACTIVE = "ACTIVE";
    public static final String ENACH_CANCELLED = "CANCELLED";
    public static final String ENACH_LINK_EXPIRED = "LINK EXPIRED";
    public static final String ENACH_INITIALIZED = "INITIALIZED";
}
