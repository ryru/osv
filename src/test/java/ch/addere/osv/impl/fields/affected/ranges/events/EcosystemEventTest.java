package ch.addere.osv.impl.fields.affected.ranges.events;

import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class EcosystemEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> EcosystemEvent.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> EcosystemEvent.of(EventSpecifier.introduced, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument version must not be null");
  }

  @Test
  void testIntroducedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.limited, VERSION);
    assertThat(ecoEvent).satisfies(eco -> {
      assertThat(eco.event()).isEqualTo(EventSpecifier.limited);
      assertThat(eco.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    Event otherEcoEvent = ecoEvent;
    assertThat(ecoEvent).isEqualTo(otherEcoEvent);
  }

  @Test
  void testEquality() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent).isEqualTo(otherEcoEvent);
  }

  @Test
  void testNonEquality() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifier.limited, VERSION);
    assertThat(ecoEvent).isNotEqualTo(otherEcoEvent);
  }

  @Test
  void testHashCode() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    Event otherEcoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent.hashCode()).isEqualTo(otherEcoEvent.hashCode());
  }

  @Test
  void testToString() {
    Event ecoEvent = EcosystemEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(ecoEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + ecoEvent.event() + ", " + ecoEvent.release());
  }
}
