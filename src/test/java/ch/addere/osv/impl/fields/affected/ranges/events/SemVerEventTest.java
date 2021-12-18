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
    assertThatThrownBy(() -> SemVerEvent.of(EventSpecifierImpl.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument semVer must not be null");
  }

  @Test
  void testInvalidSemVer() {
    assertThatThrownBy(() -> SemVerEvent.of(EventSpecifierImpl.FIXED, "not-a-semantic-version"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid semantic version: 'not-a-semantic-version'");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event event2 = SemVerEvent.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierImpl.INTRODUCED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.FIXED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierImpl.FIXED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(semVerEvent).satisfies(semVer -> {
      assertThat(semVer.event()).isEqualTo(EventSpecifierImpl.LIMITED);
      assertThat(semVer.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(semVerEvent).isEqualTo(otherSemVerEvent);
  }

  @Test
  void testNonEquality() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(semVerEvent).isNotEqualTo(otherSemVerEvent);
  }

  @Test
  void testHashCode() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherSemVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(semVerEvent).hasSameHashCodeAs(otherSemVerEvent);
  }

  @Test
  void testToString() {
    Event semVerEvent = SemVerEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(semVerEvent)
        .hasToString(EVENTS_KEY + ": " + semVerEvent.event() + ", " + semVerEvent.release());
  }
}
