package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElectricityBillResponse extends LoanResponse implements Serializable {

}
