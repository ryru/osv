package ch.addere.osv.impl.fields.affected.ranges.events;

import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.events.EventSpecifier;
import java.util.Objects;

/**
 * Git event to be used in ranges.
 */
public final class GitEvent implements Event {

  private final EventSpecifier event;
  private final String gitCommit;

  private GitEvent(EventSpecifier event, String gitCommit) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(gitCommit, "argument gitCommit must not be null");
    this.event = event;
    this.gitCommit = gitCommit;
  }

  public static GitEvent of(EventSpecifier event, String gitCommit) {
    return new GitEvent(event, gitCommit);
  }

  @Override
  public EventSpecifier event() {
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
    GitEvent that = (GitEvent) o;
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
