package ch.addere.osv.impl.fields.affected.ranges.events;

import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class SemVerEventTest {

  private static final String VERSION = "1.0.0";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> SemVerEvent.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> SemVerEvent.of(EventSpecifier.introduced, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument semVer must not be null");
  }

  @Test
  void testInvalidSemVer() {
    assertThatThrownBy(() -> SemVerEvent.of(EventSpecifier.fixed, "not-a-semantic-version"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version: 'not-a-semantic-version'");
  }

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
