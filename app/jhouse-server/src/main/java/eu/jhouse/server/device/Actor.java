package eu.jhouse.server.device;

/**
 * @author tomekk
 * @since 2010-10-03, 02:13:28
 */
public class Actor extends Unit {

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void changeEnabled() {
        this.enabled = !this.enabled;
        stateChanged();
    }

    public boolean setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            changeEnabled();
            return true;
        } else {
            return false;
        }
    }

    public void stateChanged() {
        ((OutputSwitchDevice)getDevice()).sendSynchronizeRequest();
    }
}
