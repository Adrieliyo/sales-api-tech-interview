package com.macronnect.sales_api.model.dto.auth;

public class LoginResponse {
    private String accessToken;

    private String tokenType;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
