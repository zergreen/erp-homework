/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package livescore;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

// my student import
import java.util.Scanner;

/**
 *
 * @author sarun
 */
public class Main {
    @Resource(mappedName = "jms/SimpleJMSTopic")
    private static Topic topic;
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/SimpleJMSQueue")
    private static Queue queue;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Live Score Program");
        final int NUM_MSGS;
        Connection connection = null;
        
        // student
        Scanner sc = new Scanner(System.in);
        String text = null;
        

        if ((args.length < 1) || (args.length > 2)) {
            System.err.println(
                    "Program takes one or two arguments: "
                    + "<dest_type> [<number-of-messages>]");
            System.exit(1);
        }

        String destType = args[0];
        System.out.println("Destination type is " + destType);

        if (!(destType.equals("queue") || destType.equals("topic"))) {
            System.err.println("Argument must be \"queue\" or " + "\"topic\"");
            System.exit(1);
        }

        if (args.length == 2) {
            NUM_MSGS = (new Integer(args[1])).intValue();
        } else {
            NUM_MSGS = 1;
        }

        Destination dest = null;

        try {
            if (destType.equals("queue")) {
                dest = (Destination) queue;
            } else {
                dest = (Destination) topic;
            }
        } catch (Exception e) {
            System.err.println("Error setting destination: " + e.toString());
            System.exit(1);
        }

        
        try {
            connection = connectionFactory.createConnection();

            Session session = connection.createSession(
                        false,
                        Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(dest);
            TextMessage message = session.createTextMessage();
            //producer.setTimeToLive(10000);  //message live is set to 10 seconds
            //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            for (int i = 0; ; i++) {
                
                System.out.print("Enter Live Score (q to exit) : ");
                text = sc.nextLine();
                
                if(text.equals(new String("q"))){
                    System.out.println("Stop input");
                    break;
                }           
                
                
                message.setText(text);
//                System.out.println("Sending message: " + message.getText());
                producer.send(message);
                  /*if (i == 2) {
                    producer.send(message, DeliveryMode.NON_PERSISTENT, 4, 5000);
                }
                  else {
                      producer.send(message);
                  }*/
                  
                
            }

            /*
             * Send a non-text control message indicating end of
             * messages.
             */
            // ส่ง ข้อความว่าง เพื่อเป็นการปิด msg
            producer.send(session.createMessage());
        } catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }

    
    
}
