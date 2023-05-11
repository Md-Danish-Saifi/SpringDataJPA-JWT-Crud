package com.example.myProject.Response;

public class JwtResponse {

    private String token;

    public JwtResponse() {
    }

    /**
     * @param token
     */
    public JwtResponse(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
