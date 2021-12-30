package ch.addere.osv.property.affected.ranges.events;

import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.INTRODUCED;
import static ch.addere.osv.property.affected.ranges.events.EventSpecifierValue.LIMITED;

import ch.addere.osv.property.affected.ranges.Event;
import java.util.Objects;

/**
 * Ecosystem event to be used in ranges.
 */
public final class EcosystemEventValues implements Event {

  private final EventSpecifierValue event;
  private final String version;

  private EcosystemEventValues(EventSpecifierValue event, String version) {
    this.event = event;
    this.version = version;
  }

  /**
   * Create an EcosystemEventValue.
   *
   * @param event   the EventSpecifierValue of this event
   * @param version the version that is affected
   * @return a valid EcosystemEventValue
   */
  public static EcosystemEventValues of(EventSpecifierValue event, String version) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(version, "argument version must not be null");

    if (isInvalidVersionZero(event, version)) {
      throw new IllegalArgumentException(
          "invalid version, must not be 0 with non 'introduced' events");
    }

    if (isInvalidVersionInfinity(event, version)) {
      throw new IllegalArgumentException(
          "invalid version, must not be * with non 'limited' events");
    }

    return new EcosystemEventValues(event, version);
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
    EcosystemEventValues that = (EcosystemEventValues) o;
    return event.equals(that.event) && Objects.equals(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(event, version);
  }

  @Override
  public String toString() {
    return EVENTS_KEY + ": " + event + ", " + version;
  }

  private static boolean isInvalidVersionInfinity(EventSpecifierValue event, String semVer) {
    return event != LIMITED && "*".equals(semVer);
  }

  @Override
  public String version() {
    return version;
  }
}
