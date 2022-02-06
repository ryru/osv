package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.types.EventType;
import ch.addere.osvs.lib.field.types.EventsEntry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventsFieldImplTest {

  private static final EventsEntry EVENT_1 = EventsEntry.of(EventType.INTRODUCED, "an event");
  private static final EventsEntry EVENT_2 = EventsEntry.of(EventType.INTRODUCED, "another event");
  private static final EventsEntry EVENT_3 = EventsEntry.of(EventType.FIXED, "yet another event");

  @Test
  void testOfEventsNull() {
    assertThatThrownBy(() -> EventsField.of((EventsEntry[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument events must not be null");
  }

  @Test
  void testRequireOneIntroducedEventType() {
    assertThatThrownBy(() -> EventsField.of(EVENT_3))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must at least contain one \"INTRODUCED\" type");
  }

  @Test
  void testKeyValue() {
    EventsField event = EventsField.of(EVENT_1, EVENT_2);
    assertThat(event)
        .satisfies(
            e -> {
              assertThat(e.getKey()).isEqualTo("events");
              assertThat(e.getValue()).isEqualTo(List.of(EVENT_1, EVENT_2));
            });
  }

  @Test
  void testEquality() {
    EventsField event1 = EventsField.of(EVENT_1);
    EventsField event2 = EventsField.of(EVENT_1);
    assertThat(event1)
        .satisfies(
            e -> {
              assertThat(e).isEqualTo(event1);
              assertThat(e).isEqualTo(event2);
            });
  }

  @Test
  void testNonEquality() {
    EventsField event1 = EventsField.of(EVENT_1);
    EventsField event2 = EventsField.of(EVENT_2);
    assertThat(event1)
        .satisfies(
            e -> {
              assertThat(e).isNotEqualTo(null);
              assertThat(e).isNotEqualTo(event2);
            });
  }

  @Test
  void testHashCode() {
    EventsField event1 = EventsField.of(EVENT_1);
    EventsField event2 = EventsField.of(EVENT_1);
    assertThat(event1).hasSameHashCodeAs(event2);
  }

  @Test
  void testToString() {
    EventsField event1 = EventsField.of(EVENT_1);
    assertThat(event1).hasToString("events: [ (introduced: an event) ]");
  }
}
