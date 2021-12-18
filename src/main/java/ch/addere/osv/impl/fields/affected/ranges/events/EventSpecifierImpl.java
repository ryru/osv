package ch.addere.osv.impl.fields.affected.ranges.events;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.affected.ranges.events.EventSpecifier;

/**
 * An event specifier defines the either introduces, fixed or limited.
 */
public enum EventSpecifierImpl implements EventSpecifier {

  FIXED("fixed"),
  INTRODUCED("introduced"),
  LIMITED("limited");

  private final String eventSpecifier;

  EventSpecifierImpl(String eventSpecifier) {
    this.eventSpecifier = eventSpecifier;
  }

  @Override
  public String value() {
    return this.eventSpecifier;
  }

  /**
   * Get EventSpecifier from event specifier string.
   *
   * @param eventSpecifier string value of an event specifier
   * @return               valid EventSpecifier
   */
  public static EventSpecifierImpl of(String eventSpecifier) {
    return stream(EventSpecifierImpl.values())
        .filter(values -> values.value().equals(eventSpecifier))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid event specifier", eventSpecifier)));
  }

  @Override
  public String toString() {
    return value();
  }
}
