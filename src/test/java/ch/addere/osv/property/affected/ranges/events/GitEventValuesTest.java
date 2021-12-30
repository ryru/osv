package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.FIXED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.property.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class GitEventValuesTest {

  private static final String VERSION = "aVersion";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> GitEventValues.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> GitEventValues.of(INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument gitCommit must not be null");
  }

  @Test
  void testNonIntroducedVersionZero() {
    assertThatThrownBy(() -> GitEventValues.of(FIXED, "0"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid version, must not be 0 with non 'introduced' events");
  }

  @Test
  void testNonLimitedVersionAsterisk() {
    assertThatThrownBy(() -> GitEventValues.of(FIXED, "*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid version, must not be * with non 'limited' events");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = GitEventValues.of(INTRODUCED, VERSION);
    Event event2 = GitEventValues.of(event1.event(), event1.version());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event gitEvent = GitEventValues.of(INTRODUCED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(INTRODUCED);
      assertThat(git.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event gitEvent = GitEventValues.of(FIXED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(FIXED);
      assertThat(git.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event gitEvent = GitEventValues.of(LIMITED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(LIMITED);
      assertThat(git.version()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event gitEvent = GitEventValues.of(INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(INTRODUCED, VERSION);
    assertThat(gitEvent).satisfies(g -> {
      assertThat(g).isEqualTo(gitEvent);
      assertThat(g).isEqualTo(otherGitEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event gitEvent = GitEventValues.of(INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(LIMITED, VERSION);
    assertThat(gitEvent).satisfies(g -> {
      assertThat(g).isNotEqualTo(null);
      assertThat(g).isNotEqualTo(otherGitEvent);
    });
  }

  @Test
  void testHashCode() {
    Event gitEvent = GitEventValues.of(INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(INTRODUCED, VERSION);
    assertThat(gitEvent).hasSameHashCodeAs(otherGitEvent);
  }

  @Test
  void testToString() {
    Event gitEvent = GitEventValues.of(INTRODUCED, VERSION);
    assertThat(gitEvent)
        .hasToString(EVENTS_KEY + ": " + gitEvent.event() + ", " + gitEvent.version());
  }
}
