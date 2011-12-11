package eu.jhouse.server.device;

import java.util.Set;

/**
 * @author tomekk
 * @since 2010-10-09, 00:56:53
 */
public class ActorsChangedEvent {

    private Set<Actor> actorsChanged;

    public ActorsChangedEvent(Set<Actor> actorSet) {
        this.actorsChanged = actorSet;

    }

    public Set<Actor> getActorsChanged() {
        return actorsChanged;
    }
}
