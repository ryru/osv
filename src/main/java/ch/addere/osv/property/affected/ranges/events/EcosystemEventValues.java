package ch.addere.osv.property.affected.ranges.events;

import ch.addere.osv.property.affected.ranges.Event;
import java.util.Objects;

/**
 * Ecosystem event to be used in ranges.
 */
public final class EcosystemEventValues implements Event {

  private final EventSpecifierValue event;
  private final String version;

  private EcosystemEventValues(EventSpecifierValue event, String version) {
    Objects.requireNonNull(event, "argument event must not be null");
    Objects.requireNonNull(version, "argument version must not be null");
    this.event = event;
    this.version = version;
  }

  public static EcosystemEventValues of(EventSpecifierValue event, String version) {
    return new EcosystemEventValues(event, version);
  }

  @Override
  public EventSpecifierValue event() {
    return event;
  }

  @Override
  public String release() {
    return version;
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
}
