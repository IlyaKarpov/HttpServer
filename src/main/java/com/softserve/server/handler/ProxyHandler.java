package com.softserve.server.handler;

import com.softserve.ntlm.Type2HeaderGenerator;
import com.softserve.server.states.ProxyServerCodes;
import com.softserve.util.StreamUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import jcifs.ntlmssp.Type1Message;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 05.02.2016.
 *
 */
public class ProxyHandler implements Runnable {

    private static final String CRCN = "\r\n";
    private static final Logger logger = Logger.getLogger(ProxyHandler.class);

    private Socket client;
    private InputStream streamFromClient;
    private OutputStream streamToClient;

    private Socket server;
    private InputStream streamFromServer;
    private OutputStream streamToServer;

    private String type1Code;

    public ProxyHandler(Socket client, Socket server) {
        this.client = client;
        this.server = server;
        init();
    }

    private void init() {
        try {
            logger.warn("If some socket is turned off this method can throw an IOException");
            this.streamFromClient = client.getInputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Client's input stream has been initialized");
            }
            this.streamToClient = client.getOutputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Client's output stream has been initialized");
            }
            this.streamFromServer = server.getInputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Proxy-Server's input stream has been initialized");
            }
            this.streamToServer = server.getOutputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Proxy-Server's output stream has been initialized");
            }
        } catch (IOException e) {
            logger.error("Connection refused");
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            logger.warn("If some socket is turned off this method can throw an IOException");
            if (!checkClientRequest()) {
                write407Response();

                if (checkClientRequest()) {
                    write407ResponseWithType2MessageHeader();

                    if (checkClientRequest()) {
                        readHeader();
                        writeResponse();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Connection refused");
            e.printStackTrace();
        }
        finally {
            try {
                logger.warn("If connection has been refused this method can throw an IOException");
                server.close();
                client.close();
            } catch (IOException e) {
                logger.error("The Sockets of client and server haven't been closed");
                e.printStackTrace();
            }
        }
    }

    private void readHeader() throws IOException {
        new Thread() {
            public void run() {
                try {
                    logger.warn("If connection has been refused this method can throw an IOException");

                    IOUtils.copy(streamFromClient, streamToServer);

                    if (logger.isInfoEnabled()) {
                        logger.info("Proxy-Server has sent a request from client to the HttpServer");
                    }

                    client.shutdownInput();
                    server.shutdownOutput();
                } catch (IOException e) {
                    logger.error("Connection refused");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void writeResponse() throws IOException {
        IOUtils.copy(streamFromServer, streamToClient);

        if (logger.isInfoEnabled()) {
            logger.info("Proxy-Server has sent a response from the HttpServer to the client");
        }

        client.shutdownOutput();
        server.shutdownInput();
    }

    private void write407Response() throws IOException {
        String response = ProxyServerCodes.CODE407.getMessage() + CRCN + CRCN;
        sentResponse(response);

        if (logger.isDebugEnabled()) {
            logger.debug("Proxy-Server has sent a response: \n" + response);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Proxy-Server has sent a response");
        }
    }

    private void write407ResponseWithType2MessageHeader() throws IOException {
        Type1Message type1Message = new Type1Message(Base64.decode(type1Code));
        Type2HeaderGenerator type2HeaderGenerator = new Type2HeaderGenerator(type1Message);
        String response = ProxyServerCodes.CODE407.getMessage() + " " +
                Base64.encode(type2HeaderGenerator.generate().toByteArray()) + CRCN + CRCN;
        sentResponse(response);

        if (logger.isDebugEnabled()) {
            logger.debug("Proxy-Server has sent a response: \n" + response);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Proxy-Server has sent a response");
        }
    }

    private boolean checkClientRequest() throws IOException {
        String request = StreamUtil.readMessageFromServer(client);
        boolean ifContains = request.contains("Proxy-Authorization");
        if (ifContains) {
            type1Code = findTypeMessage(request);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Proxy-Server has got client's request: \n" + request);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Proxy-Server has got client's request");
        }

        return ifContains;
    }

    private String findTypeMessage(String request) {
        final String proxyAuthorizationNtlmHeader = "Proxy-Authorization: NTLM ";
        for (String string : request.split(CRCN)) {
            if (string.contains(proxyAuthorizationNtlmHeader)) {
                return string.replaceAll(proxyAuthorizationNtlmHeader, "").replaceAll(CRCN, "");
            }
        }
        return null;
    }

    private void sentResponse(String response) throws IOException {
        streamToClient.write(response.getBytes());
    }

}