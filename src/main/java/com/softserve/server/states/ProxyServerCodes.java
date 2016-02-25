package com.softserve.server.states;

/**
 * Created by ikar on 25.02.2016.
 *
 */
public enum ProxyServerCodes {

    CODE407("HTTP/1.1 407 Proxy Authentication Required " +
            "Proxy-Authenticate: NTLM " +
            "Proxy-Connection: keep-alive " +
            "Content-Length: 0");

    private String message;

    ProxyServerCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}