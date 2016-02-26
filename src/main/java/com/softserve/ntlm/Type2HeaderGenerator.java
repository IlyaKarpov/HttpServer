package com.softserve.ntlm;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;

public class Type2HeaderGenerator {
    private final Type1Message type1Message;

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
        return type2Message;
    }
}