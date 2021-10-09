package ch.addere.osv.domain.model.fields.affected.ranges.events;

import ch.addere.osv.domain.model.fields.affected.ranges.Event;
import java.util.Objects;

/**
 * Ecosystem event to be used in ranges.
 */
public final class EcosystemEvent implements Event {

  private final EventSpecifier event;
  private final String version;

  private EcosystemEvent(EventSpecifier event, String version) {
    this.event = event;
    this.version = version;
  }

  public static EcosystemEvent of(EventSpecifier event, String version) {
    return new EcosystemEvent(event, version);
  }

  @Override
  public EventSpecifier event() {
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
    EcosystemEvent that = (EcosystemEvent) o;
    return event == that.event && Objects.equals(version, that.version);
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
