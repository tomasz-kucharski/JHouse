package eu.jhouse.server;

import eu.jhouse.server.runner.ConfigFileLocation;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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

	public static void main(String args[]) throws Exception {
		Main main = new Main();
		main.parseArguments(args);
		main.startServer();
	}

	private void parseArguments(String[] args) {
		Option configOption = new Option("c", "config", true, "Location of configuration directory, eg. " + ConfigFileLocation.getConfigurationDirectory());
		Option logOption = new Option("l", "logDir", true, "Location of log directory, eg. " + ConfigFileLocation.getLogDirectory());

		Options options = new Options();
		options.addOption(configOption);
		options.addOption(logOption);

		try {
			CommandLine parse = new GnuParser().parse(options, args);
			if (parse.hasOption(configOption.getOpt())) {
				ConfigFileLocation.setConfigurationDirectory("file:/"+parse.getOptionValue(configOption.getOpt())+"/");
			}
			if (parse.hasOption(logOption.getOpt())) {
				ConfigFileLocation.setLogDirectory(parse.getOptionValue(logOption.getOpt())+"/");
			}
		} catch (ParseException e) {
			HelpFormatter usageFormatter = new HelpFormatter();
			usageFormatter.setWidth(120);
			usageFormatter.printHelp("usage", options);
			System.exit(0);
		}
	}

	private void startServer() throws MalformedURLException {
		System.out.println("Starting JHouse at location: " + ConfigFileLocation.getApplicationFileLocation());
		DOMConfigurator.configure(new URL(ConfigFileLocation.getLoggingConfigurationFile()));
		log.info("Starting JHouse server...");
		log.info("\tInstallation directory        : " + ConfigFileLocation.getInstallationDirectory());
		log.info("\tLogging directory             : " + ConfigFileLocation.getLogDirectory());
		log.info("\tApplication file              : " + ConfigFileLocation.getApplicationFileLocation());
		log.info("\tLogging configuration file    : " + ConfigFileLocation.getLoggingConfigurationFile());
		log.info("\tConfiguration properties file : " + ConfigFileLocation.getConfigurationPropertiesFile());
		new ClassPathXmlApplicationContext("classpath:jetty.xml");
	}
}
