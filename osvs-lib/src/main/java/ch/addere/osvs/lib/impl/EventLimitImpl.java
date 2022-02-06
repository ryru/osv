package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventsEntry;

import static ch.addere.osvs.lib.field.types.EventType.LIMIT;

public final class EventLimitImpl extends EventImpl {

  private EventLimitImpl(String eventValue) {
    super(LIMIT, eventValue);
  }

  public static EventsEntry of(String eventValue) {
    return new EventLimitImpl(eventValue);
  }
}
