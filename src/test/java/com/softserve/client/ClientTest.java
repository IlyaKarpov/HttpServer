package com.softserve.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by ikar on 08.02.2016.
 *
 */
public class ClientTest {

    @Test
    public void testSendMessage() {
        for (int i = 0; i < 5; i++) {
            new Client(String.valueOf(i)).sentMessage();
        }
    }

}