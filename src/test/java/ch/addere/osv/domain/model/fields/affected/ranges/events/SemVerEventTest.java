package ch.addere.osv.domain.model.fields.affected.ranges.events;

import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class SemVerEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testIntroducedEvent() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.limited, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.limited);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    SemVerEvent otherSemVerEvent = semVerEvent;
    assertThat(semVerEvent).isEqualTo(otherSemVerEvent);
  }

  @Test
  void testEquality() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    SemVerEvent otherSemVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent).isEqualTo(otherSemVerEvent);
  }

  @Test
  void testNonEquality() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    SemVerEvent otherSemVerEvent = SemVerEvent.of(EventSpecifier.limited, VERSION);
    assertThat(semVerEvent).isNotEqualTo(otherSemVerEvent);
  }

  @Test
  void testHashCode() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    SemVerEvent otherSemVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent.hashCode()).isEqualTo(otherSemVerEvent.hashCode());
  }

  @Test
  void testToString() {
    SemVerEvent semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + semVerEvent.event() + ", " + semVerEvent.release());
  }
}
