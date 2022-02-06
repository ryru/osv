package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventsEntry;

import static ch.addere.osvs.lib.field.types.EventType.INTRODUCED;

public final class EventIntroducedImpl extends EventImpl {

  private EventIntroducedImpl(String eventValue) {
    super(INTRODUCED, eventValue);
  }

  public static EventsEntry of(String eventValue) {
    return new EventIntroducedImpl(eventValue);
  }
}
