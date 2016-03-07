package com.softserve.client;

import org.apache.log4j.Logger;

public class ClientInfo {

    private static final Logger logger = Logger.getLogger(ClientInfo.class);

    private String domain;
    private String workstation;
    private String user;
    private String password;

    public ClientInfo(String domain, String workstation, String user, String password) {
        this.domain = domain;
        this.workstation = workstation;
        this.user = user;
        this.password = password;
        if (logger.isInfoEnabled()) {
            logger.info("Client's domain, workstation, username and password have been initialized");
        }
    }

    public String getDomain() {
        if (logger.isInfoEnabled()) {
            logger.info("Returning the workstation of client");
        }
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
        if (logger.isInfoEnabled()) {
            logger.info("Client's domain has been initialized");
        }
    }

    public String getPassword() {
        if (logger.isInfoEnabled()) {
            logger.info("Returning the password of client");
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        if (logger.isInfoEnabled()) {
            logger.info("Client's password has been initialized");
        }
    }

    public String getUser() {
        if (logger.isInfoEnabled()) {
            logger.info("Returning the username of client");
        }
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        if (logger.isInfoEnabled()) {
            logger.info("Client's username has been initialized");
        }
    }

    public String getWorkstation() {
        if (logger.isInfoEnabled()) {
            logger.info("Returning the workstation of client");
        }
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
        if (logger.isInfoEnabled()) {
            logger.info("Client's workstation has been initialized");
        }
    }
}