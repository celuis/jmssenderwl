# JMS Sender

## Synopsis

This project is an example of a JMS sender, the peculiarity is that connects to resources hosted and deployed in Weblogic.

## Preparation
Find class: _JMSSender_ and change property _PROVIDER_URL_ on LOC 33, to the URL where the resources deployed in weblogic.

`properties.put(Context.PROVIDER_URL, "t3://URL_WEBLOGIC:PORT");`

In the same class go find LOC 46 and place the JNDI connection factory name stored in weblogic.

```language
       // create QueueConnectionFactory
       try {
           qcf = (QueueConnectionFactory) ctx.lookup("CONNECTION_FACTORY_JNDI"); //JNDI connection factory name stored in weblogic.
       } catch (NamingException ne) {
           ne.printStackTrace(System.err);
           System.exit(0);
       }
```

In LOC 71 place the JNDI queue name stored in weblogic.
```language
       // lookup Queue
       try {
           q = (Queue) ctx.lookup("QUEUE_JNDI_NAME"); //JNDI queue name stored in weblogic.
       } catch (NamingException ne) {
           ne.printStackTrace(System.err);
           System.exit(0);
       }
```

Now go find class _JMSSenderTest_ and in method _testSendMessage_ include the message you want to send.

```language
       @Test
       public void testSendMessage(){
           JMSSender.sendMessage("Hola");
       }
```


## Installation

After you have change the resources, use: 

`mvn clean install`