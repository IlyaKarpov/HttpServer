package com.softserve.client;

public class ClientInfo {
    private String domain;
    private String workstation;
    private String user;
    private String password;

    public ClientInfo(String domain, String workstation, String user, String password) {
        this.domain = domain;
        this.workstation = workstation;
        this.user = user;
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }
}