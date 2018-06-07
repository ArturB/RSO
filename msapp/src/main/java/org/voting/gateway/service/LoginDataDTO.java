package org.voting.gateway.service;

import java.io.Serializable;

public class LoginDataDTO implements Serializable {

    private String passHash;
    private boolean disabled;
    private String group;

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
