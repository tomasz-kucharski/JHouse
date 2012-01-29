package eu.jhouse.server.bus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tomekk
 * @since 2009-12-19 23:59:09
 */
public class BusImpl implements Bus {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Bus.class);

	private Map<Class, Set<Command>> eventMapping = new HashMap<Class, Set<Command>>();

	public void setEventMapping(Map<Class, Set<Command>> eventMapping) {
		this.eventMapping = eventMapping;
	}

	@Override
	public void dispatch(Object event) {
		Set<Command> eventCommandList = eventMapping.get(event.getClass());
		if (eventCommandList != null) {
			for (Command command : eventCommandList) {
				try {
					command.execute(event);
				} catch (Throwable throwable) {
					log.warn("Command: " + command + " on event: " + event + " threw exception", throwable);
				}
			}
		}
	}

	public void addCommand(Class eventClass, Command command) {
		Set<Command> eventCommandList = eventMapping.get(eventClass);
		if (eventCommandList == null) {
			eventCommandList = new HashSet<Command>();
			eventCommandList.add(command);
			eventMapping.put(eventClass, eventCommandList);
		} else {
			eventCommandList.add(command);
		}
	}
}
