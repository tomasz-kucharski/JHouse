package eu.jhouse;

import eu.jhouse.server.onewire.OWFSCommunicationReader;
import eu.jhouse.server.onewire.OWFSFileSystem;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author tomekk
 * @since 2010-10-03, 17:06:48
 */
public class CommunicationServerTest {

	@Test
	public void shouldBeDeviceDirectory() {
		//given
		OWFSCommunicationReader communicationServer = new OWFSCommunicationReader();
		communicationServer.setFileSystem(new OWFSFileSystem());
		String directory = "29.34r5rfs";

		//when
		boolean isDirectory = communicationServer.isDeviceDirectory(directory);

		//then
		Assert.assertEquals(true, isDirectory);
	}

	@Test
	public void shouldNotBeDeviceDirectory() {
		//given
		OWFSCommunicationReader communicationServer = new OWFSCommunicationReader();
		communicationServer.setFileSystem(new OWFSFileSystem());
		String directory = "55.34r5rfs";

		//when
		boolean isDirectory = communicationServer.isDeviceDirectory(directory);

		//then
		Assert.assertEquals(false, isDirectory);
	}
}
