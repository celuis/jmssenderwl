package com.clv.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class JMSSender {

    private static InitialContext ctx = null;
    private static QueueConnectionFactory qcf = null;
    private static QueueConnection qc = null;
    private static QueueSession qsess = null;
    private static Queue q = null;
    private static QueueSender qsndr = null;

    private static TextMessage textMessage = null;


    public JMSSender() {
        super();
    }

    public static void sendMessage(String mensaje) {
        // create InitialContext
        Hashtable<String,String> properties = new Hashtable<>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        // NOTE: The port number of the server is provided in the next line,
        properties.put(Context.PROVIDER_URL, "t3://URL:PORT");

        try {
            ctx = new InitialContext(properties);
        } catch (NamingException ne) {
            ne.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got InitialContext " + ctx.toString());

        // create QueueConnectionFactory
        try {
            qcf = (QueueConnectionFactory) ctx.lookup("CONNECTION_FACTORY_JNDI"); //JNDI connection factory name stored in weblogic.
        } catch (NamingException ne) {
            ne.printStackTrace(System.err);
            System.exit(0);
        }

        System.out.println("Got QueueConnectionFactory " + qcf.toString());
        // create QueueConnection
        try {
            qc = qcf.createQueueConnection();
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got QueueConnection " + qc.toString());
        // create QueueSession
        try {
            qsess = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got QueueSession " + qsess.toString());
        // lookup Queue
        try {
            q = (Queue) ctx.lookup("QUEUE_JNDI_NAME"); //JNDI queue name stored in weblogic.
        } catch (NamingException ne) {
            ne.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got Queue " + q.toString());
        // create QueueSender
        try {
            qsndr = qsess.createSender(q);
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got QueueSender " + qsndr.toString());
        // create TextMessage
        try {
            //message = qsess.createObjectMessage();

            textMessage = qsess.createTextMessage();
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Got ObjectMessage " + textMessage.toString());
        // set object message in ObjectMessage
        try {
            textMessage.setText(mensaje);
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
            System.exit(0);
        }
        System.out.println("Set object in ObjectMessage " + textMessage.toString());
        // send message
        try {
            qsndr.send(textMessage);
        } catch (JMSException jmse) {
            System.out.println(jmse.getMessage());
            jmse.printStackTrace(System.err);
            System.exit(0);
        }

        System.out.println("Sent message ");
        // clean up
        try {
            textMessage = null;
            qsndr.close();
            qsndr = null;
            q = null;
            qsess.close();
            qsess = null;
            qc.close();
            qc = null;
            qcf = null;
            ctx = null;
        } catch (JMSException jmse) {
            jmse.printStackTrace(System.err);
        }
        System.out.println("Cleaned up and done.");
    }
}