package ch.addere.osv.impl.fields.affected.ranges.events;

import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class SemVerEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testIntroducedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.limited, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifier.limited);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    Event otherSemVerEvent = semVerEvent;
    assertThat(semVerEvent).isEqualTo(otherSemVerEvent);
  }

  @Test
  void testEquality() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent).isEqualTo(otherSemVerEvent);
  }

  @Test
  void testNonEquality() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifier.limited, VERSION);
    assertThat(semVerEvent).isNotEqualTo(otherSemVerEvent);
  }

  @Test
  void testHashCode() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent.hashCode()).isEqualTo(otherSemVerEvent.hashCode());
  }

  @Test
  void testToString() {
    Event semVerEvent = SemVerEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(semVerEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + semVerEvent.event() + ", " + semVerEvent.release());
  }
}
