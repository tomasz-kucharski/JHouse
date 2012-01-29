package eu.jhouse.server;

import eu.jhouse.server.device.CommunicationReader;
import eu.jhouse.server.device.CommunicationWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomekk
 * @since 2010-10-10, 23:23:11
 */
public class Server {
	Logger log = LoggerFactory.getLogger(Server.class);

	private CommunicationReader reader;

	private CommunicationWriter writer;

	public void setReader(CommunicationReader reader) {
		this.reader = reader;
	}

	public void setWriter(CommunicationWriter writer) {
		this.writer = writer;
	}

	public void run() throws NetworkException {
		log.info("Starting server...");
		reader.init();
		writer.clear();
		serverLoop();
		reader.close();
	}

	private void serverLoop() throws NetworkException {
		boolean interrupted = false;
		while (!interrupted) {
			reader.read();
			writer.flush();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}
	}


}
