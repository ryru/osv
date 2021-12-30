package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
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
    assertThatThrownBy(() -> SemVerEventValues.of(EventSpecifierValue.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument semVer must not be null");
  }

  @Test
  void testInvalidSemVer() {
    assertThatThrownBy(() -> SemVerEventValues.of(
        EventSpecifierValue.FIXED, "not-a-semantic-version"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version: 'not-a-semantic-version'");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event event2 = SemVerEventValues.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierValue.INTRODUCED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.FIXED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierValue.FIXED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierValue.LIMITED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(semVerEvent).satisfies(s -> {
      assertThat(s).isEqualTo(semVerEvent);
      assertThat(s).isEqualTo(otherSemVerEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(semVerEvent).satisfies(s -> {
      assertThat(s).isNotEqualTo(null);
      assertThat(s).isNotEqualTo(otherSemVerEvent);
    });
  }

  @Test
  void testHashCode() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(semVerEvent).hasSameHashCodeAs(otherSemVerEvent);
  }

  @Test
  void testToString() {
    Event semVerEvent = SemVerEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(semVerEvent)
        .hasToString(EVENTS_KEY + ": " + semVerEvent.event() + ", " + semVerEvent.release());
  }
}
