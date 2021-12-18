package ch.addere.osv.impl.fields.affected.ranges.events;

import static java.lang.String.format;

import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.events.EventSpecifier;
import de.skuzzle.semantic.Version;
import de.skuzzle.semantic.Version.VersionFormatException;
import java.util.Objects;

/**
 * Semantic version event to be used in ranges.
 */
public final class SemVerEvent implements Event {

  private final EventSpecifier event;
  private final Version semVer;

  private SemVerEvent(EventSpecifier event, String semVer) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(semVer, "argument semVer must not be null");
    this.event = event;
    try {
      this.semVer = Version.parseVersion(semVer);
    } catch (VersionFormatException e) {
      throw new IllegalArgumentException(format("invalid semantic version: '%s'", semVer), e);
    }
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
    return semVer.toString();
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
    return event.equals(that.event) && Objects.equals(semVer, that.semVer);
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
