package eu.jhouse.server.brain;

import eu.jhouse.server.bus.Command;
import eu.jhouse.server.device.ActorsChangedEvent;

/**
 * @author tomekk
 * @since 2010-10-09, 00:03:55
 */
public class ActorChangedBrainConnector implements Command {

	private Brain brain;

	@Override
	public void execute(Object event) {
		ActorsChangedEvent changedEvent = (ActorsChangedEvent) event;
		brain.thinkForNewFacts(changedEvent.getActorsChanged());
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}
}
