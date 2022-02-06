package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.EventsEntry;
import ch.addere.osvs.lib.impl.EventsFieldImpl;

import java.util.List;

/**
 * The {@code events} field gives a list of events. Each object describes a single version and
 * represents a “timeline” of status changes for the affected package. There must be at least one
 * {@code introduced} object in the events array.
 */
public interface EventsField extends EntryField<List<EventsEntry>> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param events a timeline of status changes for the affected package
   * @return valid {@code EventsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static EventsField of(EventsEntry... events) {
    return EventsFieldImpl.of(events);
  }
}
