package com.clv.jms;

import org.junit.Test;

/**
 * Created by Usuario on 07/03/2016.
 */
public class JMSSenderTest {

    @Test
    public void testSendMessage(){
        JMSSender.sendMessage("Hola");
    }
}
