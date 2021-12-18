package ch.addere.osv.impl.fields.affected.ranges.events;

import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class EcosystemImplEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> EcosystemEvent.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event event2 = EcosystemEvent.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierImpl.INTRODUCED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.FIXED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierImpl.FIXED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierImpl.LIMITED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(ecoEvent).isEqualTo(otherEcoEvent);
  }

  @Test
  void testNonEquality() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(ecoEvent).isNotEqualTo(otherEcoEvent);
  }

  @Test
  void testHashCode() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(ecoEvent).hasSameHashCodeAs(otherEcoEvent);
  }

  @Test
  void testToString() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(ecoEvent)
        .hasToString(EVENTS_KEY + ": " + ecoEvent.event() + ", " + ecoEvent.release());
  }
}
