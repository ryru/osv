package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventsEntry;

import static ch.addere.osvs.lib.field.types.EventType.FIXED;

public final class EventFixedImpl extends EventImpl {

  private EventFixedImpl(String eventValue) {
    super(FIXED, eventValue);
  }

  public static EventsEntry of(String eventValue) {
    return new EventFixedImpl(eventValue);
  }
}
