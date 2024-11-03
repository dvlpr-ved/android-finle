package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanProcessingDocument implements Serializable {

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("generateContractResponseDtoList")
    private List<GenerateContractResponse> generateContractResponses;

}
