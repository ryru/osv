package ch.addere.osv.impl.fields.affected.ranges.events;

import ch.addere.osv.fields.affected.ranges.Event;
import java.util.Objects;

/**
 * Git event to be used in ranges.
 */
public final class GitEvent implements Event {

  private final EventSpecifierImpl event;
  private final String gitCommit;

  private GitEvent(EventSpecifierImpl event, String gitCommit) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(gitCommit, "argument gitCommit must not be null");
    this.event = event;
    this.gitCommit = gitCommit;
  }

  public static GitEvent of(EventSpecifierImpl event, String gitCommit) {
    return new GitEvent(event, gitCommit);
  }

  @Override
  public EventSpecifierImpl event() {
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
    return event == that.event && Objects.equals(gitCommit, that.gitCommit);
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
