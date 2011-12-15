package eu.jhouse.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tomekk
 * @since 2010-10-06, 01:07:42
 */
public class Main {

	public static final String DEFAULT_JETTY_CONFIGURATION_PATH = "jetty.xml";

	public static void main(String args[]) throws Exception {
        String springPath;
        if (args.length != 0) {
            springPath = args[0];
        } else {
            springPath = DEFAULT_JETTY_CONFIGURATION_PATH;
        }
		new ClassPathXmlApplicationContext(springPath);
    }

}
