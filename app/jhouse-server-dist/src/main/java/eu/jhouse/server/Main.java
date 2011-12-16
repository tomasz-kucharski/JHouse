package eu.jhouse.server;

import java.net.URL;
import java.security.ProtectionDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tomekk
 * @since 2010-10-06, 01:07:42
 */
public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);

	public static final String DEFAULT_JETTY_CONFIGURATION_PATH = "jetty.xml";

	public static void main(String args[]) throws Exception {
		String springPath;
		if (args.length != 0) {
			springPath = args[0];
		} else {
			springPath = DEFAULT_JETTY_CONFIGURATION_PATH;
		}
		log.debug("Starting JHouse Jetty server using:"+springPath);
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(springPath);
		log.debug("JHouse Jetty server started");

	}

	public static String getWEBINFfileLocation() {
		ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		return location.toExternalForm();
	}

}
