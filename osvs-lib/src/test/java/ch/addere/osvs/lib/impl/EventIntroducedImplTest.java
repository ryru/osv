package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.types.EventType;
import ch.addere.osvs.lib.field.types.EventsEntry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventIntroducedImplTest {

  private static final String EVENT_1 = "an event";
  private static final String EVENT_2 = "another event";

  @Test
  void testOfEventIntroducedImplNull() {
    assertThatThrownBy(() -> EventIntroducedImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument eventValue must not be null");
  }

  @Test
  void testKeyValue() {
    EventsEntry event = EventIntroducedImpl.of(EVENT_1);
    assertThat(event)
        .satisfies(
            e -> {
              assertThat(e.eventTyp()).isEqualTo(EventType.INTRODUCED);
              assertThat(e.eventValue()).isEqualTo(EVENT_1);
            });
  }

  @Test
  void testEquality() {
    EventsEntry event1 = EventIntroducedImpl.of(EVENT_1);
    EventsEntry event2 = EventIntroducedImpl.of(EVENT_1);
    assertThat(event1)
        .satisfies(
            e -> {
              assertThat(e).isEqualTo(event1);
              assertThat(e).isEqualTo(event2);
            });
  }

  @Test
  void testNonEquality() {
    EventsEntry event1 = EventIntroducedImpl.of(EVENT_1);
    EventsEntry event2 = EventIntroducedImpl.of(EVENT_2);
    assertThat(event1)
        .satisfies(
            e -> {
              assertThat(e).isNotEqualTo(null);
              assertThat(e).isNotEqualTo(event2);
            });
  }

  @Test
  void testHashCode() {
    EventsEntry event1 = EventIntroducedImpl.of(EVENT_1);
    EventsEntry event2 = EventIntroducedImpl.of(EVENT_1);
    assertThat(event1).hasSameHashCodeAs(event2);
  }

  @Test
  void testToString() {
    EventsEntry event1 = EventIntroducedImpl.of(EVENT_1);
    assertThat(event1).hasToString("(introduced: an event)");
  }
}
