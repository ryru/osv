package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
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
    assertThatThrownBy(() -> GitEventValues.of(EventSpecifierValue.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument gitCommit must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event event2 = GitEventValues.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierValue.INTRODUCED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.FIXED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierValue.FIXED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierValue.LIMITED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(gitEvent).satisfies(g -> {
      assertThat(g).isEqualTo(gitEvent);
      assertThat(g).isEqualTo(otherGitEvent);
    });
  }

  @Test
  void testNonEquality() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(EventSpecifierValue.LIMITED, VERSION);
    assertThat(gitEvent).satisfies(g -> {
      assertThat(g).isNotEqualTo(null);
      assertThat(g).isNotEqualTo(otherGitEvent);
    });
  }

  @Test
  void testHashCode() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    Event otherGitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(gitEvent).hasSameHashCodeAs(otherGitEvent);
  }

  @Test
  void testToString() {
    Event gitEvent = GitEventValues.of(EventSpecifierValue.INTRODUCED, VERSION);
    assertThat(gitEvent)
        .hasToString(EVENTS_KEY + ": " + gitEvent.event() + ", " + gitEvent.release());
  }
}
