package ch.addere.osv.impl.fields.affected.ranges.events;

/**
 * An event specifier defines the either introduces, fixed or limited.
 */
public enum EventSpecifier {
  introduced("introduced"),
  fixed("fixed"),
  limited("limited");

  private final String eventSpecifier;

  EventSpecifier(String eventSpecifier) {
    this.eventSpecifier = eventSpecifier;
  }

  @Override
  public String toString() {
    return eventSpecifier;
  }
}
