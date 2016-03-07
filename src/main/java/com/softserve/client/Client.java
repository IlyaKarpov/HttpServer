package com.softserve.client;

import com.softserve.ntlm.Type1HeaderGenerator;
import com.softserve.ntlm.Type3HeaderGenerator;
import com.softserve.util.StreamUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import jcifs.ntlmssp.Type2Message;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 03.02.2016.
 *
 */
public class Client {

    private static final String CRCN = "\r\n";
    private static final Logger logger = Logger.getLogger(Client.class);

    private String request;
    private int port = 9090;

    private Socket client;
    private InputStream streamToClient;
    private OutputStream streamFromClient;

    private String type2Code;

    private ClientInfo clientInfo;

    public Client(String request) {
        this.request = request;
        if (logger.isInfoEnabled()) {
            logger.info("Client's request has been initialized");
        }
        init();
    }

    public Client(String request, int port) {
        this.request = request;
        this.port = port;
        if (logger.isInfoEnabled()) {
            logger.info("Client's request and proxy server's port have been initialized");
        }
        init();
    }

//    public String getMarker() {
//        if (logger.isInfoEnabled()) {
//            logger.info("Returning client's request");
//        }
//        return request;
//    }

    private void init() {
        try {
            logger.warn("If server is turned off this method can throw an IOException");
            client = new Socket("localhost", port);
            if (logger.isInfoEnabled()) {
                logger.info("Client has accepted proxy server's port");
            }
            streamToClient = client.getInputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Stream from server to client has been initialized");
            }
            streamFromClient = client.getOutputStream();
            if (logger.isInfoEnabled()) {
                logger.info("Stream from client to server has been initialized");
            }
        } catch (IOException e) {
            logger.error("Connection has been refused");
            e.printStackTrace();
        }
    }

    public void sentMessage() {
        try {
            logger.warn("If server is turned off this method can throw an IOException");

            clientInfo = new ClientInfo("SOFTSERVE", "DP850", "ikar", "password1");

            streamFromClient.write(request.getBytes());
            if (logger.isDebugEnabled()) {
                logger.debug("Client has sent a request: " + request);
            }
            if (logger.isInfoEnabled()) {
                logger.info("Client has sent a request");
            }

            if (checkServerResponseOn407Code()) {
                sentRequestWithType1Message();

                if (checkServerResponseOn407Code()) {
                    sentRequestWithType3Message();
                }
            }

        } catch (IOException e) {
            logger.error("Connection has been refused");
            e.printStackTrace();
        } finally {
            try {
                logger.warn("If connection has been refused this method can throw an IOException");

                if (logger.isInfoEnabled()) {
                    logger.info("Closing connection");
                }

                client.close();

                if (logger.isInfoEnabled()) {
                    logger.info("Socket has been closed");
                }
            } catch (IOException e) {
                logger.error("Client's socket hasn't been closed");

                e.printStackTrace();
            }

            if (logger.isInfoEnabled()) {
                logger.info("Client's session ends");
            }
        }
    }

    public static void main(String[] args) {
        if (logger.isInfoEnabled()) {
            logger.info("Starting...");
        }
        String request = getRequest() + CRCN;
        new Client(request).sentMessage();
    }

    private boolean checkServerResponseOn407Code() throws IOException {
        String response = StreamUtil.readMessageFromServer(client);
        boolean ifContains = response.contains("407 Proxy Authentication Required");
        if (ifContains) {
            type2Code = findTypeMessage(response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Server has sent response message with code 407: \n" + response);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Server has sent response message with code 407");
        }

        return ifContains;
    }

    private String findTypeMessage(String request) {
        final String proxyAuthenticateNtlmHeader = "Proxy-Authenticate: NTLM ";
        for (String string : request.split(CRCN)) {
            if (string.contains(proxyAuthenticateNtlmHeader)) {
                return string.replaceAll(proxyAuthenticateNtlmHeader, "").replaceAll(CRCN, "");
            }
        }
        return null;
    }

    private void sentRequestWithType1Message() throws IOException {
        Type1HeaderGenerator type1HeaderGenerator = new Type1HeaderGenerator(clientInfo.getDomain(),
                clientInfo.getWorkstation());
        String request = getRequest() + getProxyAuthorizationNTLMHeader() +
                Base64.encode(type1HeaderGenerator.generate().toByteArray()) + CRCN + CRCN;
        writeRequest(request);

        if (logger.isDebugEnabled()) {
            logger.debug("Client has sent a request : " + request);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Client has sent a request");
        }
    }

    private void sentRequestWithType3Message() throws IOException {
        Type2Message type2Message = new Type2Message(Base64.decode(type2Code));
        Type3HeaderGenerator type3HeaderGenerator = new Type3HeaderGenerator(type2Message, clientInfo);
        String request = getRequest() + getProxyAuthorizationNTLMHeader() +
                Base64.encode(type3HeaderGenerator.generate().toByteArray()) + CRCN + CRCN;
        writeRequest(request);

        if (logger.isDebugEnabled()) {
            logger.debug("Client has sent a request : " + request);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Client has sent a request");
        }
    }

    private static String getRequest() {
        return "GET / HTTP/1.1" + CRCN +
                "Accept: text/html, application/xhtml+xml, */*" + CRCN +
                "Accept-Language: uk-UA" + CRCN +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" + CRCN +
                "Accept-Encoding: gzip, deflate" + CRCN +
                "Host: localhost:9000" + CRCN +
                "Connection: Keep-Alive" + CRCN;
    }

    private static String getProxyAuthorizationNTLMHeader() {
        return "Proxy-Authorization: NTLM ";
    }

    private void writeRequest(String request) throws IOException {
        streamFromClient.write(request.getBytes());
    }

}