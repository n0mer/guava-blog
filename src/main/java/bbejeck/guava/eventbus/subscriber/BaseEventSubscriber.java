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

public abstract class BaseEventSubscriber<T> {

    EventBus eventBus;
    List<T> events = new ArrayList<T>();

    public BaseEventSubscriber(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public abstract void handleEvent(T event);

    public List<T> getHandledEvents() {
        return events;
    }
}
