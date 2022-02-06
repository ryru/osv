package ch.addere.osvs.lib.field.types;

import ch.addere.osvs.lib.impl.EventFixedImpl;
import ch.addere.osvs.lib.impl.EventIntroducedImpl;
import ch.addere.osvs.lib.impl.EventLimitImpl;

/** Event entry. */
public interface EventsEntry {

  /**
   * Type of this event.
   *
   * @return type of this event.
   */
  EventType eventTyp();

  /**
   * Value of this event.
   *
   * @return value of this event.
   */
  String eventValue();

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param eventType type of this event
   * @param eventValue value of this event
   * @return valid {@code EventsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static EventsEntry of(EventType eventType, String eventValue) {
    switch (eventType) {
      case FIXED:
        return EventFixedImpl.of(eventValue);
      case INTRODUCED:
        return EventIntroducedImpl.of(eventValue);
      case LIMIT:
        return EventLimitImpl.of(eventValue);
      default:
        throw new IllegalStateException("invalid eventType");
    }
  }
}
