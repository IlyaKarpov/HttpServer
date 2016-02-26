package com.softserve.ntlm;

import com.softserve.client.ClientInfo;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;

public class Type3HeaderGenerator {
    private final Type2Message type2Message;
    private final ClientInfo clientInfo;

    public Type3HeaderGenerator(Type2Message type2Message, ClientInfo clientInfo) {
        this.type2Message = type2Message;
        this.clientInfo = clientInfo;
    }

    public Type3Message generate() {
        return new Type3Message(type2Message, clientInfo.getPassword(), clientInfo.getDomain(), clientInfo.getUser(),
                clientInfo.getWorkstation(), type2Message.getFlags());
    }
}