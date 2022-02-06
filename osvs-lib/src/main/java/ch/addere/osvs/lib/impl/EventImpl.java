package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventType;
import ch.addere.osvs.lib.field.types.EventsEntry;

import java.util.Objects;

public abstract class EventImpl implements EventsEntry {

  private final EventType eventType;
  private final String eventValue;

  protected EventImpl(EventType eventType, String eventValue) {
    Objects.requireNonNull(eventType, "argument eventType must not be null");
    Objects.requireNonNull(eventValue, "argument eventValue must not be null");

    this.eventType = eventType;
    this.eventValue = eventValue;
  }

  @Override
  public EventType eventTyp() {
    return eventType;
  }

  @Override
  public String eventValue() {
    return eventValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EventImpl event = (EventImpl) o;

    if (eventType != event.eventType) return false;
    return eventValue.equals(event.eventValue);
  }

  @Override
  public int hashCode() {
    int result = eventType.hashCode();
    result = 31 * result + eventValue.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "(" + eventType + ": " + eventValue + ")";
  }
}
