package ch.addere.osv.domain.model.fields.affected.ranges.events;

import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class EcosystemEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testIntroducedEvent() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.limited, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.limited);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    EcosystemEvent otherEcoEvent = ecoEvent;
    assertThat(ecoEvent).isEqualTo(otherEcoEvent);
  }

  @Test
  void testEquality() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    EcosystemEvent otherEcoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent).isEqualTo(otherEcoEvent);
  }

  @Test
  void testNonEquality() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    EcosystemEvent otherEcoEvent = EcosystemEvent.of(EventSpecifier.limited, VERSION);
    assertThat(ecoEvent).isNotEqualTo(otherEcoEvent);
  }

  @Test
  void testHashCode() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    EcosystemEvent otherEcoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent.hashCode()).isEqualTo(otherEcoEvent.hashCode());
  }

  @Test
  void testToString() {
    EcosystemEvent ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + ecoEvent.event() + ", " + ecoEvent.release());
  }
}
