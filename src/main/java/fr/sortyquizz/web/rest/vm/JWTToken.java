package fr.sortyquizz.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JWTToken implements Serializable {

    private String idToken;

    public JWTToken() {
    }

    public JWTToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}
