package ch.addere.osv.impl.fields.affected.ranges.events;

import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.Event;
import org.junit.jupiter.api.Test;

class GitEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testOfEventNull() {
    assertThatThrownBy(() -> GitEvent.of(null, VERSION))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument event must not be null");
  }

  @Test
  void testOfVersionNull() {
    assertThatThrownBy(() -> GitEvent.of(EventSpecifierImpl.INTRODUCED, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument gitCommit must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Event event1 = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event event2 = GitEvent.of(event1.event(), event1.release());
    assertThat(event1).isEqualTo(event2);
  }

  @Test
  void testIntroducedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierImpl.INTRODUCED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.FIXED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierImpl.FIXED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifierImpl.LIMITED);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testEquality() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(gitEvent).isEqualTo(otherGitEvent);
  }

  @Test
  void testNonEquality() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifierImpl.LIMITED, VERSION);
    assertThat(gitEvent).isNotEqualTo(otherGitEvent);
  }

  @Test
  void testHashCode() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(gitEvent).hasSameHashCodeAs(otherGitEvent);
  }

  @Test
  void testToString() {
    Event gitEvent = GitEvent.of(EventSpecifierImpl.INTRODUCED, VERSION);
    assertThat(gitEvent)
        .hasToString(EVENTS_KEY + ": " + gitEvent.event() + ", " + gitEvent.release());
  }
}
