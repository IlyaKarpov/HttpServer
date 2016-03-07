package com.softserve.ntlm;

import jcifs.ntlmssp.Type1Message;
import jcifs.util.Base64;
import org.apache.log4j.Logger;

public class Type1HeaderGenerator {
    private final String domain;
    private final String workstation;
    private static final int DEFAULT_FLAGS = -1576488441;
    private static final Logger logger = Logger.getLogger(Type1HeaderGenerator.class);

    public Type1HeaderGenerator(String domain, String workstation) {
        this.domain = domain;
        this.workstation = workstation;
    }

    public Type1Message generate() {
        Type1Message type1Message = new Type1Message(DEFAULT_FLAGS, domain, workstation);
        if (logger.isDebugEnabled()) {
            logger.debug("NTMLs type 1 message has been generated: " + Base64.encode(type1Message.toByteArray()));
        }
        if (logger.isInfoEnabled()) {
            logger.info("NTMLs type 1 message has been generated");
        }
        return type1Message;
    }
}