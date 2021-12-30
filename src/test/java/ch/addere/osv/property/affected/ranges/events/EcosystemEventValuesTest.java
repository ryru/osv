package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.property.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class EcosystemEventValuesTest {

  private static final String VERSION = "aVersion";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> EcosystemEventValues.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event event2 = EcosystemEventValues.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierValue.INTRODUCED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.FIXED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierValue.FIXED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifierValue.LIMITED);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(ecoEvent).satisfies(e -> {
      assertThat(e).isEqualTo(ecoEvent);
      assertThat(e).isEqualTo(otherEcoEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(ecoEvent).satisfies(e -> {
      assertThat(e).isNotEqualTo(null);
      assertThat(e).isNotEqualTo(otherEcoEvent);
    });
  }

  @Test
  void testHashCode() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(ecoEvent).hasSameHashCodeAs(otherEcoEvent);
  }

  @Test
  void testToString() {
    Event ecoEvent = EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(ecoEvent)
        .hasToString(EVENTS_KEY + ": " + ecoEvent.event() + ", " + ecoEvent.release());
  }
}
