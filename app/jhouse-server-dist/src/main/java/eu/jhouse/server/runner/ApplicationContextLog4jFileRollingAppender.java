package eu.jhouse.server.runner;

import org.apache.log4j.RollingFileAppender;

/**
 * @author tkucharski tomasz.kucharski@javart.eu
 * @since 29.01.12, 21:36
 */
public class ApplicationContextLog4jFileRollingAppender extends RollingFileAppender {

	@Override
	public void setFile(String file) {
		super.setFile(ConfigFileLocation.getLogDirectory()+file);
	}
}
