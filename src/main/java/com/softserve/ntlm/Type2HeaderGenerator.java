package com.softserve.ntlm;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.util.Base64;
import org.apache.log4j.Logger;

public class Type2HeaderGenerator {
    private final Type1Message type1Message;
    private static final Logger logger = Logger.getLogger(Type2HeaderGenerator.class);

    public Type2HeaderGenerator(Type1Message type1Message) {
        this.type1Message = type1Message;
    }

    public Type2Message generate() {
        Type2Message type2Message = new Type2Message(type1Message);
        type2Message.setChallenge("testChallenge".getBytes());
        type2Message.setContext("testContext".getBytes());
        type2Message.setTarget("testTarget");
        type2Message.setTargetInformation("targetInformation".getBytes());
        type2Message.setFlags(type1Message.getFlags());
        if (logger.isDebugEnabled()) {
            logger.debug("NTMLs type 2 message has been generated: " + Base64.encode(type2Message.toByteArray()));
        }
        if (logger.isInfoEnabled()) {
            logger.info("NTMLs type 2 message has been generated");
        }
        return type2Message;
    }
}