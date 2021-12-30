package ch.addere.osv.property.affected.ranges.events;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.Value;

/**
 * An event specifier defines the either introduces, fixed or limited.
 */
public enum EventSpecifierValue implements Value<String> {

  FIXED("fixed"),
  INTRODUCED("introduced"),
  LIMITED("limited");

  private final String value;

  EventSpecifierValue(String value) {
    this.value = value;
  }

  /**
   * Get EventSpecifier from event specifier string.
   *
   * @param eventSpecifier string value of an event specifier
   * @return valid EventSpecifier
   */
  public static EventSpecifierValue of(String eventSpecifier) {
    return stream(EventSpecifierValue.values())
        .filter(values -> values.value().equals(eventSpecifier))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid event specifier", eventSpecifier)));
  }

  @Override
  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return value();
  }
}
