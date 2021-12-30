package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;
import static java.lang.String.format;

import ch.addere.osv.property.affected.ranges.Event;
import de.skuzzle.semantic.Version;
import de.skuzzle.semantic.Version.VersionFormatException;
import java.util.Objects;

/**
 * Semantic version event to be used in ranges.
 */
public final class SemVerEventValues implements Event {

  private final EventSpecifierValue event;
  private final Version semVer;
  private final String specialVersion;

  private SemVerEventValues(EventSpecifierValue event, Version semVer, String specialVersion) {
    this.event = event;
    this.semVer = semVer;
    this.specialVersion = specialVersion;
  }

  /**
   * Create a SemVerEventValue.
   *
   * @param event  the EventSpecifierValue of this event
   * @param semVer the semantic version that is affected
   * @return a valid SemVerEventValue
   */
  public static SemVerEventValues of(EventSpecifierValue event, String semVer) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(semVer, "argument semVer must not be null");

    if (semVer.startsWith("v")) {
      throw new IllegalArgumentException("invalid semantic version, must not start with 'v'");
    }

    if (isInvalidVersionZero(event, semVer)) {
      throw new IllegalArgumentException(
          "invalid semantic version, must not be 0 with non 'introduced' events");
    }
    if (isInvalidVersionInfinity(event, semVer)) {
      throw new IllegalArgumentException(
          "invalid semantic version, must not be * with non 'limited' events");
    }

    if (isValidVersionZero(event, semVer) || isValidVersionInfinity(event, semVer)) {
      return new SemVerEventValues(event, null, semVer);
    } else {
      try {
        return new SemVerEventValues(event, Version.parseVersion(semVer), null);
      } catch (VersionFormatException e) {
        throw new IllegalArgumentException(format("invalid semantic version: '%s'", semVer), e);
      }
    }
  }

  @Override
  public EventSpecifierValue event() {
    return event;
  }

  private static boolean isValidVersionZero(EventSpecifierValue event, String semVer) {
    return event == INTRODUCED && "0".equals(semVer);
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

  private static boolean isInvalidVersionZero(EventSpecifierValue event, String semVer) {
    return event != INTRODUCED && "0".equals(semVer);
  }

  private static boolean isValidVersionInfinity(EventSpecifierValue event, String semVer) {
    return event == LIMITED && "*".equals(semVer);
  }

  private static boolean isInvalidVersionInfinity(EventSpecifierValue event, String semVer) {
    return event != LIMITED && "*".equals(semVer);
  }

  @Override
  public String version() {
    if (semVer != null && specialVersion == null) {
      return semVer.toString();
    } else {
      return specialVersion;
    }
  }
}
