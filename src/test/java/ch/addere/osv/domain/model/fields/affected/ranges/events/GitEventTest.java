package ch.addere.osv.domain.model.fields.affected.ranges.events;

import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class GitEventTest {

  private static final String VERSION = "aVersion";

  @Test
  void testIntroducedEvent() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.introduced);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testFixedEvent() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.fixed, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.fixed);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testLimitedEvent() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.limited, VERSION);
    assertThat(gitEvent).satisfies(git -> {
      assertThat(git.event()).isEqualTo(EventSpecifier.limited);
      assertThat(git.release()).isEqualTo(VERSION);
    });
  }

  @Test
  void testSameness() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    GitEvent otherGitEvent = gitEvent;
    assertThat(gitEvent).isEqualTo(otherGitEvent);
  }

  @Test
  void testEquality() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    GitEvent otherGitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent).isEqualTo(otherGitEvent);
  }

  @Test
  void testNonEquality() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    GitEvent otherGitEvent = GitEvent.of(EventSpecifier.limited, VERSION);
    assertThat(gitEvent).isNotEqualTo(otherGitEvent);
  }

  @Test
  void testHashCode() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    GitEvent otherGitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent.hashCode()).isEqualTo(otherGitEvent.hashCode());
  }

  @Test
  void testToString() {
    GitEvent gitEvent = GitEvent.of(EventSpecifier.introduced, VERSION);
    assertThat(gitEvent.toString()).isEqualTo(
        EVENTS_KEY + ": " + gitEvent.event() + ", " + gitEvent.release());
  }
}
