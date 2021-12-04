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
    assertThatThrownBy(() -> GitEvent.of(EventSpecifier.introduced, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument gitCommit must not be null");
  }

  @Test
  void testIntroducedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    Event gitEvent = GitEvent.of(EventSpecifier.limited, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.limited);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    Event otherGitEvent = gitEvent;
    assertThat(gitEvent).isEqualTo(otherGitEvent);
  }

  @Test
  void testEquality() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent).isEqualTo(otherGitEvent);
  }

  @Test
  void testNonEquality() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifier.limited, VERSION);
    assertThat(gitEvent).isNotEqualTo(otherGitEvent);
  }

  @Test
  void testHashCode() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    Event otherGitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent.hashCode()).isEqualTo(otherGitEvent.hashCode());
  }

  @Test
  void testToString() {
    Event gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + gitEvent.event() + ", " + gitEvent.release());
  }
}
