package eu.jhouse.server.onewire;

import org.owfs.jowfsclient.Enums;
import org.owfs.jowfsclient.OwfsClient;
import org.owfs.jowfsclient.OwfsException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author tomekk
 * @since 2010-10-03, 17:26:17
 */
public class OWFSFileSystem implements OwfsClient {

    public static final String UNCACHED = "uncached";

    private Stack<String> path = new Stack<String>();

    private OwfsClient client;
    private static final String SLASH = "/";

    public void setClient(OwfsClient client) {
        this.client = client;
    }

    public OwfsClient getClient() {
        return client;
    }

    public void changeDirectory(String directory) {
        this.path.push(directory);
    }

    public void changeDirectoryUp() {
        this.path.pop();
    }

    public void changeDirectoryRoot() {
        this.path.clear();
    }

    public String getCurrentPath() {
        StringBuffer buffer = new StringBuffer();
        for (String directory : path) {
            buffer.append(SLASH).append(directory);
        }
        return buffer.toString();
    }

    public String getCurrentDirectory() {
        StringBuffer buffer = new StringBuffer();
        for (String directory : path) {
            buffer.append(SLASH).append(directory);
        }
        return buffer.toString();
    }

    public void disconnect() throws IOException {
        client.disconnect();
    }

    public void setTimeout(int timeout) {
        client.setTimeout(timeout);
    }

    public void setDeviceDisplayFormat(Enums.OwDeviceDisplayFormat deviceDisplay) {
        client.setDeviceDisplayFormat(deviceDisplay);
    }

    public void setTemperatureScale(Enums.OwTemperatureScale tempScale) {
        client.setTemperatureScale(tempScale);
    }

    public void setPersistence(Enums.OwPersistence persistence) {
        client.setPersistence(persistence);
    }

    public void setAlias(Enums.OwAlias alias) {
        client.setAlias(alias);
    }

    public void setBusReturn(Enums.OwBusReturn busReturn) {
        client.setBusReturn(busReturn);
    }

    public String read(String path) throws IOException, OwfsException {
        return client.read(getCurrentPath()+SLASH+path);
    }

    public void write(String path, String dataToWrite) throws IOException, OwfsException {
        client.write(getCurrentPath()+SLASH+path,dataToWrite);
    }

    public Boolean exists(String path) throws IOException, OwfsException {
        return client.exists(getCurrentPath()+SLASH+path);
    }

    public Boolean exists() throws IOException, OwfsException {
        return client.exists(getCurrentPath());
    }

    public List<String> listDirectoryAll(String path) throws OwfsException, IOException {
        List<String> stringList = client.listDirectoryAll(getCurrentPath() + SLASH + path);
        return truncateAbsolutePath(stringList);
    }

    public List<String> listDirectoryAll() throws OwfsException, IOException {
        List<String> list = client.listDirectoryAll(getCurrentPath());
        return truncateAbsolutePath(list);
    }

    private List<String> truncateAbsolutePath(List<String> list) {
        List<String> finalList = new ArrayList<String>();
        for (String directory : list) {
            String[] pathElements = directory.split(SLASH);
            finalList.add(pathElements[pathElements.length-1]);
        }
        return finalList;
    }

    public List<String> listDirectory(String path) throws OwfsException, IOException {
        List<String> stringList = client.listDirectory(getCurrentPath() + SLASH + path);
        return truncateAbsolutePath(stringList);
    }

    public List<String> listDirectory() throws OwfsException, IOException {
        List<String> stringList = client.listDirectory(getCurrentPath());
        return truncateAbsolutePath(stringList);
    }
}
