package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.FIXED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.property.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class SemVerEventValuesTest {

  private static final String VERSION = "1.0.0";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> SemVerEventValues.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> SemVerEventValues.of(INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument semVer must not be null");
  }

  @Test
  void testOfInvalidLeadingV() {
    assertThatThrownBy(() -> SemVerEventValues.of(INTRODUCED, "v1.0.0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version, must not start with 'v'");
  }

  @Test
  void testInvalidSemVer() {
    assertThatThrownBy(() -> SemVerEventValues.of(
        FIXED, "not-a-semantic-version"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version: 'not-a-semantic-version'");
  }

  @Test
  void testNonIntroducedVersionZero() {
    assertThatThrownBy(() -> SemVerEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "invalid semantic version, must not be 0 with non 'introduced' events");
  }

  @Test
  void testNonLimitedVersionAsterisk() {
    assertThatThrownBy(() -> SemVerEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "invalid semantic version, must not be * with non 'limited' events");
  }

  @Test
  void testValidVersionZero() {
    Event event = SemVerEventValues.of(INTRODUCED, "0");
    assertThat(event.version()).isEqualTo("0");
  }

  @Test
  void testValidVersionAsterisk() {
    Event event = SemVerEventValues.of(LIMITED, "*");
    assertThat(event.version()).isEqualTo("*");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = SemVerEventValues.of(INTRODUCED, VERSION);
    Event event2 = SemVerEventValues.of(event1.event(), event1.version());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event semVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(INTRODUCED);
      assertThat(semVer.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event semVerEvent = SemVerEventValues.of(FIXED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(FIXED);
      assertThat(semVer.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event semVerEvent = SemVerEventValues.of(LIMITED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(LIMITED);
      assertThat(semVer.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event semVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    assertThat(semVerEvent).satisfies(s -> {
      assertThat(s).isEqualTo(semVerEvent);
      assertThat(s).isEqualTo(otherSemVerEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event semVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(LIMITED, VERSION);
    assertThat(semVerEvent).satisfies(s -> {
      assertThat(s).isNotEqualTo(null);
      assertThat(s).isNotEqualTo(otherSemVerEvent);
    });
  }

  @Test
  void testHashCode() {
    Event semVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    assertThat(semVerEvent).hasSameHashCodeAs(otherSemVerEvent);
  }

  @Test
  void testToString() {
    Event semVerEvent = SemVerEventValues.of(INTRODUCED, VERSION);
    assertThat(semVerEvent)
        .hasToString(EVENTS_KEY + ": " + semVerEvent.event() + ", " + semVerEvent.version());
  }
}
