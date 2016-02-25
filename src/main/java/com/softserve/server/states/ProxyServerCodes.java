package com.softserve.server.states;

/**
 * Created by ikar on 25.02.2016.
 *
 */
public enum ProxyServerCodes {

    CODE407("HTTP/1.1 407 Proxy Authentication Required" + "\r\n" +
            "Proxy-Authenticate: NTLM" + "\r\n" +
            "Proxy-Connection: keep-alive" + "\r\n" +
            "Content-Length: 0" + "\r\n" + "\r\n");

    private String message;

    ProxyServerCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}