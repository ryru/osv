package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventType;
import ch.addere.osvs.lib.validation.Validation;

import java.util.function.Predicate;

public final class EventsFieldValidation extends Validation<EventsFieldImpl> {

  public EventsFieldValidation(EventsFieldImpl value) {
    super(value);

    this.addRuleForValidation(
        hasAtLeastOneEventTypeIntroduced(), "events must at least contain one \"INTRODUCED\" type");
  }

  private Predicate<EventsFieldImpl> hasAtLeastOneEventTypeIntroduced() {
    return events ->
        events.getValue().stream().anyMatch(event -> event.eventTyp() == EventType.INTRODUCED);
  }
}
