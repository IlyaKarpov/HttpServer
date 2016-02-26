package com.softserve.server.handler;

import com.softserve.ntlm.Type2HeaderGenerator;
import com.softserve.server.states.ProxyServerCodes;
import com.softserve.util.StreamUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import jcifs.ntlmssp.Type1Message;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by ikar on 05.02.2016.
 *
 */
public class ProxyHandler implements Runnable {

    private static final String CRCN = "\r\n";

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
            this.streamFromClient = client.getInputStream();
            this.streamToClient = client.getOutputStream();
            this.streamFromServer = server.getInputStream();
            this.streamToServer = server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
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
            e.printStackTrace();
        }
        finally {
            try {
                server.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHeader() throws IOException {
        new Thread() {
            public void run() {
                try {
                    IOUtils.copy(streamFromClient, streamToServer);
                    client.shutdownInput();
                    server.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void writeResponse() throws IOException {
        IOUtils.copy(streamFromServer, streamToClient);
        client.shutdownOutput();
        server.shutdownInput();
    }

    private void write407Response() throws IOException {
        String response = ProxyServerCodes.CODE407.getMessage() + CRCN + CRCN;
        sentResponse(response);
    }

    private void write407ResponseWithType2MessageHeader() throws IOException {
        Type1Message type1Message = new Type1Message(Base64.decode(type1Code));
        Type2HeaderGenerator type2HeaderGenerator = new Type2HeaderGenerator(type1Message);
        String response = ProxyServerCodes.CODE407.getMessage() + " " +
                Base64.encode(type2HeaderGenerator.generate().toByteArray()) + CRCN + CRCN;
        sentResponse(response);
    }

    private boolean checkClientRequest() throws IOException {
        String request = StreamUtil.readMessageFromServer(client);
        boolean ifContains = request.contains("Proxy-Authorization");
        if (ifContains) {
            type1Code = findTypeMessage(request);
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