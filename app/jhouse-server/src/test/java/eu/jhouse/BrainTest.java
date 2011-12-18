package eu.jhouse;

import eu.jhouse.server.NetworkException;
import eu.jhouse.server.brain.Brain;
import eu.jhouse.server.device.Actor;
import eu.jhouse.server.device.OutputSwitchDevice;
import eu.jhouse.server.device.Sensor;
import java.io.File;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author tomekk
 * @since 2010-10-07, 23:26:27
 */
public class BrainTest {
    private static final String LOGIC_PATH = "src/test/resources/conf/TestSimpleLogic.drl";

    @Test
    public void shouldLoadSimpleLogic() {
        //given
        Brain brain = new Brain();

        brain.setPathToLogicFile(LOGIC_PATH);

        //when
        brain.init();
    }

    @Test
    public void shouldTurnOnLight() throws NetworkException {

        //given
        Brain brain = new Brain();
        brain.setPathToLogicFile(LOGIC_PATH);
        brain.init();
        List list = new ArrayList();

        Sensor sensor = new Sensor();
        sensor.setName("Switch1".intern());
        sensor.setEnabled(true);

        OutputSwitchDevice device = mock(OutputSwitchDevice.class);
        Actor actor = new Actor();
        actor.setName("Light1".intern());
        actor.setDevice(device);

        list.add(sensor);
        list.add(actor);

        //when
        brain.thinkForNewFacts(list);

        //then
        verify(device).sendSynchronizeRequest();
    }

    @Test
    public void light2ShouldChangeState() throws NetworkException {

        //given
        Brain brain = new Brain();
        brain.setPathToLogicFile(LOGIC_PATH);
        brain.init();

        List list = new ArrayList();
        OutputSwitchDevice device = mock(OutputSwitchDevice.class);
        Actor actor = new Actor();
        actor.setName("Light2".intern());
        actor.setDevice(device);
        list.add(actor);

        //when
        brain.thinkForNewFacts(list);

        //then
        verify(device).sendSynchronizeRequest();
    }

    @Test
    public void light1ShouldChangeStateTwice() throws NetworkException {

        //given
        Brain brain = new Brain();
        brain.setPathToLogicFile(LOGIC_PATH);
        brain.init();

        OutputSwitchDevice device = mock(OutputSwitchDevice.class);
        Actor actor = new Actor();
        actor.setName("Light1".intern());
        actor.setDevice(device);
        brain.thinkForNewFact(actor);

        Sensor sensor = new Sensor();
        sensor.setName("Switch1");
        brain.thinkForNewFact(sensor);

        //when
        sensor.setEnabled(true);
        brain.thinkForNewFact(sensor);
        sensor.setEnabled(false);
        brain.thinkForNewFact(sensor);

        sensor.setEnabled(true);
        brain.thinkForNewFact(sensor);
        sensor.setEnabled(false);
        brain.thinkForNewFact(sensor);

        //then
        verify(device,times(2)).sendSynchronizeRequest();
    }
}
