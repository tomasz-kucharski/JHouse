package eu.jhouse.server.onewire;

import org.owfs.jowfsclient.Enums;
import org.owfs.jowfsclient.OwfsClient;
import org.owfs.jowfsclient.OwfsClientFactory;

/**
 * @author tomekk
 * @since 2010-10-06, 00:36:39
 */
public class OWFSFileSystemFactory {

    private String owfsServerAddress;

    public void setOWFSServerAddress(String OWFSServerAddress) {
        this.owfsServerAddress = OWFSServerAddress;
    }

    public OWFSFileSystem createOWFSFileSystem() throws Exception {
        OwfsClient client = OwfsClientFactory.newOwfsClient(owfsServerAddress,4304, true);
        client.setDeviceDisplayFormat(Enums.OwDeviceDisplayFormat.OWNET_DDF_F_DOT_IC);
        client.setBusReturn(Enums.OwBusReturn.OWNET_BUSRETURN_ON);
        client.setPersistence(Enums.OwPersistence.OWNET_PERSISTENCE_ON);
        OWFSFileSystem fileSystem = new OWFSFileSystem();
        fileSystem.setClient(client);
        return fileSystem;
    }

}
