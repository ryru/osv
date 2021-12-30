package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.FIXED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;
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
    assertThatThrownBy(() -> EcosystemEventValues.of(INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testNonIntroducedVersionZero() {
    assertThatThrownBy(() -> EcosystemEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid version, must not be 0 with non 'introduced' events");
  }

  @Test
  void testNonLimitedVersionAsterisk() {
    assertThatThrownBy(() -> EcosystemEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid version, must not be * with non 'limited' events");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = EcosystemEventValues.of(INTRODUCED, VERSION);
    Event event2 = EcosystemEventValues.of(event1.event(), event1.version());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event ecoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(INTRODUCED);
      assertThat(eco.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event ecoEvent = EcosystemEventValues.of(FIXED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(FIXED);
      assertThat(eco.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event ecoEvent = EcosystemEventValues.of(LIMITED, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(LIMITED);
      assertThat(eco.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event ecoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    assertThat(ecoEvent).satisfies(e -> {
      assertThat(e).isEqualTo(ecoEvent);
      assertThat(e).isEqualTo(otherEcoEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event ecoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(LIMITED, VERSION);
    assertThat(ecoEvent).satisfies(e -> {
      assertThat(e).isNotEqualTo(null);
      assertThat(e).isNotEqualTo(otherEcoEvent);
    });
  }

  @Test
  void testHashCode() {
    Event ecoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    Event otherEcoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    assertThat(ecoEvent).hasSameHashCodeAs(otherEcoEvent);
  }

  @Test
  void testToString() {
    Event ecoEvent = EcosystemEventValues.of(INTRODUCED, VERSION);
    assertThat(ecoEvent)
        .hasToString(EVENTS_KEY + ": " + ecoEvent.event() + ", " + ecoEvent.version());
  }
}
