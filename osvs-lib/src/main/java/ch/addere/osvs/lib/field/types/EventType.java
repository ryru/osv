package ch.addere.osvs.lib.field.types;

/**
 * The values of “introduced”, “fixed” and “limit” are version strings as defined by the {@link
 * ch.addere.osvs.lib.field.types.RangeType} field.
 */
public enum EventType {

  /** Fixes a vulnerability. */
  FIXED("fixed"),

  /** Introduces a vulnerability. */
  INTRODUCED("introduced"),

  /** Sets an upper limit on the range being described. */
  LIMIT("limit");

  private final String type;

  EventType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
