package bbejeck.guava.eventbus.subscriber;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by IntelliJ IDEA.
 * User: bbejeck
 * Date: 1/4/12
 * Time: 11:04 PM
 */
public class AllEventSubscriber extends EventSubscriber<Object> {

    public AllEventSubscriber(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void handleEvent(Object event) {
        events.add(event);
    }
}
