package eu.jhouse.server;

import java.net.URL;
import java.security.ProtectionDomain;
import org.apache.log4j.xml.DOMConfigurator;
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

	public static final String LOGGING_FILENAME = "conf/log4j.xml";
	
	private String jettyConfigurationPath;
	
	public static void main(String args[]) throws Exception {
		Main main = new Main();
		main.parseArguments(args);
		main.configureLogging();
		main.startServer();
	}

	private void parseArguments(String[] args) {
		if (args.length != 0) {
			jettyConfigurationPath = args[0];
		} else {
			jettyConfigurationPath = DEFAULT_JETTY_CONFIGURATION_PATH;
		}
	}

	private  void startServer() {
		log.debug("Starting JHouse Jetty server using:"+jettyConfigurationPath);
		new ClassPathXmlApplicationContext(jettyConfigurationPath);
		log.debug("JHouse Jetty server started");
	}

	private void configureLogging() {
		System.out.println("Loading logging configuration...");
		DOMConfigurator.configure(LOGGING_FILENAME);
	}

	public static String getWEBINFfileLocation() {
		ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		return location.toExternalForm();
	}
}
