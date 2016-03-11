package com.softserve.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


public class TestCertificate {
    private static final int PORT = 9999;

    public static final TrustManager[] trustManager = new TrustManager[]
            {
                    new X509TrustManager()
                    {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                            for (X509Certificate cert : certs)  {
                                System.out.println( "============================================\n"  );
                                System.out.println( cert  );
                            }
                        }
                    }
            };



    public static void main(String[] args) throws Exception {

        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustManager, new java.security.SecureRandom());
        SSLSocketFactory sslSocketFactory = sc.getSocketFactory();

//        SSLSocket sslsocket = (SSLSocket) sslSocketFactory.createSocket("google.com", 443);
//        SSLSocket sslsocket = (SSLSocket) sslSocketFactory.createSocket("cisco.webex.com", 443);
        SSLSocket sslsocket = (SSLSocket) sslSocketFactory.createSocket("localhost", PORT);
        sslsocket.startHandshake();
        sslsocket.close();

    }
}