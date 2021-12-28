package ch.addere.osv.impl.fields.affected.ranges.events;

import static java.lang.String.format;

import ch.addere.osv.fields.affected.ranges.Event;
import de.skuzzle.semantic.Version;
import de.skuzzle.semantic.Version.VersionFormatException;
import java.util.Objects;

/**
 * Semantic version event to be used in ranges.
 */
public final class SemVerEventValues implements Event {

  private final EventSpecifierValue event;
  private final Version semVer;

  private SemVerEventValues(EventSpecifierValue event, String semVer) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(semVer, "argument semVer must not be null");
    this.event = event;
    try {
      this.semVer = Version.parseVersion(semVer);
    } catch (VersionFormatException e) {
      throw new IllegalArgumentException(format("invalid semantic version: '%s'", semVer), e);
    }
  }

  public static SemVerEventValues of(EventSpecifierValue event, String semVer) {
    return new SemVerEventValues(event, semVer);
  }

  @Override
  public EventSpecifierValue event() {
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
    SemVerEventValues that = (SemVerEventValues) o;
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
