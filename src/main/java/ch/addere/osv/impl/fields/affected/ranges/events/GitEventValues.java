package ch.addere.osv.impl.fields.affected.ranges.events;

import ch.addere.osv.fields.affected.ranges.Event;
import java.util.Objects;

/**
 * Git event to be used in ranges.
 */
public final class GitEventValues implements Event {

  private final EventSpecifierValue event;
  private final String gitCommit;

  private GitEventValues(EventSpecifierValue event, String gitCommit) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(gitCommit, "argument gitCommit must not be null");
    this.event = event;
    this.gitCommit = gitCommit;
  }

  public static GitEventValues of(EventSpecifierValue event, String gitCommit) {
    return new GitEventValues(event, gitCommit);
  }

  @Override
  public EventSpecifierValue event() {
    return event;
  }

  @Override
  public String release() {
    return gitCommit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GitEventValues that = (GitEventValues) o;
    return event.equals(that.event) && Objects.equals(gitCommit, that.gitCommit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(event, gitCommit);
  }

  @Override
  public String toString() {
    return EVENTS_KEY + ": " + event + ", " + gitCommit;
  }
}
