package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;

import ch.addere.osv.property.affected.ranges.Event;
import java.util.Objects;

/**
 * Git event to be used in ranges.
 */
public final class GitEventValues implements Event {

  private final EventSpecifierValue event;
  private final String gitCommit;

  private GitEventValues(EventSpecifierValue event, String gitCommit) {
    this.event = event;
    this.gitCommit = gitCommit;
  }

  /**
   * Create a GitEventValue.
   *
   * @param event     the EventSpecifierValue of this event
   * @param gitCommit the version that is affected
   * @return a valid GitEventValue
   */
  public static GitEventValues of(EventSpecifierValue event, String gitCommit) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(gitCommit, "argument gitCommit must not be null");

    if (isInvalidVersionZero(event, gitCommit)) {
      throw new IllegalArgumentException(
          "invalid version, must not be 0 with non 'introduced' events");
    }

    if (isInvalidVersionInfinity(event, gitCommit)) {
      throw new IllegalArgumentException(
          "invalid version, must not be * with non 'limited' events");
    }

    return new GitEventValues(event, gitCommit);
  }

  @Override
  public EventSpecifierValue event() {
    return event;
  }

  private static boolean isInvalidVersionZero(EventSpecifierValue event, String semVer) {
    return event != INTRODUCED && "0".equals(semVer);
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

  private static boolean isInvalidVersionInfinity(EventSpecifierValue event, String semVer) {
    return event != LIMITED && "*".equals(semVer);
  }

  @Override
  public String version() {
    return gitCommit;
  }
}
