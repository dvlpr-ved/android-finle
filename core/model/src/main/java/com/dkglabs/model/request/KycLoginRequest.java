package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KycLoginRequest {

    // Credentials For test environment
//    @JsonProperty("username")
//    private final String username = "esavari_test";
//
//    @JsonProperty("password")
//    private final String password = "br5thuwrinefrech";

    //Credentials for Production Environment.
    @JsonProperty("username")
    private final String username = "esavari_prod_v2";

    @JsonProperty("password")
    private final String password = "UmpRCCrQee";
}
