package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KycAuthModel {
    @JsonProperty("id")
    private String id;

    @JsonProperty("ttl")
    private int ttl;

    @JsonProperty("created")
    private String created;

    @JsonProperty("userId")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
