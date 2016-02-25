package com.softserve.ntlm;

import jcifs.ntlmssp.Type1Message;

public class Type1HeaderGenerator {
    private final String domain;
    private final String workstation;
    private static final int DEFAULT_FLAGS = -1576488441;

    public Type1HeaderGenerator(String domain, String workstation) {
        this.domain = domain;
        this.workstation = workstation;
    }

    public Type1Message generate() {
        return new Type1Message(DEFAULT_FLAGS, domain, workstation);
    }
}