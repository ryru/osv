package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.types.EventsEntry;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

public final class EventsFieldImpl implements EventsField {

  private static final String JSON_KEY = "events";

  private final List<EventsEntry> events;

  private EventsFieldImpl(EventsEntry... events) {
    Objects.requireNonNull(events, "argument events must not be null");

    this.events = List.of(events);

    Validation<EventsFieldImpl> validator = new EventsFieldValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  public static EventsField of(EventsEntry... events) {
    return new EventsFieldImpl(events);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public List<EventsEntry> getValue() {
    return new LinkedList<>(events);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EventsFieldImpl that = (EventsFieldImpl) o;

    return events.equals(that.events);
  }

  @Override
  public int hashCode() {
    return events.hashCode();
  }

  @Override
  public String toString() {
    return JSON_KEY
        + ": [ "
        + getValue().stream().map(Object::toString).collect(joining(", "))
        + " ]";
  }
}
