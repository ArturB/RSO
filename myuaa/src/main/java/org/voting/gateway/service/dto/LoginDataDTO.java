package org.voting.gateway.service.dto;

import java.io.Serializable;

public class LoginDataDTO implements Serializable {

    private String passHash;

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }
}
