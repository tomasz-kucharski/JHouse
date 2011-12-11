package eu.jhouse.server.onewire;

import java.io.IOException;
import java.util.List;

import org.owfs.jowfsclient.OwfsClient;
import org.owfs.jowfsclient.OwfsClientFactory;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.Enums.OwBusReturn;
import org.owfs.jowfsclient.Enums.OwDeviceDisplayFormat;
import org.owfs.jowfsclient.Enums.OwPersistence;

public class OwfsClientExample {
    private static OwfsClient client;

    private byte inputDeviceState;
    private byte outputDeviceState;
    private byte lastDeviceStateEnabled = Byte.MAX_VALUE;


    public static void main(String[] args) throws OwfsException, IOException {
        OwfsClientExample clientExample = new OwfsClientExample();
        try {
            clientExample.initConnectionByIP("192.168.1.2");
            clientExample.listDirectories();
            clientExample.runEndlessLoop();
        } finally {
            clientExample.closeConnection();
        }
    }

    public void initConnectionByIP(String ipAddress) {
        client = OwfsClientFactory.newOwfsClient(ipAddress,4304, true);
        client.setDeviceDisplayFormat(OwDeviceDisplayFormat.OWNET_DDF_F_DOT_IC);
        client.setBusReturn(OwBusReturn.OWNET_BUSRETURN_ON);
        client.setPersistence(OwPersistence.OWNET_PERSISTENCE_ON);
    }

    public void closeConnection() throws IOException {
        if (client != null) {
            client.disconnect();
        }
    }

    public void listDirectories() throws OwfsException, IOException {
        System.out.println("List main directory");
        List<String> directories = client.listDirectoryAll("/");
        for (String dir : directories) {
            System.out.println(dir);
            List<String> subdirectories = client.listDirectoryAll(dir);
            System.out.println("List subdirectory");
            for (String subdir : subdirectories) {
                System.out.println("\t" + subdir);
            }
        }
        String value = client.read("/29.BF1E00000000DE/PIO.ALL");
        System.out.println(value);
        client.write("/29.BF1E00000000DE/PIO.ALL","0,1,0,1,0,1,0,1");
    }

    public void runEndlessLoop() {

        while (true) {
            try {
//                turnOnOutputIfInputLatched();
                turnOnOutputSequentially();
                Thread.sleep(300);
            } catch (Exception e) {

            }
        }
    }

    public void turnOnOutputSequentially() throws OwfsException, IOException {
        lastDeviceStateEnabled = (byte)(lastDeviceStateEnabled >>> 1);
        System.out.println(lastDeviceStateEnabled);
        if (lastDeviceStateEnabled == 0) {
            lastDeviceStateEnabled = Byte.MAX_VALUE;
        }
        writeOutputDevice(lastDeviceStateEnabled);
    }


    public void turnOnOutputIfInputLatched() throws OwfsException, IOException {
        inputDeviceState = readInputDevice();
        if (inputDeviceState != outputDeviceState) {
            writeOutputDevice(inputDeviceState);
            outputDeviceState = inputDeviceState;
        }
    }


    public byte readInputDevice() throws OwfsException, IOException {
        return Byte.parseByte(client.read("/29.BE1E00000000E9/sensed.BYTE"));
    }

    public void writeOutputDevice(byte newValue) throws OwfsException, IOException {
        client.write("/29.BF1E00000000DE/PIO.BYTE",Byte.toString(newValue));
    }
}
