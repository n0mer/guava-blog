package bbejeck.guava.eventbus.subscriber;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bbejeck
 * Date: 12/31/11
 * Time: 10:04 PM
 */

public abstract class EventSubscriber<T> {

    List<T> events = new ArrayList<>();

    public EventSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    public List<T> getHandledEvents() {
        return events;
    }
}
