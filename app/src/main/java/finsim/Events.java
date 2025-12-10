package finsim;

import finsim.event.AnnualGrowth;
import finsim.event.InflatingAmt;
import finsim.event.Move;
import finsim.event.PercentMove;

import java.util.*;
import java.util.stream.Collectors;

import static finsim.Constant.INFLATION_RATE;
import static finsim.Event.Purpose.*;
import static finsim.EventType.*;
import static finsim.Util.asStream;

public class Events {
    private Map<EventType, List<Event>> events = new HashMap<>();

    public Events() {
    }

    public Events(Map<EventType, List<Event>> events) {
        if (events == null)
            throw new IllegalArgumentException("events must not be null");
        this.events = events;
    }

    public static Events empty() {
        Map<EventType, List<Event>> e = new HashMap<>();
        e.put(Initial, Collections.emptyList());
        e.put(Monthly, Collections.emptyList());
        e.put(MonthlyRemainder, Collections.emptyList());
        e.put(Final, Collections.emptyList());
        return new Events(e);
    }

    public static Events defaultEvents() {
        Events e = empty();
        e.setEvents(Monthly, defaultMonthlyEvents());
        e.setEvents(MonthlyRemainder, defaultMonthlyRemainderEvents());
        return e;
    }

    public static List<Event> defaultMonthlyEvents() {
        return List.of(
                new InflatingAmt("spending", PostTax, -100.0, INFLATION_RATE, "checking")
        );
    }

    public static List<Event> defaultMonthlyRemainderEvents() {
        return List.of(
                new PercentMove("checking remainder to savings", PostExpense, 0.3, "checking",
                        "savings")
        );
    }

    public static Events defaultWorkingEvents() {
        Events e = empty();
        e.setEvents(Monthly, Arrays.asList(
                // pre-tax
                new InflatingAmt("income", Income, 1000.0, 0.01, "checking"),
                // post-tax
                new Move("move", PostTax, 500.0, "checking", "savings"),
                // growth
                new AnnualGrowth("growth", Growth, 0.08, "growth")
        ));
        e.setEvents(MonthlyRemainder, List.of(
                new PercentMove("checking remainder to savings", PostExpense, 0.5, "checking",
                        "savings")
        ));
        return e;
    }

    public void clear() {
        events = new HashMap<>();
    }

    public void setEvents(EventType eventType, List<Event> events) {
        this.events.put(eventType, events);
    }

    public Collection<Event> getChain(EventType eventType) {
        return asStream(events.get(eventType))
                .sorted(Comparator.comparingInt(a -> a.getPurpose().getOrder()))
                .collect(Collectors.toList());
    }

    public Event getEvent(EventType eventType, String name) {
        return events.get(eventType).stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public void prependEvent(EventType eventType, Event event) {
        events.get(eventType).add(0, event);
    }

    public void replace(EventType eventType, Event event) {
        List<Event> newList = events.get(eventType).stream()
                .filter(e -> !e.getName().equals(event.getName()))
                .collect(Collectors.toList());
        newList.add(0, event);
        events.put(eventType, newList);
    }

    public Events withReplace(EventType eventType, Event event) {
        replace(eventType, event);
        return this;
    }

    public Events withPrependEvent(EventType eventType, Event event) {
        prependEvent(eventType, event);
        return this;
    }

    public Events without(EventType eventType, String name) {
        events.put(eventType, events.get(eventType).stream()
                .filter(e -> !e.getName().equals(name))
                .collect(Collectors.toList()));
        return this;
    }

    public void appendEvent(EventType eventType, Event event) {
        events.get(eventType).add(event);
    }

    public Events withMonthly(Event event) {
        appendEvent(Monthly, event); // TODO: should this be replace?
        return this;
    }

    public Events withInitial(Event event) {
        replace(Initial, event);
        return this;
    }
}
