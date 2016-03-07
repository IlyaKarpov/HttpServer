package com.softserve.ntlm;

import com.softserve.client.ClientInfo;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.util.Base64;
import org.apache.log4j.Logger;

public class Type3HeaderGenerator {
    private final Type2Message type2Message;
    private final ClientInfo clientInfo;
    private static final Logger logger = Logger.getLogger(Type3HeaderGenerator.class);

    public Type3HeaderGenerator(Type2Message type2Message, ClientInfo clientInfo) {
        this.type2Message = type2Message;
        this.clientInfo = clientInfo;
    }

    public Type3Message generate() {
        Type3Message type3Message = new Type3Message(type2Message, clientInfo.getPassword(), clientInfo.getDomain(), clientInfo.getUser(),
                clientInfo.getWorkstation(), type2Message.getFlags());
        if (logger.isDebugEnabled()) {
            logger.debug("NTMLs type 1 message has been generated: " + Base64.encode(type3Message.toByteArray()));
        }
        if (logger.isInfoEnabled()) {
            logger.info("NTMLs type 1 message has been generated");
        }
        return type3Message;
    }
}