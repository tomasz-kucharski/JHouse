package eu.jhouse.server.runner;

import java.net.URL;
import java.security.ProtectionDomain;

/**
 * @author tkucharski tomasz.kucharski@jhouse.eu
 * @since 28.01.12, 19:47
 */
public class ConfigFileLocation {

	private static String applicationFileLocation;

	private static String installationDirectory = getApplicationFileLocation().substring(0, getApplicationFileLocation().lastIndexOf("/"));

	private static String configurationDirectory = getInstallationDirectory() + "/conf/";
	
	private static String logDirectory = getInstallationDirectory().substring(5) + "/logs/";

	public static String getApplicationFileLocation() {
		if (applicationFileLocation == null) {
			ProtectionDomain protectionDomain = ConfigFileLocation.class.getProtectionDomain();
			URL location = protectionDomain.getCodeSource().getLocation();
			applicationFileLocation = location.toExternalForm();
		}
		return applicationFileLocation;
	}

	public static String getInstallationDirectory() {
		return installationDirectory;
	}

	public static String getConfigurationDirectory() {
		return configurationDirectory;
	}

	public static void setConfigurationDirectory(String configurationDirectory) {
		ConfigFileLocation.configurationDirectory = configurationDirectory;
	}

	public static String getConfigurationPropertiesFile() {
		return getConfigurationDirectory() + "jhouse.properties";
	}

	public static String getLoggingConfigurationFile() {
		return getConfigurationDirectory() + "log4j.xml";
	}

	public static String getLogDirectory() {
		return logDirectory;
	}

	public static void setLogDirectory(String logDirectory) {
		ConfigFileLocation.logDirectory = logDirectory;
	}
}
