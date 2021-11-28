package ch.addere.osv.impl.fields.affected.ranges.events;

import ch.addere.osv.fields.affected.ranges.Event;
import java.util.Objects;

/**
 * Semantic version event to be used in ranges.
 */
public final class SemVerEvent implements Event {

  private final EventSpecifier event;
  // TODO this should be of type Semantic Version
  private final String semVer;

  private SemVerEvent(EventSpecifier event, String semVer) {
    this.event = event;
    this.semVer = semVer;
  }

  public static SemVerEvent of(EventSpecifier event, String semVer) {
    return new SemVerEvent(event, semVer);
  }

  @Override
  public EventSpecifier event() {
    return event;
  }

  @Override
  public String release() {
    return semVer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SemVerEvent that = (SemVerEvent) o;
    return event == that.event && Objects.equals(semVer, that.semVer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(event, semVer);
  }

  @Override
  public String toString() {
    return EVENTS_KEY + ": " + event + ", " + semVer;
  }
}
