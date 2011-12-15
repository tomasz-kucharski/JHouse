package eu.jhouse;

import eu.jhouse.server.Server;
import eu.jhouse.server.device.CommunicationReader;
import eu.jhouse.server.device.CommunicationWriter;
import eu.jhouse.server.onewire.OWFSCommunicationReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author tomekk
 * @since 2010-10-06, 01:07:42
 */
public class Main {

    public static void main(String args[]) throws Exception {
        String springPath;
        if (args.length != 0) {
            springPath = args[0];
        } else {
            springPath = "jetty.xml";
        }
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext (springPath);
    }

}
