package bbejeck.guava.eventbus.subscriber;

import bbejeck.guava.eventbus.events.CashPurchaseEvent;
import bbejeck.guava.eventbus.events.CreditPurchaseEvent;
import bbejeck.guava.eventbus.events.SimpleEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bbejeck
 * Date: 1/5/12
 * Time: 10:36 PM
 */
public class MultiHandlerSubscriber {

    private List<CashPurchaseEvent> cashEvents = new ArrayList<>();
    private List<CreditPurchaseEvent> creditEvents = new ArrayList<>();
    private List<SimpleEvent> simpleEvents = new ArrayList<>();

    public MultiHandlerSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void handleCashEvents(CashPurchaseEvent event) {
        cashEvents.add(event);
    }

    @Subscribe
    public void handleCreditEvents(CreditPurchaseEvent event) {
        creditEvents.add(event);
    }

    @Subscribe
    public void handleSimpleEvents(SimpleEvent event) {
        simpleEvents.add(event);
    }

    public List<CashPurchaseEvent> getCashEvents() {
        return cashEvents;
    }

    public List<CreditPurchaseEvent> getCreditEvents() {
        return creditEvents;
    }

    public List<SimpleEvent> getSimpleEvents() {
        return simpleEvents;
    }
}
